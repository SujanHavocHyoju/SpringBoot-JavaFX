/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.resources;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author bibek
 */
@Data
@RequiredArgsConstructor
public class UploadFileResponse {

    @NonNull private String fileName;
    @NonNull private String fileDownloadUri;
    @NonNull private String fileType;
    @NonNull private Long size;
    
}
