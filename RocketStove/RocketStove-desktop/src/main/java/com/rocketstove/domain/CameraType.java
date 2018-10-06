/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.domain;

/**
 *
 * @author bibek
 */
public enum CameraType {
    APPLICATION_FIRST_FORM("subsidy_first_form.jpg"),
    APPLICATION_SECOND_FORM("subsidy_second_form.jpg"),
    STOVE_ID("stove_id.jpg"),
    STOVE_HANDOVER("stove_handover.jpg"),
    CITIZEN_SHIP_FRONT("citizen_ship_front.jpg"),
    CITIZEN_SHIP_BACK("citizen_ship_back.jpg");
    private final String filename;

    private CameraType(String filename) {
        this.filename = filename;
    }

    public String getFileName() {
        return filename;
    }
}
