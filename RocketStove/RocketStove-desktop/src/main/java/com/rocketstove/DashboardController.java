/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

/**
 *
 * @author bibek
 */
@Component
public class DashboardController implements Initializable {
    @FXML
    private VBox dashboard;
    @FXML
    private VBox main_content;

    @FXML
    private JFXButton homeBtn;

    @FXML
    private JFXButton reviewBtn;

    @FXML
    private JFXButton automationBtn;

    @FXML
    private JFXButton failedSormSubmission;
    @FXML
    private JFXButton submittedForm;

    @FXML
    private Pane reviewedList;

    @FXML
    private Pane totalAutomatedBtnClicked;

    @FXML
    private Pane totalDownloadedBtn;

    @FXML
    private Pane totalRejected;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         this.loadHome();
    }

    @FXML
    void onAutomationBtnClicked(ActionEvent event) {
        this.loadAutomation();
    }

    @FXML
    void onFailedSubmissionBtnClicked(ActionEvent event) {
        this.loadFailed();
    }

    @FXML
    void onHomeBtnClick(ActionEvent event) {
        this.loadHome();
    }

    
    private void loadHome() {
        try {
            FXMLLoader homeLoader = new FXMLLoader();
            homeLoader.setControllerFactory(MainApp.springContext::getBean);
            VBox homeVbox = homeLoader.load(getClass().getResourceAsStream("/fxml/home.fxml"));
            main_content.getChildren().clear();
            main_content.getChildren().add(homeVbox);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void onReviewBtnClicked(ActionEvent event) {
        this.loadReview();
    }

    private void loadFailed() {
        try {
            FXMLLoader homeLoader = new FXMLLoader();
            homeLoader.setControllerFactory(MainApp.springContext::getBean);
            VBox homeVbox = homeLoader.load(getClass().getResourceAsStream("/fxml/failed.fxml"));
            main_content.getChildren().clear();
            main_content.getChildren().add(homeVbox);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadReview() {
        try {
            FXMLLoader homeLoader = new FXMLLoader();
            homeLoader.setControllerFactory(MainApp.springContext::getBean);
            VBox homeVbox = homeLoader.load(getClass().getResourceAsStream("/fxml/review.fxml"));
            main_content.getChildren().clear();
            main_content.getChildren().add(homeVbox);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadAutomation() {
        try {
            FXMLLoader homeLoader = new FXMLLoader();
            homeLoader.setControllerFactory(MainApp.springContext::getBean);
            VBox homeVbox = homeLoader.load(getClass().getResourceAsStream("/fxml/automation.fxml"));
            main_content.getChildren().clear();
            main_content.getChildren().add(homeVbox);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    public void onSubmittedClicked(){
        try {
            FXMLLoader homeLoader = new FXMLLoader();
            homeLoader.setControllerFactory(MainApp.springContext::getBean);
            VBox homeVbox = homeLoader.load(getClass().getResourceAsStream("/fxml/submited.fxml"));
            main_content.getChildren().clear();
            main_content.getChildren().add(homeVbox);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
