/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.utils;

/**
 *
 * @author SUJAN
 */
import com.google.gson.reflect.TypeToken;
import com.rocketstove.domain.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;

public class LocalJsonUtils {

    //private static final String ASSEST_LOCATION = "C:\\Users\\SUJAN\\Documents\\NetBeansProjects\\RocketStove\\src\\main\\resources\\assets\\";
    public static List<District> loadDistrict(String location) {
        try {
            Type type = new TypeToken<List<District>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "district.json"), Charset.defaultCharset()), type);
        } catch (IOException E) {
            E.printStackTrace();
        }
        return null;
    }

    public static List<VPNP> loadVDCnp(String location) {
        try {
            Type type = new TypeToken<List<VPNP>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "VDC_NPList.json"), Charset.defaultCharset()), type);
        } catch (IOException e) {
        }
        return null;
    }

    public static List<VPNP> loadvpnp(String location) {
        try {
            Type type = new TypeToken<List<VPNP>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "VP_NPList.json"), Charset.defaultCharset()), type);
        } catch (IOException e) {
        }
        return null;
    }

    public static List<Ethnicity> loadEthinicity(String location) {
        try {
            Type type = new TypeToken<List<Ethnicity>>() {
            }.getType();
            String content = FileUtils.readFileToString(new File(location, "ethnicity.json"),Charset.defaultCharset());
            return JsonUtils.fromJsonToList(content, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Manufacturer> loadManufacturer(String location) {
        try {
            Type type = new TypeToken<List<Manufacturer>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "manufacturer.json"), Charset.defaultCharset()), type);
        } catch (IOException e) {
        }
        return null;
    }

    public static List<Gender> loadGender(String location) {
        try {
            Type type = new TypeToken<List<Gender>>() {
            }.getType();
            return JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(location, "gender.json"), Charset.defaultCharset()), type);
        } catch (IOException e) {
        }
        return null;
    }

    public static List<AreaClass> loadAreaClass(String location) {
        try {
            Type type = new TypeToken<List<AreaClass>>() {
            }.getType();
            String json
                    = FileUtils.readFileToString(new File(location, "areaclass.json"), Charset.defaultCharset());
            return JsonUtils.fromJsonToList(json, type);
        } catch (IOException e) {
        }
        return null;
    }

    public static AggregateForm loadAggregateForm(File file) {
        try {
            return JsonUtils.fromJsonToObj(FileUtils.readFileToString(file, Charset.defaultCharset()), AggregateForm.class);
        } catch (IOException e) {
        }
        return null;
    }

}