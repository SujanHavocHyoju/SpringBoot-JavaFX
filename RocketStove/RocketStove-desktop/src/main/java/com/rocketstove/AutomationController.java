/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.rocketstove.ui.Form;
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
import com.rocketstove.domain.AggregateForm;
import com.rocketstove.formautomation.FormAutomation;
import com.rocketstove.response.Response;
import com.rocketstove.service.AggregateFormService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author bibek
 */
@Component
public class AutomationController implements Initializable {

    @FXML
    private JFXTextField searchByStoveId;
    @Autowired
    private AggregateFormService aggregateFormService;
    @Autowired
    private FormAutomation formAutomation;
    @FXML
    private AnchorPane automationAnchorPane;
    @FXML
    private JFXButton automatedBtn;
    @FXML
    private JFXTreeTableView<Form> treeView;
    @Value("${return_image}")
    private String reviewImage;

    void doTree() {
        // TODO
        JFXTreeTableColumn<Form, String> serialNumber = new JFXTreeTableColumn<>("S.N");
        serialNumber.setPrefWidth(50);
        serialNumber.setCellValueFactory((TreeTableColumn.CellDataFeatures<Form, String> param) -> param.getValue().getValue().serialNumber);

        JFXTreeTableColumn<Form, String> stoveIdColumn = new JFXTreeTableColumn<>("Stove Id");
        stoveIdColumn.setPrefWidth(155);
        stoveIdColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Form, String> param) -> param.getValue().getValue().stoveId);

        JFXTreeTableColumn<Form, String> fullNameColumn = new JFXTreeTableColumn<>("Full Name");
        fullNameColumn.setPrefWidth(155);
        fullNameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Form, String> param) -> param.getValue().getValue().fullName);

        JFXTreeTableColumn<Form, String> citizenShipNumberColumn = new JFXTreeTableColumn<>("CitizenShip No.");
        citizenShipNumberColumn.setPrefWidth(155);
        citizenShipNumberColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Form, String> param) -> param.getValue().getValue().citizenShipNumber);

        JFXTreeTableColumn<Form, String> citizenDistrictColumn = new JFXTreeTableColumn<>("Citizen District");
        citizenDistrictColumn.setPrefWidth(155);
        citizenDistrictColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Form, String> param) -> param.getValue().getValue().citizenDistrict);

        JFXTreeTableColumn<Form, Boolean> reviewColumn = new JFXTreeTableColumn<>("Review");
        reviewColumn.setPrefWidth(80);
        reviewColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Form, Boolean> param) -> param.getValue().getValue().isEditable);
        reviewColumn.setCellFactory((TreeTableColumn<Form, Boolean> param) -> new ReviewCell(treeView));

        ObservableList<Form> forms = FXCollections.observableArrayList();
        List<Form> aggregateForms = this.aggregateFormService.automationFormList();
        forms.addAll(aggregateForms);
        final TreeItem<Form> formTreeItem = new RecursiveTreeItem<>(forms, (param) -> {
            return param.getChildren();
        });
        treeView.setRoot(formTreeItem);
        treeView.getColumns().setAll(serialNumber, stoveIdColumn, fullNameColumn, citizenShipNumberColumn, citizenDistrictColumn, reviewColumn);
        treeView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends TreeItem<Form>> observable, TreeItem<Form> oldValue, TreeItem<Form> newValue) -> {
            TreeItem<Form> selectedItem = (TreeItem<Form>) newValue;
            if (selectedItem != null) {
                Form selectedForm = selectedItem.getValue();
                selectedForm.setIsEditable(true);
            }
        });
        treeView.setShowRoot(false);
        searchByStoveId.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            treeView.setPredicate((TreeItem<Form> t) -> t.getValue().stoveId.getValue().contains(newValue));
        });
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        doTree();
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
            operation();
        } else {

        }

    }

    private void operation() {
        new Thread(() -> {
            formAutomation.automateAll();
        }).start();
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

    /**
     * A table cell containing a button for adding a review.
     */
    private class ReviewCell extends TreeTableCell<Form, Boolean> {

        public Button reviewButton = new Button();

        public StackPane paddedButton = new StackPane();
        public DoubleProperty buttonY = new SimpleDoubleProperty();

        ReviewCell(final JFXTreeTableView table) {
            try {
                Image image = new Image(new FileInputStream(new File(reviewImage)));
                ImageView imageView = new ImageView(image);
                reviewButton.setStyle("-fx-backgroud-color:transparent");
                imageView.setSmooth(true);
                imageView.setPreserveRatio(true);
                imageView.setCache(true);
                imageView.setFitHeight(20);
                imageView.setFitWidth(20);
                reviewButton.setGraphic(imageView);
                paddedButton.getChildren().add(reviewButton);
                setAlignment(Pos.CENTER);
                reviewButton.setOnMousePressed((MouseEvent event) -> {
                    buttonY.set(event.getScreenY());
                });
                reviewButton.setOnAction((ActionEvent actionEvent) -> {
                    int selectedIndex = table.getSelectionModel().getSelectedIndex();
                    table.getSelectionModel().select(selectedIndex);
                    TreeItem<Form> treeItem = table.getSelectionModel().getModelItem(selectedIndex);
                    if (selectedIndex < 0 || treeItem == null || treeItem.getValue() == null) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Form Selection");
                        alert.setContentText("Please select the form first");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation for Unreviewed");
                        alert.setContentText("Do you really want to move this form to Review List?");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            aggregateFormService.unreviewed(treeItem
                                    .getValue().stoveId.getValue());
                        }
                        doTree();
                    }
                });
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ReviewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /**
         * places an add button in the row only if the row is not empty.
         */
        @Override
        protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setGraphic(paddedButton);
            } else {
                setGraphic(null);
            }
        }
    }
}
