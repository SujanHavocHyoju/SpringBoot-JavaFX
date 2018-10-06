package com.solar.domain;

/**
 * Created by bibek on 2/10/18.
 */
public class StarterForm {
    private String solarId;
    private long picTakenTimeStramp;
    private String sAFormFirstImg;
    private String sAFormSecondImg;
    private String billInvoiceImage;
    private String batteryVoucherImage;
    private String muchulkaImage;
    private boolean isUpdated = false;
    private boolean isFilledLater = false;
    private boolean isUploaded = false;
    private boolean isFailedUploaded = false;

    public String getSolarId() {
        return solarId;
    }

    public void setSolarId(String solarId) {
        this.solarId = solarId;
    }

    public long getPicTakenTimeStramp() {
        return picTakenTimeStramp;
    }

    public void setPicTakenTimeStramp(long picTakenTimeStramp) {
        this.picTakenTimeStramp = picTakenTimeStramp;
    }

    public String getsAFormFirstImg() {
        return sAFormFirstImg;
    }

    public void setsAFormFirstImg(String sAFormFirstImg) {
        this.sAFormFirstImg = sAFormFirstImg;
    }

    public String getsAFormSecondImg() {
        return sAFormSecondImg;
    }

    public void setsAFormSecondImg(String sAFormSecondImg) {
        this.sAFormSecondImg = sAFormSecondImg;
    }

    public String getBillInvoiceImage() {
        return billInvoiceImage;
    }

    public void setBillInvoiceImage(String billInvoiceImage) {
        this.billInvoiceImage = billInvoiceImage;
    }

    public String getBatteryVoucherImage() {
        return batteryVoucherImage;
    }

    public void setBatteryVoucherImage(String batteryVoucherImage) {
        this.batteryVoucherImage = batteryVoucherImage;
    }

    public String getMuchulkaImage() {
        return muchulkaImage;
    }

    public void setMuchulkaImage(String muchulkaImage) {
        this.muchulkaImage = muchulkaImage;
    }


    public boolean isFilledLater() {
        return isFilledLater;
    }

    public void setFilledLater(boolean filledLater) {
        isFilledLater = filledLater;
    }

    public boolean isUploaded() {
        return isUploaded;
    }

    public void setUploaded(boolean uploaded) {
        isUploaded = uploaded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StarterForm that = (StarterForm) o;

        return solarId != null ? solarId.equals(that.solarId) : that.solarId == null;
    }

    @Override
    public int hashCode() {
        return solarId != null ? solarId.hashCode() : 0;
    }


    public boolean isUpdated() {
        return isUpdated;
    }

    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }

    public boolean isFailedUploaded() {
        return isFailedUploaded;
    }

    public void setFailedUploaded(boolean failedUploaded) {
        isFailedUploaded = failedUploaded;
    }



    @Override
    public String toString() {
        return "StarterForm{" +
                "solarId='" + solarId + '\'' +
                ", picTakenTimeStramp=" + picTakenTimeStramp +
                ", sAFormFirstImg='" + sAFormFirstImg + '\'' +
                ", sAFormSecondImg='" + sAFormSecondImg + '\'' +
                ", billInvoiceImage='" + billInvoiceImage + '\'' +
                ", batteryVoucherImage='" + batteryVoucherImage + '\'' +
                ", muchulkaImage='" + muchulkaImage + '\'' +
                ", isUpdated=" + isUpdated +
                ", isFilledLater=" + isFilledLater +
                ", isUploaded=" + isUploaded +
                '}';
    }
}
