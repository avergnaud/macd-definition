package com.poc.macddefinition.rest.macd;

import com.poc.macddefinition.persistence.macd.MacdEntity;
import fr.xebia.extras.selma.IgnoreMissing;
import fr.xebia.extras.selma.Mapper;
import fr.xebia.extras.selma.Maps;

@Mapper(withIgnoreMissing = IgnoreMissing.ALL)
public interface MacdMapper {

    @Maps(withIgnoreFields = "com.poc.macddefinition.persistence.macd.MacdEntity.macdDefinition")
    Macd asMacd(MacdEntity macdEntity);

    MacdEntity asMacdEntity(Macd macd);
}
