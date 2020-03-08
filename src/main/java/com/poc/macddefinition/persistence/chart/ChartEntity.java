package com.poc.macddefinition.persistence.chart;

import javax.persistence.*;

@Table(
        name = "chart",
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"exchange", "tradingPair", "timeFrameInterval"})
)
@Entity
@NamedQueries({
        @NamedQuery(name="ChartEntity.getAll", query = "SELECT c from ChartEntity c"),
        @NamedQuery(name="ChartEntity.count", query = "SELECT count(c) from ChartEntity c")
})
public class ChartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String exchange;
    private String tradingPair;
    private int timeFrameInterval;
    private long lastOHLCTimeEpochTimestamp;
    private long lastMACDTimeEpochTimestamp;

    @Override
    public String toString() {
        return "Chart{" +
                "id=" + id +
                ", exchange='" + exchange + '\'' +
                ", tradingPair='" + tradingPair + '\'' +
                ", timeFrameInterval=" + timeFrameInterval +
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

    public long getLastOHLCTimeEpochTimestamp() {
        return lastOHLCTimeEpochTimestamp;
    }

    public void setLastOHLCTimeEpochTimestamp(long lastOHLCTimeEpochTimestamp) {
        this.lastOHLCTimeEpochTimestamp = lastOHLCTimeEpochTimestamp;
    }

    public long getLastMACDTimeEpochTimestamp() {
        return lastMACDTimeEpochTimestamp;
    }

    public void setLastMACDTimeEpochTimestamp(long lastMACDTimeEpochTimestamp) {
        this.lastMACDTimeEpochTimestamp = lastMACDTimeEpochTimestamp;
    }
}
