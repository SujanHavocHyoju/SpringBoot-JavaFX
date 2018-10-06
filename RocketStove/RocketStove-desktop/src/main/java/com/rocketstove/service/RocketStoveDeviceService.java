package com.rocketstove.service;

import com.rocketstove.HomeController;
import com.rocketstove.domain.AggregateForm;
import com.rocketstove.domain.StarterForm;
import com.rocketstove.exception.RocketStoveFileNotFoundException;
import com.rocketstove.utils.ImageUtils;
import com.rocketstove.utils.JsonUtils;
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
public class RocketStoveDeviceService {

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
                throw new RocketStoveFileNotFoundException("file not found exception " + fileName);
            }
        } catch (MalformedURLException malformedURLException) {
            throw new RocketStoveFileNotFoundException("file not found exception " + malformedURLException.getMessage());
        }
    }

    public void syncForm(AggregateForm aggregateForm) {
        StarterForm starterForm = aggregateForm.getStarterForm();
        if (starterForm != null) {
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
                String stoveidImg = starterForm.getStoveIDImg();
                if (StringUtils.isNotBlank(stoveidImg)) {
                    File file = new File(stoveidImg);
                    if (file.exists()) {
                        starterForm.setStoveIDImg(ImageUtils
                                .imageBase64(file));
                        FileUtils.deleteQuietly(file);
                    }
                }
                String stoveHandoverImage = starterForm.getStoveHandOverImg();
                if (StringUtils.isNotBlank(stoveHandoverImage)) {
                    File file = new File(stoveHandoverImage);
                    if (file.exists()) {
                        starterForm.setStoveHandOverImg(ImageUtils
                                .imageBase64(file));
                        FileUtils.deleteQuietly(file);
                    }
                }
                String citizenFrontImage = starterForm.getCitizenFrontImg();
                if (StringUtils.isNotBlank(citizenFrontImage)) {
                    File file = new File(citizenFrontImage);
                    if (file.exists()) {
                        starterForm.setCitizenFrontImg(ImageUtils
                                .imageBase64(file));
                        FileUtils.deleteQuietly(file);
                    }
                }
                String citizenBackImage = starterForm.getCitizenBackImg();
                if (StringUtils.isNotBlank(citizenBackImage)) {
                    File file = new File(citizenBackImage);
                    if (file.exists()) {
                        starterForm.setCitizenBackImg(ImageUtils
                                .imageBase64(file));
                        FileUtils.deleteQuietly(file);
                    }
                }
                aggregateForm.setStarterForm(starterForm);
                File file = new File(formPath);
                if (!file.exists()) {
                    file.mkdir();
                }
                String time = LocalDate.now()
                        .toString();
                String[] splitter = time.split("-");
                time = splitter[1] + splitter[2] + splitter[0];
                File timeFile = new File(file, time);
                if (timeFile.exists() == false) {
                    timeFile.mkdir();
                }
                String fileName = starterForm.getRocketId().toUpperCase() + ".json";
                File jsonFile = new File(timeFile, fileName);
                if (jsonFile.exists()) {
                    fileName = starterForm.getRocketId().toUpperCase() + "_duplicate_" + RandomUtils.nextInt() + ".json";
                    jsonFile = new File(timeFile, fileName);
                }
                log.info(jsonFile.getAbsolutePath());
                FileUtils.deleteDirectory(new File(storagePath));
                FileUtils.writeStringToFile(jsonFile, JsonUtils.toJsonObj(aggregateForm), Charset.defaultCharset());
            } catch (IOException e) {
                throw new RocketStoveFileNotFoundException("Something went wrong : " + e.getMessage());
            }
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
