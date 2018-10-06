package com.rocketstove.domain;

/**
 * Created by bibek on 2/10/18.
 */
public class AggregateForm implements Domain{
    private long id;
    private String rocketId;
    private long uploadedCloudOn;
    private String rocketWebId;
    private String rocketWebHtmlId;
    private String message;
    private StarterForm starterForm;
    private GeneralInformationI generalForm;
    private CurrentAddress currentAddressForm;
    private PermanentAddress permanentAddressForm;
    private Technical technicalForm;
    private Investment investmentForm;
    private int failedAt;
    private boolean isCompleted = false;
    private boolean isUploadedToServer = false;
    private boolean isFailedToAutomated = false;
    private boolean isReviewed = false;
    private boolean manualRequired =false;
    private boolean isAutomationReady = false;
    private boolean isWebAutomationSucceed=false;
    
    public AggregateForm() {
    }

    public AggregateForm(StarterForm starterForm, GeneralInformationI generalForm, CurrentAddress currentAddressForm, PermanentAddress permanentAddressForm, Technical technicalForm, Investment investmentForm) {
        this.starterForm = starterForm;
        this.generalForm = generalForm;
        this.currentAddressForm = currentAddressForm;
        this.permanentAddressForm = permanentAddressForm;
        this.technicalForm = technicalForm;
        this.investmentForm = investmentForm;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public StarterForm getStarterForm() {
        return starterForm;
    }

    public void setStarterForm(StarterForm starterForm) {
        this.starterForm = starterForm;
    }

    public GeneralInformationI getGeneralForm() {
        return generalForm;
    }

    public void setGeneralForm(GeneralInformationI generalForm) {
        this.generalForm = generalForm;
    }

    public CurrentAddress getCurrentAddressForm() {
        return currentAddressForm;
    }

    public void setCurrentAddressForm(CurrentAddress currentAddressForm) {
        this.currentAddressForm = currentAddressForm;
    }

    public PermanentAddress getPermanentAddressForm() {
        return permanentAddressForm;
    }

    public void setPermanentAddressForm(PermanentAddress permanentAddressForm) {
        this.permanentAddressForm = permanentAddressForm;
    }

    public Technical getTechnicalForm() {
        return technicalForm;
    }

    public void setTechnicalForm(Technical technicalForm) {
        this.technicalForm = technicalForm;
    }

    public Investment getInvestmentForm() {
        return investmentForm;
    }

    public void setInvestmentForm(Investment investmentForm) {
        this.investmentForm = investmentForm;
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

    public boolean isUploadedToServer() {
        return isUploadedToServer;
    }

    public void setUploadedToServer(boolean uploadedToServer) {
        isUploadedToServer = uploadedToServer;
    }

    public boolean isFailedToAutomated() {
        return isFailedToAutomated;
    }

    public void setFailedToAutomated(boolean failedToAutomated) {
        isFailedToAutomated = failedToAutomated;
    }

    public boolean isReviewed() {
        return isReviewed;
    }

    public void setReviewed(boolean reviewed) {
        isReviewed = reviewed;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getRocketWebHtmlId() {
        return rocketWebHtmlId;
    }

    public void setRocketWebHtmlId(String rocketWebHtmlId) {
        this.rocketWebHtmlId = rocketWebHtmlId;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AggregateForm that = (AggregateForm) o;

        if (uploadedCloudOn != that.uploadedCloudOn) {
            return false;
        }
        return rocketId.equals(that.rocketId);
    }

    @Override
    public int hashCode() {
        int result = rocketId.hashCode();
        result = 31 * result + (int) (uploadedCloudOn ^ (uploadedCloudOn >>> 32));
        return result;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isManualRequired() {
        return manualRequired;
    }

    public void setManualRequired(boolean manualRequired) {
        this.manualRequired = manualRequired;
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
    
    @Override
    public String toString() {
        return "AggregateForm{"
                + "starterForm=" + starterForm
                + ", generalForm=" + generalForm
                + ", currentAddressForm=" + currentAddressForm
                + ", permanentAddressForm=" + permanentAddressForm
                + ", technicalForm=" + technicalForm
                + ", investmentForm=" + investmentForm
                + ", isCompleted=" + isCompleted
                + '}';
    }
    
}
