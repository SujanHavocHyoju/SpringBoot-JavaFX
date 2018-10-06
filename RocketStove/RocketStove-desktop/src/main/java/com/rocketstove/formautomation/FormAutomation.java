/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.formautomation;
import com.itextpdf.text.DocumentException;
import com.rocketstove.domain.*;
import com.rocketstove.html.HtmlTag;
import com.rocketstove.response.Response;
import com.rocketstove.service.AggregateFormService;
import com.rocketstove.utils.ImageUtils;
import com.rocketstove.utils.JsonUtils;
import com.rocketstove.utils.LocalJsonUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author SUJAN
 */
@Component
public class FormAutomation {

    @Value("${asset_dir}")
    private String assetDir;
    @Value("${subsidy_file_pdf}")
    private String subsidyFilePdf;
    @Value("${citizen_pdf}")
    private String citizenPDF;
    @Value("${pdf_folder}")
    private String pdf_folder;
    @Value("${second_form_img}")
    private String secondFormImg;
    private Document document;
    @Autowired
    private LoginFormAutomation loginFormAutomation;
    @Autowired
    private AggregateFormService aggregateFormService;
    private String rocketIdFromWeb;
    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(FormAutomation.class);
    private WebDriver webDriver;

    public String getRocketIdFromWeb() {
        return rocketIdFromWeb;
    }

    public void setRocketIdFromWeb(String rocketIdFromWeb) {
        this.rocketIdFromWeb = rocketIdFromWeb;
    }

    public void automateAll() {
        Response response = null;
        try {
            response = loginFormAutomation.doLogin();
            LOG.info("Login success");
            if (response != null) {
                if (response.isNext()) {
                    LOG.info("Ready to automate");
                    doFormAutomation();

                }
            }
        } catch (Exception ex) {
            if (ex instanceof WebDriverException) {
                if (((WebDriverException) ex).getMessage().contains("reachable")) {
                    if (webDriver != null) {
                        webDriver.quit();
                    }
                    LOG.info("errror " + ((WebDriverException) ex).getMessage());
                }

            }
        }
    }

    public void doFormAutomation() throws Exception {
        LOG.info("automation ma chu");
        List<AggregateForm> aggregateForms = aggregateFormService.automationList();
        LOG.info("size "+aggregateForms.size());
        submit(aggregateForms);
    }

    public void submit(List<AggregateForm> aggregateForms) {
        for (AggregateForm aggregateForm : aggregateForms) {
            LOG.info("here");
            LOG.info(JsonUtils.toJsonObj(aggregateForm));
            double size = 0;
            StarterForm starterForm = aggregateForm.getStarterForm();
            if(StringUtils.isBlank(starterForm.getsAFormFirstImg())){
                aggregateForm.setMessage("Application Front image is missing from given directory");
                this.aggregateFormService.failedAutomationAtFirstPage(aggregateForm);
                continue;
            }
            File checkForFileExists = new File(starterForm.getsAFormFirstImg());
            if (!checkForFileExists.exists()) {
                aggregateForm.setMessage("Application Front image is missing from given directory");
                LOG.info("Application Front at citzen");
                this.aggregateFormService.failedAutomationAtFirstPage(aggregateForm);
                continue;
            } else {
                size += com.rocketstove.utils.FileUtils.calculateDirectoryInMegaBytes(checkForFileExists);
            }
            if(StringUtils.isBlank(starterForm.getsAFormSecondImg())){
                aggregateForm.setMessage("Application back image is missing from given directory");
                LOG.info("Application back at citzen");
                this.aggregateFormService.failedAutomationAtFirstPage(aggregateForm);
                continue;
            }
            checkForFileExists = new File(starterForm.getsAFormSecondImg());
            if (!checkForFileExists.exists()) {
                aggregateForm.setMessage("Application back image is missing from given directory");
                LOG.info("Application back at citzen");
                this.aggregateFormService.failedAutomationAtFirstPage(aggregateForm);
                continue;
            } else {
                size += com.rocketstove.utils.FileUtils.calculateDirectoryInMegaBytes(checkForFileExists);
                if (size > 1.6) {
                    aggregateForm.setMessage("Application images size is large, please review.");
                    LOG.info("Here we are " + size);
                    this.aggregateFormService.failedAutomationAtFirstPage(aggregateForm);
                    continue;
                }

            }
            if(StringUtils.isBlank(starterForm.getCitizenBackImg())){
                aggregateForm.setMessage("Citizen back image is missing from given directory");
                this.aggregateFormService.failedAutomationAtFirstPage(aggregateForm);
                continue;
            }
            checkForFileExists = new File(starterForm.getCitizenBackImg());
            if (!checkForFileExists.exists()) {
                aggregateForm.setMessage("Citizen back image is missing from given directory");
                this.aggregateFormService.failedAutomationAtFirstPage(aggregateForm);
                continue;
            } else {
                size = 0;
                size += com.rocketstove.utils.FileUtils.calculateDirectoryInMegaBytes(checkForFileExists);
            }
            if(StringUtils.isBlank(starterForm.getCitizenFrontImg())){
                aggregateForm.setMessage("Citizen front images is missing from given directory");
                this.aggregateFormService.failedAutomationAtFirstPage(aggregateForm);
                continue;
            }
            checkForFileExists = new File(starterForm.getCitizenFrontImg());
            if (!checkForFileExists.exists()) {
                aggregateForm.setMessage("Citizen front images is missing from given directory");
                this.aggregateFormService.failedAutomationAtFirstPage(aggregateForm);
                continue;
            } else {
                size += com.rocketstove.utils.FileUtils.calculateDirectoryInMegaBytes(checkForFileExists);
                if (size > 1.6) {
                    aggregateForm.setMessage("Citizen images size is large, please review.");
                    LOG.info("Here we are " + size);
                    this.aggregateFormService.failedAutomationAtFirstPage(aggregateForm);
                    continue;
                }
            }
            if(StringUtils.isBlank(starterForm.getStoveHandOverImg())){
                aggregateForm.setMessage("Stove Handover image is missing from given directory");
                this.aggregateFormService.failedAutomationAtFirstPage(aggregateForm);
                continue;
            }
            checkForFileExists = new File(starterForm.getStoveHandOverImg());
            if (!checkForFileExists.exists()) {
                aggregateForm.setMessage("Stove Handover image is missing from given directory");
                this.aggregateFormService.failedAutomationAtFirstPage(aggregateForm);
                continue;
            } else {
                size = 0;
                size += com.rocketstove.utils.FileUtils.calculateDirectoryInMegaBytes(checkForFileExists);
                if (size > 1.6) {
                    aggregateForm.setMessage("Stove Handover image size is large, please review.");
                    LOG.info("Here we are " + size);
                    this.aggregateFormService.failedAutomationAtFirstPage(aggregateForm);
                    continue;
                }
            }
            if(StringUtils.isBlank(starterForm.getStoveIDImg())){
                aggregateForm.setMessage("Stove image is missing from given directory");
                this.aggregateFormService.failedAutomationAtFirstPage(aggregateForm);
                continue;
            }
            checkForFileExists = new File(starterForm.getStoveIDImg());
            if (!checkForFileExists.exists()) {
                aggregateForm.setMessage("Stove image is missing from given directory");
                this.aggregateFormService.failedAutomationAtFirstPage(aggregateForm);
                continue;
            } else {
                size = 0;
                size += com.rocketstove.utils.FileUtils.calculateDirectoryInMegaBytes(checkForFileExists);
                LOG.info("Here we are " + size);
                if (size > 1.6) {
                    aggregateForm.setMessage("Stove image size is large, please review.");
                    this.aggregateFormService.failedAutomationAtFirstPage(aggregateForm);
                    continue;
                }
            }
            if (!aggregateForm.getTechnicalForm().getInstallDate().matches("[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}")) {
                aggregateForm.setMessage("Please match installation date format (Month/Day/Year) according to the website.");
                this.aggregateFormService.failedAutomationAtFirstPage(aggregateForm);
                continue;
            }
            if(StringUtils.isBlank(aggregateForm.getTechnicalForm().getEngraveNumber())){
                aggregateForm.setMessage("Engrave Number is missing");
                this.aggregateFormService.failedAutomationAtFirstPage(aggregateForm);
            }
            //check for file 
            //ALPHABET NUMBER SLASH
            if (!aggregateForm.getGeneralForm().getCitizenShip().matches("^[0-9a-zA-Z/-]*$")) {
                aggregateForm.setMessage("Citizenship has invalid characters (Only Alphabet or Numbers or Slash are supported), please review.");
                LOG.info("Failed at citzen");
                this.aggregateFormService.failedAutomationAtFirstPage(aggregateForm);
                continue;
            }
            if (StringUtils.isNotBlank(aggregateForm.getRocketWebId())) {
                LOG.info("rocket web id : " + aggregateForm.getRocketWebId());
                webDriver = Navigation.doNewApplication("http://110.44.116.37:1762/t_rkt_Subsidy_Card.aspx", false);
                boolean isThereFailed = doAutomationForFormAlreadySubmitted(webDriver, aggregateForm);
                if (!isThereFailed) {
                    doAutomationForForm(webDriver, aggregateForm);
                }
            } else {
                webDriver = Navigation.doNewApplication("http://110.44.116.37:1762/t_rkt_Subsidy_Card.aspx", true);
                doAutomationForForm(webDriver, aggregateForm);
                webDriver = Navigation.doNewApplication("http://110.44.116.37:1762/t_rkt_Subsidy_Card.aspx", false);
            }
        }
    }

    public boolean doAutomationForForm(WebDriver webDriver, AggregateForm aggregateForm) {
        rocketIdFromWeb = webDriver.findElement(By.id(HtmlTag.ROCKET_ID_DIV)).getText();
        aggregateForm.setRocketWebId(rocketIdFromWeb);
        String sourceWeb = webDriver.getPageSource();
        String rocketWebHtmlId = getRocketWebHTMLIdFromJsoup(sourceWeb);
        LOG.info(rocketWebHtmlId);
        LOG.info(rocketIdFromWeb);
        if (StringUtils.isNotBlank(rocketWebHtmlId)) {
            aggregateForm.setRocketWebHtmlId(rocketWebHtmlId);
        }
        boolean isOk = editTextSubmission(aggregateForm, webDriver);
        if (isOk) {
            dropDownSubmission(aggregateForm, webDriver);
            webDriver.findElement(By.id(HtmlTag.SAVE_FORM_1_ID)).click();
            this.webDriver = webDriver;
            boolean isNotRightCitizen = findElementById("mainContentPlaceHolder_RegularExpressionValidator20");
            if (isNotRightCitizen) {
                aggregateForm.setMessage("Please match citizenship no. according to the website.");
                this.aggregateFormService.failedAutomationAtFirstPage(aggregateForm);
                return false;
            } else {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FormAutomation.class.getName()).log(Level.SEVERE, null, ex);
                    aggregateForm.setMessage("Problem in server try again");
                    this.aggregateFormService.failedAutomationAtFirstPage(aggregateForm);
                }

                String recordMessage = webDriver.findElement(By.id("mainContentPlaceHolder_l_Msg")).getText();
                if (recordMessage.toLowerCase().contains("blank")) {
                    dropDownSubmission(aggregateForm, webDriver);
                    webDriver.findElement(By.id(HtmlTag.SAVE_FORM_1_ID)).click();
                    recordMessage = webDriver.findElement(By.id("mainContentPlaceHolder_l_Msg")).getText();
                }
                if (recordMessage.equalsIgnoreCase("Record saved!")) {
                    this.aggregateFormService.passedFirstForm(aggregateForm);
                    boolean isUploaded = uploadAttachment(aggregateForm, webDriver);
                    if (isUploaded) {
                        boolean isSecondForm = doSecondForm(aggregateForm, webDriver);
                        if (isSecondForm) {
                            return true;
                        }
                    }

                } else {
                    aggregateForm.setMessage(recordMessage);
                    this.aggregateFormService.failedAutomationAtFirstPage(aggregateForm);
                }
                return false;

            }

        }
        return false;
    }

    private boolean doSecondForm(AggregateForm aggregateForm, WebDriver webDriver) {
        try {
            webDriver.findElement(By.id(HtmlTag.NEXT_PAGE_FORM)).click();
            submitSecondForm(aggregateForm, webDriver);
            String recordMessage2 = webDriver.findElement(By.id("mainContentPlaceHolder_l_Msg")).getText();
            if (recordMessage2.equalsIgnoreCase("Record saved!")) {
                this.aggregateFormService.webAutomationSucceed(aggregateForm);
                return true;
            } else {
                aggregateForm.setMessage(recordMessage2);
                this.aggregateFormService.failedAutomationAtSecondForm(aggregateForm);
            }
        } catch (NoSuchElementException | InterruptedException e) {
           return false;
        }
        return false;
    }

    private void submitSecondForm(AggregateForm aggregateForm, WebDriver webDriver) throws InterruptedException {
        LOG.info("here");
        Thread.sleep(1000);
        webDriver.findElement(By.id(HtmlTag.ENGRAVE_NUMBER_ID)).clear();
        LOG.info("here");
        Thread.sleep(1000);
        webDriver.findElement(By.id(HtmlTag.ENGRAVE_NUMBER_ID)).sendKeys(aggregateForm.getTechnicalForm().getEngraveNumber());
        Thread.sleep(3000);
        webDriver.findElement(By.id(HtmlTag.TOTAL_STOVE_COST)).clear();
        Thread.sleep(1000);
        webDriver.findElement(By.id(HtmlTag.TOTAL_STOVE_COST)).sendKeys(String.valueOf(aggregateForm.getInvestmentForm().getTotalStoveCost()));
        Thread.sleep(3000);
        webDriver.findElement(By.id(HtmlTag.SUBSIDY_SUPPORT_COST)).clear();
        webDriver.findElement(By.id(HtmlTag.SUBSIDY_SUPPORT_COST))
                .sendKeys(String.valueOf(aggregateForm.getInvestmentForm().getSubsidySupport()));
        webDriver.findElement(By.id(HtmlTag.USER_AMOUNT)).clear();
        webDriver.findElement(By.id(HtmlTag.USER_AMOUNT))
                .sendKeys(String.valueOf(aggregateForm.getInvestmentForm().getUserAmount()));

        List<Manufacturer> manufacturers = LocalJsonUtils.loadManufacturer(assetDir);

        Manufacturer manufacturer = manufacturers.stream().filter(m -> m.getManufacturerName().equalsIgnoreCase(aggregateForm
                .getTechnicalForm().getManufacturer())).findAny().orElse(manufacturers.get(0));
        List<StoveModel> stoveModels = manufacturer.getStoveModel();
        StoveModel stoveModel = stoveModels.stream().filter(s -> s.getStovemodelName().equalsIgnoreCase(aggregateForm.getTechnicalForm().getModel())).findAny()
                .orElse(stoveModels.get(0));

        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;

        webDriver.findElement(By.id(HtmlTag.MANUFACTURER_ID)).click();

        webDriver.findElement(By.id(manufacturer.getManufacturerListId())).click();

        int indexOfStoveModel = IntStream.range(0, stoveModels.size())
                .filter(s -> stoveModel.equals(stoveModels.get(s)))
                .findFirst().orElse(0);
        Thread.sleep(5000);
        webDriver.findElement(By.id(HtmlTag.INSTALL_DATE_ID)).clear();
        webDriver.findElement(By.id(HtmlTag.INSTALL_DATE_ID)).sendKeys(aggregateForm.getTechnicalForm().getInstallDate());

        executeJavascript(javascriptExecutor, stoveModel.getStovemodelName(),
                stoveModel.getStovemodelId(),
                HtmlTag.STOVE_MODEL_TAG, "1",
                indexOfStoveModel);
        Thread.sleep(3000);
        webDriver.findElement(By.id(HtmlTag.SAVE_2_FORM_BTN)).click();
        Thread.sleep(5000);
    }

    private boolean uploadAttachment(AggregateForm aggregateForm, WebDriver webDriver) {
        try {
            List<String> attachmentMessages = new ArrayList<>();
            String errormsg = "";
            boolean exceedStatus = false;
            uploadAll(aggregateForm, webDriver, attachmentMessages);
            for (String message : attachmentMessages) {
                exceedStatus = true;
                errormsg += message;
            }
            if (exceedStatus) {
                aggregateForm.setMessage(errormsg);
                this.aggregateFormService.failedAutomationAtAttachment(aggregateForm);
                return false;
            } else {
                return true;
            }

        } catch (DocumentException | IOException | InterruptedException ex) {
            LOG.info(ex.getMessage());
            this.aggregateFormService.failedAutomationAtAttachment(aggregateForm);
            return false;
        }
    }

    public void uploadAll(AggregateForm aggregateForm, WebDriver webDriver, List<String> attachmentMessage) throws IOException, DocumentException, InterruptedException {
        List<File> subsidyFiles = new ArrayList<>();
        List<File> citizenFiles = new ArrayList<>();

        File subsidyFrontFile = new File(aggregateForm.getStarterForm().getsAFormFirstImg());
        subsidyFiles.add(subsidyFrontFile);
        File subsidyBackFile = new File(secondFormImg);
        subsidyFiles.add(subsidyBackFile);
        File citizenFront = new File(aggregateForm.getStarterForm().getCitizenFrontImg());
        File citizenBack = new File(aggregateForm.getStarterForm().getCitizenBackImg());
        citizenFiles.add(citizenFront);
        citizenFiles.add(citizenBack);
        File stockIdFiletoUpload = new File(aggregateForm.getStarterForm().getStoveIDImg());
        File stoveHandOverToUpload = new File(aggregateForm.getStarterForm().getStoveHandOverImg());
        if (StringUtils.isBlank(rocketIdFromWeb)) {
            rocketIdFromWeb = aggregateForm.getRocketWebId();
        }
        File subsidyFileToUpload = ImageUtils.toPdf(pdf_folder, subsidyFilePdf, rocketIdFromWeb, aggregateForm.getRocketId(), subsidyFiles);
        File citizenFileToUpload = ImageUtils.toPlainPdf(pdf_folder, citizenPDF, aggregateForm.getRocketId(), citizenFiles);
        webDriver.findElement(By.id(HtmlTag.ATTACHMENT_ID)).click();
        webDriver.findElement(By.id(HtmlTag.applicationPDFUPLOADTAG)).clear();
        webDriver.findElement(By.id(HtmlTag.applicationPDFUPLOADTAG)).sendKeys(subsidyFileToUpload.getPath());
        webDriver.findElement(By.id(HtmlTag.applicationPDF_UPLOADBTN)).click();
        Thread.sleep(3000);
        String sizeId = "mainContentPlaceHolder_l_Msg";
        if (existsElement(sizeId)) {
            attachmentMessage.add(webDriver.findElement(By.id(sizeId)).getText());
        }
        webDriver.findElement(By.id(HtmlTag.citizenPDFUPLOADTAG)).clear();
        webDriver.findElement(By.id(HtmlTag.citizenPDFUPLOADTAG)).sendKeys(citizenFileToUpload.getPath());
        Thread.sleep(3000);
        webDriver.findElement(By.id(HtmlTag.citizenPDF_UPLOADBTN)).click();
        Thread.sleep(3000);
        if (existsElement(sizeId)) {
            attachmentMessage.add(webDriver.findElement(By.id(sizeId)).getText());
        }
        webDriver.findElement(By.id(HtmlTag.stoveIDIMGUPLOADTAG)).clear();
        webDriver.findElement(By.id(HtmlTag.stoveIDIMGUPLOADTAG)).sendKeys(stockIdFiletoUpload.getPath());
        Thread.sleep(3000);
        webDriver.findElement(By.id(HtmlTag.stoveID_UPLOADBTN)).click();
        Thread.sleep(3000);
        if (existsElement(sizeId)) {
            attachmentMessage.add(webDriver.findElement(By.id(sizeId)).getText());
        }
        webDriver.findElement(By.id(HtmlTag.stoveHandoverIMGUPLOADTAG)).clear();
        webDriver.findElement(By.id(HtmlTag.stoveHandoverIMGUPLOADTAG))
                .sendKeys(stoveHandOverToUpload.getPath());
        Thread.sleep(3000);
        webDriver.findElement(By.id(HtmlTag.stoveHandover_UPLOADBTN)).click();
        Thread.sleep(3000);
        if (existsElement(sizeId)) {
            attachmentMessage.add(webDriver.findElement(By.id(sizeId)).getText());
        }
        webDriver.findElement(By.id(HtmlTag.RETURN_CARD_FROM_ATTACHEMENT)).click();
    }

    private boolean existsElement(String id) {
        try {
            webDriver.findElement(By.id(id));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    private void dropDownSubmission(AggregateForm aggregateForm, WebDriver webDriver) {
        try {
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
            List<AreaClass> areaClasses = LocalJsonUtils.loadAreaClass(assetDir);
            AreaClass areaClass = areaClasses.stream().filter(areaClass1 -> areaClass1.getAreaclassName()
                    .equalsIgnoreCase(aggregateForm.getStarterForm().getAreaClass()))
                    .findAny().orElse(areaClasses.get(0));
            List<Gender> genders = LocalJsonUtils.loadGender(assetDir);
            Gender gender = genders.stream().filter(g -> g.getGenderName().equalsIgnoreCase(aggregateForm.getGeneralForm().getGender()))
                    .findAny().orElse(genders.get(0));
            int genderIndexOf = IntStream
                    .range(0, genders.size())
                    .filter(i -> gender.equals(genders.get(i)))
                    .findFirst().orElse(0);
            List<Ethnicity> ethnicityList = LocalJsonUtils.loadEthinicity(assetDir);
            Ethnicity ethnicity = ethnicityList.stream().filter(e
                    -> e.getEthnicityName().equalsIgnoreCase(aggregateForm.getGeneralForm().getEthinicity())
            )
                    .findAny().orElse(ethnicityList.get(0));
            List<District> districts = LocalJsonUtils.loadDistrict(assetDir);
            District citizenDistrict = districts.stream().filter(d -> d.getDistrictName().equalsIgnoreCase(aggregateForm.getGeneralForm().getCitizenDistrict()))
                    .findAny().orElse(districts.get(0));

            int districtIndexOf = IntStream.range(0, districts.size())
                    .filter(i -> citizenDistrict.equals(districts.get(i)))
                    .findFirst().orElse(0);
            Thread.sleep(3000);
            executeJavascript(javascriptExecutor, citizenDistrict.getDistrictName(), citizenDistrict.getDistrictId(), HtmlTag.DISTRICT_SELECT_TAG, "4", districtIndexOf);
            Thread.sleep(3000);
            District currentDistrict = districts.stream()
                    .filter(cd -> cd.getDistrictName().equalsIgnoreCase(
                    aggregateForm.getCurrentAddressForm().getDistrict()
            )).findAny().orElse(districts.get(0));
            webDriver.findElement(By.id(HtmlTag.CURRENT_ADDRESS_DIV)).click();
            webDriver.findElement(By.id(currentDistrict.getDistrictListId())).click();
            Thread.sleep(3000);
            List<VPNP> vpnps = currentDistrict.getVpnp();
            VPNP vpnp = vpnps.stream()
                    .filter(v -> v.getAddressName().equalsIgnoreCase(
                    aggregateForm.getCurrentAddressForm().getVpNp()))
                    .findAny().orElse(vpnps.get(0));

            List<VPNP> vdcnps = currentDistrict.getVpnp();
            VPNP permanentVpnp = vdcnps.stream()
                    .filter(v -> v.getAddressName().equalsIgnoreCase(
                    aggregateForm.getPermanentAddressForm().getVpNp()
            ))
                    .findAny().orElse(vdcnps.get(0));
            Thread.sleep(3000);
            int areaClassIndexOf = IntStream.range(0, areaClasses.size())
                    .filter(i -> areaClass.equals(areaClasses.get(i)))
                    .findFirst().orElse(0);
            executeJavascript(javascriptExecutor, areaClass.getAreaclassName(), areaClass.getAreaclassId(),
                    HtmlTag.AREA_CLASS_SELECT_ID, "1", areaClassIndexOf);
            Thread.sleep(3000);
            executeJavascript(javascriptExecutor, gender.getGenderName(), gender.getGenderId(), HtmlTag.GENDER_SELECT_TAG,
                    "2", genderIndexOf);
            Thread.sleep(3000);
            int ethinicityIndexOf = IntStream.range(0, ethnicityList.size())
                    .filter(i -> ethnicity.equals(ethnicityList.get(i)))
                    .findFirst().orElse(0);
            webDriver.findElement(By.id(HtmlTag.ETHNICITY_DIV)).click();
            webDriver.findElement(By.id(ethnicity.getEthnicityListId())).click();
            executeJavascript(javascriptExecutor, ethnicity.getEthnicityName(), ethnicity.getEthnicityId(), HtmlTag.ETHNICITY_SELECT_TAG, "3", ethinicityIndexOf);
            Thread.sleep(3000);
            webDriver.findElement(By.id(HtmlTag.PERM_ADDRESS_DIV)).click();
            webDriver.findElement(By.id(HtmlTag.SELECT_VP_NP_DIV_TAG)).click();
            Thread.sleep(3000);
            int vdcIndexOf = IntStream.range(0, vpnps.size())
                    .filter(i -> vpnp.equals(vpnps.get(i)))
                    .findFirst().orElse(0);
            Thread.sleep(3000);
            executeJavascript(javascriptExecutor, vpnp.getAddressName(), vpnp.getAddressId(), HtmlTag.VPNI_SELECT_TAG, "6", vdcIndexOf);
            int permanentVpnpIndexOf = IntStream.range(0, vpnps.size())
                    .filter(i -> permanentVpnp.equals(vpnps.get(i)))
                    .findFirst().orElse(0);
            executeJavascript(javascriptExecutor, permanentVpnp.getAddressName(), permanentVpnp.getAddressId(), HtmlTag.PER_VPNP_SELECT_TAG, "7", permanentVpnpIndexOf);
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            aggregateForm.setMessage("Problem in server try later");
            this.aggregateFormService.failedAutomationAtFirstPage(aggregateForm);
        }
    }

    private boolean editTextSubmission(AggregateForm aggregateForm, WebDriver webDriver) {
        webDriver.findElement(By.id(HtmlTag.OWNER_NAME_ID)).clear();
        webDriver.findElement(By.id(HtmlTag.OWNER_NAME_ID))
                .sendKeys(aggregateForm.getGeneralForm().getOwnerName());
        webDriver.findElement(By.id(HtmlTag.CALLING_NAME_ID)).clear();
        webDriver.findElement(By.id(HtmlTag.CALLING_NAME_ID)).sendKeys(aggregateForm.getGeneralForm().getCallingName());
        webDriver.findElement(By.id(HtmlTag.FATHER_MOTHER_NAME)).clear();
        webDriver.findElement(By.id(HtmlTag.FATHER_MOTHER_NAME)).sendKeys(aggregateForm.getGeneralForm().getFatherMotherName());
        webDriver.findElement(By.id(HtmlTag.HUSBAND_WIFER_ID)).clear();
        webDriver.findElement(By.id(HtmlTag.HUSBAND_WIFER_ID)).sendKeys(aggregateForm.getGeneralForm().getHusbandWifeName());
        String citizenId = aggregateForm.getGeneralForm().getCitizenShip().trim();
        if (StringUtils.containsWhitespace(citizenId)) {
            citizenId = citizenId.replaceAll(" ", "/");
        }
        webDriver.findElement(By.id(HtmlTag.CITIZEN_ID)).clear();
        webDriver.findElement(By.id(HtmlTag.CITIZEN_ID)).sendKeys(citizenId);
        this.webDriver = webDriver;
        boolean isThereElement = findElementById("mainContentPlaceHolder_RegularExpressionValidator20");
        LOG.info("There is " + isThereElement);
        if (isThereElement) {
            aggregateForm.setMessage("Please match citizenship no. according to the website.");
            aggregateFormService.failedAutomationAtFirstPage(aggregateForm);
            return false;
        }
        webDriver.findElement(By.id(HtmlTag.HOUSE_NU)).clear();
        webDriver.findElement(By.id(HtmlTag.HOUSE_NU)).sendKeys(aggregateForm.getCurrentAddressForm().getHouseNumber());
        webDriver.findElement(By.id(HtmlTag.VILLAGE)).clear();
        webDriver.findElement(By.id(HtmlTag.VILLAGE)).sendKeys(aggregateForm.getCurrentAddressForm().getVillage());
        webDriver.findElement(By.id(HtmlTag.WARD_NU_ID)).clear();
        webDriver.findElement(By.id(HtmlTag.WARD_NU_ID)).sendKeys(aggregateForm.getCurrentAddressForm().getWardNumber());
        webDriver.findElement(By.id(HtmlTag.NEAREST_ROAD)).clear();
        webDriver.findElement(By.id(HtmlTag.NEAREST_ROAD)).sendKeys(aggregateForm.getCurrentAddressForm().getNearestRoom());
        webDriver.findElement(By.id(HtmlTag.PERM_WARD_NUMBER)).clear();
        webDriver.findElement(By.id(HtmlTag.PERM_WARD_NUMBER)).sendKeys(aggregateForm.getPermanentAddressForm().getWardNumber());
        webDriver.findElement(By.id(HtmlTag.PERM_VILLAGE)).clear();
        webDriver.findElement(By.id(HtmlTag.PERM_VILLAGE)).sendKeys(aggregateForm.getPermanentAddressForm().getVillage());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(FormAutomation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    private boolean findElementById(String id) {
        document = Jsoup.parse(webDriver.getPageSource());
        Element element = document.selectFirst("span#" + id);
        if (element != null) {
            String style = element.attr("style");
            LOG.info(style);
            String styleValue = style.split(":")[1];
            if (styleValue.trim().startsWith("visible")) {
                LOG.info("worry");
                return true;
            }
        }
        return false;
    }

    public void executeJavascript(JavascriptExecutor javascriptExecutor,
            String value,
            String valueId, String selectTagId, String index,
            int indexOf
    ) {
        if (Integer.valueOf(index) == 5 || Integer.valueOf(index) == 6 || Integer.valueOf(index) == 7) {
            String jquerySelect = "$(\"#" + selectTagId + " option[value='0']\").attr('selected',false);";
            javascriptExecutor.executeScript(jquerySelect);
        }
        String jquery = "$(\"#" + selectTagId + " option[value='" + valueId + "']\").attr('selected',true);";
        javascriptExecutor.executeScript("document.getElementsByClassName(\"chzn-single\")[" + index + "].innerHTML = '" + value + "';");

        javascriptExecutor.executeScript("document.getElementsByClassName(\"chzn-single\")[" + index + "].nextSibling.getElementsByClassName(\"chzn-results\")[0].getElementsByClassName(\"active-result result-selected\")[0].classList.remove(\"result-selected\");");
        javascriptExecutor.executeScript("document.getElementsByClassName(\"chzn-single\")[" + index + "].nextSibling.getElementsByClassName(\"chzn-results\")[0].getElementsByClassName(\"active-result\")[" + indexOf + "].classList.add(\"result-selected\");");
        javascriptExecutor.executeScript(jquery);

    }

    private boolean doAutomationForFormAlreadySubmitted(WebDriver webDriver, AggregateForm aggregateForm) {
        webDriver.findElement(By.id("mainContentPlaceHolder_cb_RKT_ID_chzn")).click();
        try {
            Thread.sleep(5000);
            webDriver.findElement(By.id(aggregateForm.getRocketWebHtmlId())).click();
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(FormAutomation.class.getName()).log(Level.SEVERE, null, ex);
        }
        switch (aggregateForm.getFailedAt()) {
            case 1:
                boolean isUploaded = uploadAttachment(aggregateForm, webDriver);
                if (isUploaded) {
                    doSecondForm(aggregateForm, webDriver);
                }
                return true;
            case 2:
                doSecondForm(aggregateForm, webDriver);
                return true;
            default: {
                return false;
            }

        }
    }

    private String getRocketWebHTMLIdFromJsoup(String sourceWeb) {
        document = Jsoup.parse(sourceWeb);
        Elements divElements = document.select("div#mainContentPlaceHolder_cb_RKT_ID_chzn");
        for (Element divElement : divElements) {
            Element divChznDrop = divElement.selectFirst("div.chzn-drop");
            Elements ulElements = divChznDrop.select("ul.chzn-results");
            for (Element ulElement : ulElements) {
                Elements liElement = ulElement.select("li");
                for (Element element : liElement) {
                    if (element.hasClass("result-selected")) {
                        if (!StringUtils.isNotBlank(rocketIdFromWeb)) {
                            rocketIdFromWeb = element.text();
                        }
                        return element.id();
                    }
                }
            }
        }
        return null;
    }
}
