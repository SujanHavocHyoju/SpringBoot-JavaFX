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
@Table(name = "current_addresses")
public class CurrentAddress  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String solarId;
    private String district;
    private String wardNumber;
    private String vpNp;
    private String village;
}
