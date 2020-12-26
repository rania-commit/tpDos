package com.myapp.mycompany.web.rest;

import com.myapp.mycompany.ProjetApp;
import com.myapp.mycompany.domain.Equipe;
import com.myapp.mycompany.repository.EquipeRepository;
import com.myapp.mycompany.service.EquipeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EquipeResource} REST controller.
 */
@SpringBootTest(classes = ProjetApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EquipeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALISATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCALISATION = "BBBBBBBBBB";

    @Autowired
    private EquipeRepository equipeRepository;

    @Autowired
    private EquipeService equipeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEquipeMockMvc;

    private Equipe equipe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipe createEntity(EntityManager em) {
        Equipe equipe = new Equipe()
            .name(DEFAULT_NAME)
            .localisation(DEFAULT_LOCALISATION);
        return equipe;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipe createUpdatedEntity(EntityManager em) {
        Equipe equipe = new Equipe()
            .name(UPDATED_NAME)
            .localisation(UPDATED_LOCALISATION);
        return equipe;
    }

    @BeforeEach
    public void initTest() {
        equipe = createEntity(em);
    }

    @Test
    @Transactional
    public void createEquipe() throws Exception {
        int databaseSizeBeforeCreate = equipeRepository.findAll().size();
        // Create the Equipe
        restEquipeMockMvc.perform(post("/api/equipes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipe)))
            .andExpect(status().isCreated());

        // Validate the Equipe in the database
        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeCreate + 1);
        Equipe testEquipe = equipeList.get(equipeList.size() - 1);
        assertThat(testEquipe.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEquipe.getLocalisation()).isEqualTo(DEFAULT_LOCALISATION);
    }

    @Test
    @Transactional
    public void createEquipeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = equipeRepository.findAll().size();

        // Create the Equipe with an existing ID
        equipe.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquipeMockMvc.perform(post("/api/equipes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipe)))
            .andExpect(status().isBadRequest());

        // Validate the Equipe in the database
        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLocalisationIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipeRepository.findAll().size();
        // set the field null
        equipe.setLocalisation(null);

        // Create the Equipe, which fails.


        restEquipeMockMvc.perform(post("/api/equipes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipe)))
            .andExpect(status().isBadRequest());

        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEquipes() throws Exception {
        // Initialize the database
        equipeRepository.saveAndFlush(equipe);

        // Get all the equipeList
        restEquipeMockMvc.perform(get("/api/equipes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipe.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].localisation").value(hasItem(DEFAULT_LOCALISATION)));
    }
    
    @Test
    @Transactional
    public void getEquipe() throws Exception {
        // Initialize the database
        equipeRepository.saveAndFlush(equipe);

        // Get the equipe
        restEquipeMockMvc.perform(get("/api/equipes/{id}", equipe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(equipe.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.localisation").value(DEFAULT_LOCALISATION));
    }
    @Test
    @Transactional
    public void getNonExistingEquipe() throws Exception {
        // Get the equipe
        restEquipeMockMvc.perform(get("/api/equipes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEquipe() throws Exception {
        // Initialize the database
        equipeService.save(equipe);

        int databaseSizeBeforeUpdate = equipeRepository.findAll().size();

        // Update the equipe
        Equipe updatedEquipe = equipeRepository.findById(equipe.getId()).get();
        // Disconnect from session so that the updates on updatedEquipe are not directly saved in db
        em.detach(updatedEquipe);
        updatedEquipe
            .name(UPDATED_NAME)
            .localisation(UPDATED_LOCALISATION);

        restEquipeMockMvc.perform(put("/api/equipes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEquipe)))
            .andExpect(status().isOk());

        // Validate the Equipe in the database
        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeUpdate);
        Equipe testEquipe = equipeList.get(equipeList.size() - 1);
        assertThat(testEquipe.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEquipe.getLocalisation()).isEqualTo(UPDATED_LOCALISATION);
    }

    @Test
    @Transactional
    public void updateNonExistingEquipe() throws Exception {
        int databaseSizeBeforeUpdate = equipeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipeMockMvc.perform(put("/api/equipes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipe)))
            .andExpect(status().isBadRequest());

        // Validate the Equipe in the database
        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEquipe() throws Exception {
        // Initialize the database
        equipeService.save(equipe);

        int databaseSizeBeforeDelete = equipeRepository.findAll().size();

        // Delete the equipe
        restEquipeMockMvc.perform(delete("/api/equipes/{id}", equipe.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
