package com.poc.macddefinition.persistence.macd;

import com.poc.macddefinition.persistence.macddefinition.MacdDefinitionEntity;
import com.poc.macddefinition.persistence.ohlc.OHLCEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;

public class MacdCalculator {

    public MacdEntity getMacd(
            final MacdDefinitionEntity macdDef,
            OHLCEntity ohlc,
            List<MacdEntity> lastMacds
    ) {

        long epochTimeStamp = ohlc.getTimeEpochTimestamp();
        MacdEntity lastMacd = lastMacds.get(lastMacds.size() - 1);
        MacdEntity previous = lastMacds.get(lastMacds.size() - 2);

        if(lastMacds.size() != macdDef.getMacdEma() - 1) {
            throw new RuntimeException("lastMacds size different from macdDef Ema minus 1: "
             + lastMacds.size() + " != " + macdDef.getMacdEma());
        }

        final BigDecimal K_FAST = this.getKFast(macdDef);
        final BigDecimal K_SLOW = this.getKSlow(macdDef);

        MacdEntity macd = new MacdEntity();
        macd.setMacdDefinition(macdDef);
        macd.setTimeEpochTimestamp(epochTimeStamp);
        macd.setClosingPrice(ohlc.getClosingPrice());
        /** shortEmaValue */
        BigDecimal shortEmaValue = ohlc.getClosingPrice()
                .multiply(K_FAST)
                .add(
                        previous.getShortEmaValue().multiply(
                                BigDecimal.ONE.subtract(K_FAST)
                        )
                )
                .setScale(10, RoundingMode.HALF_EVEN);
        macd.setShortEmaValue(shortEmaValue);
        /** longEmaValue */
        BigDecimal longEmaValue = ohlc.getClosingPrice()
                .multiply(K_SLOW)
                .add(
                        previous.getLongEmaValue().multiply(
                                BigDecimal.ONE.subtract(K_SLOW)
                        )
                )
                .setScale(10, RoundingMode.HALF_EVEN);
        macd.setLongEmaValue(longEmaValue);
        /** macdValue */
        macd.setMacdValue(shortEmaValue.subtract(longEmaValue));
        /** signalValue. La moyenne c'est la moyenne des 9 derniers, en incluant celui-là */
        lastMacds.add(macd);
        BigDecimal signalValue = this.average(lastMacds, MacdEntity::getMacdValue);
        macd.setSignalValue(signalValue);

        return macd;
    }

    /**
     *
     * @param macdDef
     * @param ohlcs
     * @return
     */
    public SortedMap<Integer,MacdEntity> getInitialMacds(
            final MacdDefinitionEntity macdDef,
            final List<OHLCEntity> ohlcs) {

        final BigDecimal K_FAST = this.getKFast(macdDef);
        final BigDecimal K_SLOW = this.getKSlow(macdDef);

        SortedMap<Integer,MacdEntity> macds = new TreeMap<>();

        final int i1 = macdDef.getShortPeriodEma() - 1;
        final int i2 = macdDef.getLongPeriodEma() - 1;
        final int i3 = macdDef.getLongPeriodEma() + macdDef.getMacdEma() - 2;

        for(int index=i1; index<ohlcs.size(); index++) {

            OHLCEntity ohlc = ohlcs.get(index);
            MacdEntity macd = new MacdEntity();
            MacdEntity previous = macds.get(index - 1);/*nulls not used*/
            macd.setMacdDefinition(macdDef);
            macd.setTimeEpochTimestamp(ohlc.getTimeEpochTimestamp());
            macd.setClosingPrice(ohlc.getClosingPrice());

            /** shortEmaValue */
            BigDecimal shortEmaValue = null;
            if(index == i1) {
                List<OHLCEntity> subList = ohlcs.subList(0,macdDef.getShortPeriodEma());
                shortEmaValue = this.average(subList, OHLCEntity::getClosingPrice);
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
                longEmaValue = this.average(subList, OHLCEntity::getClosingPrice);
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
                        macds.subMap(index - macdDef.getMacdEma() + 1, index).values()
                );
                subList.add(macd);
                BigDecimal signalValue = this.average(subList, MacdEntity::getMacdValue);
                macd.setSignalValue(signalValue);
            }

            macds.put(index, macd);
        }

        return macds;
    }

    /*
    factorisation de code
    constante pour la moyenne mobile rapide
     */
    private BigDecimal getKFast(MacdDefinitionEntity macdDef) {

        return BigDecimal.valueOf(2)
                .divide(
                        BigDecimal.valueOf(macdDef.getShortPeriodEma()).add(BigDecimal.ONE),
                        10,
                        RoundingMode.HALF_EVEN
                )
                .setScale(10, RoundingMode.HALF_EVEN);
    }

    /*
    factorisation de code
    constante pour la moyenne mobile lente
     */
    private BigDecimal getKSlow(MacdDefinitionEntity macdDef) {
        return BigDecimal.valueOf(2)
                .divide(
                        BigDecimal.valueOf(macdDef.getLongPeriodEma()).add(BigDecimal.ONE),
                        10,
                        RoundingMode.HALF_EVEN
                )
                .setScale(10, RoundingMode.HALF_EVEN);
    }

    /*
    factorisation de code
    moyenne
     */
    private <T> BigDecimal average(List<T> list, Function<T,BigDecimal> getter) {
        BigDecimal somme = list.stream()
                .map(getter)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(10, RoundingMode.HALF_EVEN);
        BigDecimal average = somme.divide(BigDecimal.valueOf(list.size()), 10, RoundingMode.HALF_EVEN);
        return average;
    }
}
