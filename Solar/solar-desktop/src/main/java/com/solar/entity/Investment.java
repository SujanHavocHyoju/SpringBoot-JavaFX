package com.solar.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * Created by bibek on 2/10/18.
 */
@Data
@javax.persistence.Entity
@Table(name = "investments")
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String solarId;
    private double totalSystemCost;
    private double supportAmount;
    private double annualIncome;
    private double batteryAmount;
    private double subsidyAmount;
    private double userAmount;
    private String incomeSource;
    private double earthquakeSubsidy;

}
