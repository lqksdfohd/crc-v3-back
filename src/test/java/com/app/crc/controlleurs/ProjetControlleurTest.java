package com.app.crc.controlleurs;


import com.app.crc.dtos.ProjetDto;
import com.app.crc.entites.Projet;
import com.app.crc.services.MapstructService;
import com.app.crc.services.ProjetService;
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

    @Test
    @DisplayName("si le projet n'existe pas déjà, le créer")
    public void testCréerUnNouveauProjet() throws Exception{
        ProjetDto dto = new ProjetDto();
        dto.setNomProjet("test");

        Projet projetTemp = new Projet();
        projetTemp.setNom(dto.getNomProjet());

        Projet resultat = new Projet();
        resultat.setNom(dto.getNomProjet());
        resultat.setId(1L);

        ProjetDto resultatDto = new ProjetDto();
        resultatDto.setNomProjet(resultat.getNom());
        resultatDto.setId(resultat.getId());

        Mockito.when(mapstructService.projetDtoVersProjet(dto))
                        .thenReturn(projetTemp);
        Mockito.when(projetService.creerProjet(projetTemp))
                        .thenReturn(resultat);
        Mockito.when(mapstructService.projetVersProjetDto(resultat))
                        .thenReturn(resultatDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/projet")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)));
    }


    @Test
    @DisplayName("si le projet en input existe déjà renvoyer une exception")
    public void testCreerProjet_ProjetExistant() throws Exception{
        ProjetDto dto = new ProjetDto();
        dto.setNomProjet("projet existant");

        mockMvc.perform(MockMvcRequestBuilders.post("/projet")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.raison", Matchers.is("projet déjà existant")));
    }

}
