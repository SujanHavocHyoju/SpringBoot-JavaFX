package com.rocketstove.domain;

import java.io.Serializable;

/**
 * Created by bibek on 2/10/18.
 */
public class GeneralInformationI implements Serializable , Domain {
    private String rocketId;
    private String ownerName;
    private String husbandWifeName;
    private String citizenShip;
    private String callingName;
    private String gender;
    private String citizenDistrict;
    private String fatherMotherName;
    private String ethinicity;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GeneralInformationI that = (GeneralInformationI) o;

        if (!rocketId.equals(that.rocketId)) return false;
        return citizenShip.equals(that.citizenShip);
    }

    @Override
    public int hashCode() {
        int result = rocketId.hashCode();
        result = 31 * result + citizenShip.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "GeneralInformationI{" +
                "rocketId='" + rocketId + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", husbandWifeName='" + husbandWifeName + '\'' +
                ", citizenShip='" + citizenShip + '\'' +
                ", callingName='" + callingName + '\'' +
                ", gender='" + gender + '\'' +
                ", citizenDistrict='" + citizenDistrict + '\'' +
                ", fatherMotherName='" + fatherMotherName + '\'' +
                ", ethinicity='" + ethinicity + '\'' +
                '}';
    }
}
