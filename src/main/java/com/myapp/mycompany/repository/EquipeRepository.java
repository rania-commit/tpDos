package com.myapp.mycompany.repository;

import com.myapp.mycompany.domain.Equipe;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Equipe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {
}
