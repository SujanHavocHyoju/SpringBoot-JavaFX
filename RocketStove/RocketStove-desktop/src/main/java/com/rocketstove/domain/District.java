/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.domain;

import java.util.List;

/**
 *
 * @author SUJAN
 */
public class District {
    private String districtId;
    private String districtName;
    private String districtListId;
    private List<VPNP> vpnp;
    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictListId() {
        return districtListId;
    }

    public void setDistrictListId(String districtListId) {
        this.districtListId = districtListId;
    }

    public List<VPNP> getVpnp() {
        return vpnp;
    }

    public void setVpnp(List<VPNP> vpnp) {
        this.vpnp = vpnp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        District district = (District) o;

        if (districtId != null ? !districtId.equals(district.districtId) : district.districtId != null) return false;
        if (districtName != null ? !districtName.equals(district.districtName) : district.districtName != null)
            return false;
        if (districtListId != null ? !districtListId.equals(district.districtListId) : district.districtListId != null)
            return false;
        return vpnp != null ? vpnp.equals(district.vpnp) : district.vpnp == null;
    }

    @Override
    public int hashCode() {
        int result = districtId != null ? districtId.hashCode() : 0;
        result = 31 * result + (districtName != null ? districtName.hashCode() : 0);
        result = 31 * result + (districtListId != null ? districtListId.hashCode() : 0);
        result = 31 * result + (vpnp != null ? vpnp.hashCode() : 0);
        return result;
    }
}
