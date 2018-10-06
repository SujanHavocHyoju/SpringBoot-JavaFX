/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author bibek
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RocketStoveFileNotFoundException extends RuntimeException {

    public RocketStoveFileNotFoundException(String message) {
        super(message);
    }

    public RocketStoveFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
