package com.myapp.mycompany.service.impl;

import com.myapp.mycompany.service.EquipeService;
import com.myapp.mycompany.domain.Equipe;
import com.myapp.mycompany.repository.EquipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Equipe}.
 */
@Service
@Transactional
public class EquipeServiceImpl implements EquipeService {

    private final Logger log = LoggerFactory.getLogger(EquipeServiceImpl.class);

    private final EquipeRepository equipeRepository;

    public EquipeServiceImpl(EquipeRepository equipeRepository) {
        this.equipeRepository = equipeRepository;
    }

    @Override
    public Equipe save(Equipe equipe) {
        log.debug("Request to save Equipe : {}", equipe);
        return equipeRepository.save(equipe);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Equipe> findAll(Pageable pageable) {
        log.debug("Request to get all Equipes");
        return equipeRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Equipe> findOne(Long id) {
        log.debug("Request to get Equipe : {}", id);
        return equipeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Equipe : {}", id);
        equipeRepository.deleteById(id);
    }
}
