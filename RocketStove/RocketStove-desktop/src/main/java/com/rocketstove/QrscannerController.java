/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author bibek
 */
@Slf4j
@Component
public class QrscannerController implements Initializable {
    @FXML
    private ImageView scannerImgView;
    
    private String url;

    public void setUrl(String url) {
        log.info(url);
        this.url = url;
        scannerImgView
               .setImage(new Image(this.url));
    }
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
}
