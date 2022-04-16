package com.dh.clinica.service;

import java.util.List;

public interface ICRUDService<T> {

    T findById(Integer id);

    T create(T t);

    void deleteById(Integer id);

    T update(T t, Integer id);

    List<T> findAll();
}
