/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author bibek
 */
@Entity
@Table(name = "aggregate_forms")
public class AggregateFormEntity implements Serializable, com.rocketstove.entity.Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String rocketId;
    private long uploadedCloudOn;
    private String message;
    private String rocketWebId;
    
    private String rocketWebHtmlId;
    @Transient
    private StarterFormEntity starterForm;
    @Transient
    private GeneralInformationEntity generalForm;
    @Transient
    private CurrentAddressEntity currentAddressForm;
    @Transient
    private PermanentAddressEntity permanentAddressForm;
    @Transient
    private TechnicalEntity technicalForm;
    @Transient
    private InvestmentEntity investmentForm;
    private int failedAt;
    private boolean isCompleted = false;
    private boolean isUploadedToServer = false;
    private boolean isFailedToAutomated = false;
    private boolean isReviewed = false;
    private boolean manualRequired = false;
    private boolean isAutomationReady = false;
    private boolean isWebAutomationSucceed = false;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRocketId() {
        return rocketId;
    }

    public void setRocketId(String rocketId) {
        this.rocketId = rocketId;
    }

    public long getUploadedCloudOn() {
        return uploadedCloudOn;
    }

    public void setUploadedCloudOn(long uploadedCloudOn) {
        this.uploadedCloudOn = uploadedCloudOn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StarterFormEntity getStarterForm() {
        return starterForm;
    }

    public void setStarterForm(StarterFormEntity starterForm) {
        this.starterForm = starterForm;
    }

    public GeneralInformationEntity getGeneralForm() {
        return generalForm;
    }

    public void setGeneralForm(GeneralInformationEntity generalForm) {
        this.generalForm = generalForm;
    }

    public CurrentAddressEntity getCurrentAddressForm() {
        return currentAddressForm;
    }

    public void setCurrentAddressForm(CurrentAddressEntity currentAddressForm) {
        this.currentAddressForm = currentAddressForm;
    }

    public PermanentAddressEntity getPermanentAddressForm() {
        return permanentAddressForm;
    }

    public void setPermanentAddressForm(PermanentAddressEntity permanentAddressForm) {
        this.permanentAddressForm = permanentAddressForm;
    }

    public TechnicalEntity getTechnicalForm() {
        return technicalForm;
    }

    public void setTechnicalForm(TechnicalEntity technicalForm) {
        this.technicalForm = technicalForm;
    }

    public InvestmentEntity getInvestmentForm() {
        return investmentForm;
    }

    public void setInvestmentForm(InvestmentEntity investmentForm) {
        this.investmentForm = investmentForm;
    }

    public boolean isIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public boolean isIsUploadedToServer() {
        return isUploadedToServer;
    }

    public void setIsUploadedToServer(boolean isUploadedToServer) {
        this.isUploadedToServer = isUploadedToServer;
    }

    public boolean isIsFailedToAutomated() {
        return isFailedToAutomated;
    }

    public void setIsFailedToAutomated(boolean isFailedToAutomated) {
        this.isFailedToAutomated = isFailedToAutomated;
    }

    public boolean isIsReviewed() {
        return isReviewed;
    }

    public void setIsReviewed(boolean isReviewed) {
        this.isReviewed = isReviewed;
    }

    public boolean isIsAutomationReady() {
        return isAutomationReady;
    }

    public void setIsAutomationReady(boolean isAutomationReady) {
        this.isAutomationReady = isAutomationReady;
    }

    public String getRocketWebId() {
        return rocketWebId;
    }

    public void setRocketWebId(String rocketWebId) {
        this.rocketWebId = rocketWebId;
    }

    public boolean isIsWebAutomationSucceed() {
        return isWebAutomationSucceed;
    }

    public void setIsWebAutomationSucceed(boolean isWebAutomationSucceed) {
        this.isWebAutomationSucceed = isWebAutomationSucceed;
    }

    public int getFailedAt() {
        return failedAt;
    }

    public void setFailedAt(int failedAt) {
        this.failedAt = failedAt;
    }

    public String getRocketWebHtmlId() {
        return rocketWebHtmlId;
    }

    public void setRocketWebHtmlId(String rocketWebHtmlId) {
        this.rocketWebHtmlId = rocketWebHtmlId;
    }

    public boolean isManualRequired() {
        return manualRequired;
    }

    public void setManualRequired(boolean manualRequired) {
        this.manualRequired = manualRequired;
    }
    
    
}
