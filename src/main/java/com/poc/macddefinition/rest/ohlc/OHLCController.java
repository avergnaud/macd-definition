package com.poc.macddefinition.rest.ohlc;

import com.poc.macddefinition.persistence.ohlc.OHLCRepository;
import fr.xebia.extras.selma.Selma;
import org.springframework.beans.factory.annotation.Autowired;

public class OHLCController {

    @Autowired
    private OHLCRepository ohlcRepository;

    private OHLCMapper ohlcMapper = Selma.builder(OHLCMapper.class).build();
}
