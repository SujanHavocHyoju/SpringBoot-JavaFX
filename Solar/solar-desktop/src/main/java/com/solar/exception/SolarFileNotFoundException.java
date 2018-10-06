/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author bibek
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class SolarFileNotFoundException extends RuntimeException {

    public SolarFileNotFoundException(String message) {
        super(message);
    }

    public SolarFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
