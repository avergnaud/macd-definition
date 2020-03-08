package com.poc.macddefinition.rest.ohlc;

import com.poc.macddefinition.rest.chart.Chart;

import java.math.BigDecimal;

public class OHLC {

    private Long id;
    private Chart chartEntity;
    private long timeEpochTimestamp;
    private BigDecimal openingPrice;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;
    private BigDecimal closingPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Chart getChartEntity() {
        return chartEntity;
    }

    public void setChartEntity(Chart chartEntity) {
        this.chartEntity = chartEntity;
    }

    public long getTimeEpochTimestamp() {
        return timeEpochTimestamp;
    }

    public void setTimeEpochTimestamp(long timeEpochTimestamp) {
        this.timeEpochTimestamp = timeEpochTimestamp;
    }

    public BigDecimal getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(BigDecimal openingPrice) {
        this.openingPrice = openingPrice;
    }

    public BigDecimal getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(BigDecimal highPrice) {
        this.highPrice = highPrice;
    }

    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(BigDecimal lowPrice) {
        this.lowPrice = lowPrice;
    }

    public BigDecimal getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(BigDecimal closingPrice) {
        this.closingPrice = closingPrice;
    }
}
