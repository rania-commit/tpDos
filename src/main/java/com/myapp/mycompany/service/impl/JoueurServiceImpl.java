package com.myapp.mycompany.service.impl;

import com.myapp.mycompany.service.JoueurService;
import com.myapp.mycompany.domain.Joueur;
import com.myapp.mycompany.repository.JoueurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Joueur}.
 */
@Service
@Transactional
public class JoueurServiceImpl implements JoueurService {

    private final Logger log = LoggerFactory.getLogger(JoueurServiceImpl.class);

    private final JoueurRepository joueurRepository;

    public JoueurServiceImpl(JoueurRepository joueurRepository) {
        this.joueurRepository = joueurRepository;
    }

    @Override
    public Joueur save(Joueur joueur) {
        log.debug("Request to save Joueur : {}", joueur);
        return joueurRepository.save(joueur);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Joueur> findAll(Pageable pageable) {
        log.debug("Request to get all Joueurs");
        return joueurRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Joueur> findOne(Long id) {
        log.debug("Request to get Joueur : {}", id);
        return joueurRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Joueur : {}", id);
        joueurRepository.deleteById(id);
    }
}
