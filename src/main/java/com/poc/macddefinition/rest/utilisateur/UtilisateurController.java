package com.poc.macddefinition.rest.utilisateur;

import com.poc.macddefinition.persistence.utilisateur.UtilisateurEntity;
import com.poc.macddefinition.persistence.utilisateur.UtilisateurRepository;
import fr.xebia.extras.selma.Selma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    private UtilisateurMapper mapper = Selma.builder(UtilisateurMapper.class).build();

    @GetMapping(value = "/{id}", produces = "application/json")
    public Utilisateur getUtilisateur(@PathVariable long id) {
        UtilisateurEntity utilisateurEntity = utilisateurRepository.get(id);
        return mapper.asUtilisateur(utilisateurEntity);
    }
}
