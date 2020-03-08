package com.poc.macddefinition.macd;

import com.poc.macddefinition.persistence.macddefinition.MacdDefinitionEntity;
import com.poc.macddefinition.persistence.ohlc.OHLCEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class MacdCalculator {

    public SortedMap<Integer,MacdEntity> getInitialMacds(
            final MacdDefinitionEntity macdDef,
            final List<OHLCEntity> ohlcs) {

        final BigDecimal K_FAST = BigDecimal.valueOf(2)
                .divide(
                        BigDecimal.valueOf(macdDef.getShortPeriodEma()).add(BigDecimal.ONE),
                        10,
                        RoundingMode.HALF_EVEN
                )
                .setScale(10, RoundingMode.HALF_EVEN);

        final BigDecimal K_SLOW = BigDecimal.valueOf(2)
                .divide(
                        BigDecimal.valueOf(macdDef.getLongPeriodEma()).add(BigDecimal.ONE),
                        10,
                        RoundingMode.HALF_EVEN
                )
                .setScale(10, RoundingMode.HALF_EVEN);

        SortedMap<Integer,MacdEntity> macds = new TreeMap<>();

        final int i1 = macdDef.getShortPeriodEma() - 1;
        final int i2 = macdDef.getLongPeriodEma() - 1;
        final int i3 = macdDef.getLongPeriodEma() + macdDef.getMacdEma() - 2;

        for(int index=i1; index<ohlcs.size(); index++) {

            OHLCEntity ohlc = ohlcs.get(index);
            MacdEntity macd = new MacdEntity();
            MacdEntity previous = macds.get(index - 1);
            macd.setMacdDefinitionEntity(macdDef);
            macd.setTimeEpochTimestamp(ohlc.getTimeEpochTimestamp());
            macd.setClosingPrice(ohlc.getClosingPrice());

            /** shortEmaValue */
            BigDecimal shortEmaValue = null;
            if(index == i1) {
                List<OHLCEntity> subList = ohlcs.subList(0,macdDef.getShortPeriodEma());
                BigDecimal somme = subList.stream()
                        .map(OHLCEntity::getClosingPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .setScale(10, RoundingMode.HALF_EVEN);
                shortEmaValue = somme.divide(BigDecimal.valueOf(subList.size()), 10, RoundingMode.HALF_EVEN);
            } else if(index > i1) {
                shortEmaValue = ohlc.getClosingPrice()
                        .multiply(K_FAST)
                        .add(
                                previous.getShortEmaValue().multiply(
                                        BigDecimal.ONE.subtract(K_FAST)
                                )
                        )
                        .setScale(10, RoundingMode.HALF_EVEN);
            }
            macd.setShortEmaValue(shortEmaValue);

            /** longEmaValue */
            BigDecimal longEmaValue = null;
            if(index == i2) {
                List<OHLCEntity> subList = ohlcs.subList(0,macdDef.getLongPeriodEma());
                BigDecimal somme = subList.stream()
                        .map(OHLCEntity::getClosingPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .setScale(10, RoundingMode.HALF_EVEN);
                longEmaValue = somme.divide(BigDecimal.valueOf(subList.size()), 10, RoundingMode.HALF_EVEN);
            } else if(index > i2) {
                longEmaValue = ohlc.getClosingPrice()
                        .multiply(K_SLOW)
                        .add(
                                previous.getLongEmaValue().multiply(
                                        BigDecimal.ONE.subtract(K_SLOW)
                                )
                        )
                        .setScale(10, RoundingMode.HALF_EVEN);
            }
            macd.setLongEmaValue(longEmaValue);

            /** macdValue */
            if(index >= i2) {
                macd.setMacdValue(shortEmaValue.subtract(longEmaValue));
            }

            /** signalValue */
            if(index >= i3) {
                List<MacdEntity> subList = new ArrayList(
                        macds.subMap(index - macdDef.getMacdEma() + 1, index + 1).values()
                );
                BigDecimal somme = subList.stream()
                        .map(MacdEntity::getMacdValue)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .setScale(10, RoundingMode.HALF_EVEN);
                BigDecimal signalValue = somme.divide(BigDecimal.valueOf(subList.size()), 10, RoundingMode.HALF_EVEN);
                macd.setSignalValue(signalValue);
            }

            macds.put(index, macd);
        }

        return macds;

    }
}
