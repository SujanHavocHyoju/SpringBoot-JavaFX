/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.ui;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author bibek
 */
public class SubmittedForm extends RecursiveTreeObject<SubmittedForm>{
    public StringProperty serialNumber;
    public StringProperty rocketIdWeb;
    public StringProperty stoveId;
    public StringProperty fullName;
    public StringProperty citizenShipNumber;
    public StringProperty citizenDistrict;
    //public StringProperty 
    public BooleanProperty isEditable;

    public SubmittedForm(int sn,String rocketWebId,String stoveId, String fullName, String citizenShipNumber, String citizenDistrict) {
        this.serialNumber = new SimpleStringProperty(String.valueOf(sn));
        this.rocketIdWeb = new SimpleStringProperty(rocketWebId);
        this.stoveId = new SimpleStringProperty(stoveId);
        this.fullName = new SimpleStringProperty(fullName);
        this.citizenShipNumber = new SimpleStringProperty(citizenShipNumber);
        this.citizenDistrict = new SimpleStringProperty(citizenDistrict);
        this.isEditable = new SimpleBooleanProperty(false);
    }

    public BooleanProperty getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(Boolean isEditable) {
        this.isEditable = new SimpleBooleanProperty(isEditable);
    }
    
    @Override
    public String toString() {
        return "Form{" + "stoveId=" + stoveId + ", fullName=" + fullName + ", citizenShipNumber=" + citizenShipNumber + ", citizenDistrict=" + citizenDistrict + '}';
    }

    public StringProperty getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(StringProperty serialNumber) {
        this.serialNumber = serialNumber;
    }

    
    
    public StringProperty getStoveId() {
        return stoveId;
    }

    public void setStoveId(StringProperty stoveId) {
        this.stoveId = stoveId;
    }

    public StringProperty getFullName() {
        return fullName;
    }

    public void setFullName(StringProperty fullName) {
        this.fullName = fullName;
    }

    public StringProperty getCitizenShipNumber() {
        return citizenShipNumber;
    }

    public void setCitizenShipNumber(StringProperty citizenShipNumber) {
        this.citizenShipNumber = citizenShipNumber;
    }

    public StringProperty getCitizenDistrict() {
        return citizenDistrict;
    }

    public void setCitizenDistrict(StringProperty citizenDistrict) {
        this.citizenDistrict = citizenDistrict;
    }

    public StringProperty getRocketIdWeb() {
        return rocketIdWeb;
    }

    public void setRocketIdWeb(StringProperty rocketIdWeb) {
        this.rocketIdWeb = rocketIdWeb;
    }
   
}
