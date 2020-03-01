package com.poc.macddefinition.rest.chart;

public class Chart {

    private Long id;
    private String exchange;
    private String tradingPair;
    private int timeFrameInterval;
    private long lastClosingTimeEpochTimestamp;

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

    public long getLastClosingTimeEpochTimestamp() {
        return lastClosingTimeEpochTimestamp;
    }

    public void setLastClosingTimeEpochTimestamp(long lastClosingTimeEpochTimestamp) {
        this.lastClosingTimeEpochTimestamp = lastClosingTimeEpochTimestamp;
    }
}
