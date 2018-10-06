/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solar;

import com.google.gson.reflect.TypeToken;
import com.solar.domain.AreaClass;
import com.solar.domain.AssId;
import com.solar.domain.AssIdType;
import com.solar.domain.BDAType;
import com.solar.domain.BDAid;
import com.solar.domain.BatteryManufacturer;
import com.solar.domain.BatteryModel;
import com.solar.domain.BatteryType;
import com.solar.domain.BulbType;
import com.solar.domain.ChargeControllerModel;
import com.solar.domain.ChargeManufacuturer;
import com.solar.domain.CitizenDistrict;
import com.solar.domain.District;
import com.solar.domain.Ethnicity;
import com.solar.domain.Gender;
import com.solar.domain.Installer;
import com.solar.domain.InstitutionType;
import com.solar.domain.LampManufacturer;
import com.solar.domain.LampModel;
import com.solar.domain.MobileCharge;
import com.solar.domain.PanelManufacturer;
import com.solar.domain.PanelModel;
import com.solar.domain.Radio;
import com.solar.domain.RadioCharge;
import com.solar.domain.TargetGroup;
import com.solar.domain.Television;
import com.solar.domain.VPNP;
import com.solar.entity.Attachment;
import com.solar.entity.Banking;
import com.solar.entity.BatteryTechnical;
import com.solar.entity.ChargeControllerTechnical;
import com.solar.entity.Confirmation;
import com.solar.entity.CurrentAddress;
import com.solar.entity.EndUse;
import com.solar.entity.GeneralTechnical;
import com.solar.entity.Investment;
import com.solar.entity.LampTechnical;
import com.solar.entity.Owner;
import com.solar.entity.PanelTechnical;
import com.solar.entity.PermanentAddress;
import com.solar.entity.SolarForm;
import com.solar.service.SolarFormService;
import com.solar.utils.JsonUtils;
import com.solar.utils.LocalJsonUtils;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.HostServices;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Component
public class MainFormPageFXMLController implements Initializable {

    @FXML
    private Button btnLogout;
    @FXML
    private ComboBox<String> areaClassBox;
    @FXML
    private TextField solarId;
    @FXML
    private TextField citizenshipNo;
    @FXML
    private ComboBox<String> genderBox;
    @FXML
    private ComboBox<String> ethnicityBox;
    @FXML
    private ComboBox<String> citizenshipDistrictBox;
    @FXML
    private TextField ownerName;
    @FXML
    private TextField callingName;
    @FXML
    private ComboBox<String> targetGroupBox;
    @FXML
    private TextField ownerMobileNo;
    @FXML
    private TextField neighbourName;
    @FXML
    private TextField husbandFatherName;
    @FXML
    private TextField hfMobileNo;
    @FXML
    private TextField neighbourMobileNo;
    @FXML
    private TextField addressVP_NP_WardNo;
    @FXML
    private TextField addressVP_NP_Village;
    @FXML
    private ComboBox<String> districtBox;
    @FXML
    private ComboBox<String> vpnpBox;
    @FXML
    private TextField addressVDC_NP_WardNo;
    @FXML
    private TextField addressVDC_NP_Village;
    @FXML
    private ComboBox<String> vdcnpBox;
    @FXML
    private ComboBox<String> assTypeBox;
    @FXML
    private ComboBox<String> installerBox;
    @FXML
    private TextField installationDate;
    @FXML
    private ComboBox<String> bdaTypeBox;
    @FXML
    private ComboBox<String> bdaIdBox;
    @FXML
    private ComboBox<String> assIdBox;
    @FXML
    private TextField capacityGeneral;
    @FXML
    private ComboBox<String> technicalPanelManufacturerBox;
    @FXML
    private ComboBox<String> technicalPanelModelBox;
    @FXML
    private TextField technicalPanelSNo;
    @FXML
    private TextField capacityPanel;
    @FXML
    private TextField technicalBatterySNo;
    @FXML
    private TextField capacityBattery;
    @FXML
    private ComboBox<String> technicalBatteryManufacturerBox;
    @FXML
    private ComboBox<String> technicalBatteryModelBox;
    @FXML
    private ComboBox<String> technicalBatteryTypeBox;
    @FXML
    private ComboBox<String> technicalChargeManufacturerBox;
    @FXML
    private ComboBox<String> technicalChargeModelBox;
    @FXML
    private Label dLabel;
    @FXML
    private TextField brand;
    @FXML
    private TextField rating;
    @FXML
    private TextField capacityLamp;
    @FXML
    private ComboBox<String> technicalLampManufacturerBox;
    @FXML
    private ComboBox<String> technicalLampModelBox;
    @FXML
    private TextField annualIncome;
    @FXML
    private TextField totalSystemCost;
    @FXML
    private TextField batteryAmount;
    @FXML
    private TextField subsidyAmount;
    @FXML
    private TextField supportAmount;
    @FXML
    private TextField userAmount;
    @FXML
    private TextField incomeSource;
    @FXML
    private TextField earthquakeSubsidy;
    @FXML
    private TextField institutionAddress;
    @FXML
    private ComboBox<String> institutionTypeBox;
    @FXML
    private TextField loanAmount;
    @FXML
    private TextField institutionName;
    @FXML
    private TextField interestRate;
    @FXML
    private TextField maturtyPeriod;
    @FXML
    private TextField approvalDate;
    @FXML
    private TextField noOfBulbs;
    @FXML
    private TextField otherConnection;
    @FXML
    private ComboBox<String> mobileChargeOptionBox;
    @FXML
    private ComboBox<String> radioChargeOptionBox;
    @FXML
    private ComboBox<String> radioBox;
    @FXML
    private ComboBox<String> televisionBox;
    @FXML
    private ComboBox<String> typeOfBulbBox;
    @FXML
    private Button subsidyFrontBtn;
    @FXML
    private Button systemOwnerUserInstallerBtn;
    @FXML
    private Button subsidyFrontBtnEdit;
    @FXML
    private Button systemOwnerUserInstallerBtnEdit;
    @FXML
    private Button subsidyBackBtn;
    @FXML
    private Button batteryVoucherBtn;
    @FXML
    private Button subsidyBackBtnEdit;
    @FXML
    private Button batteryVoucherBtnEdit;
    @FXML
    private Button citizenFrontBtn;
    @FXML
    private Button invoiceBtn;
    @FXML
    private Button citizenFrontBtnEdit;
    @FXML
    private Button invoiceBtnEdit;
    @FXML
    private Button citizenBackBtn;
    @FXML
    private Button muchulkaBtn;
    @FXML
    private Button citizenBackBtnEdit;
    @FXML
    private Button systemOwnerUserBtn;
    @FXML
    private Button earthquakeBtn;
    @FXML
    private Button systemOwnerUserBtnEdit;
    @FXML
    private Button earthquakeBtnEdit;
    @FXML
    private Button muchulkaBtnEdit;
    @FXML
    private Button btnNext;
    @FXML
    private Button sendToAutomate;

    @Autowired
    private SolarFormService solarFormService;

    @Value("${asset_dir}")
    private String assetDir;

    @Value("${img_folder}")
    private String imgFilePath;

    private String solarID;

    private String subsidyFrontImg, subsidyBackImg, citizenFrontImg, citizenBackImg, systemOwnerUserImg, systemOwnerUserInstallerImg, batteryVoucherImg, invoiceImg, muchulkaImg, earthquakeImg;

    private SolarForm solarForm;

    private List<String> eList = null;
    private List<Ethnicity> eeList = null;

    private List<String> tgList = null;
    private List<TargetGroup> tggList = null;

    HostServices hs;

    private final List<String> filePaths = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        populateComboBox();
    }

    public void setSolarId(String solarId) {
        this.solarID = solarId;
        //use following to retrieve all the first form relatedData
        this.solarForm = this.solarFormService.findBySolarId(solarId);
        readJSONfile(this.solarForm);
        this.solarId.setText(this.solarID);

    }

    @FXML
    private void closeEvent(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation for delete");
        alert.setContentText("Do you really want to delete this form?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            this.solarFormService.deleteBySolarId(solarForm.getSolarId());
        }
        Stage stage = (Stage) areaClassBox.getScene().getWindow();
        stage.close();
        informationSaved("Solar Id : " + solarForm.getSolarId() + " form has been deleted");
    }

    private void informationSaved(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Form Submission");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void districtComboChanged(ActionEvent event) {
        String selectedDistrict = districtBox.getValue();
        vpnpBox.setItems(populateVPNPComboBox(selectedDistrict));
        vdcnpBox.setItems(populateVDCNPComboBox(selectedDistrict));
    }

    @FXML
    private void assTypeComboChanged(ActionEvent event) {
    }

    @FXML
    private void bdaTypeComboChanged(ActionEvent event) {
    }

    @FXML
    private void bdaIdComboChanged(ActionEvent event) {
    }

    @FXML
    private void assIdComboChanged(ActionEvent event) {
    }

    @FXML
    private void panelManufacturerComboChanged(ActionEvent event) {
    }

    @FXML
    private void batteryManufacturerComboChanged(ActionEvent event) {
    }

    @FXML
    private void chargeManufacturerComboChanged(ActionEvent event) {
    }

    @FXML
    private void lampManufacturerComboChanged(ActionEvent event) {
    }

    @FXML
    private void submitAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Review Form");
        alert.setContentText("Do you want to save this form?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            this.modifyJSONfile();
            log.info(JsonUtils.toJsonObj(solarForm));
            boolean isSaved = this.solarFormService.saveOrUpdateForm(Optional.ofNullable(solarForm));
            if (isSaved) {
                informationSaved("Form is saved successfully.");
            } else {
                informationSaved("Form is not saved, please review.");
            }
        }
    }

    @FXML
    private void submitActionForSendToAutomate(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Review Form");
        alert.setContentText("Do you want to move this form into automation list?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            this.modifyJSONfile();
            this.solarForm.setAutomationReady(true);
            this.solarForm.setReviewed(true);
            this.solarForm.setFailedToAutomated(false);
            this.solarForm.setMessage(null);
            this.solarFormService.saveOrUpdateForm(Optional.ofNullable(solarForm));
            Stage stage = (Stage) areaClassBox.getScene().getWindow();
            stage.close();
        }
    }

    public void populateComboBox() {
        areaClassBox.setItems(populateAreaClassComboBox());
        genderBox.setItems(populateGenderComboBox());
        ethnicityBox.setItems(populateEthnicityComboBox());
        citizenshipDistrictBox.setItems(populateCitizenshipDistrictComboBox());
        districtBox.setItems(populateDistrictComboBox());

        targetGroupBox.setItems(populateTargetGroupComboBox());
        bdaTypeBox.setItems(populateBDATypeComboBox());
        assTypeBox.setItems(populateASSTypeComboBox());
        installerBox.setItems(populateInstallerComboBox());
        bdaIdBox.setItems(populateBDAIdComboBox());
        assIdBox.setItems(populateASSIdComboBox());
        technicalPanelManufacturerBox.setItems(populatePanelManufacturerComboBox());
        technicalPanelModelBox.setItems(populatePanelModelComboBox());

        technicalBatteryManufacturerBox.setItems(populateBatteryManufacturerComboBox());
        technicalBatteryModelBox.setItems(populateBatteryModelComboBox());
        technicalBatteryTypeBox.setItems(populateBatteryTypeComboBox());
        technicalChargeManufacturerBox.setItems(populateChargeManufacturerComboBox());
        technicalChargeModelBox.setItems(populateChargeModelComboBox());
        technicalLampManufacturerBox.setItems(populateLampManufacturerComboBox());
        technicalLampModelBox.setItems(populateLampModelComboBox());
        institutionTypeBox.setItems(populateInstitutionTypeComboBox());
        mobileChargeOptionBox.setItems(populateMobileChargeComboBox());
        radioBox.setItems(populateRadioComboBox());
        typeOfBulbBox.setItems(populateBulbTypeComboBox());
        radioChargeOptionBox.setItems(populateRadioChargeComboBox());
        televisionBox.setItems(populateTelevisionComboBox());

        if (solarForm != null) {
            if (solarForm.getCurrentAddress() != null) {
                if (StringUtils.isNotBlank(solarForm.getCurrentAddress().getDistrict())) {
                    vpnpBox.setItems(populateVDCNPComboBox(solarForm.getCurrentAddress().getDistrict()));
                    vdcnpBox.setItems(populateVDCNPComboBox(solarForm.getCurrentAddress().getDistrict()));
                }
            }
        }
        districtBox.valueProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            vpnpBox.setItems(populateVPNPComboBox(newValue));
            vdcnpBox.setItems(populateVPNPComboBox(newValue));
        });
//        manufacturerBox.setItems(populateManufacturerComboBox());
//        manufacturerBox.valueProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
//            modelBox.setItems(populatemodelComboBox(newValue));
//        });
//        if (solarForm.getTechnicalForm() != null) {
//            if (StringUtils.isNotBlank(solarForm.getTechnicalForm().getManufacturer())) {
//                modelBox.setItems(populatemodelComboBox(solarForm.getTechnicalForm().getManufacturer()));
//
//            }
//        }

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
        List<CitizenDistrict> citizenDistricts = null;
        ObservableList<String> citizenDistrictStringList;
        citizenDistrictStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<CitizenDistrict>>() {
            }.getType();
            citizenDistricts = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "citizenDistrict.json"), "UTF-8"), type);
            for (CitizenDistrict citizenDistrict : citizenDistricts) {
                citizenDistrictStringList.add(citizenDistrict.getCitizenDistrictName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return citizenDistrictStringList;
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

    public ObservableList<String> populateTargetGroupComboBox() {
        List<TargetGroup> targetGroups = null;
        ObservableList<String> targetGroupStringList;
        targetGroupStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<TargetGroup>>() {
            }.getType();
            targetGroups = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "targetGroup.json"), "UTF-8"), type);
            for (TargetGroup targetGroup : targetGroups) {
                targetGroupStringList.add(targetGroup.getTargetGroupName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        tgList = targetGroupStringList;
        return targetGroupStringList;
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

    public ObservableList<String> populateBDATypeComboBox() {
        List<BDAType> bdaTypes = null;
        ObservableList<String> bdaTypeStringList;
        bdaTypeStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<BDAType>>() {
            }.getType();
            bdaTypes = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "bdatype.json"), "UTF-8"), type);
            for (BDAType bdaType : bdaTypes) {
                bdaTypeStringList.add(bdaType.getBdaTypeName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return bdaTypeStringList;
    }

    public ObservableList<String> populateASSTypeComboBox() {
        List<AssIdType> assIdTypes = null;
        ObservableList<String> assIdTypeStringList;
        assIdTypeStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<AssIdType>>() {
            }.getType();
            assIdTypes = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "asstype.json"), "UTF-8"), type);
            for (AssIdType assIdType : assIdTypes) {
                assIdTypeStringList.add(assIdType.getAssTypeName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return assIdTypeStringList;
    }

    public ObservableList<String> populateInstallerComboBox() {
        List<Installer> installers = null;
        ObservableList<String> installerStringList;
        installerStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<Installer>>() {
            }.getType();
            installers = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "installer.json"), "UTF-8"), type);
            for (Installer installer : installers) {
                installerStringList.add(installer.getInstallerName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return installerStringList;
    }

    public ObservableList<String> populateBDAIdComboBox() {
        List<BDAid> bdaIds = null;
        ObservableList<String> bdaIdStringList;
        bdaIdStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<BDAid>>() {
            }.getType();
            bdaIds = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "bdaid.json"), "UTF-8"), type);
            for (BDAid bdaType : bdaIds) {
                bdaIdStringList.add(bdaType.getBdaIdName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return bdaIdStringList;
    }

    public ObservableList<String> populateASSIdComboBox() {
        List<AssId> assIds = null;
        ObservableList<String> assIdStringList;
        assIdStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<AssId>>() {
            }.getType();
            assIds = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "assid.json"), "UTF-8"), type);
            for (AssId assId : assIds) {
                assIdStringList.add(assId.getAssIdName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return assIdStringList;
    }

    public ObservableList<String> populatePanelManufacturerComboBox() {
        List<PanelManufacturer> panelManufacturers = null;
        ObservableList<String> panelManufacturerStringList;
        panelManufacturerStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<PanelManufacturer>>() {
            }.getType();
            panelManufacturers = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "panelManufacturer.json"), "UTF-8"), type);
            for (PanelManufacturer panelManufacturer : panelManufacturers) {
                panelManufacturerStringList.add(panelManufacturer.getPanelManufacturerName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return panelManufacturerStringList;
    }

    public ObservableList<String> populatePanelModelComboBox() {
        List<PanelModel> panelModels = null;
        ObservableList<String> panelModelStringList;
        panelModelStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<PanelModel>>() {
            }.getType();
            panelModels = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "panelModel.json"), "UTF-8"), type);
            for (PanelModel panelModel : panelModels) {
                panelModelStringList.add(panelModel.getPanelModelName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return panelModelStringList;
    }

    public ObservableList<String> populateBatteryManufacturerComboBox() {
        List<BatteryManufacturer> batteryManufacturers = null;
        ObservableList<String> batteryManufacturerStringList;
        batteryManufacturerStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<BatteryManufacturer>>() {
            }.getType();
            batteryManufacturers = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "batteryManufacturer.json"), "UTF-8"), type);
            for (BatteryManufacturer batteryManufacturer : batteryManufacturers) {
                batteryManufacturerStringList.add(batteryManufacturer.getBatteryManufacturerName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return batteryManufacturerStringList;
    }

    public ObservableList<String> populateBatteryModelComboBox() {
        List<BatteryModel> batteryModels = null;
        ObservableList<String> batteryModelStringList;
        batteryModelStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<BatteryModel>>() {
            }.getType();
            batteryModels = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "batteryModel.json"), "UTF-8"), type);
            for (BatteryModel batteryModel : batteryModels) {
                batteryModelStringList.add(batteryModel.getBatteryModelName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return batteryModelStringList;
    }

    public ObservableList<String> populateBatteryTypeComboBox() {
        List<BatteryType> batteryTypes = null;
        ObservableList<String> batteryTypeStringList;
        batteryTypeStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<BatteryType>>() {
            }.getType();
            batteryTypes = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "batterytype.json"), "UTF-8"), type);
            for (BatteryType batteryType : batteryTypes) {
                batteryTypeStringList.add(batteryType.getBatteryTypeName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return batteryTypeStringList;
    }

    public ObservableList<String> populateChargeManufacturerComboBox() {
        List<ChargeManufacuturer> chargeControllers = null;
        ObservableList<String> chargeControllerStringList;
        chargeControllerStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<ChargeManufacuturer>>() {
            }.getType();
            chargeControllers = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "chargeControllerManufacturer.json"), "UTF-8"), type);
            for (ChargeManufacuturer chargeController : chargeControllers) {
                chargeControllerStringList.add(chargeController.getChargeControllerManufacturerName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return chargeControllerStringList;
    }

    public ObservableList<String> populateChargeModelComboBox() {
        List<ChargeControllerModel> chargeControllerModels = null;
        ObservableList<String> chargeControllerModelStringList;
        chargeControllerModelStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<ChargeControllerModel>>() {
            }.getType();
            chargeControllerModels = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "chargeControllerModel.json"), "UTF-8"), type);
            for (ChargeControllerModel chargeControllerModel : chargeControllerModels) {
                chargeControllerModelStringList.add(chargeControllerModel.getChargeControllerModelName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return chargeControllerModelStringList;
    }

    public ObservableList<String> populateLampManufacturerComboBox() {
        List<LampManufacturer> lampManufacturers = null;
        ObservableList<String> lampManufacturerStringList;
        lampManufacturerStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<LampManufacturer>>() {
            }.getType();
            lampManufacturers = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "lampManufacturer.json"), "UTF-8"), type);
            for (LampManufacturer lampManufacturer : lampManufacturers) {
                lampManufacturerStringList.add(lampManufacturer.getLampManufacturerName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return lampManufacturerStringList;
    }

    public ObservableList<String> populateLampModelComboBox() {
        List<LampModel> lamplModels = null;
        ObservableList<String> lampModelStringList;
        lampModelStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<LampModel>>() {
            }.getType();
            lamplModels = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "lampmodel.json"), "UTF-8"), type);
            for (LampModel lampModel : lamplModels) {
                lampModelStringList.add(lampModel.getLampModelName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return lampModelStringList;
    }

    public ObservableList<String> populateInstitutionTypeComboBox() {
        List<InstitutionType> institutionTypes = null;
        ObservableList<String> institutionTypeStringList;
        institutionTypeStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<InstitutionType>>() {
            }.getType();
            institutionTypes = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "institutiontype.json"), "UTF-8"), type);
            for (InstitutionType institutionType : institutionTypes) {
                institutionTypeStringList.add(institutionType.getInstitutionTypeName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return institutionTypeStringList;
    }

    public ObservableList<String> populateMobileChargeComboBox() {
        List<MobileCharge> mobileCharges = null;
        ObservableList<String> mobileChargeStringList;
        mobileChargeStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<MobileCharge>>() {
            }.getType();
            mobileCharges = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "mobilecharge.json"), "UTF-8"), type);
            for (MobileCharge mobileCharge : mobileCharges) {
                mobileChargeStringList.add(mobileCharge.getMobilechargeName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return mobileChargeStringList;
    }

    public ObservableList<String> populateRadioComboBox() {
        List<Radio> radios = null;
        ObservableList<String> radioStringList;
        radioStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<Radio>>() {
            }.getType();
            radios = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "radio.json"), "UTF-8"), type);
            for (Radio radio : radios) {
                radioStringList.add(radio.getRadioName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return radioStringList;
    }

    public ObservableList<String> populateBulbTypeComboBox() {
        List<BulbType> bulbTypes = null;
        ObservableList<String> bulbTypeStringList;
        bulbTypeStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<BulbType>>() {
            }.getType();
            bulbTypes = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "bulbtype.json"), "UTF-8"), type);
            for (BulbType bulbType : bulbTypes) {
                bulbTypeStringList.add(bulbType.getBulbName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return bulbTypeStringList;
    }

    public ObservableList<String> populateRadioChargeComboBox() {
        List<RadioCharge> radioCharges = null;
        ObservableList<String> radioChargeStringList;
        radioChargeStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<RadioCharge>>() {
            }.getType();
            radioCharges = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "radiocharge.json"), "UTF-8"), type);
            for (RadioCharge radioCharge : radioCharges) {
                radioChargeStringList.add(radioCharge.getRadiochargeName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return radioChargeStringList;
    }

    public ObservableList<String> populateTelevisionComboBox() {
        List<Television> televisions = null;
        ObservableList<String> televisionStringList;
        televisionStringList = FXCollections.observableArrayList();
        try {
            Type type = new TypeToken<List<Television>>() {
            }.getType();
            televisions = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "television.json"), "UTF-8"), type);
            for (Television television : televisions) {
                televisionStringList.add(television.getTelevisionName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return televisionStringList;
    }

    private void readJSONfile(SolarForm solarForm) {
        if (solarForm != null) {
            solarID = solarForm.getSolarId();

            if (StringUtils.isNotBlank(solarForm.getAreaClass())) {
                List<AreaClass> areaClasses = LocalJsonUtils.loadAreaClass(assetDir);
                AreaClass areaClass = areaClasses.stream().filter(areaClass1 -> areaClass1.getAreaclassName()
                        .equalsIgnoreCase(solarForm.getAreaClass()))
                        .findAny().orElse(areaClasses.get(0));
                areaClassBox.getSelectionModel().select(areaClass.getAreaclassName());
            }
        }
        if (solarForm.getAttachment() != null) {
            subsidyFrontImg = solarForm.getAttachment().getApplicationFrontImage();
            subsidyBackImg = solarForm.getAttachment().getApplicationBackImage();
            citizenFrontImg = solarForm.getAttachment().getCitizenshipFrontImage();
            citizenBackImg = solarForm.getAttachment().getCitizenshipBackImage();
            systemOwnerUserImg = solarForm.getAttachment().getSystemUserManualImage();
            systemOwnerUserInstallerImg = solarForm.getAttachment().getInstallerImage();
            batteryVoucherImg = solarForm.getAttachment().getBatteryVoucherImage();
            invoiceImg = solarForm.getAttachment().getBillInvoiceImage();
            muchulkaImg = solarForm.getAttachment().getMuchulkaImage();
            earthquakeImg = solarForm.getAttachment().getEarthquakeImage();
        }

        if (solarForm.getOwner() != null) {
            ownerName.setText(solarForm.getOwner().getName());
            List<Gender> genders = LocalJsonUtils.loadGender(assetDir);
            Gender gender = genders.stream().filter(g -> g.getGenderName().equalsIgnoreCase(solarForm.getOwner().getGender()))
                    .findAny().orElse(genders.get(0));
            genderBox.getSelectionModel().select(gender.getGenderName());
            List<Ethnicity> ethnicityList = LocalJsonUtils.loadEthinicity(assetDir);
            eeList = ethnicityList;
            int ethnicityIndex = 0;
            for (Ethnicity e : ethnicityList) {
                //if (e.getEthnicityName().equalsIgnoreCase(solarForm.getOwner().getEthnicity())) {
                if (e.getEthnicityId().equalsIgnoreCase(solarForm.getOwner().getEthnicity())) {
                    ethnicityIndex = ethnicityList.indexOf(e);
                }
            }
            String toSelect = eList.get(ethnicityIndex);
            ethnicityBox.getSelectionModel().select(toSelect);
            callingName.setText(solarForm.getOwner().getCallingName());
            citizenshipNo.setText(solarForm.getOwner().getCitizenShipNumber());
            List<TargetGroup> targetGroupList = LocalJsonUtils.loadTargetGroup(assetDir);
            tggList = targetGroupList;
            int targetGroupIndex = 0;
            for (TargetGroup t : targetGroupList) {
                //if (t.getTargetGroupName().equalsIgnoreCase(solarForm.getOwner().getTargetGroup())) {
                if (t.getTargetGroupId().equalsIgnoreCase(solarForm.getOwner().getTargetGroup())) {
                    targetGroupIndex = targetGroupList.indexOf(t);
                }
            }
            String toSelectTG = tgList.get(targetGroupIndex);
            targetGroupBox.getSelectionModel().select(toSelectTG);
            ownerMobileNo.setText(solarForm.getOwner().getMobileNumber());
            List<CitizenDistrict> citizenDistricts = LocalJsonUtils.loadCitizenshipDistrict(assetDir);
            CitizenDistrict cDistrict = citizenDistricts.stream().filter(cd -> cd.getCitizenDistrictName().equalsIgnoreCase(solarForm.getOwner().getCitizenshipDistrict()))
                    .findAny().orElse(citizenDistricts.get(0));
            citizenshipDistrictBox.getSelectionModel().select(cDistrict.getCitizenDistrictName());
            husbandFatherName.setText(solarForm.getOwner().getHusbandOrFatherName());
            neighbourName.setText(solarForm.getOwner().getNeighbourName());
            hfMobileNo.setText(solarForm.getOwner().getHusbandOrFatherMobileNumber());
            neighbourMobileNo.setText(solarForm.getOwner().getNeighbourMobileNumber());
        }

        if (solarForm.getCurrentAddress() != null) {
            List<District> districts = LocalJsonUtils.loadDistrict(assetDir);
            District district = districts.stream().filter(d -> d.getDistrictName().equalsIgnoreCase(solarForm.getCurrentAddress().getDistrict()))
                    .findAny().orElse(districts.get(0));
            District currentDistrict = districts.stream().filter(cd -> cd.getDistrictName().equalsIgnoreCase(solarForm.getCurrentAddress().getDistrict()))
                    .findAny().orElse(districts.get(0));
            districtBox.getSelectionModel().select(district.getDistrictName());
            List<VPNP> vpnps = currentDistrict.getVpnp();
            VPNP vpnp = vpnps.stream().filter(v -> v.getAddressName().equalsIgnoreCase(solarForm.getCurrentAddress().getVpNp()))
                    .findAny().orElse(vpnps.get(0));
            districtBox.getSelectionModel().select(currentDistrict.getDistrictName());
            vpnpBox.getSelectionModel().select(vpnp.getAddressName());
            addressVP_NP_WardNo.setText(solarForm.getCurrentAddress().getWardNumber());
            addressVP_NP_Village.setText(solarForm.getCurrentAddress().getVillage());
            if (solarForm.getPermanentAddress() != null) {
                VPNP permanentVpnp = vpnps.stream().filter(v -> v.getAddressName().equalsIgnoreCase(solarForm.getPermanentAddress().getVpNp()))
                        .findAny().orElse(vpnps.get(0));
                vdcnpBox.getSelectionModel().select(permanentVpnp.getAddressName());
                addressVDC_NP_WardNo.setText(solarForm.getPermanentAddress().getWardNumber());
                addressVDC_NP_Village.setText(solarForm.getPermanentAddress().getVillage());
            }
        }

        if (solarForm.getGeneralTechnical() != null) {
            List<BDAType> bdaTypes = LocalJsonUtils.loadBDATypes(assetDir);
            BDAType bdaType = bdaTypes.stream().filter(bd -> bd.getBdaTypeName().equalsIgnoreCase(solarForm.getGeneralTechnical().getBdaType()))
                    .findAny().orElse(bdaTypes.get(0));
            bdaTypeBox.getSelectionModel().select(bdaType.getBdaTypeName());

            List<AssIdType> assIdTypes = LocalJsonUtils.loadASSTypes(assetDir);
            AssIdType assIdType = assIdTypes.stream().filter(as -> as.getAssTypeName().equalsIgnoreCase(solarForm.getGeneralTechnical().getAssType()))
                    .findAny().orElse(assIdTypes.get(0));
            assTypeBox.getSelectionModel().select(assIdType.getAssTypeName());

            List<Installer> installers = LocalJsonUtils.loadInstallers(assetDir);
            Installer installer = installers.stream().filter(i -> i.getInstallerName().equalsIgnoreCase(solarForm.getGeneralTechnical().getInstaller()))
                    .findAny().orElse(installers.get(0));
            installerBox.getSelectionModel().select(installer.getInstallerName());

            List<BDAid> bdaIds = LocalJsonUtils.loadBDAIds(assetDir);
            BDAid bdaId = bdaIds.stream().filter(bid -> bid.getBdaIdName().equalsIgnoreCase(solarForm.getGeneralTechnical().getBdaId()))
                    .findAny().orElse(bdaIds.get(0));
            bdaIdBox.getSelectionModel().select(bdaId.getBdaIdName());

            List<AssId> assIds = LocalJsonUtils.loadASSIds(assetDir);
            AssId assId = assIds.stream().filter(aid -> aid.getAssIdName().equalsIgnoreCase(solarForm.getGeneralTechnical().getAssId()))
                    .findAny().orElse(assIds.get(0));
            assIdBox.getSelectionModel().select(assId.getAssIdName());

            capacityGeneral.setText(String.valueOf(solarForm.getGeneralTechnical().getSystemCapacity()));
            installationDate.setText(solarForm.getGeneralTechnical().getInstallDate());

        } else {
            bdaTypeBox.getSelectionModel().select(3);
            assTypeBox.getSelectionModel().select(3);
            capacityGeneral.setText(String.valueOf(10.00));
        }

        if (solarForm.getPanelTechnical() != null) {
            List<PanelManufacturer> panelManufacturers = LocalJsonUtils.loadPanelMfgs(assetDir);
            PanelManufacturer panelManufacturer = panelManufacturers.stream().filter(p -> p.getPanelManufacturerName().equalsIgnoreCase(solarForm.getPanelTechnical().getManufacturer()))
                    .findAny().orElse(panelManufacturers.get(0));
            technicalPanelManufacturerBox.getSelectionModel().select(panelManufacturer.getPanelManufacturerName());

            List<PanelModel> panelModels = LocalJsonUtils.loadPanelModels(assetDir);
            PanelModel panelModel = panelModels.stream().filter(pm -> pm.getPanelModelName().equalsIgnoreCase(solarForm.getPanelTechnical().getModel()))
                    .findAny().orElse(panelModels.get(0));
            technicalPanelModelBox.getSelectionModel().select(panelModel.getPanelModelName());
            String completeTechnicalPanelSNo = solarForm.getPanelTechnical().getSerialNumber();
            String lastFiveTechPanelSNo = completeTechnicalPanelSNo.replace("FNS03SM", "");
            technicalPanelSNo.setText(lastFiveTechPanelSNo);
            capacityPanel.setText(String.valueOf(solarForm.getPanelTechnical().getCapacity()));
        } else {
            //technicalPanelManufacturerBox.getSelectionModel().select(81);
            technicalPanelManufacturerBox.getSelectionModel().select("Jiangsu Wanfeng PV Company Ltd.");
            //technicalPanelModelBox.getSelectionModel().select(1);
            technicalPanelModelBox.getSelectionModel().select("WF10P-02F");
            capacityPanel.setText(String.valueOf(10.00));
        }

        if (solarForm.getBatteryTechnical() != null) {
            List<BatteryManufacturer> batteryManufacturers = LocalJsonUtils.loadBatteryMfgs(assetDir);
            BatteryManufacturer batteryManufacturer = batteryManufacturers.stream().filter(b -> b.getBatteryManufacturerName().equalsIgnoreCase(solarForm.getBatteryTechnical().getManufacturer()))
                    .findAny().orElse(batteryManufacturers.get(0));
            technicalBatteryManufacturerBox.getSelectionModel().select(batteryManufacturer.getBatteryManufacturerName());

            String completeTechnicalBatterySNo = solarForm.getBatteryTechnical().getSerialNumber();
            String lastFiveTechBatterySNo = completeTechnicalBatterySNo.replace("FSNG", "");
            technicalBatterySNo.setText(lastFiveTechBatterySNo);
            capacityBattery.setText(String.valueOf(solarForm.getBatteryTechnical().getCapacityAH()));

            List<BatteryModel> batteryModels = LocalJsonUtils.loadBatteryModels(assetDir);
            BatteryModel batteryModel = batteryModels.stream().filter(bm -> bm.getBatteryModelName().equalsIgnoreCase(solarForm.getBatteryTechnical().getModel()))
                    .findAny().orElse(batteryModels.get(0));
            technicalBatteryModelBox.getSelectionModel().select(batteryModel.getBatteryModelName());

            List<BatteryType> batteryTypes = LocalJsonUtils.loadBatteryTypes(assetDir);
            BatteryType batteryType = batteryTypes.stream().filter(bt -> bt.getBatteryTypeName().equalsIgnoreCase(solarForm.getBatteryTechnical().getType()))
                    .findAny().orElse(batteryTypes.get(0));
            technicalBatteryTypeBox.getSelectionModel().select(batteryType.getBatteryTypeName());
        } else {
            technicalBatteryManufacturerBox.getSelectionModel().select(10);
            capacityBattery.setText(String.valueOf(7.80));
            technicalBatteryModelBox.getSelectionModel().select(1);
            technicalBatteryTypeBox.getSelectionModel().select(5);
        }

        if (solarForm.getChargeControllerTechnical() != null) {
            List<ChargeManufacuturer> chargeManufacturers = LocalJsonUtils.loadChargeMfgs(assetDir);
            ChargeManufacuturer chargeManufacturer = chargeManufacturers.stream().filter(c -> c.getChargeControllerManufacturerName().equalsIgnoreCase(solarForm.getChargeControllerTechnical().getManufacturer()))
                    .findAny().orElse(chargeManufacturers.get(0));
            technicalChargeManufacturerBox.getSelectionModel().select(chargeManufacturer.getChargeControllerManufacturerName());

            List<ChargeControllerModel> chargeModels = LocalJsonUtils.loadChargeModels(assetDir);
            ChargeControllerModel chargeModel = chargeModels.stream().filter(cm -> cm.getChargeControllerModelName().equalsIgnoreCase(solarForm.getChargeControllerTechnical().getModel()))
                    .findAny().orElse(chargeModels.get(0));
            technicalChargeModelBox.getSelectionModel().select(chargeModel.getChargeControllerModelName());

            brand.setText(solarForm.getChargeControllerTechnical().getBrand());
            rating.setText(String.valueOf(solarForm.getChargeControllerTechnical().getRatingA()));
        } else {
            technicalChargeManufacturerBox.getSelectionModel().select(13);
            technicalChargeModelBox.getSelectionModel().select(1);
            brand.setText("FNS");
            rating.setText(String.valueOf(3.00));
        }

        if (solarForm.getLampTechnical() != null) {
            List<LampManufacturer> lampManufacturers = LocalJsonUtils.loadLampMfgs(assetDir);
            LampManufacturer lampManufacturer = lampManufacturers.stream().filter(l -> l.getLampManufacturerName().equalsIgnoreCase(solarForm.getLampTechnical().getManufacturer()))
                    .findAny().orElse(lampManufacturers.get(0));
            technicalLampManufacturerBox.getSelectionModel().select(lampManufacturer.getLampManufacturerName());

            List<LampModel> lampModels = LocalJsonUtils.loadLampModels(assetDir);
            LampModel lampModel = lampModels.stream().filter(lm -> lm.getLampModelName().equalsIgnoreCase(solarForm.getLampTechnical().getModel()))
                    .findAny().orElse(lampModels.get(0));
            technicalLampModelBox.getSelectionModel().select(lampModel.getLampModelName());

            capacityLamp.setText(String.valueOf(solarForm.getLampTechnical().getCapacityW()));
        } else {
            technicalLampManufacturerBox.getSelectionModel().select(10);
            technicalLampModelBox.getSelectionModel().select(1);
            capacityLamp.setText(String.valueOf(2.00));
        }

        if (solarForm.getInvestmentEntity() != null) {
            totalSystemCost.setText(String.valueOf(solarForm.getInvestmentEntity().getTotalSystemCost()));
            supportAmount.setText(String.valueOf(solarForm.getInvestmentEntity().getSupportAmount()));
            annualIncome.setText(String.valueOf(solarForm.getInvestmentEntity().getAnnualIncome()));
            userAmount.setText(String.valueOf(solarForm.getInvestmentEntity().getUserAmount()));
            incomeSource.setText(solarForm.getInvestmentEntity().getIncomeSource());
        } else {
//            totalSystemCost.setText(String.valueOf(5500.00));
//            supportAmount.setText(String.valueOf(0.00));
//            userAmount.setText(String.valueOf(1000.00));
        }

        if (solarForm.getBanking() != null) {
            loanAmount.setText(String.valueOf(solarForm.getBanking().getLoanAmount()));
            institutionAddress.setText(solarForm.getBanking().getInstitutionAddress());
            interestRate.setText(String.valueOf(solarForm.getBanking().getInterestRate()));
            institutionName.setText(solarForm.getBanking().getInsitutionName());

            List<InstitutionType> institutionTypes = LocalJsonUtils.loadInstitutions(assetDir);
            InstitutionType institutionType = institutionTypes.stream().filter(in -> in.getInstitutionTypeName().equalsIgnoreCase(solarForm.getBanking().getInstitutionType()))
                    .findAny().orElse(institutionTypes.get(0));
            institutionTypeBox.getSelectionModel().select(institutionType.getInstitutionTypeName());

            maturtyPeriod.setText(solarForm.getBanking().getMaturityPeriod());
            approvalDate.setText(solarForm.getBanking().getApprovalDate());
        } else {
            loanAmount.setText(String.valueOf(0.00));
            interestRate.setText(String.valueOf(0.00));
        }

        if (solarForm.getEndUse() != null) {
            noOfBulbs.setText(String.valueOf(solarForm.getEndUse().getNumberOfBulb()));

            List<MobileCharge> mobileCharges = LocalJsonUtils.loadMobileCharges(assetDir);
            MobileCharge mobileCharge = mobileCharges.stream().filter(mc -> mc.getMobilechargeName().equalsIgnoreCase(solarForm.getEndUse().getMobileChargeOption().toString()))
                    .findAny().orElse(mobileCharges.get(0));
            mobileChargeOptionBox.getSelectionModel().select(mobileCharge.getMobilechargeName());

            List<Radio> radios = LocalJsonUtils.loadRadios(assetDir);
            Radio radio = radios.stream().filter(r -> r.getRadioName().equalsIgnoreCase(solarForm.getEndUse().getRadio().toString()))
                    .findAny().orElse(radios.get(0));
            radioBox.getSelectionModel().select(radio.getRadioName());

            otherConnection.setText(solarForm.getEndUse().getOtherConnection());

            List<BulbType> bulbTypes = LocalJsonUtils.loadBulbTypes(assetDir);
            BulbType bulbType = bulbTypes.stream().filter(bt -> bt.getBulbName().equalsIgnoreCase(solarForm.getEndUse().getTypeOfBulb()))
                    .findAny().orElse(bulbTypes.get(0));
            typeOfBulbBox.getSelectionModel().select(bulbType.getBulbName());

            List<RadioCharge> radioChargeTypes = LocalJsonUtils.loadRadioCharges(assetDir);
            RadioCharge radioChargeType = radioChargeTypes.stream().filter(rc -> rc.getRadiochargeName().equalsIgnoreCase(solarForm.getEndUse().getRadioChargeOption().toString()))
                    .findAny().orElse(radioChargeTypes.get(0));
            radioChargeOptionBox.getSelectionModel().select(radioChargeType.getRadiochargeName());

            List<Television> televisions = LocalJsonUtils.loadTelevisions(assetDir);
            Television television = televisions.stream().filter(t -> t.getTelevisionName().equalsIgnoreCase(solarForm.getEndUse().getTelevision().toString()))
                    .findAny().orElse(televisions.get(0));
            televisionBox.getSelectionModel().select(television.getTelevisionName());
        } else {
            noOfBulbs.setText(String.valueOf(2));
            mobileChargeOptionBox.getSelectionModel().select(1);
            radioBox.getSelectionModel().select(2);
            typeOfBulbBox.getSelectionModel().select(1);
            radioChargeOptionBox.getSelectionModel().select(2);
            televisionBox.getSelectionModel().select(2);
        }
    }

    private void modifyJSONfile() throws IOException {
        String areaClassBoxValue = areaClassBox.getValue();
        //Owner General Values
        String ownerNameValue = ownerName.getText();
        String genderBoxValue = genderBox.getValue();
        String ethnicityBoxValue = ethnicityBox.getValue();
        String callingNameValue = callingName.getText();
        String ciizenshipNoValue = citizenshipNo.getText();
        String targetGroupBoxValue = targetGroupBox.getValue();
        String ownerNoValue = ownerMobileNo.getText();
        String citizenshipDistrictBoxValue = citizenshipDistrictBox.getValue();
        //Owner Additional Values
        String husbandFatherNameValue = husbandFatherName.getText();
        String neighbourNameValue = neighbourName.getText();
        String hfMobileNoValue = hfMobileNo.getText();
        String neighbourMobileNoValue = neighbourMobileNo.getText();
        //Current Address Values
        String districtBoxValue = districtBox.getValue();
        String vpnpBoxValue = vpnpBox.getValue();
        String addressVP_NP_WardNoValue = addressVP_NP_WardNo.getText();
        String addressVP_NP_VillageValue = addressVP_NP_Village.getText();
        //Permanent Address Values
        String vdcnpBoxValue = vdcnpBox.getValue();
        String addressVDC_NP_WardNoValue = addressVDC_NP_WardNo.getText();
        String addressVDC_NP_VillageValue = addressVDC_NP_Village.getText();
        //Technical General Values
        String bdaTypeBoxValue = bdaTypeBox.getValue();
        String assTypeBoxValue = assTypeBox.getValue();
        String installerBoxValue = installerBox.getValue();
        String bdaIdBoxValue = bdaIdBox.getValue();
        String assIdBoxValue = assIdBox.getValue();
        String capacityGeneralValue = capacityGeneral.getText();
        String installationDateValue = installationDate.getText();
        //Technical Panel Values
        //String technicalPanelManufacturerBoxValue = technicalPanelManufacturerBox.getValue();
        String technicalPanelManufacturerBoxValue = "Jiangsu Wanfeng PV Company Ltd.";
        //String technicalPanelModelBoxValue = technicalPanelModelBox.getValue();
        String technicalPanelModelBoxValue = "WF10P-02F";
        String technicalPanelSNoValue = "FNS03SM" + technicalPanelSNo.getText();
        String capacityPanelValue = capacityPanel.getText();
        //Technical Battery Values
        String technicalBatteryManufacturerBoxValue = technicalBatteryManufacturerBox.getValue();
        String technicalBatterySNoValue = "FSNG" + technicalBatterySNo.getText();
        String capacityBatteryValue = capacityBattery.getText();
        String technicalBatteryModelBoxValue = technicalBatteryModelBox.getValue();
        String technicalBatteryTypeBoxValue = technicalBatteryTypeBox.getValue();
        //Technical Charge COntrolelr Values
        String technicalChargeManufacturerBoxValue = technicalChargeManufacturerBox.getValue();
        String technicalChargeModelBoxValue = technicalChargeModelBox.getValue();
        String brandValue = brand.getText();
        String ratingValue = rating.getText();
        //Technical Lamp Values
        String technicalLampManufacturerBoxValue = technicalLampManufacturerBox.getValue();
        String technicalLampModelBoxValue = technicalLampModelBox.getValue();
        String capacityLampValue = capacityLamp.getText();
        //Investment Values
        String totalSystemCostValue = totalSystemCost.getText() == null ? "0.00" : totalSystemCost.getText();
        String supportAmountValue = supportAmount.getText() == null ? "0.00" : supportAmount.getText();
        String annualIncomeValue = annualIncome.getText() == null ? "0.00" : annualIncome.getText();
        String batteryAmountValue = batteryAmount.getText() == null ? "0.00" : batteryAmount.getText();
        String subsidyAmountValue = subsidyAmount.getText() == null ? "0.00" : subsidyAmount.getText();
        String userAmountValue = userAmount.getText() == null ? "0.00" : userAmount.getText();
        String incomeSourceValue = incomeSource.getText() == null ? "0.00" : incomeSource.getText();
        String earthquakeSubsidyValue = earthquakeSubsidy.getText() == null ? "0.00" : earthquakeSubsidy.getText();
        //Banking Values
        String loanAmountValue = loanAmount.getText() == null ? "0.00" : loanAmount.getText();
        String institutionAddressValue = institutionAddress.getText();
        String interestRateValue = interestRate.getText();
        String institutionNameValue = institutionName.getText();
        String institutionTypeBoxValue = institutionTypeBox.getValue();
        String maturtyPeriodValue = maturtyPeriod.getText();
        String approvalDateValue = approvalDate.getText();
        //End Use Values
        String noOfBulbsValue = noOfBulbs.getText();
        String mobileChargeOptionBoxValue = mobileChargeOptionBox.getValue();
        String radioBoxValue = radioBox.getValue();
        String otherConnectionValue = otherConnection.getText();
        String typeOfBulbBoxValue = typeOfBulbBox.getValue();
        String radioChargeOptionBoxValue = radioChargeOptionBox.getValue();
        String televisionBoxValue = televisionBox.getValue();

        solarForm.setAreaClass(areaClassBoxValue);

        Owner owner = new Owner();
        owner.setSolarId(solarID);
        owner.setName(ownerNameValue);
        owner.setGender(genderBoxValue);
        //owner.setEthnicity(ethnicityBoxValue);
        int convertEthnicity = 0;
        for (String ethnicityString : eList) {
            if (ethnicityString.equalsIgnoreCase(ethnicityBoxValue)) {
                convertEthnicity = eList.indexOf(ethnicityString);
            }
        }
        List<Ethnicity> ethnicityList = LocalJsonUtils.loadEthinicity(assetDir);
        eeList = ethnicityList;
        owner.setEthnicity(eeList.get(convertEthnicity).getEthnicityId());
        owner.setCallingName(callingNameValue);
        owner.setCitizenShipNumber(ciizenshipNoValue);
        int convertTargetGroup = 0;
        for (String targetGroupString : tgList) {
            if (targetGroupString.equalsIgnoreCase(targetGroupBoxValue)) {
                convertTargetGroup = tgList.indexOf(targetGroupString);
            }
        }
        List<TargetGroup> targetGroupList = LocalJsonUtils.loadTargetGroup(assetDir);
        tggList = targetGroupList;
        owner.setTargetGroup(tggList.get(convertTargetGroup).getTargetGroupId());
        owner.setMobileNumber(ownerNoValue);
        owner.setCitizenshipDistrict(citizenshipDistrictBoxValue);
        owner.setHusbandOrFatherName(husbandFatherNameValue);
        owner.setNeighbourName(neighbourNameValue);
        owner.setHusbandOrFatherMobileNumber(hfMobileNoValue);
        owner.setNeighbourMobileNumber(neighbourMobileNoValue);
        solarForm.setOwner(owner);

        CurrentAddress currentAddress = new CurrentAddress();
        currentAddress.setSolarId(solarID);
        currentAddress.setDistrict(districtBoxValue);
        currentAddress.setVpNp(vpnpBoxValue);
        currentAddress.setWardNumber(addressVP_NP_WardNoValue);
        currentAddress.setVillage(addressVP_NP_VillageValue);
        solarForm.setCurrentAddress(currentAddress);

        PermanentAddress permanentAddress = new PermanentAddress();
        permanentAddress.setSolarId(solarID);
        permanentAddress.setVpNp(vdcnpBoxValue);
        permanentAddress.setWardNumber(addressVDC_NP_WardNoValue);
        permanentAddress.setVillage(addressVDC_NP_VillageValue);
        solarForm.setPermanentAddress(permanentAddress);

        GeneralTechnical generalTechnical = new GeneralTechnical();
        generalTechnical.setSolarId(solarID);
        generalTechnical.setBdaType(bdaTypeBoxValue);
        generalTechnical.setAssType(assTypeBoxValue);
        generalTechnical.setInstaller(installerBoxValue);
        generalTechnical.setBdaId(bdaIdBoxValue);
        generalTechnical.setAssId(assIdBoxValue);
        if (StringUtils.isNotBlank(capacityGeneralValue)) {
            generalTechnical.setSystemCapacity(Double.parseDouble(capacityGeneralValue));
        }
        generalTechnical.setInstallDate(installationDateValue);
        solarForm.setGeneralTechnical(generalTechnical);

        PanelTechnical panelTechnical = new PanelTechnical();
        panelTechnical.setSolarId(solarID);
        panelTechnical.setManufacturer(technicalPanelManufacturerBoxValue);
        panelTechnical.setModel(technicalPanelModelBoxValue);
        panelTechnical.setSerialNumber(technicalPanelSNoValue);
        if (StringUtils.isNotBlank(capacityPanelValue)) {
            panelTechnical.setCapacity(Double.parseDouble(capacityPanelValue));
        }
        solarForm.setPanelTechnical(panelTechnical);

        BatteryTechnical batteryTechnical = new BatteryTechnical();
        batteryTechnical.setSolarId(solarID);
        batteryTechnical.setManufacturer(technicalBatteryManufacturerBoxValue);
        batteryTechnical.setSerialNumber(technicalBatterySNoValue);
        if (StringUtils.isNotBlank(capacityBatteryValue)) {
            batteryTechnical.setCapacityAH(Double.parseDouble(capacityBatteryValue));
        }
        batteryTechnical.setModel(technicalBatteryModelBoxValue);
        batteryTechnical.setType(technicalBatteryTypeBoxValue);
        solarForm.setBatteryTechnical(batteryTechnical);

        ChargeControllerTechnical chargeControllerTechnical = new ChargeControllerTechnical();
        chargeControllerTechnical.setSolarId(solarID);
        chargeControllerTechnical.setManufacturer(technicalChargeManufacturerBoxValue);
        chargeControllerTechnical.setModel(technicalChargeModelBoxValue);
        chargeControllerTechnical.setBrand(brandValue);
        if (StringUtils.isNotBlank(ratingValue)) {
            chargeControllerTechnical.setRatingA(Double.parseDouble(ratingValue));
        }
        solarForm.setChargeControllerTechnical(chargeControllerTechnical);

        LampTechnical lampTechnical = new LampTechnical();
        lampTechnical.setSolarId(solarID);
        lampTechnical.setManufacturer(technicalLampManufacturerBoxValue);
        lampTechnical.setModel(technicalLampModelBoxValue);
        if (StringUtils.isNotBlank(capacityLampValue)) {
            lampTechnical.setCapacityW(Double.parseDouble(capacityLampValue));
        }
        solarForm.setLampTechnical(lampTechnical);

        Investment investment = new Investment();
        investment.setSolarId(solarID);
        if (StringUtils.isNotBlank(totalSystemCostValue)) {
            investment.setTotalSystemCost(Double.parseDouble(totalSystemCostValue));
            investment.setUserAmount(Double.parseDouble(userAmountValue));
        }
        if (StringUtils.isNotBlank(supportAmountValue)) {
            investment.setSupportAmount(Double.parseDouble(supportAmountValue));
        }
        if (StringUtils.isNotBlank(annualIncomeValue)) {
            investment.setAnnualIncome(Double.parseDouble(annualIncomeValue));
        }
        if (StringUtils.isNotBlank(batteryAmountValue)) {
            investment.setBatteryAmount(Double.parseDouble(batteryAmountValue));
        }
        if (StringUtils.isNotBlank(subsidyAmountValue)) {
            investment.setSubsidyAmount(Double.parseDouble(subsidyAmountValue));
        }
        if (StringUtils.isNotBlank(userAmountValue)) {
            investment.setUserAmount(Double.parseDouble(userAmountValue));
        }
        investment.setIncomeSource(incomeSourceValue);
        if (StringUtils.isNotBlank(earthquakeSubsidyValue)) {
            investment.setEarthquakeSubsidy(Double.parseDouble(earthquakeSubsidyValue));
        }
        solarForm.setInvestmentEntity(investment);

        Banking banking = new Banking();
        banking.setSolarId(solarID);
        if (StringUtils.isNotBlank(loanAmountValue)) {
            banking.setLoanAmount(Double.parseDouble(loanAmountValue));
        }
        banking.setInstitutionAddress(institutionAddressValue);
        if (StringUtils.isNotBlank(interestRateValue)) {
            banking.setInterestRate(Double.parseDouble(interestRateValue));
        }
        banking.setInsitutionName(institutionNameValue);
        banking.setInstitutionType(institutionTypeBoxValue);
        banking.setMaturityPeriod(maturtyPeriodValue);
        banking.setApprovalDate(approvalDateValue);
        solarForm.setBanking(banking);

        EndUse enduse = new EndUse();
        enduse.setSolarId(solarID);
        if (StringUtils.isNotBlank(noOfBulbsValue)) {
            enduse.setNumberOfBulb(Integer.parseInt(noOfBulbsValue));
        }
        if (mobileChargeOptionBoxValue != null) {
            if (mobileChargeOptionBoxValue.equals("Yes")) {
                enduse.setMobileChargeOption(Confirmation.YES);
            } else {
                enduse.setMobileChargeOption(Confirmation.NO);
            }
        }
        if (radioBoxValue != null) {
            if (radioBoxValue.equals("Yes")) {
                enduse.setRadio(Confirmation.YES);
            } else {
                enduse.setRadio(Confirmation.NO);
            }
        }
        enduse.setOtherConnection(otherConnectionValue);
        enduse.setTypeOfBulb(typeOfBulbBoxValue);
        if (radioChargeOptionBoxValue != null) {
            if (radioChargeOptionBoxValue.equals("Yes")) {
                enduse.setRadioChargeOption(Confirmation.YES);
            } else {
                enduse.setRadioChargeOption(Confirmation.NO);
            }
        }
        if (televisionBoxValue != null) {
            if (televisionBoxValue.equals("Yes")) {
                enduse.setTelevision(Confirmation.YES);
            } else {
                enduse.setTelevision(Confirmation.NO);
            }
        }
        solarForm.setEndUse(enduse);

        Attachment attachment = new Attachment();
        attachment.setSolarId(solarID);
        attachment.setApplicationFrontImage(subsidyFrontImg);
        attachment.setApplicationBackImage(subsidyBackImg);
        attachment.setCitizenshipFrontImage(citizenFrontImg);
        attachment.setCitizenshipBackImage(citizenBackImg);
        attachment.setSystemUserManualImage(systemOwnerUserImg);
        attachment.setInstallerImage(systemOwnerUserInstallerImg);
        attachment.setBatteryVoucherImage(batteryVoucherImg);
        attachment.setBillInvoiceImage(invoiceImg);
        attachment.setMuchulkaImage(muchulkaImg);
        attachment.setEarthquakeImage(earthquakeImg);
        solarForm.setAttachment(attachment);
    }

    @FXML
    private void subsidyFrontBtnClick(ActionEvent event) {
        if (solarForm.getAttachment().getApplicationFrontImage() != null) {
            String subsidyFrontImg = solarForm.getAttachment().getApplicationFrontImage();
            if (StringUtils.isNotBlank(subsidyFrontImg)) {
                MainApp.hs.showDocument(subsidyFrontImg);
            } else {
                alertMessage();
            }
        } else {
            alertMessage();
        }
    }

    @FXML
    private void subsidyBackBtnClick(ActionEvent event) {
        if (solarForm.getAttachment().getApplicationBackImage() != null) {
            String subsidyBackImg = solarForm.getAttachment().getApplicationBackImage();
            if (StringUtils.isNotBlank(subsidyBackImg)) {
                MainApp.hs.showDocument(subsidyBackImg);
            } else {
                alertMessage();
            }
        } else {
            alertMessage();
        }
    }

    @FXML
    private void citizenFrontBtnClick(ActionEvent event) {
        if (solarForm.getAttachment().getCitizenshipFrontImage() != null) {
            String cFrontImage = solarForm.getAttachment().getCitizenshipFrontImage();
            if (StringUtils.isNotBlank(cFrontImage)) {
                MainApp.hs.showDocument(cFrontImage);
            } else {
                alertMessage();
            }
        } else {
            alertMessage();
        }
    }

    @FXML
    private void citizenBackBtnClick(ActionEvent event) {
        if (solarForm.getAttachment().getCitizenshipBackImage() != null) {
            String cBackImage = solarForm.getAttachment().getCitizenshipBackImage();
            if (StringUtils.isNotBlank(cBackImage)) {
                MainApp.hs.showDocument(cBackImage);
            } else {
                alertMessage();
            }
        } else {
            alertMessage();
        }
    }

    @FXML
    private void systemOwnerUserBtnClick(ActionEvent event) {
        if (solarForm.getAttachment().getSystemUserManualImage() != null) {
            String systemOwnerUserManualImg = solarForm.getAttachment().getSystemUserManualImage();
            if (StringUtils.isNotBlank(systemOwnerUserManualImg)) {
                MainApp.hs.showDocument(systemOwnerUserManualImg);
            } else {
                alertMessage();
            }
        } else {
            alertMessage();
        }
    }

    @FXML
    private void systemOwnerUserInstallerBtnClick(ActionEvent event) {
        if (solarForm.getAttachment().getInstallerImage() != null) {
            String systemOwnerUserManualInstallerImg = solarForm.getAttachment().getInstallerImage();
            if (StringUtils.isNotBlank(systemOwnerUserManualInstallerImg)) {
                MainApp.hs.showDocument(systemOwnerUserManualInstallerImg);
            } else {
                alertMessage();
            }
        } else {
            alertMessage();
        }
    }

    @FXML
    private void batteryVoucherBtnClick(ActionEvent event) {
        if (solarForm.getAttachment().getBatteryVoucherImage() != null) {
            String batteryVoucherImg = solarForm.getAttachment().getBatteryVoucherImage();
            if (StringUtils.isNotBlank(batteryVoucherImg)) {
                MainApp.hs.showDocument(batteryVoucherImg);
            } else {
                alertMessage();
            }
        } else {
            alertMessage();
        }
    }

    @FXML
    private void invoiceBtnClick(ActionEvent event) {
        if (solarForm.getAttachment().getBillInvoiceImage() != null) {
            String billInvoiceImg = solarForm.getAttachment().getBillInvoiceImage();
            if (StringUtils.isNotBlank(billInvoiceImg)) {
                MainApp.hs.showDocument(billInvoiceImg);
            } else {
                alertMessage();
            }
        } else {
            alertMessage();
        }
    }

    @FXML
    private void muchulkaBtnClick(ActionEvent event) {
        if (solarForm.getAttachment().getMuchulkaImage() != null) {
            String muchulkaImg = solarForm.getAttachment().getMuchulkaImage();
            if (StringUtils.isNotBlank(muchulkaImg)) {
                MainApp.hs.showDocument(muchulkaImg);
            } else {
                alertMessage();
            }
        } else {
            alertMessage();
        }
    }

    @FXML
    private void earthquakeBtnClick(ActionEvent event) {
        if (solarForm.getAttachment().getEarthquakeImage() != null) {
            String earthquakeImg = solarForm.getAttachment().getEarthquakeImage();
            if (StringUtils.isNotBlank(earthquakeImg)) {
                MainApp.hs.showDocument(earthquakeImg);
            } else {
                alertMessage();
            }
        } else {
            alertMessage();
        }
    }

    @FXML
    private void subsidyFrontBtnEditAction(ActionEvent event) {
        subsidyFrontImg = getNewImageFile();
        solarForm.getAttachment().setApplicationFrontImage(subsidyFrontImg);
        persistImage();
    }

    @FXML
    private void subsidyBackBtnEditAction(ActionEvent event) {
        subsidyBackImg = getNewImageFile();
        solarForm.getAttachment().setApplicationBackImage(subsidyBackImg);
        persistImage();
    }

    @FXML
    private void citizenFrontBtnEditAction(ActionEvent event) {
        citizenFrontImg = getNewImageFile();
        solarForm.getAttachment().setCitizenshipFrontImage(citizenFrontImg);
        persistImage();
    }

    @FXML
    private void citizenBackBtnEditAction(ActionEvent event) {
        citizenBackImg = getNewImageFile();
        solarForm.getAttachment().setCitizenshipBackImage(citizenBackImg);
        persistImage();
    }

    @FXML
    private void systemOwnerUserBtnEditAction(ActionEvent event) {
        systemOwnerUserImg = getNewImageFile();
        solarForm.getAttachment().setSystemUserManualImage(systemOwnerUserImg);
        persistImage();
    }

    @FXML
    private void systemOwnerUserInstallerBtnEditAction(ActionEvent event) {
        systemOwnerUserInstallerImg = getNewImageFile();
        solarForm.getAttachment().setInstallerImage(systemOwnerUserInstallerImg);
        persistImage();
    }

    @FXML
    private void batteryVoucherBtnEditAction(ActionEvent event) {
        batteryVoucherImg = getNewImageFile();
        solarForm.getAttachment().setBatteryVoucherImage(batteryVoucherImg);
        persistImage();
    }

    @FXML
    private void invoiceBtnEditAction(ActionEvent event) {
        invoiceImg = getNewImageFile();
        solarForm.getAttachment().setBillInvoiceImage(invoiceImg);
        persistImage();
    }

    @FXML
    private void muchulkaBtnEditAction(ActionEvent event) {
        muchulkaImg = getNewImageFile();
        solarForm.getAttachment().setMuchulkaImage(muchulkaImg);
        persistImage();
    }

    @FXML
    private void earthquakeBtnEditAction(ActionEvent event) {
        earthquakeImg = getNewImageFile();
        solarForm.getAttachment().setEarthquakeImage(earthquakeImg);
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

    private void persistImage() {
        boolean persist = this.solarFormService.saveImages(solarForm.getAttachment());
        if (!persist) {
            alertForBadImage();
        }
    }

    private void alertMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Form Incomplete");
        alert.setContentText("Image is not provided. Please upload the image for given field.");
        alert.showAndWait();
    }

    private void alertForBadImage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Edit Image");
        alert.setContentText("Image is failed to edit. Please try again with valid image.");
        alert.showAndWait();
    }

    private void alertForNotSaved() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Form Submission");
        alert.setContentText("Form failed to submit. Please try again.");
        alert.showAndWait();
    }

   
}
