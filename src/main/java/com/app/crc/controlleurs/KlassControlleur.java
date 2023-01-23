package com.app.crc.controlleurs;

import com.app.crc.entites.Klass;
import com.app.crc.services.KlassService;
import com.app.crc.services.MapstructService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KlassControlleur {

    @Autowired
    private MapstructService mapstructService;

    @Autowired
    private KlassService klassService;


    @DeleteMapping("/klass/{klassId}")
    public void supprimerUneKlassDUnProjet(@PathVariable(value = "klassId") Long klassId){
        klassService.supprimerUneKlass(klassId);
    }

    @GetMapping("/klass/{klassId}/is-collaborant")
    public boolean klassHasCollaborant(@PathVariable(value = "klassId") Long id){
        Klass fromBase = klassService.findById(id);
        return fromBase.isCollaborants();
    }
}
