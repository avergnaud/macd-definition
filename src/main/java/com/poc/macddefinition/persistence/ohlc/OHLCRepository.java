package com.poc.macddefinition.persistence.ohlc;

import com.poc.macddefinition.persistence.chart.ChartEntity;
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

    public List<OHLCEntity> getLast(ChartEntity chartEntity) {

        return entityManager.createNamedQuery("OHLCEntity.findOne")
                .setParameter("chartEntity", chartEntity)
                .setParameter("timeEpochTimestamp", chartEntity.getLastOHLCTimeEpochTimestamp())
                .getResultList();
    }

    public List<OHLCEntity> getAll(ChartEntity chartEntity) {
        return entityManager.createNamedQuery("OHLCEntity.getAll")
                .setParameter("chartEntity", chartEntity)
                .getResultList();
    }

    /**
     * update or create
     * @param newOhlcEntity
     * @return
     */
    public OHLCEntity upsert(OHLCEntity newOhlcEntity) {

        /*
        UNIQUE INDEX ... ON public.ohlc USING btree (chart_entity_id, time_epoch_timestamp)
        limited calls due to &since=ChartEntity.timeEpochTimestamp
         */
        List<OHLCEntity> existings = entityManager.createNamedQuery("OHLCEntity.findOne")
                .setParameter("chartEntity", newOhlcEntity.getChartEntity())
                .setParameter("timeEpochTimestamp", newOhlcEntity.getTimeEpochTimestamp())
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