/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.exception;

import com.rocketstove.response.Response;

/**
 *
 * @author SUJAN
 */
public class RocketStoveException extends RuntimeException {
    private final Response response;
    public RocketStoveException(Response response) {
        super(response.getMessage());
        this.response = response;
    }
    public Response getResponse() {
        return response;
    }
}
