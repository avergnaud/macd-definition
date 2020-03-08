package com.poc.macddefinition.persistence.utilisateur;

import com.poc.macddefinition.persistence.macddefinition.MacdDefinitionEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;

@Entity
@Table(name = "utilisateur")
public class UtilisateurEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<MacdDefinitionEntity> macdDefinitions;

    private String wirePusherId;

    @Email
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<MacdDefinitionEntity> getMacdDefinitions() {
        return macdDefinitions;
    }

    public void setMacdDefinitions(Set<MacdDefinitionEntity> macdDefinitions) {
        this.macdDefinitions = macdDefinitions;
    }

    public String getWirePusherId() {
        return wirePusherId;
    }

    public void setWirePusherId(String wirePusherId) {
        this.wirePusherId = wirePusherId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
