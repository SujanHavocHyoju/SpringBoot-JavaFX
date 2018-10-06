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
@Table(name = "owners")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String solarId;
    private String name;
    private String gender;
    private String ethnicity;
    private String callingName;
    private String citizenShipNumber;
    private String targetGroup;
    private String mobileNumber;
    private String citizenshipDistrict;
    private String husbandOrFatherName;
    private String neighbourName;
    private String husbandOrFatherMobileNumber;
    private String neighbourMobileNumber;
}
