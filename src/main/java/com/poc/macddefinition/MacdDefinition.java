package com.poc.macddefinition;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MacdDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String exchange;
    private String tradingPair;
    private int timeFrameInterval;
    private int shortPeriodEma;
    private int longPeriodEma;
    private int macdEma;

    @Override
    public String toString() {
        return "MacdDefinition{" +
                "exchange='" + exchange + '\'' +
                ", tradingPair='" + tradingPair + '\'' +
                ", timeFrameInterval=" + timeFrameInterval +
                ", shortPeriodEma=" + shortPeriodEma +
                ", longPeriodEma=" + longPeriodEma +
                ", macdEma=" + macdEma +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getTradingPair() {
        return tradingPair;
    }

    public void setTradingPair(String tradingPair) {
        this.tradingPair = tradingPair;
    }

    public int getTimeFrameInterval() {
        return timeFrameInterval;
    }

    public void setTimeFrameInterval(int timeFrameInterval) {
        this.timeFrameInterval = timeFrameInterval;
    }

    public int getShortPeriodEma() {
        return shortPeriodEma;
    }

    public void setShortPeriodEma(int shortPeriodEma) {
        this.shortPeriodEma = shortPeriodEma;
    }

    public int getLongPeriodEma() {
        return longPeriodEma;
    }

    public void setLongPeriodEma(int longPeriodEma) {
        this.longPeriodEma = longPeriodEma;
    }

    public int getMacdEma() {
        return macdEma;
    }

    public void setMacdEma(int macdEma) {
        this.macdEma = macdEma;
    }
}
