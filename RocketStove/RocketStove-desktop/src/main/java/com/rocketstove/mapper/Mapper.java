/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.mapper;

import com.rocketstove.domain.Domain;
import com.rocketstove.entity.Entity;
import java.util.Optional;

/**
 *
 * @author bibek
 * @param <T>
 * @param <E>
 */
public interface Mapper<T extends Domain,E extends Entity>{
    Optional<E> mapFormToEntity(Optional<T> t);
    default Optional<T> mapEntityToForm(Optional<E> e){
        return null;
    }
}
