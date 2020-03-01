package com.poc.macddefinition.persistence.utilisateur;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class UtilisateurRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public UtilisateurEntity create(UtilisateurEntity utilisateurEntity) {
        entityManager.persist(utilisateurEntity);
        entityManager.flush();
        return utilisateurEntity;
    }

    public UtilisateurEntity get(Long id) {
        return entityManager.find(UtilisateurEntity.class, id);
    }
}
