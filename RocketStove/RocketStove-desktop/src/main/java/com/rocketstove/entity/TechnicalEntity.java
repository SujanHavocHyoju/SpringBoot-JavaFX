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
@Table(name = "technicals")
public class TechnicalEntity implements Serializable,com.rocketstove.entity.Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String rocketId;
    private String manufacturer;
    private String engraveNumber;
    private String model;
    private String installDate;
    private String adToBS;

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
