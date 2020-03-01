package com.poc.macddefinition.rest.utilisateur;

import com.poc.macddefinition.rest.macddefinition.MacdDefinition;

import java.util.Set;

public class Utilisateur {

    private Long id;
    private Set<MacdDefinition> macdDefinitions;
    private String wirePusherId;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<MacdDefinition> getMacdDefinitions() {
        return macdDefinitions;
    }

    public void setMacdDefinitions(Set<MacdDefinition> macdDefinitions) {
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
