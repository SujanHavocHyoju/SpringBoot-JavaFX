/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solar.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.IOException;

import java.lang.reflect.Type;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author SUJAN
 */
@Slf4j
public class JsonUtils {

    /**
     * Null serialize is used because else Gson will ignore all null fields.
     */
    private static Gson gson = new GsonBuilder().setLenient().serializeNulls().create();
    

    /**
     * Made private because all methods are static and hence do not need object
     * instantiation
     */
    private JsonUtils() {
        
    }

    /**
     * To Json Converter using Goolge's Gson Package<br>
     * this method converts a simple object to a json string<br>
     *
     * @param obj
     * @return a json string
     */
    public static <T> String toJsonObj(T obj) {
        return gson.toJson(obj);
    }

    /**
     * Converts a collection of objects using Google's Gson Package
     *
     * @param objCol
     * @return a json string array
     */
    public static <T> String toJsonList(List<T> objCol) {
        return gson.toJson(objCol);
    }

    /**
     * Returns the specific object given the Json String
     *
     * @param <T>
     * @param jsonString
     * @param obj
     * @return a specific object as defined by the user calling the method
     */
    public static <T> T fromJsonToObj(String jsonString, Class<T> obj) {
        return gson.fromJson(jsonString, obj);
    }

    /**
     * Returns a list of specified object from the given json array
     *
     * @param <T>
     * @param jsonString
     * @param t the type defined by the user
     * @return a list of specified objects as given in the json array
     */
    public static <T> List<T> fromJsonToList(String jsonString, Type t) {
        return gson.fromJson(jsonString, t);
    }

    public static <T> List<T> fromJSONTOGivenFile(String assetDir, String filename, Type t) {
        try {
            return fromJsonToList(org.apache.commons.io.FileUtils.readFileToString(new File(assetDir, filename+".json"), "UTF-8"), t);
        } catch (IOException ex) {
            log.info(ex.getMessage());
            return null;
        }
    }
}
