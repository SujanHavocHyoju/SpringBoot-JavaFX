/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.repository;

import com.rocketstove.entity.CurrentAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author bibek
 */
public interface CurrentAddressRespository extends JpaRepository<CurrentAddressEntity, Long>{
    CurrentAddressEntity findByRocketId(String rocketId);
}
