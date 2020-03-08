package com.poc.macddefinition.persistence.macddefinition;

import com.poc.macddefinition.persistence.chart.ChartEntity;
import com.poc.macddefinition.persistence.utilisateur.UtilisateurEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "macd_definition")
@NamedQueries({
        @NamedQuery(name="MacdDefinitionEntity.getAll", query = "SELECT m from MacdDefinitionEntity m")
})
public class MacdDefinitionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private ChartEntity chart;
    private int shortPeriodEma;
    private int longPeriodEma;
    private int macdEma;

    @ManyToMany(mappedBy="macdDefinitions", fetch = FetchType.EAGER)
    private Set<UtilisateurEntity> utilisateurs;

    @Override
    public String toString() {
        return "MacdDefinitionEntity{" +
                "id=" + id +
                ", chart=" + chart +
                ", shortPeriodEma=" + shortPeriodEma +
                ", longPeriodEma=" + longPeriodEma +
                ", macdEma=" + macdEma +
                ", utilisateurs=" + utilisateurs +
                '}';
    }

    public Set<UtilisateurEntity> getUtilisateurs() {
        return utilisateurs;
    }
    public void setUtilisateurs(Set<UtilisateurEntity> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    public ChartEntity getChart() {
        return chart;
    }

    public void setChart(ChartEntity chart) {
        this.chart = chart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
