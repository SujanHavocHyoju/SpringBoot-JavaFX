/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solar.domain;

/**
 *
 * @author bibek
 */
public enum CameraType {
    APPLICATION_FIRST_FORM("application_first_form.jpg"),
    APPLICATION_SECOND_FORM("application_second_form.jpg"),
    BATTERY_VOUCHER("battery_voucher.jpg"),
    BILLING_INVOICE("billing_invoice.jpg"),
    MUCHULKA("muchulka.jpg");
    private final String filename;

    private CameraType(String filename) {
        this.filename = filename;
    }

    public String getFileName() {
        return filename;
    }
}
