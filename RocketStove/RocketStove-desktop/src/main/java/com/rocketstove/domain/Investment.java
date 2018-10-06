package com.rocketstove.domain;

import java.io.Serializable;

/**
 * Created by bibek on 2/10/18.
 */
public class Investment implements Serializable, Domain  {
    private double totalStoveCost;
    private double subsidySupport;
    private double subsidyAmount;
    private double userAmount;
    private String rocketId;

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
