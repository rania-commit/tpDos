package com.myapp.mycompany.repository;

import com.myapp.mycompany.domain.Joueur;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Joueur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JoueurRepository extends JpaRepository<Joueur, Long> {
}
