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
public class StoveModel {
    private String stovemodelId;
    private String stovemodelName;
    private String stovemodelListId;

    public String getStovemodelId() {
        return stovemodelId;
    }

    public void setStovemodelId(String stovemodelId) {
        this.stovemodelId = stovemodelId;
    }

    public String getStovemodelName() {
        return stovemodelName;
    }

    public void setStovemodelName(String stovemodelName) {
        this.stovemodelName = stovemodelName;
    }

    public String getStovemodelListId() {
        return stovemodelListId;
    }

    public void setStovemodelListId(String stovemodelListId) {
        this.stovemodelListId = stovemodelListId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StoveModel that = (StoveModel) o;

        if (stovemodelId != null ? !stovemodelId.equals(that.stovemodelId) : that.stovemodelId != null) return false;
        if (stovemodelName != null ? !stovemodelName.equals(that.stovemodelName) : that.stovemodelName != null)
            return false;
        return stovemodelListId != null ? stovemodelListId.equals(that.stovemodelListId) : that.stovemodelListId == null;
    }

    @Override
    public int hashCode() {
        int result = stovemodelId != null ? stovemodelId.hashCode() : 0;
        result = 31 * result + (stovemodelName != null ? stovemodelName.hashCode() : 0);
        result = 31 * result + (stovemodelListId != null ? stovemodelListId.hashCode() : 0);
        return result;
    }
}
