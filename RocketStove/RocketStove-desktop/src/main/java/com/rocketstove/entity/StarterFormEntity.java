/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author bibek
 */
@Entity
@Table(name = "starter_forms")
public class StarterFormEntity implements Serializable,com.rocketstove.entity.Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String surveyName;
    private String rocketId;
    private String areaClass;
    private long picTakenTimeStramp;
    private String sAFormFirstImg;
    private String sAFormSecondImg;
    private String stoveIDImg;
    private String stoveHandOverImg;
    private String citizenFrontImg;
    private String citizenBackImg;
    private boolean isUpdated = false;
    private boolean isFilledLater = false;
    private boolean isUploaded = false;
    private boolean isCompleted = false;
    private boolean isFailedUploaded = false;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public String getRocketId() {
        return rocketId;
    }

    public void setRocketId(String rocketId) {
        this.rocketId = rocketId;
    }

    public String getAreaClass() {
        return areaClass;
    }

    public void setAreaClass(String areaClass) {
        this.areaClass = areaClass;
    }

    public long getPicTakenTimeStramp() {
        return picTakenTimeStramp;
    }

    public void setPicTakenTimeStramp(long picTakenTimeStramp) {
        this.picTakenTimeStramp = picTakenTimeStramp;
    }

    public String getsAFormFirstImg() {
        return sAFormFirstImg;
    }

    public void setsAFormFirstImg(String sAFormFirstImg) {
        this.sAFormFirstImg = sAFormFirstImg;
    }

    public String getsAFormSecondImg() {
        return sAFormSecondImg;
    }

    public void setsAFormSecondImg(String sAFormSecondImg) {
        this.sAFormSecondImg = sAFormSecondImg;
    }

    public String getStoveIDImg() {
        return stoveIDImg;
    }

    public void setStoveIDImg(String stoveIDImg) {
        this.stoveIDImg = stoveIDImg;
    }

    public String getStoveHandOverImg() {
        return stoveHandOverImg;
    }

    public void setStoveHandOverImg(String stoveHandOverImg) {
        this.stoveHandOverImg = stoveHandOverImg;
    }

    public String getCitizenFrontImg() {
        return citizenFrontImg;
    }

    public void setCitizenFrontImg(String citizenFrontImg) {
        this.citizenFrontImg = citizenFrontImg;
    }

    public String getCitizenBackImg() {
        return citizenBackImg;
    }

    public void setCitizenBackImg(String citizenBackImg) {
        this.citizenBackImg = citizenBackImg;
    }

    public boolean isIsUpdated() {
        return isUpdated;
    }

    public void setIsUpdated(boolean isUpdated) {
        this.isUpdated = isUpdated;
    }

    public boolean isIsFilledLater() {
        return isFilledLater;
    }

    public void setIsFilledLater(boolean isFilledLater) {
        this.isFilledLater = isFilledLater;
    }

    public boolean isIsUploaded() {
        return isUploaded;
    }

    public void setIsUploaded(boolean isUploaded) {
        this.isUploaded = isUploaded;
    }

    public boolean isIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public boolean isIsFailedUploaded() {
        return isFailedUploaded;
    }

    public void setIsFailedUploaded(boolean isFailedUploaded) {
        this.isFailedUploaded = isFailedUploaded;
    }

    @Override
    public String toString() {
        return "StarterForm{"
                + "rocketId='" + rocketId + '\''
                + ", areaClass='" + areaClass + '\''
                + ", picTakenTimeStramp=" + picTakenTimeStramp
                + ", sAFormFirstImg='" + sAFormFirstImg + '\''
                + ", sAFormSecondImg='" + sAFormSecondImg + '\''
                + ", stoveIDImg='" + stoveIDImg + '\''
                + ", stoveHandOverImg='" + stoveHandOverImg + '\''
                + ", citizenFrontImg='" + citizenFrontImg + '\''
                + ", citizenBackImg='" + citizenBackImg + '\''
                + ", isUpdated=" + isUpdated
                + ", isFilledLater=" + isFilledLater
                + ", isUploaded=" + isUploaded
                + '}';
    }
}
