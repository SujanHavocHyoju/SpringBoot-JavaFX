/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.repository;

import com.rocketstove.entity.StarterFormEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author bibek
 */
public interface StarterFormRepository extends JpaRepository<StarterFormEntity, Long> {

    public StarterFormEntity findByRocketId(String rocketId);

}
