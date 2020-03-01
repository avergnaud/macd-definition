package com.poc.macddefinition.rest.macddefinition;

import com.poc.macddefinition.persistence.macddefinition.MacdDefinitionEntity;
import fr.xebia.extras.selma.IgnoreMissing;
import fr.xebia.extras.selma.Mapper;
import fr.xebia.extras.selma.Maps;

@Mapper(withIgnoreMissing = IgnoreMissing.ALL)
public interface MacdDefinitionMapper {

    /* to avoid jackson infinite loop */
    @Maps(withIgnoreFields = "com.poc.macddefinition.persistence.utilisateur.UtilisateurEntity.macdDefinitions")
    MacdDefinition asMacdDefinition(MacdDefinitionEntity macdDefinitionEntity);

    MacdDefinitionEntity asMacdDefinitionEntity(MacdDefinition macdDefinition);
}
