package com.poc.macddefinition.persistence.macddefinition;

import com.poc.macddefinition.persistence.chart.ChartEntity;
import com.poc.macddefinition.rest.macddefinition.MacdDefinition;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class MacdDefinitionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public MacdDefinitionEntity create(MacdDefinitionEntity macdDefinitionEntity) {
        entityManager.persist(macdDefinitionEntity);
        entityManager.flush();
        return macdDefinitionEntity;
    }

    public MacdDefinitionEntity get(Long id) {
        return entityManager.find(MacdDefinitionEntity.class, id);
    }

    public List<MacdDefinitionEntity> getAll() {
        return entityManager.createNamedQuery("MacdDefinitionEntity.getAll", MacdDefinitionEntity.class)
                .getResultList();
    }

    public MacdDefinitionEntity update(MacdDefinitionEntity macdDefinitionEntity) {
        return entityManager.merge(macdDefinitionEntity);
    }
}
