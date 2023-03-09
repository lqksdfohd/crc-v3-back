package com.app.crc.controlleurs;

import com.app.crc.dtos.KlassDto;
import com.app.crc.entites.Klass;
import com.app.crc.services.KlassService;
import com.app.crc.services.MapstructService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KlassControlleur {

    @Autowired
    private KlassService klassService;

    @Autowired
    private MapstructService mapstructService;

    @GetMapping("/klass/{klassId}")
    public KlassDto recupererKlassParId(@PathVariable("klassId") long id){
        Klass temp = klassService.findKlassById(id);
        KlassDto dto = mapstructService.klassVersKlassDto(temp);
        return dto;
    }
}
