package com.poc.macddefinition.rest.macddefinition;


import com.poc.macddefinition.rest.chart.Chart;
import com.poc.macddefinition.rest.utilisateur.Utilisateur;

import java.util.Set;

public class MacdDefinition {

    private Long id;
    private Chart chart;
    private int shortPeriodEma;
    private int longPeriodEma;
    private int macdEma;
    private Set<Utilisateur> utilisateurs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
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

    public Set<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }
}
