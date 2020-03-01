package com.poc.macddefinition.rest.ohlc;

import com.poc.macddefinition.persistence.ohlc.OHLCEntity;
import fr.xebia.extras.selma.IgnoreMissing;
import fr.xebia.extras.selma.Mapper;

@Mapper(withIgnoreMissing = IgnoreMissing.ALL)
public interface OHLCMapper {

    OHLC asOHLC(OHLCEntity ohlcEntity);

    OHLCEntity asOHLCEntity(OHLC ohlc);
}
