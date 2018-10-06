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
@Table(name = "lamp_technical")
public class LampTechnical {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String solarId;
    private String manufacturer;
    private String model;
    private double capacityW;
}
