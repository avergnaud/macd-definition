package com.poc.macddefinition.rest.macd;

import com.poc.macddefinition.rest.macddefinition.MacdDefinition;

import java.math.BigDecimal;

public class Macd {

    private Long id;
    private MacdDefinition macdDefinition;
    private long timeEpochTimestamp;
    private BigDecimal closingPrice;
    private BigDecimal shortEmaValue;
    private BigDecimal longEmaValue;
    private BigDecimal macdValue;
    private BigDecimal signalValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MacdDefinition getMacdDefinition() {
        return macdDefinition;
    }

    public void setMacdDefinition(MacdDefinition macdDefinition) {
        this.macdDefinition = macdDefinition;
    }

    public long getTimeEpochTimestamp() {
        return timeEpochTimestamp;
    }

    public void setTimeEpochTimestamp(long timeEpochTimestamp) {
        this.timeEpochTimestamp = timeEpochTimestamp;
    }

    public BigDecimal getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(BigDecimal closingPrice) {
        this.closingPrice = closingPrice;
    }

    public BigDecimal getShortEmaValue() {
        return shortEmaValue;
    }

    public void setShortEmaValue(BigDecimal shortEmaValue) {
        this.shortEmaValue = shortEmaValue;
    }

    public BigDecimal getLongEmaValue() {
        return longEmaValue;
    }

    public void setLongEmaValue(BigDecimal longEmaValue) {
        this.longEmaValue = longEmaValue;
    }

    public BigDecimal getMacdValue() {
        return macdValue;
    }

    public void setMacdValue(BigDecimal macdValue) {
        this.macdValue = macdValue;
    }

    public BigDecimal getSignalValue() {
        return signalValue;
    }

    public void setSignalValue(BigDecimal signalValue) {
        this.signalValue = signalValue;
    }
}
