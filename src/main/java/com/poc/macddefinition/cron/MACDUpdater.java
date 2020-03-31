package com.poc.macddefinition.cron;

import com.poc.macddefinition.persistence.macd.MacdCalculator;
import com.poc.macddefinition.persistence.macd.MacdEntity;
import com.poc.macddefinition.persistence.macd.MacdRepository;
import com.poc.macddefinition.persistence.chart.ChartEntity;
import com.poc.macddefinition.persistence.chart.ChartRepository;
import com.poc.macddefinition.persistence.macddefinition.MacdDefinitionEntity;
import com.poc.macddefinition.persistence.macddefinition.MacdDefinitionRepository;
import com.poc.macddefinition.persistence.ohlc.OHLCEntity;
import com.poc.macddefinition.persistence.ohlc.OHLCRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MACDUpdater {

    private static final Logger log = LoggerFactory.getLogger(MACDUpdater.class);

    private MacdDefinitionRepository macdDefinitionRepository;
    private Stack<MacdDefinitionEntity> macdDefinitionEntities;

    @Autowired
    private OHLCRepository ohlcRepository;

    @Autowired
    private MacdCalculator macdCalculator;

    @Autowired
    private MacdRepository macdRepository;

    @Autowired
    private ChartRepository chartRepository;

    public MACDUpdater(@Autowired MacdDefinitionRepository macdDefinitionRepository) {
        this.macdDefinitionRepository = macdDefinitionRepository;
        fillMacdDefinitions();
    }

    private void fillMacdDefinitions() {
        this.macdDefinitionEntities = new Stack();
        this.macdDefinitionEntities.addAll(this.macdDefinitionRepository.getAll());
    }

    @Scheduled(fixedDelay = 20_000)
    public void updateMacds() {

        if (this.macdDefinitionEntities.isEmpty()) {
            fillMacdDefinitions();
            return;
        }
        final MacdDefinitionEntity macdDef = this.macdDefinitionEntities.pop();

        final List<OHLCEntity> ohlcs = this.ohlcRepository.getAll(macdDef.getChart());
        if (ohlcs == null || ohlcs.size() == 0) {
            log.info("no OHLC data found for chart " + macdDef.getChart()
                    + ". Retrying later...");
            return;
        }
        int minRequired = macdDef.getLongPeriodEma() + macdDef.getMacdEma();
        if (ohlcs.size() < minRequired) {
            log.info("not enough OHLC data (" + ohlcs.size() + ") to calculate MACD " + macdDef.toString());
            return;
        }

        MacdEntity lastMacd = null;
        if (macdDef.getChart().getLastMACDTimeEpochTimestamp() == 0) {
            /* cas initial */
            SortedMap<Integer, MacdEntity> macds = this.macdCalculator.getInitialMacds(macdDef, ohlcs);
            this.macdRepository.create(macds.values());
            lastMacd = macds.get(macds.lastKey());
        } else {
            /* cas suivants */
            lastMacd = this.macdRepository.getLast(macdDef);
            List<OHLCEntity> ohlcEntities = this.ohlcRepository.getFrom(macdDef.getChart(), lastMacd.getTimeEpochTimestamp());
            for (OHLCEntity ohlc : ohlcEntities) {
                List<MacdEntity> lastMacds = this.macdRepository.getNLastBefore(
                        macdDef,
                        ohlc.getTimeEpochTimestamp(),
                        macdDef.getMacdEma() - 1
                );
                lastMacd = this.macdCalculator.getMacd(macdDef, ohlc, lastMacds);
                this.macdRepository.upsert(lastMacd);
            }
        }
        final ChartEntity chartEntity = this.chartRepository.get(
                macdDef.getChart().getId()
        );
        chartEntity.setLastMACDTimeEpochTimestamp(lastMacd.getTimeEpochTimestamp());
        this.chartRepository.update(chartEntity);
    }
}
