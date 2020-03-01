package com.poc.macddefinition.persistence.macddefinition;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

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
}
