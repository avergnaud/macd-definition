/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.poc.macddefinition.cron;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Stack;
import java.util.function.Function;

import com.poc.macddefinition.persistence.chart.ChartEntity;
import com.poc.macddefinition.persistence.chart.ChartRepository;
import com.poc.macddefinition.kraken.service.KrakenService;
import com.poc.macddefinition.persistence.macddefinition.MacdDefinitionEntity;
import com.poc.macddefinition.persistence.macddefinition.MacdDefinitionRepository;
import com.poc.macddefinition.persistence.ohlc.OHLCEntity;
import com.poc.macddefinition.persistence.ohlc.OHLCRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

@Component
public class OHLCUpdater {

    private static final Logger log = LoggerFactory.getLogger(OHLCUpdater.class);

    @Autowired
    private KrakenService krakenService;

    @Autowired
    private OHLCRepository ohlcRepository;

    @Autowired
    private ChartRepository chartRepository;

    private MacdDefinitionRepository macdDefinitionRepository;
    private Stack<MacdDefinitionEntity> macdDefinitionEntities;

    public OHLCUpdater(@Autowired MacdDefinitionRepository macdDefinitionRepository) {
        this.macdDefinitionRepository = macdDefinitionRepository;
        fillMacdDefinitions();
    }

    private void fillMacdDefinitions() {
        this.macdDefinitionEntities = new Stack();
        this.macdDefinitionEntities.addAll(macdDefinitionRepository.getAll());
    }

    /**
     * call Kraken every @fixedDelay at most
     */
    @Scheduled(fixedDelay = 20_000)
    public void callKraken() {

        if(this.macdDefinitionEntities.isEmpty()) {
            fillMacdDefinitions();
            return;
        }
        final MacdDefinitionEntity macdDefinitionEntity = this.macdDefinitionEntities.pop();
        final ChartEntity chartEntity = macdDefinitionEntity.getChart();

        Function<UriBuilder, URI> uri = uriBuilder -> uriBuilder.path("/OHLC")
                .queryParam("pair", chartEntity.getTradingPair())
                .queryParam("interval", chartEntity.getTimeFrameInterval())
                .queryParam("since", chartEntity.getLastOHLCTimeEpochTimestamp())
                .build();

        Mono<JSONObject> krakenOhlcsMono = krakenService.queryPublicAPI(uri);
        krakenOhlcsMono.subscribe(krakenOHLC -> handle(macdDefinitionEntity, krakenOHLC));
    }

    private void handle(final MacdDefinitionEntity macdDefinitionEntity, JSONObject krakenOHLC) {

        /* {"error":["EAPI:Rate limit exceeded"]} */
        if(krakenOHLC.getJSONArray("error").length() != 0) {
            log.error(krakenOHLC.getJSONArray("error").toString());
            return;
        }

        JSONObject result = krakenOHLC.getJSONObject("result");
        long last = result.getLong("last");

        final ChartEntity chartEntity = this.chartRepository.get(
                macdDefinitionEntity.getChart().getId()
        );
        chartEntity.setLastOHLCTimeEpochTimestamp(last);
        this.chartRepository.update(chartEntity);

        JSONArray ohlcArray = result.getJSONArray(chartEntity.getTradingPair());
        log.info("handle " + chartEntity.toString() + " : " + ohlcArray.length());
        for(int i=0; i<ohlcArray.length(); i++) {
            JSONArray ohlc = (JSONArray)ohlcArray.get(i);
            OHLCEntity ohlcEntity = new OHLCEntity();
            ohlcEntity.setChartEntity(chartEntity);
            ohlcEntity.setTimeEpochTimestamp(ohlc.getLong(0));
            String open = ohlc.getString(1);
            ohlcEntity.setOpeningPrice(new BigDecimal(open));
            String high = ohlc.getString(2);
            ohlcEntity.setHighPrice(new BigDecimal(high));
            String low = ohlc.getString(3);
            ohlcEntity.setLowPrice(new BigDecimal(low));
            String close = ohlc.getString(4);
            ohlcEntity.setClosingPrice(new BigDecimal(close));
            ohlcRepository.upsert(ohlcEntity);
        }
    }
}