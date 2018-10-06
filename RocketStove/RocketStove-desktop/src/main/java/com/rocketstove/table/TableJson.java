/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.table;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author SUJAN
 */
public class TableJson {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty StoveSerialId;

    public TableJson(Integer id, String StoveSerialId) {
        super();
        this.id = new SimpleIntegerProperty(id);
        this.StoveSerialId = new SimpleStringProperty(StoveSerialId);
    }

    public int getId() {
        return id.get();
    }

    public String getStoveSerialId() {
        return StoveSerialId.get();
    }
    
}
