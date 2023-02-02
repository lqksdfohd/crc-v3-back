package com.app.crc.services;

import com.app.crc.entites.Klass;
import com.app.crc.entites.Responsabilite;
import com.app.crc.repository.CollaborateurRepository;
import com.app.crc.repository.KlassRepository;
import com.app.crc.repository.ResponsabiliteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class KlassServiceTest {

    @InjectMocks
    private KlassService klassService;

    @Mock
    private KlassRepository klassRepository;
    @Mock
    private ResponsabiliteRepository responsabiliteRepository;
    @Mock
    private CollaborateurRepository collaborateurRepository;

    private Klass enBase;
    private Klass enInput;

    @BeforeEach
    public void setup(){
        enBase = new Klass();
        enBase.setId(1L);
        Responsabilite r1 = new Responsabilite();
        r1.setId(1L);
        r1.setTitre("r1");
        r1.setKlass(enBase);
        Responsabilite r2 = new Responsabilite();
        r2.setTitre("r2");
        r2.setId(2L);
        r2.setKlass(enBase);

        List<Responsabilite> rEnBase = new ArrayList<>();
        rEnBase.add(r1);
        rEnBase.add(r2);
        enBase.setListeResponsabilites(rEnBase);

        enInput = new Klass();
        enInput.setId(1L);
        enInput.setListeResponsabilites(new ArrayList<>());

    }

    @DisplayName("si la responsabilite a un titre non présent en base alors on la considère comme nouvelle et on la garde")
    @Test
    public void testTrouverLesNouvellesResponsabilites_vraiNouvelles(){
        //une responsabilite est une "vrai nouvelle" si elle possède
        //un titre nouveau par rapport à ceux présent en base.
        Responsabilite n = new Responsabilite();
        n.setTitre("nouvelle");
        enInput.getListeResponsabilites().add(n);


        List<Responsabilite> resultat = klassService.trouverLesNouvellesResponsabilites(enInput, enBase);

        Assertions.assertEquals(1, resultat.size());
    }

    @DisplayName("si la responsabilite a un titre égall à une responsabilite " +
            "en base alors on ne la considère pas comme nouvelle et on ne la garde pas")
    @Test
    public void testTrouverLesNouvellesResponsabilites_fausseNouvelle(){
        Responsabilite n = new Responsabilite();
        n.setTitre("r1");
        enInput.getListeResponsabilites().add(n);

        List<Responsabilite> resultat = klassService.trouverLesNouvellesResponsabilites(enInput, enBase);

        Assertions.assertEquals(0, resultat.size());
    }

    @DisplayName("si une responsabilité est égale à une responsabilité en base, " +
            "alors on met à jour celle en base à partir de la nouvelle")
    @Test
    public void mettreAJourLesResponsabilitesCommunes_vraiCommune(){
        Responsabilite r1 = new Responsabilite();
        r1.setId(1L);
        r1.setTitre("titre modifié");
        enInput.getListeResponsabilites().add(r1);

        List<Responsabilite> resultat = klassService.mettreAJourLesResponsabilitesCommunes(enInput, enBase);

        Assertions.assertEquals("titre modifié", resultat.get(0).getTitre());
    }

    @DisplayName("si une responsabilité n'est correspond à aucune responsabilité (même titre ou même id)" +
            "en base alors elle n'est pas considéré comme commune")
    @Test
    public void testMettreAJourLesResponsabilitesCommunes_pasDeCommunes(){
        Responsabilite r = new Responsabilite();
        r.setTitre("pas de responsabilites communes");
        enInput.getListeResponsabilites().add(r);

        List<Responsabilite> resultat = klassService.mettreAJourLesResponsabilitesCommunes(enInput, enBase);

        Assertions.assertEquals(0, resultat.size());
    }

    @DisplayName("si une responsabilité en input partage le même titre qu'une responsabilité en base alors elles sont " +
            "considérées comme communes")
    @Test
    public void testMettreAJourLesResponsabilitesCommunes_memeTitre(){
        Responsabilite r = new Responsabilite();
        r.setTitre("r1");
        enInput.getListeResponsabilites().add(r);

        List<Responsabilite> resultat = klassService.mettreAJourLesResponsabilitesCommunes(enInput, enBase);

        Assertions.assertEquals(1L, resultat.get(0).getId());
    }


    @DisplayName("si une responsabilité en base ne correspond à aucune responsabilité en input (même titre ou même id) " +
            "alors elle est considérée à supprimer")
    @Test
    public void testTrouverLesResponsabilitesASupprimer_uneResponsabiliteASupprimer(){
        Responsabilite r = new Responsabilite();
        r.setId(1L);
        r.setTitre("égall à r1 à garder");
        enInput.getListeResponsabilites().add(r);

        List<Responsabilite> resultat = klassService.trouverLesResponsabilitesASupprimer(enBase, enInput.getListeResponsabilites());

        Assertions.assertEquals("r2", resultat.get(0).getTitre());
    }





}
