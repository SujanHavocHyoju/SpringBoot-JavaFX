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
public class VPNP {
    private String addressId;
    private String addressName;
    private String addressListId;


    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getAddressListId() {
        return addressListId;
    }

    public void setAddressListId(String addressListId) {
        this.addressListId = addressListId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VPNP vpnp = (VPNP) o;

        if (addressId != null ? !addressId.equals(vpnp.addressId) : vpnp.addressId != null) return false;
        if (addressName != null ? !addressName.equals(vpnp.addressName) : vpnp.addressName != null) return false;
        return addressListId != null ? addressListId.equals(vpnp.addressListId) : vpnp.addressListId == null;
    }

    @Override
    public int hashCode() {
        int result = addressId != null ? addressId.hashCode() : 0;
        result = 31 * result + (addressName != null ? addressName.hashCode() : 0);
        result = 31 * result + (addressListId != null ? addressListId.hashCode() : 0);
        return result;
    }
}

