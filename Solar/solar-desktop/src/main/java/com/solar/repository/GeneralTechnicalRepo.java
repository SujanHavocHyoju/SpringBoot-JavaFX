/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solar.repository;

import com.solar.entity.GeneralTechnical;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author bibek
 */
public interface GeneralTechnicalRepo extends JpaRepository<GeneralTechnical,Long>{
    GeneralTechnical findBySolarId(String solarId);
}
