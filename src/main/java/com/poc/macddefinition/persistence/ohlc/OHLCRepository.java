package com.poc.macddefinition.persistence.ohlc;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class OHLCRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public OHLCEntity create(OHLCEntity ohlcEntity) {
        entityManager.persist(ohlcEntity);
        entityManager.flush();
        return ohlcEntity;
    }

    public OHLCEntity get(Long id) {
        return entityManager.find(OHLCEntity.class, id);
    }

    /**
     * update or create
     * @param newOhlcEntity
     * @return
     */
    public OHLCEntity upsert(OHLCEntity newOhlcEntity) {

        /*
        UNIQUE INDEX ... ON public.ohlc USING btree (chart_entity_id, closing_time_epoch_timestamp)
        limited calls due to &since=ChartEntity.lastClosingTimeEpochTimestamp
         */
        List<OHLCEntity> existings = entityManager.createNamedQuery("OHLCEntity.findOne")
                .setParameter("chartEntity", newOhlcEntity.getChartEntity())
                .setParameter("closingTimeEpochTimestamp", newOhlcEntity.getClosingTimeEpochTimestamp())
                .getResultList();

        if(existings.size() > 0) {
            newOhlcEntity.setId(existings.get(0).getId());
            return this.update(newOhlcEntity);
        }

        return this.create(newOhlcEntity);
    }

    public OHLCEntity update(OHLCEntity ohlcEntity) {
        return entityManager.merge(ohlcEntity);
    }
}
