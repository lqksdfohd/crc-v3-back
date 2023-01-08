package com.app.crc.advices;

import com.app.crc.advices.utils.Erreur;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControlleurAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Erreur> gererException(IllegalArgumentException ex, WebRequest request){
        Erreur erreur = new Erreur();
        erreur.setCode("400");
        erreur.setRaison(ex.getMessage());
        ResponseEntity<Erreur> responseEntity = new ResponseEntity<Erreur>(erreur, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }
}
