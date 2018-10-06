/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solar;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.solar.ui.Form;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.solar.service.SolarFormService;

/**
 * FXML Controller class
 *
 * @author bibek
 */
@Component
public class ReviewController implements Initializable {

    @FXML
    private JFXTextField searchByStoveId;
    @Autowired
    private SolarFormService solarFormService;
    @FXML
    private JFXTreeTableView<Form> treeView;
    @Value("${edit_image}")
    private String editImageDir;
    void doTree(){
         JFXTreeTableColumn<Form, String> serialNumber = new JFXTreeTableColumn<>("S.N");
        serialNumber.setPrefWidth(50);
        serialNumber.setCellValueFactory((TreeTableColumn.CellDataFeatures<Form, String> param) -> param.getValue().getValue().serialNumber);
        JFXTreeTableColumn<Form, String> stoveIdColumn = new JFXTreeTableColumn<>("Solar Id");
        stoveIdColumn.setPrefWidth(140);
        stoveIdColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Form, String> param) -> param.getValue().getValue().solarId);

        JFXTreeTableColumn<Form, String> fullNameColumn = new JFXTreeTableColumn<>("Full Name");
        fullNameColumn.setPrefWidth(140);
        fullNameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Form, String> param) -> param.getValue().getValue().fullName);

        JFXTreeTableColumn<Form, String> citizenShipNumberColumn = new JFXTreeTableColumn<>("CitizenShip No.");
        citizenShipNumberColumn.setPrefWidth(140);
        citizenShipNumberColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Form, String> param) -> param.getValue().getValue().citizenShipNumber);

        JFXTreeTableColumn<Form, String> citizenDistrictColumn = new JFXTreeTableColumn<>("Citizen District");
        citizenDistrictColumn.setPrefWidth(140);
        citizenDistrictColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Form, String> param) -> param.getValue().getValue().citizenDistrict);

        JFXTreeTableColumn<Form, Boolean> reviewColumn = new JFXTreeTableColumn<>("Review");
        reviewColumn.setPrefWidth(80);
        reviewColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Form, Boolean> param) -> param.getValue().getValue().isEditable);
        reviewColumn.setCellFactory((TreeTableColumn<Form, Boolean> param) -> new ReviewCell(treeView));
        ObservableList<Form> forms = FXCollections.observableArrayList();
        List<Form> aggregateForms = this.solarFormService.reviewList();
        forms.addAll(aggregateForms);
        final TreeItem<Form> formTreeItem = new RecursiveTreeItem<>(forms, (param) -> {
            return param.getChildren(); //To change body of generated lambdas, choose Tools | Templates.
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
            treeView.setPredicate((TreeItem<Form> t) -> t.getValue().solarId.getValue().contains(newValue));
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
        // TODO
       doTree();
    }

    /**
     * A table cell containing a button for adding a new person.
     */
    private class ReviewCell extends TreeTableCell<Form, Boolean> {

        // a button for adding a new person.
        //hide  Button only select when row select
        public Button reviewButton = new Button();

        // pads and centers the add button in the cell.
        public StackPane paddedButton = new StackPane();
        // records the y pos of the last button press so that the add person dialog can be shown next to the cell.
        public DoubleProperty buttonY = new SimpleDoubleProperty();

        ReviewCell(final JFXTreeTableView table) {
            try {
                Image image = new Image(new FileInputStream(new File(editImageDir)));
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
                    Stage primaryStage = new Stage();
                    FXMLLoader formPageLoader = new FXMLLoader();
                    formPageLoader.setControllerFactory(MainApp.springContext::getBean);
                    ScrollPane root = null;
                    try {
                        //root = formPageLoader.load(getClass().getResourceAsStream("/fxml/FormPage1FXML.fxml"));
                        root = formPageLoader.load(getClass().getResourceAsStream("/fxml/MainFormPageFXML.fxml"));
                    } catch (IOException ex) {
                        Logger.getLogger(ReviewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    MainFormPageFXMLController mainFormPageFXMLController
                            = (MainFormPageFXMLController) formPageLoader.getController();
                    //FormPage1FXMLController formPage1FXMLController = (FormPage1FXMLController) formPageLoader.getController();
                    
                    mainFormPageFXMLController.setSolarId(treeItem
                            .getValue().solarId.getValue());
                    Scene scene1 = new Scene(root);
                    primaryStage.setMaximized(false);
                    primaryStage.setResizable(false);
                    primaryStage.setTitle("Solar Form");
                    primaryStage.setScene(scene1);
                    primaryStage.setFullScreen(false);
                    primaryStage.showAndWait();
                    doTree();
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