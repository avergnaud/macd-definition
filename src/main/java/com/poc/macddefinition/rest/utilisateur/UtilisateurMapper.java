package com.poc.macddefinition.rest.utilisateur;

import com.poc.macddefinition.persistence.utilisateur.UtilisateurEntity;
import fr.xebia.extras.selma.IgnoreMissing;
import fr.xebia.extras.selma.Mapper;
import fr.xebia.extras.selma.Maps;

@Mapper(withIgnoreMissing = IgnoreMissing.ALL)
public interface UtilisateurMapper {

    @Maps(withIgnoreFields = "com.poc.macddefinition.persistence.macddefinition.MacdDefinitionEntity.utilisateurs")
    Utilisateur asUtilisateur(UtilisateurEntity utilisateurEntity);

    UtilisateurEntity asUtilisateurEntity(Utilisateur utilisateur);
}
