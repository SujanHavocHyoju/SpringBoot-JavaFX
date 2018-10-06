/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solar.resources;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author bibek
 */
@Data
@RequiredArgsConstructor
public class UploadedFormResponse {
    @NonNull private Integer code;
    @NonNull private String message;
}
