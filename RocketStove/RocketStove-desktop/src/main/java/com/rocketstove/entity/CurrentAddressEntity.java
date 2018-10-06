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
@Table(name = "current_addresses")
public class CurrentAddressEntity implements Serializable,com.rocketstove.entity.Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String district;
    private String wardNumber;
    private String nearestRoom;
    private String vpNp;
    private String village;
    private String houseNumber;
    private String rocketId;

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWardNumber() {
        return wardNumber;
    }

    public void setWardNumber(String wardNumber) {
        this.wardNumber = wardNumber;
    }

    public String getNearestRoom() {
        return nearestRoom;
    }

    public void setNearestRoom(String nearestRoom) {
        this.nearestRoom = nearestRoom;
    }

    public String getVpNp() {
        return vpNp;
    }

    public void setVpNp(String vpNp) {
        this.vpNp = vpNp;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getRocketId() {
        return rocketId;
    }

    public void setRocketId(String rocketId) {
        this.rocketId = rocketId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
