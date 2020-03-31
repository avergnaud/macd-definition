package com.poc.macddefinition.rest.macd;

import com.poc.macddefinition.persistence.macd.MacdEntity;
import com.poc.macddefinition.persistence.macd.MacdRepository;
import fr.xebia.extras.selma.Selma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("macd")
public class MACDController {

    @Autowired
    MacdRepository macdRepository;

    private MacdMapper mapper = Selma.builder(MacdMapper.class).build();

    @GetMapping("/")
    @ResponseBody
    public List<Macd> search(@RequestParam Long macdDefinitionId, @RequestParam(required = false) Integer last) {

        List<MacdEntity> liste = new ArrayList<>();
        if(last != null) {
            liste = macdRepository.getByDefinitionId(macdDefinitionId, last);
        } else {
            liste = macdRepository.getByDefinitionId(macdDefinitionId);
        }

        return liste.stream()
                .map(mapper::asMacd)
                .collect(Collectors.toList());
    }
}
