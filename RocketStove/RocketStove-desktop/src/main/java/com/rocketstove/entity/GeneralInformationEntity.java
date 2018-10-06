package com.rocketstove.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by bibek on 2/10/18.
 */
@Entity
@Table(name = "general_informations")
public class GeneralInformationEntity implements Serializable,com.rocketstove.entity.Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String rocketId;
    private String ownerName;
    private String husbandWifeName;
    private String citizenShip;
    private String callingName;
    private String gender;
    private String citizenDistrict;
    private String fatherMotherName;
    private String ethinicity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRocketId() {
        return rocketId;
    }

    public void setRocketId(String rocketId) {
        this.rocketId = rocketId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getHusbandWifeName() {
        return husbandWifeName;
    }

    public void setHusbandWifeName(String husbandWifeName) {
        this.husbandWifeName = husbandWifeName;
    }

    public String getCitizenShip() {
        return citizenShip;
    }

    public void setCitizenShip(String citizenShip) {
        this.citizenShip = citizenShip;
    }

    public String getCallingName() {
        return callingName;
    }

    public void setCallingName(String callingName) {
        this.callingName = callingName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCitizenDistrict() {
        return citizenDistrict;
    }

    public void setCitizenDistrict(String citizenDistrict) {
        this.citizenDistrict = citizenDistrict;
    }

    public String getFatherMotherName() {
        return fatherMotherName;
    }

    public void setFatherMotherName(String fatherMotherName) {
        this.fatherMotherName = fatherMotherName;
    }

    public String getEthinicity() {
        return ethinicity;
    }

    public void setEthinicity(String ethinicity) {
        this.ethinicity = ethinicity;
    }

}
