package com.app.crc.services;

import com.app.crc.entites.Klass;
import com.app.crc.entites.Responsabilite;
import com.app.crc.repository.CollaborateurRepository;
import com.app.crc.repository.KlassRepository;
import com.app.crc.repository.ResponsabiliteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class KlassService {

    @Autowired
    private KlassRepository repository;

    @Autowired
    private CollaborateurRepository collaborateurRepo;

    @Autowired
    private ResponsabiliteRepository responsabiliteRepo;

    public Klass sauvegarderUneKlass(Klass klass) {
        Optional<Klass> optional = repository.findByProjetAndNom(klass.getProjet(), klass.getNom());
        if (optional.isPresent()) {
            throw new IllegalArgumentException("il existe déjà une Klass de ce nom");
        } else {
            return repository.save(klass);
        }
    }

    public void supprimerUneKlass(long id) {
        Optional<Klass> fromBase = repository.findById(id);
        if (fromBase.isPresent()) {
            repository.delete(fromBase.get());
        } else {
            throw new IllegalArgumentException("aucune klass avec cet id n'existe en base");
        }
    }

    public Klass modifierUneKlass(Klass input) {
        if (input.getListeResponsabilites().size() > 3) {
            throw new IllegalArgumentException("une klass ne peut avoir au maximum que 3 responsabilités");
        }
        Klass enBase = findKlassById(input.getId());
        List<Responsabilite> nouvellesResponsabilites = trouverLesNouvellesResponsabilites(input, enBase);
        List<Responsabilite> responsabilitesRestante = mettreAJourLesResponsabilitesCommunes(input, enBase);

        responsabilitesRestante.addAll(nouvellesResponsabilites);
        List<Responsabilite> aSupprimer = trouverLesResponsabilitesASupprimer(enBase, responsabilitesRestante);

        for(Responsabilite r: responsabilitesRestante){
            r.setKlass(enBase);
        }

        Iterable<Responsabilite> iterable = responsabiliteRepo.saveAll(responsabilitesRestante);
        List<Responsabilite> liste = iterToList(iterable);
        responsabiliteRepo.deleteAll(aSupprimer);
        enBase.setListeResponsabilites(liste);






        return enBase;
    }

    /**
     * permet de trouver les nouvelles responsabilites, celle qui ne sont pas présente en base
     * @param input la klass dont sont issues les nouvelles responsabilité
     * @param enBase la klass dont sont issues les responsabilites en base
     * @return une liste de responsabilites nouvelles
     */
    List<Responsabilite> trouverLesNouvellesResponsabilites(Klass input, Klass enBase) {
        List<Responsabilite> rEnInput = input.getListeResponsabilites().stream()
                .filter(r -> r.getId() == null).collect(Collectors.toList());
        List<Responsabilite> rEnBase = enBase.getListeResponsabilites();
        List<Responsabilite> liste = new ArrayList<>();

        for (Responsabilite r : rEnInput) {
            boolean nouveau = true;
            for (Responsabilite s : rEnBase) {
                if (r.hasSameTitre(s)) {
                    nouveau = false;
                    break;
                }
            }
            if (nouveau) {
                liste.add(r);
            }
        }
        return liste;
    }

    /**
     * permet d'actualiser les responsabilites en base à partir des responsabilites en input
     * @param input
     * @param enBase
     * @return une liste de responsabilites à jour (cad nouveau titre)
     */
    public List<Responsabilite> mettreAJourLesResponsabilitesCommunes(Klass input, Klass enBase) {
        List<Responsabilite> rEnInput = input.getListeResponsabilites();
        List<Responsabilite> rEnBase = enBase.getListeResponsabilites();
        List<Responsabilite> presenteDsLes2 = new ArrayList<>();

        for (Responsabilite r : rEnInput) {
            for (Responsabilite s : rEnBase) {
                if (r.equals(s)) {
                    s.setTitre(r.getTitre());
                    presenteDsLes2.add(s);
                }else if(r.hasSameTitre(s)){
                    presenteDsLes2.add(s);
                }
            }
        }
        return presenteDsLes2;
    }


    /**
     * permet de trouver une klass à partir de son id
     * @param id
     * @return
     */
    public Klass findKlassById(Long id) {
        Long klassId = id;
        if (klassId == null) {
            throw new IllegalArgumentException("id de klass ne  doit pas être null");
        }
        Optional<Klass> optional = repository.findById(klassId);
        if (!optional.isPresent()) {
            throw new IllegalArgumentException("aucune klass avec cet id n'existe en base");
        }
        return optional.get();
    }

    /**
     * permet de determiner les responsabilites qui ne sont pas en input et qui doivent être supprimées
     * @param enBase
     * @param restants
     * @return
     */
    public List<Responsabilite> trouverLesResponsabilitesASupprimer(Klass enBase, List<Responsabilite> restants){
        List<Responsabilite> output = new ArrayList<>();

        for(Responsabilite r:enBase.getListeResponsabilites()){
            if( ! restants.contains(r) ){
                output.add(r);
            }
        }
        return output;

    }

    private <E> List<E> iterToList(Iterable<E> iterable) {
        List<E> output = new ArrayList<>();
        for (E e : iterable) {
            output.add(e);
        }
        return output;
    }
}
