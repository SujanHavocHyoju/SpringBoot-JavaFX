/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solar.domain;

import java.util.List;

/**
 *
 * @author SUJAN
 */
public class Manufacturer {
    private String manufacturerId;
    private String manufacturerName;
    private String manufacturerListId;
    private List<StoveModel> stoveModel;

    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getManufacturerListId() {
        return manufacturerListId;
    }

    public void setManufacturerListId(String manufacturerListId) {
        this.manufacturerListId = manufacturerListId;
    }

    public List<StoveModel> getStoveModel() {
        return stoveModel;
    }

    public void setStoveModel(List<StoveModel> stoveModel) {
        this.stoveModel = stoveModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Manufacturer that = (Manufacturer) o;

        if (manufacturerId != null ? !manufacturerId.equals(that.manufacturerId) : that.manufacturerId != null)
            return false;
        if (manufacturerName != null ? !manufacturerName.equals(that.manufacturerName) : that.manufacturerName != null)
            return false;
        if (manufacturerListId != null ? !manufacturerListId.equals(that.manufacturerListId) : that.manufacturerListId != null)
            return false;
        return stoveModel != null ? stoveModel.equals(that.stoveModel) : that.stoveModel == null;
    }

    @Override
    public int hashCode() {
        int result = manufacturerId != null ? manufacturerId.hashCode() : 0;
        result = 31 * result + (manufacturerName != null ? manufacturerName.hashCode() : 0);
        result = 31 * result + (manufacturerListId != null ? manufacturerListId.hashCode() : 0);
        result = 31 * result + (stoveModel != null ? stoveModel.hashCode() : 0);
        return result;
    }
}
