/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solar.repository;

import com.solar.entity.PermanentAddress;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author bibek
 */
public interface PermanentAddressRepo extends JpaRepository<PermanentAddress,Long>{
    PermanentAddress findBySolarId(String solarId);
}
