package com.poc.macddefinition.macd;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;

@Repository
@Transactional
public class MacdRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Collection<MacdEntity> create(Collection<MacdEntity> macdEntities) {

        for(MacdEntity macdEntity : macdEntities) {
            entityManager.persist(macdEntity);
        }
        entityManager.flush();
        return macdEntities;
    }
}
