package com.dh.clinica.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Mapper {


    //Mapper de objeto a JSON
    public static <T> String mapObjectToJson(T t) throws JsonProcessingException {
        return getObjectMapper().writeValueAsString(t);
    }

    //Mapper de JSON a objeto
    public static <T> T mapJsonToObject(String json, Class<T> t) throws JsonProcessingException {
        return getObjectMapper().readValue(json, t);
    }


    private static ObjectMapper getObjectMapper(){
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new JavaTimeModule());
    }
}
