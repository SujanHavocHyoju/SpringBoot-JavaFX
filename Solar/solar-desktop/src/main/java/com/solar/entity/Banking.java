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
@Table(name = "banking")
public class Banking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String solarId;
    private double loanAmount;
    private String institutionAddress;
    private double interestRate;
    private String insitutionName;
    private String institutionType;
    private String maturityPeriod;
    private String approvalDate;
    private String adToBs;
}
