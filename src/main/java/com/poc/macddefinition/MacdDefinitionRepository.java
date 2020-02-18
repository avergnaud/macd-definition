package com.poc.macddefinition;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "macddefinition", path = "macddefinition")
public interface MacdDefinitionRepository extends PagingAndSortingRepository<MacdDefinition, Long> {

    List<MacdDefinition> findByTradingPair(@Param("tradingPair") String tradingPair);

}
