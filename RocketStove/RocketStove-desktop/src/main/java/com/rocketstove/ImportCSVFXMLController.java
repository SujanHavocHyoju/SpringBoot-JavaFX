/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove;

import com.google.gson.reflect.TypeToken;
import com.rocketstove.domain.AggregateForm;
import com.rocketstove.formautomation.FormAutomation;
import com.rocketstove.formautomation.LoginFormAutomation;
import com.rocketstove.formautomation.Navigation;
import com.rocketstove.utils.JsonUtils;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.WebDriver;

/**
 * FXML Controller class
 *
 * @author SUJAN
 */
public class ImportCSVFXMLController implements Initializable {

    private List<String> filePaths = new ArrayList<String>();
    private static final String JSON_LOCATION = "C:\\Users\\SUJAN\\Documents\\NetBeansProjects\\RocketStove\\src\\main\\resources\\assets\\";

    @FXML
    private Button btnCSVone;

    @FXML
    private Button btnCSVmulti;

    @FXML
    private Button btnImport;

    @FXML
    private ListView<String> clickListView = new ListView<String>();

    @FXML
    private Label userLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label urlCheck;

    @FXML
    private Label pathLabel;

    @FXML
    private Label fileNameLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void GetUser(String user) {
        userLabel.setText("Username: " + user);
    }

    public void GetPassword(String user) {
        passwordLabel.setText("Password: " + user);
    }

    public void GetUrl(String url) {
        urlCheck.setText("Target Url: " + url);
    }

    public void selectOneAction(ActionEvent event) {
        filePaths.clear();
        FileChooser fcOne = new FileChooser();
        fcOne.setInitialDirectory(new File("E:\\HAVOC\\RocketStove"));
        fcOne.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File selectedFile = fcOne.showOpenDialog(null);
        if (selectedFile != null) {
            filePaths.add(selectedFile.getAbsoluteFile().toString());
            clickListView.getItems().add(selectedFile.getAbsolutePath());
        } else {
            System.out.println("Invalid File Detected");
        }
        clickListView.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> ov,
                            String old_val, String new_val) {
                        pathLabel.setText(new_val);
                        fileNameLabel.setText(FilenameUtils.getName(new_val));
                    }
                });
    }

    public void selectMultipleAction(ActionEvent event) {
        filePaths.clear();
        FileChooser fcMulti = new FileChooser();
        fcMulti.setInitialDirectory(new File("E:\\HAVOC\\RocketStove"));
        fcMulti.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        List<File> selectedFiles = fcMulti.showOpenMultipleDialog(null);
        if (selectedFiles != null) {
            selectedFiles.stream().map((selectedFile) -> {
                if (!clickListView.getItems().contains(selectedFile.getAbsoluteFile().toString())) {
                    clickListView.getItems().add(selectedFile.getAbsolutePath());
                    //fileNameLabel.setText("Direct  Setting works");
                }
                return selectedFile;
            }).forEachOrdered((selectedFile) -> {
                if (!filePaths.contains(selectedFile.getAbsoluteFile().toString())) {
                    filePaths.add(selectedFile.getAbsoluteFile().toString());
                }
            });
        } else {
            System.out.println("Invalid File Detected");
        }

        clickListView.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> ov,
                            String old_val, String new_val) {
                        pathLabel.setText(new_val);
                        fileNameLabel.setText(FilenameUtils.getName(new_val));
                    }
                });
    }

    public void importAction(ActionEvent event) throws IOException {
        String username = userLabel.getText();
        String url = urlCheck.getText();
        String password = passwordLabel.getText();
        String fileName = fileNameLabel.getText();
        String jsonPath = pathLabel.getText();

        System.out.println("File Name:" + fileName);
        System.out.println("JSON File Path:" + jsonPath);
        filePaths.forEach((filePath) -> {
            System.out.println(filePath);
        });

        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage primaryStage = new Stage();
        FXMLLoader FormPageLoader = new FXMLLoader();
        //Pane root 
        ScrollPane root = FormPageLoader.load(getClass().getResource("/fxml/FormPageFXML.fxml").openStream());
        FormPageFXMLController formPageFXMLController = (FormPageFXMLController) FormPageLoader.getController();
        formPageFXMLController.GetUser(username);
        formPageFXMLController.GetUrl(url);
        formPageFXMLController.GetPassword(password);
        formPageFXMLController.GetFileName(fileName);
        formPageFXMLController.GetFilePath(jsonPath);
        formPageFXMLController.getPathValueFromPreviousController(jsonPath);
        Scene scene = new Scene(root);
        primaryStage.setMaximized(false);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Rocket Stove Form Verification ");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    
}
