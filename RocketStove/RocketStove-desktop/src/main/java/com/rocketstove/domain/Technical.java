package com.rocketstove.domain;

import java.io.Serializable;

/**
 * Created by bibek on 2/10/18.
 */
public class Technical implements Serializable , Domain {
    private String rocketId;
    private String manufacturer;
    private String engraveNumber;
    private String model;
    private String installDate;
    private String adToBS;

    public String getRocketId() {
        return rocketId;
    }

    public void setRocketId(String rocketId) {
        this.rocketId = rocketId;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getEngraveNumber() {
        return engraveNumber;
    }

    public void setEngraveNumber(String engraveNumber) {
        this.engraveNumber = engraveNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getInstallDate() {
        return installDate;
    }

    public void setInstallDate(String installDate) {
        this.installDate = installDate;
    }

    public String getAdToBS() {
        return adToBS;
    }

    public void setAdToBS(String adToBS) {
        this.adToBS = adToBS;
    }
}
