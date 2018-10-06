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
@Table(name = "investments")
public class InvestmentEntity implements Serializable,com.rocketstove.entity.Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double totalStoveCost;
    private double subsidySupport;
    private double subsidyAmount;
    private double userAmount;
    private String rocketId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getTotalStoveCost() {
        return totalStoveCost;
    }

    public void setTotalStoveCost(double totalStoveCost) {
        this.totalStoveCost = totalStoveCost;
    }

    public double getSubsidySupport() {
        return subsidySupport;
    }

    public void setSubsidySupport(double subsidySupport) {
        this.subsidySupport = subsidySupport;
    }

    public double getSubsidyAmount() {
        return subsidyAmount;
    }

    public void setSubsidyAmount(double subsidyAmount) {
        this.subsidyAmount = subsidyAmount;
    }

    public double getUserAmount() {
        return userAmount;
    }

    public void setUserAmount(double userAmount) {
        this.userAmount = userAmount;
    }

    public String getRocketId() {
        return rocketId;
    }

    public void setRocketId(String rocketId) {
        this.rocketId = rocketId;
    }

}
