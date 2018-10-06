/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solar;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.solar.utils.ThreadUtils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.solar.service.SolarFormService;
import com.solar.utils.FileUtils;
import com.solar.utils.NetworkUtils;
import java.io.File;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.slf4j.Slf4j;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author bibek
 */
@Slf4j
@Component
public class HomeController implements Initializable {

    @Autowired
    private SolarFormService solarFormService;
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
    public static Stage qrStage;
    @Value("${img_folder}")
    private String imgFilePath;
    @FXML
    private JFXButton downloadBtn;
    @Value("${server.port}")
    private String port;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        totalDownloadedLabel.setText(String.valueOf(
                solarFormService.downloadFile()
        ));
        totalReviewLabel.setText(String.valueOf(solarFormService.reviewedCount()));
        totalReturnedLabel.setText(String.valueOf(
                solarFormService.failedCount()
        ));
        totalSubmittedLabel.setText(String.valueOf(
                solarFormService.uploadedToServerCount()
        ));
        totalReadyToAutomateLabel.setText(String.valueOf(
                solarFormService.automationReadyCount()
        ));
        totalSuccededLabel.setText(String.valueOf(solarFormService.succeedCount()));

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
                files.forEach(file -> this.solarFormService.processFormByFile(file));
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

}
