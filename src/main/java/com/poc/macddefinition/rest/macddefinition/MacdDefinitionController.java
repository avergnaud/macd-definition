package com.poc.macddefinition.rest.macddefinition;

import com.poc.macddefinition.persistence.macddefinition.MacdDefinitionEntity;
import com.poc.macddefinition.persistence.macddefinition.MacdDefinitionRepository;
import fr.xebia.extras.selma.Selma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("macd-definitions")
public class MacdDefinitionController {

    @Autowired
    private MacdDefinitionRepository macdDefinitionRepository;

    private MacdDefinitionMapper mapper = Selma.builder(MacdDefinitionMapper.class).build();

    @GetMapping(value = "/{id}", produces = "application/json")
    public MacdDefinition getMacdDefinition(@PathVariable long id) {
        MacdDefinitionEntity macdDefinitionEntity = macdDefinitionRepository.get(id);
        return mapper.asMacdDefinition(macdDefinitionEntity);
    }

    /*
    TODO : sécuriser
    @PostMapping()
    public MacdDefinition create(@RequestBody MacdDefinition macdDefinition) {
        MacdDefinitionEntity macdDefinitionEntity = mapper.asMacdDefinitionEntity(macdDefinition);
        macdDefinitionEntity = macdDefinitionRepository.create(macdDefinitionEntity);
        return mapper.asMacdDefinition(macdDefinitionEntity);
    }
     */

}
