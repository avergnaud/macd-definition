package com.poc.macddefinition.rest.chart;

import com.poc.macddefinition.persistence.chart.ChartEntity;
import fr.xebia.extras.selma.IgnoreMissing;
import fr.xebia.extras.selma.Mapper;

@Mapper(withIgnoreMissing = IgnoreMissing.ALL)
public interface ChartMapper {

    Chart asChart(ChartEntity chartEntity);

    ChartEntity asChartEntity(Chart chart);
}
