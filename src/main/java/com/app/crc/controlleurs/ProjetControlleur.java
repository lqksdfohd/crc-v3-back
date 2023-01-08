package com.app.crc.controlleurs;


import com.app.crc.dtos.ProjetDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjetControlleur {

    @PostMapping(value = "/projet",consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ProjetDto creerProjet(@RequestBody ProjetDto dto){
        ProjetDto output = new ProjetDto();
        output.setNomProjet(dto.getNomProjet());
        output.setId(101);
        return output;
    }
}
