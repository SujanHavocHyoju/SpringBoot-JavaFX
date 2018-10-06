/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.domain;

/**
 *
 * @author SUJAN
 */
public class AreaClass implements Domain {
    private String areaclassId;
    private String areaclassName;

    public String getAreaclassId() {
        return areaclassId;
    }

    public void setAreaclassId(String areaclassId) {
        this.areaclassId = areaclassId;
    }

    public String getAreaclassName() {
        return areaclassName;
    }

    public void setAreaclassName(String areaclassName) {
        this.areaclassName = areaclassName;
    }
}
