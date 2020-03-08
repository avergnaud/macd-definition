package com.poc.macddefinition.macd;

import com.poc.macddefinition.persistence.macddefinition.MacdDefinitionEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@NamedQueries({
        @NamedQuery(name="MacdEntity.findOne", query = "SELECT m from MacdEntity m" +
                " where m.macdDefinitionEntity = :macdDefinitionEntity" +
                " and m.timeEpochTimestamp = :timeEpochTimestamp"),
        @NamedQuery(name="MacdEntity.getLast", query = "SELECT m from MacdEntity m" +
                " where m.macdDefinitionEntity = :macdDefinitionEntity" +
                " order by m.timeEpochTimestamp desc"),
        @NamedQuery(name="MacdEntity.getNLastBefore", query = "SELECT m from MacdEntity m" +
                " where m.macdDefinitionEntity = :macdDefinitionEntity" +
                " and m.timeEpochTimestamp <= :timeEpochTimestamp" +
                " order by m.timeEpochTimestamp desc")
})
@Table(name = "macd", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"macd_definition_id","time_epoch_timestamp"})
})
public class MacdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "macd_definition_id")
    private MacdDefinitionEntity macdDefinitionEntity;

    @Column(name = "time_epoch_timestamp")
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

    public MacdDefinitionEntity getMacdDefinitionEntity() {
        return macdDefinitionEntity;
    }

    public void setMacdDefinitionEntity(MacdDefinitionEntity macdDefinitionEntity) {
        this.macdDefinitionEntity = macdDefinitionEntity;
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
