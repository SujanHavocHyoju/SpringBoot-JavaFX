/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.mapper;

import com.rocketstove.domain.CameraType;
import com.rocketstove.domain.StarterForm;
import com.rocketstove.entity.StarterFormEntity;
import com.rocketstove.repository.StarterFormRepository;
import com.rocketstove.utils.ImageUtils;
import com.rocketstove.utils.JsonUtils;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author bibek
 */
@Component
public class StarterFormMapper implements Mapper<StarterForm, StarterFormEntity> {

    @Value("${img_folder}")
    private String imageFolder;
    @Value("${second_form_img}")
    private String secondFormImg;
    @Autowired
    private StarterFormRepository starterFormRepository;

    @Override
    public Optional<StarterFormEntity> mapFormToEntity(Optional<StarterForm> starterFormOpt) {
        if (starterFormOpt.isPresent()) {
            StarterForm starterForm = starterFormOpt.get();
            StarterFormEntity retrieveEntity = starterFormRepository.findByRocketId(starterForm.getRocketId());
            if (retrieveEntity == null) {
                downloadApplicationFormImages(starterForm);
                downloadCitizenImages(starterForm);
                downloadStoveImage(starterForm);
            }
            StarterFormEntity starterFormEntity = JsonUtils.fromJsonToObj(JsonUtils.toJsonObj(starterForm), StarterFormEntity.class);
            if (retrieveEntity != null) {
                starterFormEntity.setId(retrieveEntity.getId());
            }
            return Optional.of(starterFormEntity);
        }
        return Optional.empty();
    }

    private void downloadApplicationFormImages(StarterForm starterForm) {
        if (StringUtils.isNotBlank(starterForm.getsAFormFirstImg())) {
            String applicationFirstFormImage = starterForm.getsAFormFirstImg();
            String rocketId = starterForm.getRocketId();
            String pathToUpload = imageFolder + File.separator + rocketId;
            String imageFileName = CameraType.APPLICATION_FIRST_FORM.getFileName();
            File subsidyFrontFile = null;
            try {
                if (StringUtils.isNotBlank(applicationFirstFormImage)) {
                    subsidyFrontFile = ImageUtils.writeImageEncodeStringFileRotate(pathToUpload, imageFileName, applicationFirstFormImage);
                    starterForm.setsAFormFirstImg(subsidyFrontFile.getAbsolutePath());
                }
                starterForm.setsAFormSecondImg(secondFormImg);
            } catch (IOException ex) {
                Logger.getLogger(StarterFormMapper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void downloadCitizenImages(StarterForm starterForm) {
        String citizenFrontImage = starterForm.getCitizenFrontImg();
        String citizenBackImage = starterForm.getCitizenBackImg();
        String rocketId = starterForm.getRocketId();
        String pathToUpload = imageFolder + File.separator + rocketId;
        String firstImage = CameraType.CITIZEN_SHIP_FRONT.getFileName();
        String secondImage = CameraType.CITIZEN_SHIP_BACK.getFileName();

        try {
            if (StringUtils.isNotBlank(citizenFrontImage)) {
                File citizenFrontImageFile = null;
                citizenFrontImageFile = ImageUtils.writeImageEncodeStringFileRotate(pathToUpload, firstImage, citizenFrontImage);
                starterForm.setCitizenFrontImg(citizenFrontImageFile.getAbsolutePath());
            }
            if (StringUtils.isNotBlank(citizenBackImage)) {
                File citizenBackImageFile = null;
                citizenBackImageFile = ImageUtils.writeImageEncodeStringFileRotate(pathToUpload, secondImage, citizenBackImage);
                starterForm.setCitizenBackImg(citizenBackImageFile.getAbsolutePath());
            }
        } catch (IOException ex) {
            Logger.getLogger(StarterFormMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void downloadStoveImage(StarterForm starterForm) {
        String stoveId = starterForm.getStoveIDImg();
        String stoveHandover = starterForm.getStoveHandOverImg();
        String rocketId = starterForm.getRocketId();
        String pathToUpload = imageFolder + File.separator + rocketId;
        String firstImage = CameraType.STOVE_ID.getFileName();
        String secondImage = CameraType.STOVE_HANDOVER.getFileName();

        try {
            if (StringUtils.isNotBlank(stoveId)) {
                File stoveIdImageFile = null;
                stoveIdImageFile = ImageUtils.writeImageEncodeStringFileRotate(pathToUpload, firstImage, stoveId);
                starterForm.setStoveIDImg(stoveIdImageFile.getAbsolutePath());
            }
            if (StringUtils.isNotBlank(stoveHandover)) {
                File stoveHandoverImageFile = null;
                stoveHandoverImageFile = ImageUtils.writeImageEncodeStringFileRotate(pathToUpload, secondImage, stoveHandover);
                starterForm.setStoveHandOverImg(stoveHandoverImageFile.getAbsolutePath());

            }
        } catch (IOException ex) {
            Logger.getLogger(StarterFormMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
