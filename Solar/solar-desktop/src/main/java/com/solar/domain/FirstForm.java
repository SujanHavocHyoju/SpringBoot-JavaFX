/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solar.domain;

import com.solar.entity.CurrentAddress;
import com.solar.entity.Owner;
import com.solar.entity.PermanentAddress;
import lombok.Data;

/**
 *
 * @author bibek
 */
@Data
public class FirstForm {
    
    private String solarId;
   
    private String areaClass;
  
    private Owner owner;
    
    private CurrentAddress currentAddress;
    
    private PermanentAddress permanentAddress;

}
