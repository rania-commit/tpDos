package com.myapp.mycompany.service;

import com.myapp.mycompany.domain.Joueur;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Joueur}.
 */
public interface JoueurService {

    /**
     * Save a joueur.
     *
     * @param joueur the entity to save.
     * @return the persisted entity.
     */
    Joueur save(Joueur joueur);

    /**
     * Get all the joueurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Joueur> findAll(Pageable pageable);


    /**
     * Get the "id" joueur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Joueur> findOne(Long id);

    /**
     * Delete the "id" joueur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
