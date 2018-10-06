/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solar;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.solar.ui.Form;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import com.solar.formautomation.SolarFormAutomation;
import com.solar.response.Response;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.solar.service.SolarFormService;

/**
 *
 * @author bibek
 */
@Component
public class AutomationController implements Initializable {
    @FXML
    private JFXTextField searchByStoveId;
    @Autowired
    private SolarFormService solarFormService;
    @Autowired
    private SolarFormAutomation solarFormAutomation;
    @FXML
    private AnchorPane automationAnchorPane;
    @FXML
    private JFXButton automatedBtn;
    @FXML
    private JFXTreeTableView<Form> treeView;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        JFXTreeTableColumn<Form, String> serialNumber = new JFXTreeTableColumn<>("S.N");
        serialNumber.setPrefWidth(50);
        serialNumber.setCellValueFactory((TreeTableColumn.CellDataFeatures<Form, String> param) -> param.getValue().getValue().serialNumber);

        JFXTreeTableColumn<Form, String> stoveIdColumn = new JFXTreeTableColumn<>("Solar Id");
        stoveIdColumn.setPrefWidth(155);
        stoveIdColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Form, String> param) -> param.getValue().getValue().solarId);

        JFXTreeTableColumn<Form, String> fullNameColumn = new JFXTreeTableColumn<>("Full Name");
        fullNameColumn.setPrefWidth(155);
        fullNameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Form, String> param) -> param.getValue().getValue().fullName);

        JFXTreeTableColumn<Form, String> citizenShipNumberColumn = new JFXTreeTableColumn<>("CitizenShip No.");
        citizenShipNumberColumn.setPrefWidth(155);
        citizenShipNumberColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Form, String> param) -> param.getValue().getValue().citizenShipNumber);

        JFXTreeTableColumn<Form, String> citizenDistrictColumn = new JFXTreeTableColumn<>("Citizen District");
        citizenDistrictColumn.setPrefWidth(155);
        citizenDistrictColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Form, String> param) -> param.getValue().getValue().citizenDistrict);
        ObservableList<Form> forms = FXCollections.observableArrayList();
        List<Form> aggregateForms = this.solarFormService.automationFormList();
        forms.addAll(aggregateForms);
        

        final TreeItem<Form> formTreeItem = new RecursiveTreeItem<>(forms, (param) -> {
            return param.getChildren(); //To change body of generated lambdas, choose Tools | Templates.
        });
        treeView.setRoot(formTreeItem);
        treeView.getColumns().setAll(serialNumber, stoveIdColumn, fullNameColumn, citizenShipNumberColumn, citizenDistrictColumn);
        treeView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends TreeItem<Form>> observable, TreeItem<Form> oldValue, TreeItem<Form> newValue) -> {
            TreeItem<Form> selectedItem = (TreeItem<Form>) newValue;
            if(selectedItem!=null){
                Form selectedForm = selectedItem.getValue();
                selectedForm.setIsEditable(true);
            }
        });
        treeView.setShowRoot(false);
        searchByStoveId.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            treeView.setPredicate((TreeItem<Form> t) -> t.getValue().solarId.getValue().contains(newValue));
        });
    }

    @FXML
    void automateAllONClick(ActionEvent event) throws InterruptedException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation for Automation");
        alert.setContentText("Do you really want to automate all form?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            automatedBtn.setDisable(true);
            JFXProgressBar jfxBar = new JFXProgressBar();
            jfxBar.setPrefWidth(500);

            Timeline timeline = new Timeline(
                    new KeyFrame(
                            Duration.ZERO,
                            new KeyValue(jfxBar.secondaryProgressProperty(), 0),
                            new KeyValue(jfxBar.progressProperty(), 0)),
                    new KeyFrame(
                            Duration.seconds(1),
                            new KeyValue(jfxBar.secondaryProgressProperty(), 1)),
                    new KeyFrame(
                            Duration.seconds(2),
                            new KeyValue(jfxBar.progressProperty(), 1)));

            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
            this.automationAnchorPane.getChildren().add(jfxBar);
            new Thread(() -> {
                operation(jfxBar);
            }).start();
        } else {

        }

    }

    private void operation(JFXProgressBar jfxBar) {
        solarFormAutomation.automateAll();
    }

    private void loggedinAgain(Response response) {

        Platform.runLater(() -> {
            try {
                Alert alert = new Alert(Alert.AlertType.WARNING);

                alert.setTitle("Login Failed");

                alert.setContentText(response.getMessage() + ""
                        + "\n Application will restart in 10 second.");
                alert.showAndWait();
                Stage stage = (Stage) automatedBtn.getScene().getWindow();
                stage.close();
                new MainApp().start(new Stage());

            } catch (Exception ex) {
                Logger.getLogger(AutomationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
