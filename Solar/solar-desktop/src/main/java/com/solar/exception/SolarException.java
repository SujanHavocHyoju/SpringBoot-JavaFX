/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solar.exception;

import com.solar.response.Response;

/**
 *
 * @author bibek
 */
public class SolarException extends RuntimeException {
    private final Response response;
    public SolarException(Response response) {
        super(response.getMessage());
        this.response = response;
    }
    public Response getResponse() {
        return response;
    }
}
