package com.app.crc.controlleurs;


import com.app.crc.dtos.ProjetDto;
import com.app.crc.entites.Projet;
import com.app.crc.services.MapstructService;
import com.app.crc.services.ProjetService;
import com.app.crc.services.ProjetValidatorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@WebMvcTest
public class ProjetControlleurTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProjetService projetService;

    @MockBean
    private MapstructService mapstructService;

    @MockBean
    private ProjetValidatorService projetValidatorService;

    @Test
    @DisplayName("si le projet est nouveau, le créer")
    public void testCreerProjet_checkInputCorrect() throws Exception{
        //dto reçu du front
        ProjetDto inputDto = new ProjetDto();
        inputDto.setNom("test");

        mockMvc.perform(MockMvcRequestBuilders.post("/projet")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @DisplayName("si le projet en input existe déjà renvoyer une exception")
    public void testCreerProjet_ProjetExistant() throws Exception{
        ProjetDto inputDto = new ProjetDto();
        inputDto.setNom("projet existant");

        Projet projetTraduit = new Projet();
        projetTraduit.setNom(inputDto.getNom());

        Mockito.when(mapstructService.projetDtoVersProjet(inputDto))
                .thenReturn(projetTraduit);
        Mockito.when(projetService.creerProjet(projetTraduit))
                        .thenThrow(new IllegalArgumentException("projet déjà existant"));

        mockMvc.perform(MockMvcRequestBuilders.post("/projet")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.raison", Matchers.is("projet déjà existant")));
    }

    @Test
    @DisplayName("si projetDto en input est mal formé, on renvoie une exception")
    public void testCreerProjet_checkValidation() throws Exception{
        ProjetDto dto = new ProjetDto();

        //  /!\
        Set<ConstraintViolation<ProjetDto>> constraints = new HashSet<>();
        constraints.add(null);


        Mockito.when(projetValidatorService.validateProjet(dto))
                        .thenReturn(constraints);

        mockMvc.perform(MockMvcRequestBuilders.post("/projet")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());


    }


    @Test
    public void testListerLesProjetsExistants_signature() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/projets")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @DisplayName("on retourne les projet existants en base")
    public void testListerLesProjetsExistants_OK() throws Exception{
        Projet projet = new Projet();
        projet.setNom("test");
        projet.setId(1l);
        List<Projet> projets = new ArrayList<>();
        projets.add(projet);

        ProjetDto dto = new ProjetDto();
        dto.setNom(projet.getNom());
        dto.setId(projet.getId());
        List<ProjetDto> dtos = new ArrayList<>();
        dtos.add(dto);


        Mockito.when(projetService.recupererTousLesProjets()).thenReturn(projets);
        Mockito.when(mapstructService.listeProjetVersListeProjetDto(projets))
                .thenReturn(dtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/projets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nom", Matchers.is("test")));

    }

    @Test
    public void testRecupererUnProjet_testSignature() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/projet/{projetId}",1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("si un projet correspond à l'id en input on retourne ce projet")
    public void testRecupererUnProjet_Ok() throws Exception{
        Projet fromBase = new Projet();
        fromBase.setId(1L);
        fromBase.setNom("test");

        ProjetDto dto =  new ProjetDto();
        dto.setId(fromBase.getId());
        dto.setNom(fromBase.getNom());


        Mockito.when(projetService.recupererUnProjetParId(1L))
                .thenReturn(fromBase);
        Mockito.when(mapstructService.projetVersProjetDto(fromBase))
                        .thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.get("/projet/{projetId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nom", Matchers.is("test")));
    }



    @Test
    @DisplayName("si aucun projet n'existe pour l'id en input on l'indique à l'utilisateur")
    public void testRecupererUnProjet_projetInexistant()  throws Exception{
        Mockito.when(projetService.recupererUnProjetParId(Mockito.anyLong()))
                .thenThrow(new IllegalArgumentException("aucun projet ne correspond à cet id"));

        mockMvc.perform(MockMvcRequestBuilders.get("/projet/{projetId}", 1L)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.raison", Matchers.is("aucun projet ne correspond à cet id")));
    }



}
