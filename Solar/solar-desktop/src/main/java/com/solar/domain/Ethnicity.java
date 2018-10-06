/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solar.domain;

/**
 *
 * @author SUJAN
 */
public class Ethnicity {
    private String ethnicityId;
    private String ethnicityName;
    private String ethnicityListId;

    public String getEthnicityId() {
        return ethnicityId;
    }

    public void setEthnicityId(String ethnicityId) {
        this.ethnicityId = ethnicityId;
    }

    public String getEthnicityName() {
        return ethnicityName;
    }

    public void setEthnicityName(String ethnicityName) {
        this.ethnicityName = ethnicityName;
    }

    public String getEthnicityListId() {
        return ethnicityListId;
    }

    public void setEthnicityListId(String ethnicityListId) {
        this.ethnicityListId = ethnicityListId;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ethnicity ethnicity = (Ethnicity) o;

        return ethnicityId != null ? ethnicityId.equals(ethnicity.ethnicityId) : ethnicity.ethnicityId == null;
    }

    @Override
    public int hashCode() {
        return ethnicityId != null ? ethnicityId.hashCode() : 0;
    }
}
