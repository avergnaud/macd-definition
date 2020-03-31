package com.poc.macddefinition.persistence.macd;

import com.poc.macddefinition.persistence.macddefinition.MacdDefinitionEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Repository
@Transactional
public class MacdRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<MacdEntity> getAll() {
        return entityManager.createNamedQuery("MacdEntity.getAll", MacdEntity.class)
                .getResultList();
    }

    public List<MacdEntity> getByDefinitionId(Long macdDefinitionId) {
        return entityManager.createNamedQuery("MacdEntity.getByDefinition", MacdEntity.class)
                .setParameter("macdDefinitionId", macdDefinitionId)
                .getResultList();
    }

    public List<MacdEntity> getByDefinitionId(Long macdDefinitionId, int last) {
        return entityManager.createNamedQuery("MacdEntity.getByDefinition", MacdEntity.class)
                .setParameter("macdDefinitionId", macdDefinitionId)
                .setMaxResults(last)
                .getResultList();
    }

    public MacdEntity getLast(MacdDefinitionEntity macdDef) {

        List<MacdEntity> macds = entityManager.createNamedQuery("MacdEntity.getLast")
                .setParameter("macdDefinitionEntity", macdDef)
                .setMaxResults(1)
                .getResultList();

        return macds.get(0);
    }

    public List<MacdEntity> getNLastBefore(MacdDefinitionEntity macdDef,
                                               long timeEpochTimestamp,
                                               int limit) {

        List<MacdEntity> entities = entityManager.createNamedQuery("MacdEntity.getNLastBefore")
                .setParameter("macdDefinitionEntity", macdDef)
                .setParameter("timeEpochTimestamp", timeEpochTimestamp)
                .setMaxResults(limit)
                .getResultList();

        entities.sort(Comparator.comparing(MacdEntity::getTimeEpochTimestamp));

        return entities;
    }

    public Collection<MacdEntity> create(Collection<MacdEntity> macdEntities) {

        for (MacdEntity macdEntity : macdEntities) {
            entityManager.persist(macdEntity);
        }
        entityManager.flush();
        return macdEntities;
    }

    public MacdEntity create(MacdEntity macdEntity) {

        entityManager.persist(macdEntity);
        entityManager.flush();
        return macdEntity;
    }

    public MacdEntity upsert(MacdEntity newMacdEntity) {

        /*
        UNIQUE INDEX ... ON public.macd USING btree (macd_definition_id, time_epoch_timestamp)
         */
        List<MacdEntity> existing = entityManager.createNamedQuery("MacdEntity.findOne")
                .setParameter("macdDefinitionEntity", newMacdEntity.getMacdDefinition())
                .setParameter("timeEpochTimestamp", newMacdEntity.getTimeEpochTimestamp())
                .getResultList();

        if (existing.size() > 0) {
            newMacdEntity.setId(existing.get(0).getId());
            return this.update(newMacdEntity);
        }

        return this.create(newMacdEntity);
    }

    public MacdEntity update(MacdEntity macdEntity) {
        return entityManager.merge(macdEntity);
    }
}
