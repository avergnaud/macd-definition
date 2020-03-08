package com.poc.macddefinition.persistence.chart;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ChartRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public ChartEntity create(ChartEntity chartEntity) {
        entityManager.persist(chartEntity);
        entityManager.flush();
        return chartEntity;
    }

    public ChartEntity get(Long id) {
        return entityManager.find(ChartEntity.class, id);
    }

    public List<ChartEntity> getAll() {
        return entityManager.createNamedQuery("ChartEntity.getAll", ChartEntity.class)
                .getResultList();
    }

    public int count() {
        Number count = (Number) entityManager.createNamedQuery("ChartEntity.count")
                .getSingleResult();
        return count.intValue();
    }

    public ChartEntity update(ChartEntity chartEntity) {
        return entityManager.merge(chartEntity);
    }
}
