package com.myapp.mycompany.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Equipe.
 */
@Entity
@Table(name = "equipe")
public class Equipe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "localisation", nullable = false)
    private String localisation;

    @OneToMany(mappedBy = "equipe")
    private Set<Joueur> joueurs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Equipe name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocalisation() {
        return localisation;
    }

    public Equipe localisation(String localisation) {
        this.localisation = localisation;
        return this;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public Set<Joueur> getJoueurs() {
        return joueurs;
    }

    public Equipe joueurs(Set<Joueur> joueurs) {
        this.joueurs = joueurs;
        return this;
    }

    public Equipe addJoueur(Joueur joueur) {
        this.joueurs.add(joueur);
        joueur.setEquipe(this);
        return this;
    }

    public Equipe removeJoueur(Joueur joueur) {
        this.joueurs.remove(joueur);
        joueur.setEquipe(null);
        return this;
    }

    public void setJoueurs(Set<Joueur> joueurs) {
        this.joueurs = joueurs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Equipe)) {
            return false;
        }
        return id != null && id.equals(((Equipe) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Equipe{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", localisation='" + getLocalisation() + "'" +
            "}";
    }
}
