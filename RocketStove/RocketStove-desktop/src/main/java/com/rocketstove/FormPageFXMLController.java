/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove;

import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.DocumentException;
import com.rocketstove.domain.AggregateForm;
import com.rocketstove.domain.AreaClass;
import com.rocketstove.domain.CurrentAddress;
import com.rocketstove.domain.District;
import com.rocketstove.domain.Ethnicity;
import com.rocketstove.domain.Gender;
import com.rocketstove.domain.GeneralInformationI;
import com.rocketstove.domain.Investment;
import com.rocketstove.domain.Manufacturer;
import com.rocketstove.domain.PermanentAddress;
import com.rocketstove.domain.StarterForm;
import com.rocketstove.domain.StoveModel;
import com.rocketstove.domain.Technical;
import com.rocketstove.domain.VPNP;
import com.rocketstove.service.AggregateFormService;
import com.rocketstove.utils.JsonUtils;
import com.rocketstove.utils.LocalJsonUtils;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.IntStream;
import javafx.application.HostServices;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author SUJAN
 */
@Component
public class FormPageFXMLController implements Initializable {

    @Autowired
    private AggregateFormService aggregateFormService;
    @Value("${asset_dir}")
    private String assetDir;

    @Value("${img_folder}")
    private String imgFilePath;
    @Value("${second_form_img}")
    private String secondFormImage;

    private AggregateForm aggregateForm;

    private String pathDirName;

    @FXML
    private Label userLabel;

    @FXML
    private Label urlCheck;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label fileNameLabel;

    @FXML
    private Label filePathLabel;

    public String takeLabel;

    @FXML
    private ComboBox<String> areaClassBox, genderBox, ethnicityBox, citizenshipDistrictBox, districtBox, vpnpBox, vdcnpBox, manufacturerBox, modelBox;

    @FXML
    private TextField installationDate;

    @FXML
    private TextField ownerName, spouseName, citizenshipNo, callingName, parentsName, addressVP_NP_WardNo, addressVP_NP_NearestRoad, addressVP_NP_Village, addressVP_NP_HouseNo;

    @FXML
    private TextField addressVDC_NP_WardNo, addressVDC_NP_Village, engraveNo, totalCost, subsidySupportAmount, subsidyAmount, userAmount;

    private String rocketId, citizenBackImg, citizenFrontImg, stoveHandOverImg, stoveIDImg, formFirstImg, formSecongImg;

    private Long picTakenTimeStamp;

    private final List<String> filePaths = new ArrayList<>();

    private List<String> eList = null;
    private List<Ethnicity> eeList = null;

    HostServices hs;

    private File stockIdFiletoUpload;

    private File stoveHandOverToUpload;

    private File subsidyFileToUpload;

    private File citizenFileToUpload;

    public void GetUser(String user) {
        userLabel.setText(user);
    }

    public void GetUrl(String url) {
        urlCheck.setText(url);
    }

    public void GetPassword(String password) {
        passwordLabel.setText(password);
    }

    public void GetFileName(String fileName) {
        fileNameLabel.setText(fileName);
    }

    public void GetFilePath(String filePath) {
        filePathLabel.setText(filePath);
    }

    public AggregateForm getAggregateForm() {
        return aggregateForm;
    }

    public void setAggregateForm(AggregateForm aggregateForm) {
        this.aggregateForm = aggregateForm;
        readJSONfile(aggregateForm);
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateComboBox();
    }

    public void districtComboChanged(ActionEvent event) {
        String selectedDistrict = districtBox.getValue();
        vpnpBox.setItems(populateVPNPComboBox(selectedDistrict));
        vdcnpBox.setItems(populateVDCNPComboBox(selectedDistrict));
    }

    public void manufacturerComboChanged(ActionEvent event) {
        String selectedManufacturer = manufacturerBox.getValue();
        modelBox.setItems(populatemodelComboBox(selectedManufacturer));
    }

    public void closeEvent(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation for delete");
        alert.setContentText("Do you really want to delete this form?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            this.aggregateFormService.deleteByRocketId(aggregateForm.getRocketId());
        }
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void submitAction(ActionEvent event) throws InterruptedException, IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Review Form");
        alert.setContentText("Do you want to save this form?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            this.modifyJSONfile();
            boolean isSavedOrUpdate = this.aggregateFormService.saveOrUpdateForm(Optional.ofNullable(aggregateForm));
            if (isSavedOrUpdate) {
                informationSaved("Form is saved successfully.");
            } else {
                informationSaved("Form is not saved, please review once again.");
            }

        }
    }

    public void attachmentsAction(ActionEvent event) throws DocumentException, InterruptedException {
        try {
            Stage primaryStage = new Stage();
            FXMLLoader attachmentLoader = new FXMLLoader();
            Pane root = attachmentLoader.load(getClass().getResource("/fxml/AttachmentFXML.fxml").openStream());
            AttachmentFXMLController attachmentFXMLController = (AttachmentFXMLController) attachmentLoader.getController();
            attachmentFXMLController.getPathValueFromPreviousController(aggregateForm);
            Scene scene = new Scene(root);
            primaryStage.setMaximized(false);
            primaryStage.setResizable(false);
            primaryStage.setTitle("View Attachments");
            primaryStage.setScene(scene);
            primaryStage.setFullScreen(false);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void submitActionForSendToAutomate(ActionEvent event) throws IOException {
        if (StringUtils.isNotBlank(aggregateForm.getStarterForm().getCitizenBackImg())
                || StringUtils.isNotBlank(aggregateForm.getStarterForm().getCitizenFrontImg())
                || StringUtils.isNotBlank(aggregateForm.getStarterForm().getsAFormFirstImg())
                || StringUtils.isNotBlank(aggregateForm.getStarterForm().getStoveHandOverImg())
                || StringUtils.isNotBlank(aggregateForm.getStarterForm().getStoveIDImg())
                || StringUtils.isNotBlank(aggregateForm.getStarterForm().getsAFormSecondImg())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Ready to automate");
            alert.setContentText("Is this form ready for automation?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                aggregateForm.setIsReviewed(true);
                aggregateForm.setCompleted(true);
                aggregateForm.setUploadedToServer(false);
                aggregateForm.setIsAutomationReady(true);
                this.modifyJSONfile();
                aggregateForm.setIsFailedToAutomated(false);
                if (StringUtils.isNotBlank(aggregateForm.getStarterForm().getCitizenBackImg())
                        || StringUtils.isNotBlank(aggregateForm.getStarterForm().getCitizenFrontImg())
                        || StringUtils.isNotBlank(aggregateForm.getStarterForm().getsAFormFirstImg())
                        || StringUtils.isNotBlank(aggregateForm.getStarterForm().getStoveHandOverImg())
                        || StringUtils.isNotBlank(aggregateForm.getStarterForm().getStoveIDImg())
                        || StringUtils.isNotBlank(aggregateForm.getStarterForm().getsAFormSecondImg())) {
                    boolean isSavedOrUpdate = this.aggregateFormService.saveOrUpdateForm(Optional.ofNullable(aggregateForm));
                    if (isSavedOrUpdate) {
                        Stage stage = (Stage) areaClassBox.getScene().getWindow();
                        stage.close();
                    } else {
                        alertForNotSaved();
                    }
                } else {
                    alertMessage();
                }

            }
        } else {
            alertMessage();
        }

    }

    public void newAction(ActionEvent event) {
        //Block of commented code removed from here
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public void modifyAction(ActionEvent event) throws IOException {
        modifyJSONfile();
    }

    public void populateComboBox() {
        areaClassBox.setItems(populateAreaClassComboBox());
        genderBox.setItems(populateGenderComboBox());
        ethnicityBox.setItems(populateEthnicityComboBox());
        citizenshipDistrictBox.setItems(populateCitizenshipDistrictComboBox());
        districtBox.setItems(populateDistrictComboBox());
        if (aggregateForm.getCurrentAddressForm() != null) {
            if (StringUtils.isNotBlank(aggregateForm.getCurrentAddressForm().getDistrict())) {
                vpnpBox.setItems(populateVDCNPComboBox(aggregateForm.getCurrentAddressForm().getDistrict()));
                vdcnpBox.setItems(populateVDCNPComboBox(aggregateForm.getCurrentAddressForm().getDistrict()));
            }
        }
        districtBox.valueProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            vpnpBox.setItems(populateVPNPComboBox(newValue));
            vdcnpBox.setItems(populateVPNPComboBox(newValue));
        });
        manufacturerBox.setItems(populateManufacturerComboBox());
        manufacturerBox.valueProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            modelBox.setItems(populatemodelComboBox(newValue));
        });
        if (aggregateForm.getTechnicalForm() != null) {
            if (StringUtils.isNotBlank(aggregateForm.getTechnicalForm().getManufacturer())) {
                modelBox.setItems(populatemodelComboBox(aggregateForm.getTechnicalForm().getManufacturer()));

            }
        }

    }

    public ObservableList<String> populateAreaClassComboBox() {
        List<AreaClass> areaclasses = null;
        ObservableList<String> areaClassStringList;
        areaClassStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<AreaClass>>() {
            }.getType();
            areaclasses = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "areaclass.json"), Charset.defaultCharset()), type);
            for (AreaClass areaclass : areaclasses) {
                areaClassStringList.add(areaclass.getAreaclassName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return areaClassStringList;
        //return genderStrings;
    }

    public ObservableList<String> populateGenderComboBox() {
        List<Gender> genders = null;
        ObservableList<String> genderStringList;
        genderStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<Gender>>() {
            }.getType();
            genders = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "gender.json"), Charset.defaultCharset()), type);
            for (Gender gender : genders) {
                genderStringList.add(gender.getGenderName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return genderStringList;
    }

    public ObservableList<String> populateEthnicityComboBox() {
        List<Ethnicity> ethnicities = null;
        ObservableList<String> ethnicityStringList;
        ethnicityStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<Ethnicity>>() {
            }.getType();
            //ethnicities = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File("src/main/resources/assets/ethnicity.json"), Charset.defaultCharset()), type);
            ethnicities = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "ethnicity.json"), "UTF-8"), type);
            for (Ethnicity ethnicity : ethnicities) {
                ethnicityStringList.add(ethnicity.getEthnicityName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        eList = ethnicityStringList;
        return ethnicityStringList;
    }

    public ObservableList<String> populateCitizenshipDistrictComboBox() {
        List<District> districts = null;
        ObservableList<String> districtStringList;
        districtStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<District>>() {
            }.getType();
            districts = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "district.json"), "UTF-8"), type);
            for (District district : districts) {
                districtStringList.add(district.getDistrictName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return districtStringList;
    }

    public ObservableList<String> populateDistrictComboBox() {
        List<District> districts = null;
        ObservableList<String> districtStringList;
        districtStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<District>>() {
            }.getType();
            districts = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "district.json"), "UTF-8"), type);
            for (District district : districts) {
                districtStringList.add(district.getDistrictName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return districtStringList;
    }

    public ObservableList<String> populateVPNPComboBox(String selectedDistrict) {
        List<District> districts = null;
        ObservableList<String> vpnpStringList;
        vpnpStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<District>>() {
            }.getType();
            districts = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "district.json"), "UTF-8"), type);
            District currentDistrict = districts.stream()
                    .filter(cd -> cd.getDistrictName().equalsIgnoreCase(selectedDistrict)).findAny().orElse(districts.get(0));
            List<VPNP> vpnps = currentDistrict.getVpnp();
            for (VPNP vpnp : vpnps) {
                vpnpStringList.add(vpnp.getAddressName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return vpnpStringList;
    }

    public ObservableList<String> populateVDCNPComboBox(String selectedDistrict) {
        List<District> districts = null;
        ObservableList<String> vdcnpStringList;
        vdcnpStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<District>>() {
            }.getType();
            districts = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "district.json"), "UTF-8"), type);
            District currentDistrict = districts.stream()
                    .filter(cd -> cd.getDistrictName().equalsIgnoreCase(selectedDistrict)).findAny().orElse(districts.get(0));
            List<VPNP> vpnps = currentDistrict.getVpnp();
            for (VPNP vpnp : vpnps) {
                vdcnpStringList.add(vpnp.getAddressName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return vdcnpStringList;
    }

    public ObservableList<String> populateManufacturerComboBox() {
        List<Manufacturer> manufacturers = null;
        ObservableList<String> manufacturerStringList;
        manufacturerStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<Manufacturer>>() {
            }.getType();
            manufacturers = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "manufacturer.json"), "UTF-8"), type);
            for (Manufacturer manufacturer : manufacturers) {
                manufacturerStringList.add(manufacturer.getManufacturerName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return manufacturerStringList;
    }

    public ObservableList<String> populatemodelComboBox(String selectedManufacturer) {
        List<Manufacturer> manufacturers = null;
        ObservableList<String> modelStringList;
        modelStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<Manufacturer>>() {
            }.getType();
            manufacturers = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "manufacturer.json"), "UTF-8"), type);
            Manufacturer currentManufacturer = manufacturers.stream()
                    .filter(m -> m.getManufacturerName().equalsIgnoreCase(selectedManufacturer)).findAny().orElse(manufacturers.get(0));
            List<StoveModel> stoveModels = currentManufacturer.getStoveModel();
            stoveModels.forEach((stove) -> {
                modelStringList.add(stove.getStovemodelName());
            });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return modelStringList;
    }

    public void setValuesInFields() {
        ownerName.setText("Test Name");
        citizenshipDistrictBox.getSelectionModel().select(2);
    }

    public void getPathValueFromPreviousController(String pathName) throws IOException {
        pathDirName = pathName;
    }

    private void readJSONfile(AggregateForm aggregateForm) {
        rocketId = aggregateForm.getStarterForm().getRocketId();
        citizenBackImg = aggregateForm.getStarterForm().getCitizenBackImg();
        citizenFrontImg = aggregateForm.getStarterForm().getCitizenFrontImg();
        picTakenTimeStamp = aggregateForm.getStarterForm().getPicTakenTimeStramp();
        stoveHandOverImg = aggregateForm.getStarterForm().getStoveHandOverImg();
        stoveIDImg = aggregateForm.getStarterForm().getStoveIDImg();
        formFirstImg = aggregateForm.getStarterForm().getsAFormFirstImg();
        formSecongImg = secondFormImage;
        List<District> districts = LocalJsonUtils.loadDistrict(assetDir);
        if (aggregateForm.getStarterForm() != null) {
            if (StringUtils.isNotBlank(aggregateForm.getStarterForm().getAreaClass())) {
                List<AreaClass> areaClasses = LocalJsonUtils.loadAreaClass(assetDir);
                AreaClass areaClass = areaClasses.stream().filter(areaClass1 -> areaClass1.getAreaclassName()
                        .equalsIgnoreCase(aggregateForm.getStarterForm().getAreaClass()))
                        .findAny().orElse(areaClasses.get(0));
                areaClassBox.getSelectionModel().select(areaClass.getAreaclassName());
            }
        }
        if (aggregateForm.getGeneralForm() != null) {
            List<Gender> genders = LocalJsonUtils.loadGender(assetDir);
            Gender gender = genders.stream().filter(g -> g.getGenderName().equalsIgnoreCase(aggregateForm.getGeneralForm().getGender()))
                    .findAny().orElse(genders.get(0));
            List<Ethnicity> ethnicityList = LocalJsonUtils.loadEthinicity(assetDir);
            eeList = ethnicityList;
            int ethnicityIndex = 0;
            for (Ethnicity e : ethnicityList) {
                if (e.getEthnicityName().equalsIgnoreCase(aggregateForm.getGeneralForm().getEthinicity())) {
                    ethnicityIndex = ethnicityList.indexOf(e);
                }
            }
            District citizenDistrict = districts.stream().filter(d -> d.getDistrictName().equalsIgnoreCase(aggregateForm.getGeneralForm().getCitizenDistrict()))
                    .findAny().orElse(districts.get(0));
            String toSelect = eList.get(ethnicityIndex);
            ethnicityBox.getSelectionModel().select(toSelect);
            ownerName.setText(aggregateForm.getGeneralForm().getOwnerName());
            callingName.setText(aggregateForm.getGeneralForm().getCallingName());
            spouseName.setText(aggregateForm.getGeneralForm().getHusbandWifeName());
            parentsName.setText(aggregateForm.getGeneralForm().getFatherMotherName());
            citizenshipNo.setText(aggregateForm.getGeneralForm().getCitizenShip());
            citizenshipDistrictBox.getSelectionModel().select(citizenDistrict.getDistrictName());
            genderBox.getSelectionModel().select(gender.getGenderName());

        }
        if (aggregateForm.getCurrentAddressForm() != null) {
            District currentDistrict = districts.stream().filter(cd -> cd.getDistrictName().equalsIgnoreCase(aggregateForm.getCurrentAddressForm().getDistrict()))
                    .findAny().orElse(districts.get(0));
            addressVP_NP_WardNo.setText(aggregateForm.getCurrentAddressForm().getWardNumber());
            addressVP_NP_NearestRoad.setText(aggregateForm.getCurrentAddressForm().getNearestRoom());
            addressVP_NP_Village.setText(aggregateForm.getCurrentAddressForm().getVillage());
            addressVP_NP_HouseNo.setText(aggregateForm.getCurrentAddressForm().getHouseNumber());
            List<VPNP> vpnps = currentDistrict.getVpnp();
            VPNP vpnp = vpnps.stream().filter(v -> v.getAddressName().equalsIgnoreCase(aggregateForm.getCurrentAddressForm().getVpNp()))
                    .findAny().orElse(vpnps.get(0));
            districtBox.getSelectionModel().select(currentDistrict.getDistrictName());
            vpnpBox.getSelectionModel().select(vpnp.getAddressName());

            if (aggregateForm.getPermanentAddressForm() != null) {
                VPNP permanentVpnp = vpnps.stream().filter(v -> v.getAddressName().equalsIgnoreCase(aggregateForm.getPermanentAddressForm().getVpNp()))
                        .findAny().orElse(vpnps.get(0));
                vdcnpBox.getSelectionModel().select(permanentVpnp.getAddressName());
                addressVDC_NP_WardNo.setText(aggregateForm.getPermanentAddressForm().getWardNumber());
                addressVDC_NP_Village.setText(aggregateForm.getPermanentAddressForm().getVillage());
            }

        }
        if (aggregateForm.getTechnicalForm() != null) {
            List<Manufacturer> manufacturers = LocalJsonUtils.loadManufacturer(assetDir);
            if (StringUtils.isNotBlank(aggregateForm.getTechnicalForm().getManufacturer())) {
                Manufacturer manufacturer = manufacturers.stream().filter(m -> m.getManufacturerName()
                        .equalsIgnoreCase(aggregateForm.getTechnicalForm().getManufacturer()))
                        .findAny().orElse(manufacturers.get(0));
                manufacturerBox.getSelectionModel().select(manufacturer.getManufacturerName());
                if (StringUtils.isNotBlank(aggregateForm.getTechnicalForm().getModel())) {
                    List<StoveModel> stoveModels = manufacturer.getStoveModel();
                    StoveModel stoveModel = stoveModels.stream().filter(s -> s.getStovemodelName().equalsIgnoreCase(aggregateForm.getTechnicalForm().getModel()))
                            .findAny().orElse(stoveModels.get(0));
                    modelBox.getSelectionModel().select(stoveModel.getStovemodelName());
                }
            }
            engraveNo.setText(aggregateForm.getTechnicalForm().getEngraveNumber());
            installationDate.setText(aggregateForm.getTechnicalForm().getInstallDate());
        }
        if (aggregateForm.getInvestmentForm() != null) {
            totalCost.setText(String.valueOf(aggregateForm.getInvestmentForm().getTotalStoveCost()));
            subsidySupportAmount.setText("0.00");
            subsidyAmount.setText(String.valueOf(aggregateForm.getInvestmentForm().getSubsidySupport()));
            userAmount.setText(String.valueOf(aggregateForm.getInvestmentForm().getUserAmount()));
        }
    }

    private void modifyJSONfile() throws IOException {
        String areaClassBoxValue = areaClassBox.getValue();
        String ethnicityBoxValue = ethnicityBox.getValue();
        String citizenshipDistrictBoxValue = citizenshipDistrictBox.getValue();
        String genderBoxValue = genderBox.getValue();
        String districtBoxValue = districtBox.getValue();
        String vpnpBoxValue = vpnpBox.getValue();
        String vdcnpBoxValue = vdcnpBox.getValue();
        String ownerNameValue = ownerName.getText();
        String callingNameValue = callingName.getText();
        String spouseNameValue = spouseName.getText();
        String parentsNameValue = parentsName.getText();
        String ciizenshipNoValue = citizenshipNo.getText();
        String addressVP_NP_WardNoValue = addressVP_NP_WardNo.getText();
        String addressVP_NP_NearestRoadValue = addressVP_NP_NearestRoad.getText();
        String addressVP_NP_VillageValue = addressVP_NP_Village.getText();
        String addressVP_NP_HouseNoValue = addressVP_NP_HouseNo.getText();
        String addressVDC_NP_WardNoValue = addressVDC_NP_WardNo.getText();
        String addressVDC_NP_VillageValue = addressVDC_NP_Village.getText();
        String manufacturerBoxValue = manufacturerBox.getValue();
        String modelBoxValue = modelBox.getValue();
        String engraveNoValue = engraveNo.getText();
        String installationDateValue = installationDate.getText();
        String totalCostValue = totalCost.getText() == null ? "0.00" : totalCost.getText();
        String subsidySupportAmountValue = subsidySupportAmount.getText() == null ? "0.00" : subsidySupportAmount.getText();
        String subsidyAmountValue = subsidyAmount.getText() == null ? "0.00" : subsidyAmount.getText();
        String userAmountValue = userAmount.getText() == null ? "0.00" : userAmount.getText();
        aggregateForm.getStarterForm().setAreaClass(areaClassBoxValue);
        aggregateForm.getStarterForm().setRocketId(rocketId);
        aggregateForm.getStarterForm().setCitizenBackImg(citizenBackImg);
        aggregateForm.getStarterForm().setCitizenFrontImg(citizenFrontImg);
        aggregateForm.getStarterForm().setPicTakenTimeStramp(picTakenTimeStamp);
        aggregateForm.getStarterForm().setStoveHandOverImg(stoveHandOverImg);
        aggregateForm.getStarterForm().setStoveIDImg(stoveIDImg);
        aggregateForm.getStarterForm().setsAFormFirstImg(formFirstImg);
        aggregateForm.getStarterForm().setsAFormSecondImg(secondFormImage);
        GeneralInformationI generalInformationI = new GeneralInformationI();
        generalInformationI.setOwnerName(ownerNameValue);
        generalInformationI.setCallingName(callingNameValue);
        generalInformationI.setHusbandWifeName(spouseNameValue);
        generalInformationI.setGender(genderBoxValue);
        generalInformationI.setFatherMotherName(parentsNameValue);
        if(eeList==null||eeList.isEmpty()){
            eeList = LocalJsonUtils.loadEthinicity(assetDir);
        }
        int convertEthnicity = 0;
        for (String ethnicityString : eList) {
            if (ethnicityString.equalsIgnoreCase(ethnicityBoxValue)) {
                convertEthnicity = eList.indexOf(ethnicityString);
                break;
            }
        }
        
//        System.err.println("List is : "+JsonUtils.toJsonList(eeList));
//        int indexOf = IntStream.range(0, eList.size())
//                .filter(e -> eList.get(e).equalsIgnoreCase(ethnicityBoxValue)).findFirst().orElse(0);
        generalInformationI.setEthinicity(eeList.get(convertEthnicity).getEthnicityName());
        generalInformationI.setCitizenShip(ciizenshipNoValue);
        generalInformationI.setCitizenDistrict(citizenshipDistrictBoxValue);
        generalInformationI.setRocketId(rocketId);
        aggregateForm.setGeneralForm(generalInformationI);
        CurrentAddress currentAddress = new CurrentAddress();
        currentAddress.setDistrict(districtBoxValue);
        currentAddress.setHouseNumber(addressVP_NP_HouseNoValue);
        currentAddress.setNearestRoom(addressVP_NP_NearestRoadValue);
        currentAddress.setVillage(addressVP_NP_VillageValue);
        currentAddress.setVpNp(vpnpBoxValue);
        currentAddress.setWardNumber(addressVP_NP_WardNoValue);
        currentAddress.setRocketId(rocketId);
        aggregateForm.setCurrentAddressForm(currentAddress);
        PermanentAddress permanentAddress = new PermanentAddress();
        permanentAddress.setVillage(addressVDC_NP_VillageValue);
        permanentAddress.setVpNp(vdcnpBoxValue);
        permanentAddress.setWardNumber(addressVDC_NP_WardNoValue);
        permanentAddress.setRocketId(rocketId);
        aggregateForm.setPermanentAddressForm(permanentAddress);
        Technical technical = new Technical();
        technical.setEngraveNumber(engraveNoValue);
        technical.setInstallDate(installationDateValue);
        technical.setManufacturer(manufacturerBoxValue);
        technical.setModel(modelBoxValue);
        technical.setRocketId(rocketId);
        aggregateForm.setTechnicalForm(technical);
        Investment investment = new Investment();
        if (StringUtils.isNotBlank(subsidyAmountValue)) {
            investment.setSubsidyAmount(Double.parseDouble(subsidyAmountValue));
        }
        if (StringUtils.isNotBlank(subsidySupportAmountValue)) {
            investment.setSubsidySupport(Double.parseDouble(subsidySupportAmountValue));

        }
        if (StringUtils.isNotBlank(totalCostValue)) {
            investment.setTotalStoveCost(Double.parseDouble(totalCostValue));
            investment.setUserAmount(Double.parseDouble(userAmountValue));
        }
        if (StringUtils.isNotBlank(userAmountValue)) {
            investment.setUserAmount(Double.parseDouble(userAmountValue));
        }
        investment.setRocketId(rocketId);
        aggregateForm.setInvestmentForm(investment);

    }

    public void stoveIdBtnClick(ActionEvent event) throws MalformedURLException {
        StarterForm starterForm = this.aggregateFormService.getStarterForm(rocketId);
        if (starterForm != null) {
            String stoveIdImg = starterForm.getStoveIDImg();
            if (StringUtils.isNotBlank(stoveIdImg)) {
                MainApp.hs.showDocument(stoveIdImg);
            } else {
                alertMessage();
            }
        } else {
            alertMessage();
        }
    }

    public void stoveHandoverBtnClick(ActionEvent event) throws MalformedURLException {
        StarterForm starterForm = this.aggregateFormService.getStarterForm(rocketId);
        if (starterForm != null) {
            String stoveHandoverImg = starterForm.getStoveHandOverImg();
            if (StringUtils.isNotBlank(stoveHandoverImg)) {
                MainApp.hs.showDocument(stoveHandoverImg);
            } else {
                alertMessage();
            }
        } else {
            alertMessage();
        }
    }

    public void subsidyFrontBtnClick(ActionEvent event) {
        StarterForm starterForm = this.aggregateFormService.getStarterForm(rocketId);
        if (starterForm != null) {
            String saFirstImage = starterForm.getsAFormFirstImg();
            if (StringUtils.isNotBlank(saFirstImage)) {
                MainApp.hs.showDocument(saFirstImage);
            } else {
                alertMessage();
            }
        } else {
            alertMessage();
        }
    }

    public void citizenFrontBtnClick(ActionEvent event) {
        StarterForm starterForm = this.aggregateFormService.getStarterForm(rocketId);
        if (starterForm != null) {
            String cFrontImage = starterForm.getCitizenFrontImg();
            if (StringUtils.isNotBlank(cFrontImage)) {
                MainApp.hs.showDocument(cFrontImage);
            } else {
                alertMessage();
            }
        } else {
            alertMessage();
        }
    }

    public void citizenBackBtnClick(ActionEvent event) {
        StarterForm starterForm = this.aggregateFormService.getStarterForm(rocketId);
        if (starterForm != null) {
            String cBackImage = starterForm.getCitizenBackImg();
            if (StringUtils.isNotBlank(cBackImage)) {
                MainApp.hs.showDocument(cBackImage);
            } else {
                alertMessage();
            }
        } else {
            alertMessage();
        }
    }

    public void stoveIdBtnEditAction(ActionEvent event) {
        //Upadate
        stoveIDImg = getNewImageFile();
        aggregateForm.getStarterForm().setStoveIDImg(stoveIDImg);
        persistImage();

    }

    private void persistImage() {
        boolean persist = this.aggregateFormService.saveImages(aggregateForm.getStarterForm());
        if (!persist) {
            alertForBadImage();
        }
    }

    public void stoveHandoverBtnEditAction(ActionEvent event) throws MalformedURLException {
        //Upadate
        stoveHandOverImg = getNewImageFile();
        aggregateForm.getStarterForm().setStoveHandOverImg(stoveHandOverImg);
        persistImage();
    }

    public void subsidyFrontBtnEditAction(ActionEvent event) throws MalformedURLException {
        //Upadate
        formFirstImg = getNewImageFile();
        aggregateForm.getStarterForm().setsAFormFirstImg(formFirstImg);
        persistImage();
    }

    public void citizenFrontBtnEditAction(ActionEvent event) throws MalformedURLException {
        //Upadate
        citizenFrontImg = getNewImageFile();
        aggregateForm.getStarterForm().setCitizenFrontImg(citizenFrontImg);
        persistImage();
    }

    public void citizenBackBtnEditAction(ActionEvent event) throws MalformedURLException {
        //Upadate
        citizenBackImg = getNewImageFile();
        aggregateForm.getStarterForm().setCitizenFrontImg(citizenBackImg);
        persistImage();
    }

    public String getNewImageFile() {
        filePaths.clear();
        String editedPath = "";
        FileChooser fcOne = new FileChooser();
        fcOne.setInitialDirectory(new File(imgFilePath));
        fcOne.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG Files", "*.jpg"));
        File selectedFile = fcOne.showOpenDialog(null);
        if (selectedFile != null) {
            filePaths.add(selectedFile.getAbsoluteFile().toString());
        } else {
            System.out.println("Invalid File Detected");
        }
        for (String filePath : filePaths) {
            editedPath = filePath;
        }
        System.out.println("New Image Path: " + editedPath);
        return editedPath;
    }

    private void alertMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Form Incomplete");
        alert.setContentText("Image is not provided. Please attach the image.");
        alert.showAndWait();
    }

    private void alertForBadImage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Edit Image");
        alert.setContentText("Image are failed to edit,please try again with valid image");
        alert.showAndWait();
    }

    private void alertForNotSaved() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Form Submission");
        alert.setContentText("Form failed to submit,please try again");
        alert.showAndWait();
    }

    private void informationSaved(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Form Submission");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
