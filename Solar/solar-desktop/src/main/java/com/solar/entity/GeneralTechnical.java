/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solar.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author bibek
 */
@Data
@javax.persistence.Entity
@Table(name = "general_technicals")
public class GeneralTechnical {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String solarId;
    private String bdaType;
    private String assType;
    private String installer;
    private String bdaId;
    private String assId;
    private double systemCapacity;
    private String installDate;
    private String adBsDate;

}
