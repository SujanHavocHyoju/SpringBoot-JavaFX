/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solar.service;

import com.solar.HomeController;
import com.solar.domain.StarterForm;
import com.solar.exception.SolarFileNotFoundException;
import com.solar.utils.ImageUtils;
import com.solar.utils.JsonUtils;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import javafx.application.Platform;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author bibek
 */
@Slf4j
@Service
public class SolarDeviceService {

    private Path fileStorageLocation;
    @Value("${file.storage.path}")
    private String storagePath;
    @Value("${file.storage.json}")
    private String formPath;

    public String storeFile(MultipartFile file, String folder) {
        File newFile = new File(storagePath);
        if (!newFile.exists()) {
            newFile.mkdir();
        }
        newFile = new File(newFile, folder);
        if (!newFile.exists()) {
            newFile.mkdir();
        }
        this.fileStorageLocation = this.fileStorageLocation = Paths.get(newFile.getAbsolutePath()).toAbsolutePath().normalize();
        //normaliza file name
        String fileName = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new IllegalArgumentException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return newFile.getAbsolutePath() + File.separator + fileName;
        } catch (IOException ex) {
            throw new IllegalArgumentException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new SolarFileNotFoundException("file not found exception " + fileName);
            }
        } catch (MalformedURLException malformedURLException) {
            throw new SolarFileNotFoundException("file not found exception " + fileName);
        }
    }

    public void syncForm(StarterForm starterForm) {
        try {
            String sAFormFirstImg = starterForm.getsAFormFirstImg();
            if (StringUtils.isNotBlank(sAFormFirstImg)) {
                File file = new File(sAFormFirstImg);
                if (file.exists()) {
                    starterForm.setsAFormFirstImg(ImageUtils
                            .imageBase64(file));
                    FileUtils.deleteQuietly(file);
                }
            }
            String sASecond = starterForm.getsAFormSecondImg();
            if (StringUtils.isNotBlank(sASecond)) {
                File file = new File(sASecond);
                if (file.exists()) {
                    starterForm.setsAFormSecondImg(ImageUtils
                            .imageBase64(file));
                    FileUtils.deleteQuietly(file);
                }
            }
            String bilingInvoice = starterForm.getBillInvoiceImage();
            if (StringUtils.isNotBlank(bilingInvoice)) {
                File file = new File(bilingInvoice);
                if (file.exists()) {
                    starterForm.setBillInvoiceImage(ImageUtils
                            .imageBase64(file));
                    FileUtils.deleteQuietly(file);
                }
            }
            String batteryVoucher = starterForm.getBatteryVoucherImage();
            if (StringUtils.isNotBlank(batteryVoucher)) {
                File file = new File(batteryVoucher);
                if (file.exists()) {
                    starterForm.setBatteryVoucherImage(ImageUtils
                            .imageBase64(file));
                    FileUtils.deleteQuietly(file);
                }
            }
            String muchulka = starterForm.getBatteryVoucherImage();
            if (StringUtils.isNotBlank(muchulka)) {
                File file = new File(muchulka);
                if (file.exists()) {
                    starterForm.setMuchulkaImage(ImageUtils
                            .imageBase64(file));
                    FileUtils.deleteQuietly(file);
                }
            }
           
            File file = new File(formPath);
            if (!file.exists()) {
                file.mkdir();
            }
            String time = LocalDate.now()
                    .toString();
            String[] splitter = time.split("-");
            time = splitter[1]+splitter[2]+splitter[0];
            File timeFile = new File(file, time);
            if (timeFile.exists() == false) {
                timeFile.mkdir();
            }
            String fileName = starterForm.getSolarId().toUpperCase() + ".json";
            File jsonFile = new File(timeFile, fileName);
            if (jsonFile.exists()) {
                fileName = starterForm.getSolarId().toUpperCase() + "_duplicate_" + RandomUtils.nextInt() + ".json";
                jsonFile = new File(timeFile, fileName);
            }
            log.info(jsonFile.getAbsolutePath());
             FileUtils.deleteDirectory(new File(storagePath));
            FileUtils.writeStringToFile(jsonFile, JsonUtils.toJsonObj(starterForm), Charset.defaultCharset());
        } catch (IOException e) {
            throw new SolarFileNotFoundException("Something went wrong : " + e.getMessage());
        }
    }

    public boolean closeQr() {
        Platform.runLater(
                () -> {
                    if (HomeController.qrStage.isShowing()) {
                        HomeController.qrStage.close();
                    }
                }
        );

        return true;
    }
}
