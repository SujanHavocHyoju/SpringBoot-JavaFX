/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove;

import com.jfoenix.controls.JFXButton;
import com.rocketstove.drive.GoogleCloudService;
import com.rocketstove.service.AggregateFormService;
import com.rocketstove.utils.FileUtils;
import com.rocketstove.utils.NetworkUtils;
import com.rocketstove.utils.ThreadUtils;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author bibek
 */
@Slf4j
@Component
public class HomeController implements Initializable {
    public static Stage qrStage;
    @Value("${img_folder}")
    private String imgFilePath;
    @Autowired
    private GoogleCloudService googleCloudService;
    @Autowired
    private AggregateFormService aggregateFormService;
    @FXML
    private VBox homeVbox;
    @FXML
    private Pane totalReadyToAutomatePane;
    
    @FXML
    private Label totalReadyToAutomateLabel;
    @FXML
    private Pane totalReviewPane;
    
    @FXML
    private Label totalDownloadedLabel;
    
    @FXML
    private Pane totalAutomatedBtnClicked;
    
    @FXML
    private Label totalReviewLabel;
    
    @FXML
    private Pane totalDownloadedPane;
    
    @FXML
    private Label totalSubmittedLabel;
    
    @FXML
    private Label totalSuccededLabel;
    
    @FXML
    private Pane totalRejectedPane;
    
    @FXML
    private Label totalReturnedLabel;
    
    @FXML
    private JFXButton downloadBtn;
    @Value("${server.port}")
    private String port;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        totalDownloadedLabel.setText(String.valueOf(
                aggregateFormService.downloadFile()
        ));
        totalReviewLabel.setText(String.valueOf(aggregateFormService.reviewedCount()));
        totalReturnedLabel.setText(String.valueOf(
                aggregateFormService.failedCount()
        ));
        totalSubmittedLabel.setText(String.valueOf(
                aggregateFormService.uploadedToServerCount()
        ));
        totalReadyToAutomateLabel.setText(String.valueOf(
                aggregateFormService.automationReadyCount()
        ));
        totalSuccededLabel.setText(String.valueOf(aggregateFormService.succeedCount()));
        
    }
    
    @FXML
    void downloadFilesFromCloud(ActionEvent event) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Solar Form");
        File defaultDirectory = new File(imgFilePath);
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(MainApp.stage);
        log.info(selectedDirectory.getAbsolutePath());
        Thread isExist = ThreadUtils.getThreadByName("downloading");
        if (isExist == null) {
            new Thread(() -> {
                List<File> files = new ArrayList<>();
                FileUtils.listf(selectedDirectory.getAbsolutePath(), files);
                log.info("Number of files that presented {}", files.size());
                files.forEach(file -> this.aggregateFormService.processFormByFile(file));
            }, "downloading").start();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Google Cloud download Information");
            alert.setContentText("Download is in progress. You can now review or automate form.");
            alert.showAndWait();
        } else {
            if (isExist.isAlive()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Google Cloud download");
                alert.setContentText("You have already requested file from cloud. Please give it a time to finish");
                alert.showAndWait();
            }
        }

    }
    @FXML
    void connectToServer(ActionEvent event) throws UnknownHostException {
        String url = NetworkUtils.provideIP4(port);
        if (StringUtils.isNotBlank(url)) {
            log.info(url);
            File file = QRCode.from(url).to(ImageType.PNG).file();
            qrStage = new Stage();
            FXMLLoader formPageLoader = new FXMLLoader();
            formPageLoader.setControllerFactory(MainApp.springContext::getBean);
            VBox root = null;
            try {
                root = formPageLoader.load(getClass().getResourceAsStream("/fxml/qrscanner.fxml"));
            } catch (IOException ex) {
                log.info("error in scanner failed to load scanner");
            }
            QrscannerController qrscannerController
                    = (QrscannerController) formPageLoader.getController();
            qrscannerController.setUrl(file.toURI().toString());
            qrStage.initModality(Modality.WINDOW_MODAL);
            qrStage.initOwner(MainApp.stage);
            Scene scene1 = new Scene(root);
            qrStage.setMaximized(false);
            qrStage.setResizable(false);
            //qrStage.initStyle(StageStyle.UNDECORATED);
            qrStage.setScene(scene1);
            qrStage.setFullScreen(false);
            qrStage.showAndWait();
        }
    }
}
