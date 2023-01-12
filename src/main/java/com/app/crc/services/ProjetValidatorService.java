package com.app.crc.services;

import com.app.crc.dtos.ProjetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class ProjetValidatorService {
    @Autowired
    private Validator validator;

    public Set<ConstraintViolation<ProjetDto>> validateProjet(ProjetDto dto){
        return validator.validate(dto);
    }
}
