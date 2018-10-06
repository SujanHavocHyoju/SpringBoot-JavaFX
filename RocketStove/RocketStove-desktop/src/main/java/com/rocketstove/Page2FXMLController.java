/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author SUJAN
 */
public class Page2FXMLController implements Initializable {

    @FXML
    private Label dLabel;
    
    @FXML
    private DatePicker installationDate;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void showDate(ActionEvent event){
        LocalDate ld = installationDate.getValue();
        dLabel.setText(ld.toString());
    }
    
    @FXML
    private void previousPageAction(ActionEvent event) {
        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader FormPageLoader = new FXMLLoader();
            ScrollPane root = FormPageLoader.load(getClass().getResource("/fxml/FormPageFXML.fxml").openStream());
            FormPageFXMLController formPageFXMLController = (FormPageFXMLController) FormPageLoader.getController();
            Scene scene = new Scene(root);
            primaryStage.setMaximized(false);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Rocket Stove Form Verification ");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void LogOut(ActionEvent event){
        try {
            ((Node)event.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("/fxml/LoginFXML.fxml").openStream());
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}
