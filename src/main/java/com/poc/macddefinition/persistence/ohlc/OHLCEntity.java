package com.poc.macddefinition.persistence.ohlc;

import com.poc.macddefinition.persistence.chart.ChartEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@NamedQueries({
        @NamedQuery(name="OHLCEntity.findOne", query = "SELECT o from OHLCEntity o" +
                " where o.chartEntity = :chartEntity" +
                " and o.closingTimeEpochTimestamp = :closingTimeEpochTimestamp")
})
@Table(name = "ohlc", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"chart_entity_id","closing_time_epoch_timestamp"})
})
public class OHLCEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chart_entity_id")
    private ChartEntity chartEntity;

    @Column(name = "closing_time_epoch_timestamp")
    private long closingTimeEpochTimestamp;

    private BigDecimal openingPrice;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;
    private BigDecimal closingPrice;

    @Override
    public String toString() {
        return "OHLC{" +
                "id=" + id +
                ", chart=" + chartEntity +
                ", closingTimeEpochTimestamp=" + closingTimeEpochTimestamp +
                ", openingPrice=" + openingPrice +
                ", highPrice=" + highPrice +
                ", lowPrice=" + lowPrice +
                ", closingPrice=" + closingPrice +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChartEntity getChartEntity() {
        return chartEntity;
    }

    public void setChartEntity(ChartEntity chartEntity) {
        this.chartEntity = chartEntity;
    }

    public long getClosingTimeEpochTimestamp() {
        return closingTimeEpochTimestamp;
    }

    public void setClosingTimeEpochTimestamp(long closingTimeEpochTimestamp) {
        this.closingTimeEpochTimestamp = closingTimeEpochTimestamp;
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
