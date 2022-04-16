package com.dh.clinica.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private String resource;
    private String field;
    private Integer valueField;

    public ResourceNotFoundException(String resource, String field, Integer valueField) {
        super(String.format("%s No encontrado con : %s : '%s'", resource, field, valueField));
        this.resource = resource;
        this.field = field;
        this.valueField = valueField;
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
