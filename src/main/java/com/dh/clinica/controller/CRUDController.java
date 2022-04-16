package com.dh.clinica.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface CRUDController<T> {

    ResponseEntity<?> findById(@PathVariable Integer id);

    ResponseEntity<?> create(@RequestBody T t);

    ResponseEntity<?> update(@RequestBody T t, Integer id);

    ResponseEntity<String> deleteById(@PathVariable Integer id);

    ResponseEntity<?> findAll();
}
