/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solar.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;

/**
 *
 * @author bibek
 */
@Data
@javax.persistence.Entity
@Table(name = "solar_forms")
public class SolarForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String solarId;
    private long uploadedCloudOn;
    private String solarWebId;
    private String solarWebHtmlId;
    @Column(columnDefinition = "TEXT")
    private String message;
    private int failedAt;
    private String areaClass;
    private boolean isCompleted = false;
    private boolean isUploadedToServer = false;
    private boolean isFailedToAutomated = false;
    private boolean isReviewed = false;
    private boolean isAutomationReady = false;
    private boolean isWebAutomationSucceed = false;
    @Transient
    private Owner owner;
    @Transient
    private CurrentAddress currentAddress;
    @Transient
    private PermanentAddress permanentAddress;
    @Transient
    private GeneralTechnical generalTechnical;
    @Transient
    private PanelTechnical panelTechnical;
    @Transient
    private BatteryTechnical batteryTechnical;
    @Transient
    private ChargeControllerTechnical chargeControllerTechnical;
    @Transient
    private LampTechnical lampTechnical;
    @Transient
    private Investment investmentEntity;
    @Transient
    private Banking banking;
    @Transient
    private EndUse endUse;
    @Transient
    private Attachment attachment;

}
