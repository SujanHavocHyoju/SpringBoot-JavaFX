/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.resources;

/**
 *
 * @author bibek
 */
import com.rocketstove.domain.AggregateForm;
import com.rocketstove.service.RocketStoveDeviceService;
import com.rocketstove.utils.JsonUtils;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author bibek
 */
@CrossOrigin
@Slf4j
@RestController 
public class RocketStoveDeviceResources {

    @Autowired
    private RocketStoveDeviceService rocketStoveDeviceService;

    @PostMapping("uploadfiles/{foldername}")
    public List<UploadFileResponse> uploadFiles(@RequestParam("file") MultipartFile[] files,@PathVariable("foldername")String folder) {
        log.info("uploading files");
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file,folder))
                .collect(Collectors.toList());
    }

    @PostMapping("uploadfile/{foldername}")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file,@PathVariable("foldername")String folder) {
        log.info("uploading files");
        String fileName = rocketStoveDeviceService.storeFile(file,folder);
        String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/tmp_storage/")
                .path(fileName)
                .toUriString();
        return new UploadFileResponse(fileName,
                fileUrl, file.getContentType(), file.getSize());
    }
    //not worked
    @GetMapping("/tmp_storage/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        //load file as Resource
        Resource resource = this.rocketStoveDeviceService.loadFileAsResource(fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type. {}",ex.getMessage());
        }
        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping("addform")
    public UploadedFormResponse uploadFormResponse(@RequestBody AggregateForm aggregateForm) {
        log.info("Inside....{}",JsonUtils.toJsonObj(aggregateForm));
        this.rocketStoveDeviceService.syncForm(aggregateForm);
        return new UploadedFormResponse(200, "Success");
    }
    
    @GetMapping
    public String homer(){
        return "Homer Simpson";
    }
    @GetMapping("closeqr")
    public Boolean closeQr(){
        return this.rocketStoveDeviceService.closeQr();
    }
    

}
