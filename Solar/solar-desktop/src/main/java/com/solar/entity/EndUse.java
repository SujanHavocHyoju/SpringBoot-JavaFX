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
@Table(name = "end_use")
public class EndUse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String solarId;
    private int numberOfBulb;
    private Confirmation mobileChargeOption=Confirmation.YES;
    private Confirmation radio = Confirmation.NO;
    private String otherConnection;
    private String typeOfBulb;
    private Confirmation radioChargeOption=Confirmation.NO;
    private Confirmation television = Confirmation.NO;

    public int getNumberOfBulb() {
        return numberOfBulb;
    }

    public void setNumberOfBulb(int numberOfBulb) {
        this.numberOfBulb = numberOfBulb;
    }

    public Confirmation getMobileChargeOption() {
        return mobileChargeOption;
    }

    public void setMobileChargeOption(Confirmation mobileChargeOption) {
        this.mobileChargeOption = mobileChargeOption;
    }

    public Confirmation getRadio() {
        return radio;
    }

    public void setRadio(Confirmation radio) {
        this.radio = radio;
    }

    public String getOtherConnection() {
        return otherConnection;
    }

    public void setOtherConnection(String otherConnection) {
        this.otherConnection = otherConnection;
    }

    public String getTypeOfBulb() {
        return typeOfBulb;
    }

    public void setTypeOfBulb(String typeOfBulb) {
        this.typeOfBulb = typeOfBulb;
    }

    public Confirmation getRadioChargeOption() {
        return radioChargeOption;
    }

    public void setRadioChargeOption(Confirmation radioChargeOption) {
        this.radioChargeOption = radioChargeOption;
    }

    public Confirmation getTelevision() {
        return television;
    }

    public void setTelevision(Confirmation television) {
        this.television = television;
    }
    
}
