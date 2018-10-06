/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author SUJAN
 */
public class Page1FXMLController implements Initializable {
    
    private List<String> attachmentFilePaths = new ArrayList<String>();
    @FXML
    private Label userLabel;
    
    @FXML
    private Label urlCheck;
    
    @FXML
    private Label passwordLabel;
    
    @FXML
    private Button btnAttachments;
    
    @FXML
    private ListView listview;
    
    @FXML
    private ComboBox<String> genderBox, ethnicityBox, citizenshipDistrictBox, districtBox, vpnpBox, vdcnpBox, manufacturerBox, modelBox;
    
    @FXML
    private Label gLabel;
    
    @FXML
    private Label dLabel;
    
    @FXML
    private DatePicker installationDate;
    
    @FXML
    private TextField ownerName, spouseName, citizenshipNo, callingName, parentsName, addressVP_NP_WardNo, addressVP_NP_NearestRoad, addressVP_NP_Village, addressVP_NP_HouseNo;
    
    @FXML
    private TextField addressVDC_NP_WardNo, addressVDC_NP_Village, engraveNo, AD_BS, totalCost, subsidySupportAmount, subsidyAmount, userAmount;
            
    public void GetUser(String user) {
        userLabel.setText(user);
    }
    
    public void GetUrl(String url) {
        urlCheck.setText(url);
    }
    
    public void GetPassword(String password){
        passwordLabel.setText(password);
    }
    
    ObservableList<String> genderList = FXCollections.observableArrayList("Male","Female","Other");
    ObservableList<String> ethnicityList = FXCollections.observableArrayList("item1","item2","item3");
    ObservableList<String> citDistrictList = FXCollections.observableArrayList("item1","item2","item3");
    ObservableList<String> districtList = FXCollections.observableArrayList("item1","item2","item3");
    ObservableList<String> vpnpList = FXCollections.observableArrayList("item1","item2","item3");
    ObservableList<String> vdcnpList = FXCollections.observableArrayList("item1","item2","item3");
    ObservableList<String> mfgList = FXCollections.observableArrayList("item1","item2","item3");
    ObservableList<String> modelList = FXCollections.observableArrayList("item1","item2","item3");
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        populateComboBox();
        setValuesInFields();
    }    
    
    public void genderComboChanged(ActionEvent event){
        gLabel.setText(genderBox.getValue());
    }
    
    @FXML
    private void showDate(ActionEvent event){
        LocalDate ld = installationDate.getValue();
        dLabel.setText(ld.toString());
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
            primaryStage.setFullScreen(false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @FXML
    private void nextPageAction(ActionEvent event) {
        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader page2Loader = new FXMLLoader();
            Pane root = page2Loader.load(getClass().getResource("/fxml/Page2FXML.fxml").openStream());
            Page2FXMLController page2Controller = (Page2FXMLController) page2Loader.getController();
            //page2Controller.GetUser(username);
            Scene scene = new Scene(root);            
            primaryStage.setMaximized(false);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.setFullScreen(false);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void attachmentsAction (ActionEvent event){
        attachmentFilePaths.clear();
        FileChooser fcOne = new FileChooser();
        fcOne.setInitialDirectory(new File("E:\\HAVOC\\RocketStove"));
        File selectedFile = fcOne.showOpenDialog(null);
        if(selectedFile != null){
            listview.getItems().add(selectedFile.getAbsoluteFile());
            attachmentFilePaths.add(selectedFile.getAbsoluteFile().toString());
        }else{
            System.out.println("Invalid File Detected");
        }
    }
    
    public void populateComboBox(){
        genderBox.setItems(genderList);
        ethnicityBox.setItems(ethnicityList);
        citizenshipDistrictBox.setItems(citDistrictList);
        districtBox.setItems(districtList);
        vpnpBox.setItems(vpnpList);
        vdcnpBox.setItems(vdcnpList);
        manufacturerBox.setItems(mfgList);
        modelBox.setItems(modelList);
    
    }
    
    public void setValuesInFields(){
        ownerName.setText("Test Name");
        installationDate.setValue(LocalDate.now());
        citizenshipDistrictBox.getSelectionModel().select(2);
    }
    
}
