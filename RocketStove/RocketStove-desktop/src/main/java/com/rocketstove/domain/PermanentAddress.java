/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.domain;

import java.io.Serializable;

/**
 *
 * @author SUJAN
 */
public class PermanentAddress implements Serializable , Domain {
    private String rocketId;
    private String vpNp;
    private String wardNumber;
    private String village;

    public String getVpNp() {
        return vpNp;
    }

    public void setVpNp(String vpNp) {
        this.vpNp = vpNp;
    }

    public String getWardNumber() {
        return wardNumber;
    }

    public void setWardNumber(String wardNumber) {
        this.wardNumber = wardNumber;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getRocketId() {
        return rocketId;
    }

    public void setRocketId(String rocketId) {
        this.rocketId = rocketId;
    }
}
