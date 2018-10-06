/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solar.domain;

import com.solar.entity.Attachment;
import com.solar.entity.Banking;
import com.solar.entity.EndUse;
import com.solar.entity.Investment;
import lombok.Data;

/**
 *
 * @author bibek
 */
@Data
public class ThirdForm {
    private Investment investment;

     private Banking banking;

     private EndUse endUse;
 
     private Attachment attachment;
}
