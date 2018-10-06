package com.rocketstove.domain;

import java.io.Serializable;

/**
 * Created by bibek on 2/10/18.
 */
public class StarterForm implements Serializable, Domain  {
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
    private boolean isFailedUploaded= false;

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

    public boolean isFilledLater() {
        return isFilledLater;
    }

    public void setFilledLater(boolean filledLater) {
        isFilledLater = filledLater;
    }

    public boolean isUploaded() {
        return isUploaded;
    }

    public void setUploaded(boolean uploaded) {
        isUploaded = uploaded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StarterForm that = (StarterForm) o;

        return rocketId != null ? rocketId.equals(that.rocketId) : that.rocketId == null;
    }

    @Override
    public int hashCode() {
        return rocketId != null ? rocketId.hashCode() : 0;
    }



    public boolean isUpdated() {
        return isUpdated;
    }

    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public boolean isFailedUploaded() {
        return isFailedUploaded;
    }

    public void setFailedUploaded(boolean failedUploaded) {
        isFailedUploaded = failedUploaded;
    }
    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }
   
    @Override
    public String toString() {
        return "StarterForm{" +
                "rocketId='" + rocketId + '\'' +
                ", areaClass='" + areaClass + '\'' +
                ", picTakenTimeStramp=" + picTakenTimeStramp +
                ", sAFormFirstImg='" + sAFormFirstImg + '\'' +
                ", sAFormSecondImg='" + sAFormSecondImg + '\'' +
                ", stoveIDImg='" + stoveIDImg + '\'' +
                ", stoveHandOverImg='" + stoveHandOverImg + '\'' +
                ", citizenFrontImg='" + citizenFrontImg + '\'' +
                ", citizenBackImg='" + citizenBackImg + '\'' +
                ", isUpdated=" + isUpdated +
                ", isFilledLater=" + isFilledLater +
                ", isUploaded=" + isUploaded +
                '}';
    }
}
