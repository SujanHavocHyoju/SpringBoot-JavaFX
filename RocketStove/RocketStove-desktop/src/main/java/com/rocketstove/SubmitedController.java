package com.rocketstove;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.rocketstove.domain.AggregateForm;
import com.rocketstove.service.AggregateFormService;
import com.rocketstove.ui.SubmittedForm;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author bibek
 */
@Component
public class SubmitedController implements Initializable {
    @FXML
    private JFXTextField searchByStoveId;
    @Autowired
    private AggregateFormService aggregateFormService;
    @FXML
    private JFXTreeTableView<SubmittedForm> treeView;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXTreeTableColumn<SubmittedForm, String> serialNumber = new JFXTreeTableColumn<>("S.N");
        serialNumber.setPrefWidth(50);
        serialNumber.setCellValueFactory((TreeTableColumn.CellDataFeatures<SubmittedForm, String> param) -> param.getValue().getValue().serialNumber);

        JFXTreeTableColumn<SubmittedForm, String> webRocketId = new JFXTreeTableColumn<>("Web Stove Id");
        webRocketId.setPrefWidth(140);
        webRocketId.setCellValueFactory((TreeTableColumn.CellDataFeatures<SubmittedForm, String> param) -> param.getValue().getValue().rocketIdWeb);

        JFXTreeTableColumn<SubmittedForm, String> stoveIdColumn = new JFXTreeTableColumn<>("Stove Id");
        stoveIdColumn.setPrefWidth(140);
        stoveIdColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<SubmittedForm, String> param) -> param.getValue().getValue().stoveId);

        JFXTreeTableColumn<SubmittedForm, String> fullNameColumn = new JFXTreeTableColumn<>("Full Name");
        fullNameColumn.setPrefWidth(140);
        fullNameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<SubmittedForm, String> param) -> param.getValue().getValue().fullName);

        JFXTreeTableColumn<SubmittedForm, String> citizenShipNumberColumn = new JFXTreeTableColumn<>("CitizenShip No.");
        citizenShipNumberColumn.setPrefWidth(140);
        citizenShipNumberColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<SubmittedForm, String> param) -> param.getValue().getValue().citizenShipNumber);

        JFXTreeTableColumn<SubmittedForm, String> citizenDistrictColumn = new JFXTreeTableColumn<>("Citizen District");
        citizenDistrictColumn.setPrefWidth(140);
        citizenDistrictColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<SubmittedForm, String> param) -> param.getValue().getValue().citizenDistrict);
        ObservableList<SubmittedForm> forms = FXCollections.observableArrayList();
        List<SubmittedForm> aggregateForms = this.aggregateFormService.succeedList();
        
        forms.addAll(aggregateForms);

        final TreeItem<SubmittedForm> formTreeItem = new RecursiveTreeItem<>(forms, (param) -> {
            return param.getChildren(); //To change body of generated lambdas, choose Tools | Templates.
        });
        treeView.setRoot(formTreeItem);
        treeView.getColumns().setAll(serialNumber, webRocketId, stoveIdColumn, fullNameColumn, citizenShipNumberColumn, citizenDistrictColumn);
        treeView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends TreeItem<SubmittedForm>> observable, TreeItem<SubmittedForm> oldValue, TreeItem<SubmittedForm> newValue) -> {
            TreeItem<SubmittedForm> selectedItem = (TreeItem<SubmittedForm>) newValue;
            if(selectedItem!=null){
                SubmittedForm selectedForm = selectedItem.getValue();
                selectedForm.setIsEditable(true);
            }
        });
        treeView.setShowRoot(false);
        searchByStoveId.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            treeView.setPredicate((TreeItem<SubmittedForm> t) -> t.getValue().stoveId.getValue().contains(newValue));
        });
    }

}
