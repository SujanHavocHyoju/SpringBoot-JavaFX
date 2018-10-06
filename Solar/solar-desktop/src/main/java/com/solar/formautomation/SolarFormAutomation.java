package com.solar.formautomation;

import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.DocumentException;
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
import com.solar.entity.CurrentAddress;
import com.solar.entity.EndUse;
import com.solar.entity.GeneralTechnical;
import com.solar.entity.Investment;
import com.solar.entity.LampTechnical;
import com.solar.entity.Owner;
import com.solar.entity.PanelTechnical;
import com.solar.entity.PermanentAddress;
import com.solar.entity.SolarForm;
import com.solar.response.Response;
import com.solar.service.SolarFormService;
import com.solar.utils.ImageUtils;
import com.solar.utils.JsonUtils;
import com.solar.utils.LocalJsonUtils;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author bibek
 */
@Data
@Slf4j
@Component
public class SolarFormAutomation {

    @Value("${asset_dir}")
    private String assetDir;
    @Value("${subsidy_file_pdf}")
    private String subsidyFilePdf;
    @Value("${citizen_pdf}")
    private String citizenPDF;
    @Value("${pdf_folder}")
    private String pdf_folder;
    ;
    @Autowired
    private LoginFormAutomation loginFormAutomation;
    private Document document;
    @Autowired
    private SolarFormService solarFormService;
    private String solarWebId, solarWebHTMLId;
    private WebDriver webDriver;
    @Autowired
    private Navigation navigation;

    public void automateAll() {
        Response response = null;

        response = loginFormAutomation.doLogin();
        log.info("Login success");
        if (response != null) {
            if (response.isNext()) {
                log.info("Ready to automate");
                doFormAutomation();
            }
        }
    }

    private void doFormAutomation() {
        log.info("automation in progress");
        List<SolarForm> solarForms = solarFormService.automationList();
        submit(solarForms);
    }

    public boolean isExist(String fileName) {
        if (StringUtils.isNotBlank(fileName)) {
            return new File(fileName).exists();
        } else {
            return false;
        }
    }

    private void submit(List<SolarForm> solarForms) {
        for (SolarForm solarForm : solarForms) {
            log.info(solarForm.getSolarId() + " is processing for validation");
            Attachment attachment = solarForm.getAttachment();
            if (!isExist(attachment.getApplicationBackImage())) {
                solarForm.setMessage("Application back image is missing!");
                this.solarFormService.failedAutomationAtFirstPage(solarForm);
                continue;
            }
            if (!isExist(attachment.getApplicationFrontImage())) {
                solarForm.setMessage("Application Front image is missing!");
                this.solarFormService.failedAutomationAtFirstPage(solarForm);
                continue;
            } else {
                double size = com.solar.utils.FileUtils.calculateDirectoryInMegaBytes(new File(attachment.getApplicationFrontImage()));
                size += com.solar.utils.FileUtils.calculateDirectoryInMegaBytes(new File(attachment.getApplicationBackImage()));
                if (size > 1.5) {
                    solarForm.setMessage("Application images size is large, please review.");
                    this.solarFormService.failedAutomationAtFirstPage(solarForm);
                    continue;
                }
            }
            if (!isExist(attachment.getCitizenshipBackImage())) {
                solarForm.setMessage("Citizen Back image is missing!");
                this.solarFormService.failedAutomationAtFirstPage(solarForm);
                continue;
            }
            if (!isExist(attachment.getCitizenshipFrontImage())) {
                solarForm.setMessage("Citizen Front image is missing!");
                this.solarFormService.failedAutomationAtFirstPage(solarForm);
                continue;
            } else {
                double size = com.solar.utils.FileUtils.calculateDirectoryInMegaBytes(new File(attachment.getCitizenshipFrontImage()));
                size += com.solar.utils.FileUtils.calculateDirectoryInMegaBytes(new File(attachment.getCitizenshipBackImage()));
                if (size > 1.5) {
                    solarForm.setMessage("Citizen images size is large, please review.");
                    this.solarFormService.failedAutomationAtFirstPage(solarForm);
                    continue;
                }
            }
            if (!isExist(attachment.getBatteryVoucherImage())) {
                solarForm.setMessage("Battery Voucher image is missing!");
                this.solarFormService.failedAutomationAtFirstPage(solarForm);
                continue;
            } else {
                double size = com.solar.utils.FileUtils.calculateDirectoryInMegaBytes(new File(attachment.getBatteryVoucherImage()));
                if (size > 1.5) {
                    solarForm.setMessage("Battery Voucher image size is large, please review.");
                    this.solarFormService.failedAutomationAtFirstPage(solarForm);
                    continue;
                }
            }
            if (!isExist(attachment.getBillInvoiceImage())) {
                solarForm.setMessage("Billing Invoice image is missing!");
                this.solarFormService.failedAutomationAtFirstPage(solarForm);
                continue;
            } else {
                double size = com.solar.utils.FileUtils.calculateDirectoryInMegaBytes(new File(attachment.getBillInvoiceImage()));
                if (size > 1.5) {
                    solarForm.setMessage("Billing Invoice image size is large, please review.");
                    this.solarFormService.failedAutomationAtFirstPage(solarForm);
                    continue;
                }
            }

            if (!isExist(attachment.getInstallerImage())) {
                solarForm.setMessage("Installer image is missing!");
                this.solarFormService.failedAutomationAtFirstPage(solarForm);
                continue;
            } else {
                double size = com.solar.utils.FileUtils.calculateDirectoryInMegaBytes(new File(attachment.getCitizenshipFrontImage()));
                if (size > 1.5) {
                    solarForm.setMessage("Installer image size is large, please review.");
                    this.solarFormService.failedAutomationAtFirstPage(solarForm);
                    continue;
                }
                size = 0;
            }
            if (!isExist(attachment.getSystemUserManualImage())) {
                solarForm.setMessage("System User Manual image is missing!");
                this.solarFormService.failedAutomationAtFirstPage(solarForm);
                continue;
            } else {
                double size = com.solar.utils.FileUtils.calculateDirectoryInMegaBytes(new File(attachment.getSystemUserManualImage()));
                if (size > 1.5) {
                    solarForm.setMessage("System User Manual image size is large, please review.");
                    this.solarFormService.failedAutomationAtFirstPage(solarForm);
                    continue;
                }
            }
            if (StringUtils.isNotBlank(attachment.getMuchulkaImage())) {
                if (!isExist(attachment.getMuchulkaImage())) {
                    solarForm.setMessage("Muchulka image is missing!");
                    this.solarFormService.failedAutomationAtFirstPage(solarForm);
                    continue;
                } else {
                    double size = com.solar.utils.FileUtils.calculateDirectoryInMegaBytes(new File(attachment.getMuchulkaImage()));
                    if (size > 1.5) {
                        solarForm.setMessage("Muchulka size is large, please review.");
                        this.solarFormService.failedAutomationAtFirstPage(solarForm);
                        continue;
                    }
                }
            }
            if (StringUtils.isNotBlank(attachment.getEarthquakeImage())) {
                if (!isExist(attachment.getEarthquakeImage())) {
                    solarForm.setMessage("EarthQuake image is missing!");
                    this.solarFormService.failedAutomationAtFirstPage(solarForm);
                    continue;
                } else {
                    double size = com.solar.utils.FileUtils.calculateDirectoryInMegaBytes(new File(attachment.getEarthquakeImage()));
                    if (size > 1.5) {
                        solarForm.setMessage("EarthQuake image size is large, please review.");
                        this.solarFormService.failedAutomationAtFirstPage(solarForm);
                        continue;
                    }
                }
            }
            if (!solarForm.getGeneralTechnical().getInstallDate().matches("[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}")) {
                solarForm.setMessage("Please match installation date format (Month/Day/Year) according to the website.");
                this.solarFormService.failedAutomationAtFirstPage(solarForm);
                continue;
            }

            log.info(solarForm.getSolarId() + " is success for validation");
            if (StringUtils.isNotBlank(solarForm.getSolarWebId())
                    && StringUtils.isNotBlank(solarForm.getSolarWebHtmlId())) {
                webDriver = navigation.goToSolarCard();

                //webDriver.manage().window().maximize();
                doAutomationForFormAlreadySubmitted(webDriver, solarForm);
                webDriver = navigation.goToSolarCard();

            } else {
                webDriver = navigation.goToNewCard();
                doAutomationForForm(webDriver, solarForm);
            }
        }

    }

    private boolean doAutomationForForm(WebDriver webDriver, SolarForm solarForm) {
        solarWebId = webDriver.findElement(By.id(HtmlTag.SOLAR_ID_DIV)).getText();
        solarForm.setSolarWebId(solarWebId);
        String sourceWeb = webDriver.getPageSource();
        solarWebHTMLId = getSolarWebHTMLIDFromWeb(sourceWeb);
        log.info("here is our solar wed id {} and solar Web html form {}", new Object[]{
            solarWebId, solarWebHTMLId
        });
        if (StringUtils.isNotBlank(solarWebHTMLId)) {
            solarForm.setSolarWebHtmlId(solarWebHTMLId);
        }
        log.info("Success one");
        boolean isOk = editTextSubmission(solarForm);
        if (isOk) {
            isOk = doLastThing(solarForm);
            if (isOk) {
                isOk = uploadAttachement(solarForm);
                if (isOk) {
                    this.solarFormService.webAutomationSucceed(solarForm);
                    return true;
                }
            }
        }
        return false;
    }

    private void doLogoutAnCloseWindow(WebDriver webDriver) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String getSolarWebHTMLIDFromWeb(String sourceWeb) {
        document = Jsoup.parse(sourceWeb);
        Elements divElements = document.select("div#mainContentPlaceHolder_cb_SHS_ID_chzn");
        for (Element divElement : divElements) {
            Element divChznDrop = divElement.selectFirst("div.chzn-drop");
            Elements ulElements = divChznDrop.select("ul.chzn-results");
            for (Element ulElement : ulElements) {
                Elements liElement = ulElement.select("li");
                for (Element element : liElement) {
                    if (element.hasClass("result-selected")) {
                        if (!StringUtils.isNotBlank(solarWebId)) {
                            solarWebId = element.text();
                        }
                        log.info(element.id());
                        solarWebHTMLId = element.id();
                        return solarWebHTMLId;
                    }
                }
            }
        }
        return null;
    }

    private boolean doAutomationForFormAlreadySubmitted(WebDriver webDriver, SolarForm solarForm) {
        this.webDriver = webDriver;
        try {
            doDropDownAutomation(HtmlTag.SOLAR_ID_DIV, solarForm.getSolarWebHtmlId());
            Thread.sleep(5000);
            boolean isOk = uploadAttachement(solarForm);
            if (isOk) {
                this.solarFormService.webAutomationSucceed(solarForm);
                return true;
            }
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
        return false;
    }

    private boolean editTextSubmission(SolarForm solarForm) {
        try {
            Owner owner = solarForm.getOwner();
            doOwner(owner);
            doFirstDropDown(solarForm);
            log.info("owner submission");
            doAddress(solarForm.getCurrentAddress(), solarForm.getPermanentAddress());
            doAddressDropDown(solarForm);
            log.info("Address Dropdown Completed");

            doGeneralTechnical(solarForm.getGeneralTechnical());
            ((JavascriptExecutor) webDriver).executeScript("$(\"#ui-datepicker-div\").hide();");
            doGeneralTechnicalDropDown(solarForm);
            log.info("General Technical Dropdown Completed");
            doPanelTechnical(solarForm.getPanelTechnical());
            doPanelTechnicalDropDown(solarForm);
            log.info("Panel Technical Dropdown Completed");
            doBatteryTechnical(solarForm.getBatteryTechnical());
            doBatteryTechnicalDropDown(solarForm);
            log.info("Battery Technical Dropdown Completed");
            doChargeController(solarForm.getChargeControllerTechnical());
            doChargeDropDown(solarForm);
            doLamp(solarForm.getLampTechnical());
            doLampDropDown(solarForm);
            doInvestment(solarForm.getInvestmentEntity());
            doBanking(solarForm.getBanking());
            doEndUse(solarForm.getEndUse());
            doEndUseDropDown(solarForm);
            return true;
        } catch (Exception e) {
            solarForm.setMessage("Try again!");
            this.solarFormService.failedAutomationAtFirstPage(solarForm);
            return false;
        }
    }

    private void doOwner(Owner owner) {
        doIdValueMapping(HtmlTag.OWNERNAME, owner.getName());
        doIdValueMapping(HtmlTag.CALLING_NAME_ID, owner.getCallingName());
        doIdValueMapping(HtmlTag.CITIZENSHIPNUMBER, owner.getCitizenShipNumber());
        doIdValueMapping(HtmlTag.OWNER_MOBILE_NUMBER, owner.getMobileNumber());
        doIdValueMapping(HtmlTag.FATHER_OR_HUSBAND, owner.getHusbandOrFatherName());
        doIdValueMapping(HtmlTag.FATHER_OR_HUSBAND_MOBILE_NUMBER, owner.getHusbandOrFatherMobileNumber());
        doIdValueMapping(HtmlTag.NEIGHBOUR_NAME, owner.getNeighbourName());
        doIdValueMapping(HtmlTag.NEIGHBOUR_MOBILE_NUMBER, owner.getNeighbourMobileNumber());

    }

    public void doIdValueMapping(String id, String value) {
        try {
            webDriver.findElement(By.id(id)).clear();
            Thread.sleep(2000);
            webDriver.findElement(By.id(id)).sendKeys(value);
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            log.info("Yes we have a problem. " + ex.getMessage());
        }
    }

    private void doAddress(CurrentAddress currentAddress, PermanentAddress permanentAddress) {
        doIdValueMapping(HtmlTag.CURRENT_ADDRESS_WARD_NUMBER, currentAddress.getWardNumber());
        doIdValueMapping(HtmlTag.CURRENT_ADDRESS_VILLAGE, currentAddress.getVillage());
        doIdValueMapping(HtmlTag.PERMANENT_ADDRESS_WARDNUMBER, permanentAddress.getWardNumber());
        doIdValueMapping(HtmlTag.PERMANENT_ADDRESS_VILLAGE, permanentAddress
                .getVillage());
    }

    private void doGeneralTechnical(GeneralTechnical generalTechnical) {
        doIdValueMapping(HtmlTag.GENERAL_SYSTEM_CAPACITY, "" + generalTechnical.getSystemCapacity());

        try {
            webDriver.findElement(By.id(HtmlTag.INSTALL_DATE_GENERAL_TECHNICAL)).clear();
            Thread.sleep(2000);
            String installDate[] = generalTechnical.getInstallDate().split("/");
            for (int i = 0; i < installDate.length; i++) {
                if (i == 2) {
                    webDriver.findElement(By.id(HtmlTag.INSTALL_DATE_GENERAL_TECHNICAL)).sendKeys(installDate[i]);
                } else {
                    webDriver.findElement(By.id(HtmlTag.INSTALL_DATE_GENERAL_TECHNICAL)).sendKeys(installDate[i] + "/");
                }
            }
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            log.info("Yes we have a problem. " + ex.getMessage());
        }
    }

    private void doPanelTechnical(PanelTechnical panelTechnical) {
        doIdValueMapping(HtmlTag.PT_SERIAL_NUM, panelTechnical.getSerialNumber());
        doIdValueMapping(HtmlTag.PT_WPS, "" + panelTechnical.getCapacity());
    }

    private void doBatteryTechnical(BatteryTechnical batteryTechnical) {
        doIdValueMapping(HtmlTag.BATTERY_SERIAL_NO, batteryTechnical.getSerialNumber());
        doIdValueMapping(HtmlTag.BATTERY_CAPACITY, String.valueOf(batteryTechnical
                .getCapacityAH()));
    }

    private void doChargeController(ChargeControllerTechnical chargeControllerTechnical) {
        doIdValueMapping(HtmlTag.CHARGE_CONTROLLER_BRAND, chargeControllerTechnical.getBrand());
        doIdValueMapping(HtmlTag.CHARGE_CONTROLLER_RATING, "" + chargeControllerTechnical.getRatingA());
    }

    private void doLamp(LampTechnical lampTechnical) {
        doIdValueMapping(HtmlTag.LAMP_CAPACITY, "" + lampTechnical.getCapacityW());
    }

    private void doInvestment(Investment investmentEntity) {
        doIdValueMapping(HtmlTag.INVESTMENT_TSC, "" + investmentEntity.getTotalSystemCost());
        doIdValueMapping(HtmlTag.SUPPORT_AMOUNT, "" + investmentEntity.getSupportAmount());
        doIdValueMapping(HtmlTag.ANNUAL_AMOUNT, "" + investmentEntity.getAnnualIncome());
        doIdValueMapping(HtmlTag.DO_USER_AMOUT, "" + investmentEntity.getUserAmount());
        doIdValueMapping(HtmlTag.INCOME_SOURCE, investmentEntity.getIncomeSource());

    }

    private void doBanking(Banking banking) {
        doIdValueMapping(HtmlTag.LOAN_AMOUNT, "" + banking.getLoanAmount());
        doIdValueMapping(HtmlTag.INSTITUTION_NAME, banking.getInstitutionAddress());
        doIdValueMapping(HtmlTag.INTEREST_RATE, "" + banking.getInterestRate());
        doIdValueMapping(HtmlTag.MATURITY_PERIOD, banking.getMaturityPeriod());
        doIdValueMapping(HtmlTag.APPROVAL_DATE, banking.getApprovalDate());
    }

    private void doEndUse(EndUse endUse) {
        doIdValueMapping(HtmlTag.NUM_OF_BULB, "" + endUse.getNumberOfBulb());
        doIdValueMapping(HtmlTag.OTHER_CONNECTION, endUse.getOtherConnection());

    }

    public void executeJavascript(JavascriptExecutor javascriptExecutor,
            String value,
            String valueId, String selectTagId, String index,
            int indexOf
    ) throws InterruptedException {
        try {
            Thread.sleep(3000);
            log.info("----Value: {} and valudId : {}", new Object[]{value, valueId});
            String jquery = "$(\"#" + selectTagId + " option[value='" + valueId + "']\").attr('selected',true);";
            log.info(jquery);
            javascriptExecutor.executeScript("document.getElementsByClassName(\"chzn-single\")[" + index + "].innerHTML = '" + value + "';");
            javascriptExecutor.executeScript("document.getElementsByClassName(\"chzn-single\")[" + index + "].nextSibling.getElementsByClassName(\"chzn-results\")[0].getElementsByClassName(\"active-result result-selected\")[0].classList.remove(\"result-selected\");");
            javascriptExecutor.executeScript("document.getElementsByClassName(\"chzn-single\")[" + index + "].nextSibling.getElementsByClassName(\"chzn-results\")[0].getElementsByClassName(\"active-result\")[" + indexOf + "].classList.add(\"result-selected\");");
            javascriptExecutor.executeScript(jquery);
            Thread.sleep(3000);
        } catch (NumberFormatException ex) {
            log.info(ex.getMessage());
        }

    }

    private boolean doFirstDropDown(SolarForm solarForm) {
        try {
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
            Type type = new TypeToken<List<AreaClass>>() {
            }.getType();
            List<AreaClass> areaclasses = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "areaclass.json"), Charset.defaultCharset()), type);
            Optional<AreaClass> areOptional = areaclasses.stream()
                    .filter(a -> a.getAreaclassName().equalsIgnoreCase(solarForm.getAreaClass()))
                    .findAny();
            if (areOptional.isPresent()) {
                int areaClassIndexOf = IntStream.range(0, areaclasses.size())
                        .filter(i -> areOptional.get().equals(areaclasses.get(i)))
                        .findFirst().orElse(0);
                executeJavascript(javascriptExecutor, areOptional.get().getAreaclassName(),
                        areOptional.get().getAreaclassId(), HtmlTag.AREACLASS_DIV_SELECT, "1", areaClassIndexOf);

            }
            List<Gender> genders = LocalJsonUtils.loadGender(assetDir);
            Optional<Gender> gender = genders.stream().filter(g -> g.getGenderName().equalsIgnoreCase(
                    solarForm.getOwner().getGender()
            )).findAny();
            if (gender.isPresent()) {
                int genderIndexOf = IntStream.range(0, genders.size())
                        .filter(i -> gender.get().equals(genders.get(i)))
                        .findFirst().orElse(0);
                executeJavascript(javascriptExecutor, gender.get().getGenderName(),
                        gender.get().getGenderId(), HtmlTag.GENDER_SELECT_DIV, "2", genderIndexOf);

            }
            List<Ethnicity> ethnicityList = LocalJsonUtils.loadEthinicity(assetDir);
            Optional<Ethnicity> ethnicity = ethnicityList.stream().filter(e
                    -> e.getEthnicityId().equalsIgnoreCase(solarForm.getOwner().getEthnicity())
            )
                    .findAny();
            if (ethnicity.isPresent()) {
                int enthnicityIndeOf = IntStream.range(0, genders.size())
                        .filter(i -> gender.get().equals(genders.get(i)))
                        .findFirst().orElse(0);
                executeJavascript(javascriptExecutor, ethnicity.get().getEthnicityName(),
                        ethnicity.get().getEthnicityId(), HtmlTag.ETHNICITY_SELECT_DIV, "4", enthnicityIndeOf);

            }

            type = new TypeToken<List<TargetGroup>>() {
            }.getType();
            List<TargetGroup> targetGroups = JsonUtils.fromJsonToList(FileUtils.readFileToString(new File(assetDir, "targetGroup.json"), "UTF-8"), type);
            Optional<TargetGroup> targetGroupOptional
                    = targetGroups.stream().filter(t -> t.getTargetGroupId().equalsIgnoreCase(
                    solarForm.getOwner().getTargetGroup()
            )).findAny();
            if (targetGroupOptional.isPresent()) {
                int targetGroupIndexOf
                        = IntStream.range(0, targetGroups.size())
                                .filter(i -> targetGroupOptional.get().equals(targetGroups.get(i)))
                                .findFirst().orElse(0);
                executeJavascript(javascriptExecutor, targetGroupOptional.get().getTargetGroupName(),
                        targetGroupOptional.get().getTargetGroupId(), HtmlTag.TARGET_GROUP_SELECT, "5", targetGroupIndexOf);

            }
            type = new TypeToken<List<CitizenDistrict>>() {
            }.getType();
            final List<CitizenDistrict> districts = JsonUtils.fromJSONTOGivenFile(assetDir, "citizenDistrict", type);

            final Optional<CitizenDistrict> districtOptional = districts.stream().
                    filter(d -> d.getCitizenDistrictName().equalsIgnoreCase(solarForm.getOwner().getCitizenshipDistrict()))
                    .findAny();

            if (districtOptional.isPresent()) {
                int districtIndexOf = IntStream.range(0, districts.size())
                        .filter(i -> districtOptional.get().equals(districts.get(i))).findFirst().orElse(0);
                executeJavascript(javascriptExecutor, districtOptional.get().getCitizenDistrictName(),
                        districtOptional.get().getCitizenDistrictId(), HtmlTag.CITIZEN_DISTRICT, "3", districtIndexOf);
            }

            return true;
        } catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }
    }

    private boolean uploadAttachement(SolarForm solarForm) {
        return uploadAll(solarForm);
    }

    private boolean uploadAll(SolarForm solarForm) {
        try {
            String errormsg = "";
            boolean exceedStatus = false;
            List<File> subsidyFiles = new ArrayList<>();
            List<File> citizenFiles = new ArrayList<>();

            File subsidyFrontFile = new File(solarForm.getAttachment().getApplicationFrontImage());
            subsidyFiles.add(subsidyFrontFile);
            File subsidyBackFile = new File(solarForm.getAttachment().getApplicationBackImage());
            subsidyFiles.add(subsidyBackFile);
            File citizenFront = new File(solarForm.getAttachment().getCitizenshipFrontImage());
            File citizenBack = new File(solarForm.getAttachment().getCitizenshipBackImage());
            citizenFiles.add(citizenFront);
            citizenFiles.add(citizenBack);
            if (StringUtils.isBlank(solarWebId)) {
                solarWebId = solarForm.getSolarWebId();
            }

            File subsidyFileToUpload = ImageUtils.toPdf(pdf_folder, subsidyFilePdf, solarWebId, solarForm.getSolarId(), subsidyFiles);
            File citizenFileToUpload = ImageUtils.toPlainPdf(pdf_folder, citizenPDF, solarForm.getSolarId(), citizenFiles);
            webDriver.findElement(By.id(HtmlTag.ATTACHMENT_ID)).click();
            doFileAutomation(HtmlTag.applicationPDFUPLOADTAG, HtmlTag.applicationPDF_UPLOADBTN, subsidyFileToUpload.getPath());
            String sizeId = "mainContentPlaceHolder_l_Msg";

            doFileAutomation(HtmlTag.citizenPDFUPLOADTAG, HtmlTag.citizenPDF_UPLOADBTN, citizenFileToUpload.getPath());

            if (StringUtils.isNotBlank(solarForm.getAttachment().getSystemUserManualImage())) {
                doFileAutomation(HtmlTag.SYS_OWNER, HtmlTag.SYS_OWNER_BTN, solarForm.getAttachment().getSystemUserManualImage());
            }

            if (StringUtils.isNotBlank(solarForm.getAttachment().getInstallerImage())) {
                doFileAutomation(HtmlTag.INSTALLER, HtmlTag.INSTALLER_BTN, solarForm.getAttachment().getInstallerImage());
            }

            File batteryVoucherFile = new File(solarForm.getAttachment().getBatteryVoucherImage());
            if (batteryVoucherFile.exists()) {
                batteryVoucherFile = ImageUtils.toSingleImagePdf(pdf_folder, "battery_voucher", solarForm.getSolarId(), batteryVoucherFile);
                doFileAutomation(HtmlTag.BATTERY_VOUCHER_INPUT, HtmlTag.BATTERY_VOUCHER_BTN, batteryVoucherFile.getPath());

            }

            File billingVoucher = new File(solarForm.getAttachment().getBillInvoiceImage());
            if (billingVoucher.exists()) {
                billingVoucher = ImageUtils.toSingleImagePdf(pdf_folder, "billing_invoice", solarForm.getSolarId(), billingVoucher);
                doFileAutomation(HtmlTag.BILLING_INVOICE_INPUT, HtmlTag.BILLING_INVOICE_BTN, billingVoucher.getPath());

            }

            if (StringUtils.isNotBlank(solarForm.getAttachment().getMuchulkaImage())) {
                File muchulkaFile = new File(solarForm.getAttachment().getMuchulkaImage());
                if (muchulkaFile.exists()) {
                    muchulkaFile = ImageUtils.toSingleImagePdf(pdf_folder, "muchulka", solarForm.getSolarId(), muchulkaFile);
                    doFileAutomation(HtmlTag.MUCHULKA_INPUT, HtmlTag.MUCHULKA_BTN, muchulkaFile.getPath());

                }
            }
            if (StringUtils.isNotBlank(solarForm.getAttachment().getEarthquakeImage())) {
                File earthQuakeFile = new File(solarForm.getAttachment().getEarthquakeImage());
                if (earthQuakeFile.exists()) {
                    earthQuakeFile = ImageUtils.toSingleImagePdf(pdf_folder, "earthQuakeFile", solarForm.getSolarId(), earthQuakeFile);
                    doFileAutomation(HtmlTag.EARTHQUAKE_INPUT, HtmlTag.EARTHQUAKE_BTN, earthQuakeFile.getPath());

                }
            }

            WebElement webElement = webDriver.findElement(By.id(HtmlTag.RETURN_CARD_FROM_ATTACHEMENT));
            Actions actions = new Actions(webDriver);
            actions.moveToElement(webElement).click();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(SolarFormAutomation.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;

        } catch (DocumentException | IOException e) {
            if (e instanceof WebDriverException) {
                return uploadAll(solarForm);
            } else {
                log.info("Error here " + e.getMessage());
                this.solarFormService.failedAutomationAtAttachment(solarForm);
                return false;
            }
        }
    }

    private boolean existsElement(String id) {
        try {
            webDriver.findElement(By.id(id));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    private void doDropDownAutomation(String id, String value) throws Exception {
        try {
            log.info("Id is {} and value is {}", new Object[]{id, value});
            WebElement element = webDriver.findElement(By.id(id));
            Actions actions = new Actions(webDriver);
            actions.moveToElement(element).click().perform();
            Thread.sleep(3000);
            webDriver.findElement(By.id(value)).click();
        } catch (ElementNotVisibleException e) {
            Thread.sleep(3000);
            doDropDownAutomation(id, value);
        }

    }

    void doFileAutomation(String idInput, String idBtn, String value) {
        try {
            log.info("Id is {} and idBtn is {} and value is {}", new Object[]{idInput, idBtn, value});
            webDriver.findElement(By.id(idInput)).sendKeys(value);
            webDriver.findElement(By.id(idBtn)).click();
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            try {
                Thread.sleep(5000);
                doFileAutomation(idInput, idBtn, value);
            } catch (InterruptedException ex1) {
                log.info("Problem in attachment");
            }
        }
    }

    static int counter = 0;

    private boolean doLastThing(SolarForm solarForm) {
        ((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0, 0);");
        String message = doClickAndMessage();
        if (message.trim().equalsIgnoreCase("MESSAGE! Saved successfully.")) {
            return this.solarFormService.passedFirstForm(solarForm);
        } else {
            solarForm.setMessage(message);
            this.solarFormService.failedAutomationAtFirstPage(solarForm);
            return false;
        }
    }

    private void doAddressDropDown(SolarForm solarForm) throws InterruptedException, Exception {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
        Type type = new TypeToken<List<District>>() {
        }.getType();

        List<District> currentDistricts = JsonUtils.fromJSONTOGivenFile(assetDir, "district", type);
        Optional<District> currentDistrict = currentDistricts.stream().
                filter(d -> d.getDistrictName().equalsIgnoreCase(solarForm.getCurrentAddress().getDistrict()))
                .findAny();
        if (currentDistrict.isPresent()) {
            String id = searchForDistrict(currentDistrict.get().getDistrictName());
//            executeJavascript(javascriptExecutor, currentDistrict.get().getDistrictName(),
//                    currentDistrict.get().getDistrictId(), HtmlTag.CURRENT_DISTRICT, "6", currentDistrictIndexOf);
            doDropDownAutomation(HtmlTag.CURRENT_DISTRICT_ID, id);
            List<VPNP> vpnps = currentDistrict.get().getVpnp();
            Optional<VPNP> vpnpOptional = vpnps.stream()
                    .filter(v -> v.getAddressName().equalsIgnoreCase(solarForm
                    .getCurrentAddress().getVpNp())).findFirst();
            if (vpnpOptional.isPresent()) {
//                int vpnpIndexOf = IntStream.range(0, vpnps.size())
//                        .filter(i -> vpnpOptional.get().equals(vpnps.get(i)))
//                        .findFirst().orElse(0);
                id = searchForVPNP(vpnpOptional.get().getAddressName());
                doDropDownAutomation(HtmlTag.VPNP_CURRENT_DIV, id);
//                executeJavascript(javascriptExecutor, vpnpOptional.get().getAddressName(),
//                        vpnpOptional.get().getAddressId(), HtmlTag.VPNP_DIV_TAG, "7", vpnpIndexOf);

            }
            Optional<VPNP> permanentVpnpOptional = vpnps.stream().filter(p -> p.getAddressName().equalsIgnoreCase(
                    solarForm.getPermanentAddress().getVpNp())).findFirst();
            if (permanentVpnpOptional.isPresent()) {
                int vpnpIndexOf = IntStream.range(0, vpnps.size())
                        .filter(i -> permanentVpnpOptional.get().equals(vpnps.get(i)))
                        .findFirst().orElse(0);
                id = searchForVPNP(permanentVpnpOptional.get().getAddressName());
                doDropDownAutomation(HtmlTag.PERMANENT_VPNP_DIV, id);
//                executeJavascript(javascriptExecutor, permanentVpnpOptional.get().getAddressName(),
//                        permanentVpnpOptional.get().getAddressId(), HtmlTag.PERMANENT_VPNP_DIV_TAG, "8", vpnpIndexOf);
            }
        }
    }

    private void doGeneralTechnicalDropDown(SolarForm solarForm) throws InterruptedException, Exception, Exception {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
        Type type = new TypeToken<List<BDAType>>() {
        }.getType();
        List<BDAType> bDATypes = JsonUtils.fromJSONTOGivenFile(assetDir, "bdatype", type);
        Optional<BDAType> bdaOptional = bDATypes.stream()
                .filter(b -> b.getBdaTypeName().equalsIgnoreCase(solarForm.getGeneralTechnical().getBdaType())).findAny();
        if (bdaOptional.isPresent()) {
            int bdaTypeIndexOf = IntStream.range(0, bDATypes.size())
                    .filter(i -> bdaOptional.get().equals(bDATypes.get(i)))
                    .findFirst().orElse(0);
            String value = searchForValue(bdaOptional.get().getBdaTypeName(), "select#mainContentPlaceHolder_cb_BDA_TYPE");
            executeJavascript(javascriptExecutor, bdaOptional.get().getBdaTypeName(),
                    value, HtmlTag.BDA_TYPE_DIV, "9", bdaTypeIndexOf);

        }
        type = new TypeToken<List<AssIdType>>() {
        }.getType();
        List<AssIdType> assIdTypes = JsonUtils.fromJSONTOGivenFile(assetDir, "asstype", type);
        Optional<AssIdType> assOptional = assIdTypes.stream()
                .filter(a -> a.getAssTypeName().equalsIgnoreCase(solarForm
                .getGeneralTechnical().getAssType())).findAny();
        if (assOptional.isPresent()) {
            int assIdTypeIndexOf = IntStream.range(0, assIdTypes.size())
                    .filter(i -> assOptional.get().equals(assIdTypes.get(i)))
                    .findFirst().orElse(0);
            String value = searchForValue(assOptional.get().getAssTypeName(), "select#mainContentPlaceHolder_cb_ASS_TYPE");
            executeJavascript(javascriptExecutor, assOptional.get().getAssTypeName(),
                    value, HtmlTag.ASS_TYPE_ID, "11", assIdTypeIndexOf);

        }
        type = new TypeToken<List<Installer>>() {
        }.getType();
        List<Installer> installers = JsonUtils.fromJSONTOGivenFile(assetDir, "installer", type);
        Optional<Installer> installerOptional = installers.stream().filter(b -> b.getInstallerName().equalsIgnoreCase(
                solarForm.getGeneralTechnical().getInstaller()
        )).findAny();
        if (installerOptional.isPresent()) {
            int installerIndexOf = IntStream.range(0, assIdTypes.size())
                    .filter(i -> installerOptional.get().equals(installers.get(i)))
                    .findFirst().orElse(0);
            String value = searchForValue(installerOptional.get().getInstallerName(), "select#mainContentPlaceHolder_cb_INSTALLER");
            executeJavascript(javascriptExecutor, installerOptional.get().getInstallerName(),
                    value, HtmlTag.INSTALLER_NAME, "13", installerIndexOf);

        }

        type = new TypeToken<List<BDAid>>() {
        }.getType();
        List<BDAid> bdaAids = JsonUtils.fromJSONTOGivenFile(assetDir, "bdaid", type);
        Optional<BDAid> bdaIDOptional = bdaAids.stream().filter(b -> b.getBdaIdName().equalsIgnoreCase(
                solarForm.getGeneralTechnical().getBdaId()
        )).findAny();
        if (bdaIDOptional.isPresent()) {
            int bdaIdIndexOf = IntStream.range(0, bdaAids.size())
                    .filter(i -> bdaIDOptional.get().equals(bdaAids.get(i)))
                    .findFirst().orElse(0);
            String value = searchForValue(bdaIDOptional.get().getBdaIdName(), "select#mainContentPlaceHolder_cb_BDA_ID");
            String id = searchForId(bdaIDOptional.get().getBdaIdName(), "div#mainContentPlaceHolder_cb_BDA_ID_chzn");
            doDropDownAutomation(HtmlTag.BDA_ID_DIV_ID, id);
            executeJavascript(javascriptExecutor, bdaIDOptional.get().getBdaIdName(),
                    value, HtmlTag.BDA_ID_DIV, "10", bdaIdIndexOf);

        }
        type = new TypeToken<List<AssId>>() {
        }.getType();
        List<AssId> assIds = JsonUtils.fromJSONTOGivenFile(assetDir, "assid", type);
        Optional<AssId> assIdOptional = assIds.stream().filter(a -> a.getAssIdName().equalsIgnoreCase(
                solarForm.getGeneralTechnical().getAssId()
        )).findAny();
        if (assIdOptional.isPresent()) {
            //int assIdIndexOf = IntStream.range(0, bdaAids.size())
            // .filter(i -> assIdOptional.get().equals(bdaAids.get(i)))
            //.findFirst().orElse(0);
            String id = searchForId(assIdOptional.get().getAssIdName(), "div#mainContentPlaceHolder_cb_ASS_ID_chzn");
            doDropDownAutomation(HtmlTag.ASS_ID_DIV, id);
            //executeJavascript(javascriptExecutor, assIdOptional.get().getAssIdName(),
            //assIdOptional.get().getAssIdId(), HtmlTag.ASS_ID_TYPE, "12", assIdIndexOf);

        }
    }

    private void doPanelTechnicalDropDown(SolarForm solarForm) throws Exception {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
        Type type = new TypeToken<List<PanelManufacturer>>() {
        }.getType();
        List<PanelManufacturer> manufacturers = JsonUtils.fromJSONTOGivenFile(assetDir, "panelManufacturer", type);
        Optional<PanelManufacturer> manufacturer = manufacturers.stream().filter(m -> m.getPanelManufacturerName()
                .equalsIgnoreCase(solarForm.getPanelTechnical().getManufacturer())).findFirst();
        if (manufacturer.isPresent()) {
//            int panelManufacturerIndexOf = IntStream.range(0, manufacturers.size())
//                    .filter(i -> manufacturer.get().equals(manufacturers.get(i)))
//                    .findFirst().orElse(0);
            String id = searchForPanel(manufacturer.get().getPanelManufacturerName());
            doDropDownAutomation(HtmlTag.PANEL_MANUFACTURER_DIV, id);
            //executeJavascript(javascriptExecutor, manufacturer.get().getPanelManufacturerName(),
            //manufacturer.get().getPanelManufacturerId(), HtmlTag.PANEL_MANUFACTURER, "14", panelManufacturerIndexOf);

        }
        type = new TypeToken<List<PanelModel>>() {
        }.getType();
        List<PanelModel> panelModels = JsonUtils.fromJSONTOGivenFile(assetDir, "panelModel", type);
        Optional<PanelModel> paneOptional = panelModels.stream().filter(m -> m.getPanelModelName().equals(solarForm.
                getPanelTechnical().getModel()))
                .findAny();
        if (paneOptional.isPresent()) {
            int panelModelIndexOf = IntStream.range(0, panelModels.size())
                    .filter(i -> paneOptional.get().equals(panelModels.get(i)))
                    .findFirst().orElse(0);
            String id = searchForPanelModel(paneOptional.get().getPanelModelName());
            String value = searchForPanelModelWithSelectTag(paneOptional.get().getPanelModelName());
            doDropDownAutomation("mainContentPlaceHolder_cb_SP_MODEL_ID_chzn", id);
            executeJavascript(javascriptExecutor, paneOptional.get().getPanelModelName(),
                    value, HtmlTag.PANEL_MODEL_DIV, "15", panelModelIndexOf);

        }
    }

    private void doBatteryTechnicalDropDown(SolarForm solarForm) throws InterruptedException, Exception {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
        Type type = new TypeToken<List<BatteryManufacturer>>() {
        }.getType();
        List<BatteryManufacturer> batteryManufacturers = JsonUtils.fromJSONTOGivenFile(assetDir, "batteryManufacturer", type);
        Optional<BatteryManufacturer> battery = batteryManufacturers.stream().filter(m -> m.getBatteryManufacturerName().equals(solarForm.
                getBatteryTechnical().getManufacturer()))
                .findAny();
        if (battery.isPresent()) {
            int batteryManufacutererIndexOf = IntStream.range(0, batteryManufacturers.size())
                    .filter(i -> battery.get().equals(batteryManufacturers.get(i)))
                    .findFirst().orElse(0);
//            executeJavascript(javascriptExecutor, battery.get().getBatteryManufacturerName(),
//                    battery.get().getBatteryManufacturerId(), HtmlTag.BATTERY_MANUFACTURER_ID, "16", batteryManufacutererIndexOf);
//            
            String id = searchForBatteryManufacturer(battery.get().getBatteryManufacturerName());
            doDropDownAutomation(HtmlTag.BATTERY_MANUFACTURER_ID_DIV, id);
        }

        type = new TypeToken<List<BatteryModel>>() {
        }.getType();
        List<BatteryModel> batteryModels = JsonUtils.fromJSONTOGivenFile(assetDir, "batteryModel", type);
        Optional<BatteryModel> batteryModel = batteryModels.stream().filter(m -> m.getBatteryModelName().equals(solarForm.
                getBatteryTechnical().getModel()))
                .findAny();
        if (batteryModel.isPresent()) {
            int batteryModelIndexOf = IntStream.range(0, batteryModels.size())
                    .filter(i -> batteryModel.get().equals(batteryModels.get(i)))
                    .findFirst().orElse(0);
            String id = searchForBatteryModel(batteryModel.get().getBatteryModelName());
            String value = searchForBatteryModelValue(batteryModel.get().getBatteryModelName());
            executeJavascript(javascriptExecutor, batteryModel.get().getBatteryModelName(),
                    batteryModel.get().getBatteryModelId(), HtmlTag.BATTERY_MODEL, "17", batteryModelIndexOf);
            doDropDownAutomation("mainContentPlaceHolder_cb_BT_MODEL_ID_chzn", id);

        }

        type = new TypeToken<List<BatteryType>>() {
        }.getType();
        List<BatteryType> batteryTypes = JsonUtils.fromJSONTOGivenFile(assetDir, "batterytype", type);
        Optional<BatteryType> batteryTypeOptional = batteryTypes.stream().filter(m -> m.getBatteryTypeName().equals(solarForm.
                getBatteryTechnical().getType()))
                .findAny();
        if (batteryTypeOptional.isPresent()) {
            int batteryTypeIndexOf = IntStream.range(0, batteryTypes.size())
                    .filter(i -> batteryTypeOptional.get().equals(batteryTypes.get(i)))
                    .findFirst().orElse(0);
            String value = searchForValue(batteryTypeOptional.get().getBatteryTypeName(), "select#mainContentPlaceHolder_cb_BT_TYPE");
            executeJavascript(javascriptExecutor, batteryTypeOptional.get().getBatteryTypeName(),
                    value, HtmlTag.BATTERY_TYPE_DIV, "18", batteryTypeIndexOf);

        }
    }

    private void doChargeDropDown(SolarForm solarForm) throws InterruptedException, Exception {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
        Type type = new TypeToken<List<ChargeManufacuturer>>() {
        }.getType();
        List<ChargeManufacuturer> chargeManufacturers = JsonUtils.fromJSONTOGivenFile(assetDir, "chargeControllerManufacturer", type);
        Optional<ChargeManufacuturer> chargeManufacture = chargeManufacturers.stream().filter(m -> m.getChargeControllerManufacturerName().equals(solarForm.
                getChargeControllerTechnical().getManufacturer()))
                .findAny();
        if (chargeManufacture.isPresent()) {
            int chargeControllerIndexOf = IntStream.range(0, chargeManufacturers.size())
                    .filter(i -> chargeManufacture.get().equals(chargeManufacturers.get(i)))
                    .findFirst().orElse(0);
            
            doDropDownAutomation(HtmlTag.CHARGE_MANUFACTURER_DIV, chargeManufacture.get().getChargeControllerManufacturerListId());
            executeJavascript(javascriptExecutor, chargeManufacture.get().getChargeControllerManufacturerName(),
                    chargeManufacture.get().getChargeControllerManufacturerId(), HtmlTag.CHARGE_MANUFACTURER, "19", chargeControllerIndexOf);

        }

        type = new TypeToken<List<ChargeControllerModel>>() {
        }.getType();
        List<ChargeControllerModel> chargeControllerModels = JsonUtils.fromJSONTOGivenFile(assetDir, "chargeControllerModel", type);
        Optional<ChargeControllerModel> chargeModel = chargeControllerModels.stream().filter(m -> m.getChargeControllerModelName().equals(solarForm.
                getChargeControllerTechnical().getModel()))
                .findAny();
        if (chargeModel.isPresent()) {
            int chargeManufactureModelIndexOf = IntStream.range(0, chargeControllerModels.size())
                    .filter(i -> chargeModel.get().equals(chargeControllerModels.get(i)))
                    .findFirst().orElse(0);
            executeJavascript(javascriptExecutor, chargeModel.get().getChargeControllerModelName(),
                    chargeModel.get().getChargeControllerModelId(), HtmlTag.CHARGE_CONTROLLER_MODEL, "20", chargeManufactureModelIndexOf);
        }

    }

    private void doLampDropDown(SolarForm solarForm) throws InterruptedException, Exception {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
        Type type = new TypeToken<List<LampManufacturer>>() {
        }.getType();
        List<LampManufacturer> lampManufacturers = JsonUtils.fromJSONTOGivenFile(assetDir, "lampManufacturer", type);
        Optional<LampManufacturer> lampManufacture = lampManufacturers.stream().filter(m -> m.getLampManufacturerName().equals(solarForm.
                getLampTechnical().getManufacturer()))
                .findAny();
        log.info(JsonUtils.toJsonObj(lampManufacture.get()));
        if (lampManufacture.isPresent()) {
            int indeXOfManufacturerLamp = IntStream.range(0, lampManufacturers.size())
                    .filter(i -> lampManufacture.get().equals(lampManufacturers.get(i)))
                    .findAny().orElse(0);
//            executeJavascript(javascriptExecutor, lampManufacture.get()
//                    .getLampManufacturerName(), lampManufacture.get()
//                            .getLampManufacturerId(), HtmlTag.LAMP_MANUFACTURER, "21", indeXOfManufacturerLamp);
            doDropDownAutomation(HtmlTag.LAMP_MANU_DIV, lampManufacture.get().getLampManufacturerListId());

        }

        type = new TypeToken<List<LampModel>>() {
        }.getType();
        List<LampModel> lampModels = JsonUtils.fromJSONTOGivenFile(assetDir, "lampmodel", type);
        Optional<LampModel> lampModel = lampModels.stream().filter(m -> m.getLampModelName().equals(solarForm.
                getLampTechnical().getModel()))
                .findAny();
        if (lampModel.isPresent()) {
            int indexOfLampModel = IntStream.range(0, lampModels.size())
                    .filter(i -> lampModel.get().equals(lampModels.get(i))).findFirst().orElse(0);
            executeJavascript(javascriptExecutor, lampModel.get().getLampModelName(), lampModel.get().getLampModelId(),
                    HtmlTag.LAMPMODEL_SELECT, "22", indexOfLampModel);
            //doDropDownAutomation(HtmlTag.LAMP_MODEL, lampModel.get().getLampModelListId());
        }
    }

    private void doEndUseDropDown(SolarForm solarForm) throws InterruptedException {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
        Type type = new TypeToken<List<MobileCharge>>() {

        }.getType();
        List<MobileCharge> mobileCharges = JsonUtils.fromJSONTOGivenFile(assetDir, "mobilecharge", type);
        Optional<MobileCharge> mobileCharge = mobileCharges.stream()
                .filter(m -> m.getMobilechargeName().equalsIgnoreCase(solarForm.getEndUse().getMobileChargeOption().toString())).findAny();
        if (mobileCharge.isPresent()) {
            int indexOfMobileCharge = IntStream.range(0, mobileCharges.size())
                    .filter(i -> mobileCharge.get().equals(mobileCharges.get(i)))
                    .findAny().orElse(0);
            executeJavascript(javascriptExecutor, mobileCharge.get()
                    .getMobilechargeName(), mobileCharge.get()
                            .getMobilechargeId(), HtmlTag.MOBILE_CHARGER, "25", indexOfMobileCharge);
        }

        type = new TypeToken<List<Radio>>() {

        }.getType();
        List<Radio> radios = JsonUtils.fromJSONTOGivenFile(assetDir, "radio", type);
        Optional<Radio> radio = radios.stream()
                .filter(m -> m.getRadioName().equalsIgnoreCase(solarForm.getEndUse().getRadio().toString())).findAny();
        if (radio.isPresent()) {
            try {
                doDropDownAutomation(HtmlTag.RADIO_DIV, radio.get().getRadioListId());
            } catch (Exception ex) {
                log.info("ERROR" + ex.getMessage());
            }
        }

        type = new TypeToken<List<BulbType>>() {

        }.getType();
        List<BulbType> bulbTypes = JsonUtils.fromJSONTOGivenFile(assetDir, "bulbtype", type);
        Optional<BulbType> bulbtype = bulbTypes.stream()
                .filter(m -> m.getBulbName().equalsIgnoreCase(solarForm.getEndUse().getTypeOfBulb())).findAny();
        if (bulbtype.isPresent()) {
            int indexOfBulbType = IntStream.range(0, bulbTypes.size())
                    .filter(i -> bulbtype.get().equals(bulbTypes.get(i)))
                    .findAny().orElse(0);
            executeJavascript(javascriptExecutor, bulbtype.get()
                    .getBulbName(), bulbtype.get()
                            .getBulbId(), HtmlTag.BULB_TYPE, "24", indexOfBulbType);
        }

        type = new TypeToken<List<RadioCharge>>() {

        }.getType();
        List<RadioCharge> radioCharges = JsonUtils.fromJSONTOGivenFile(assetDir, "radiocharge", type);
        Optional<RadioCharge> raOptional = radioCharges.stream()
                .filter(m -> m.getRadiochargeName().equalsIgnoreCase(solarForm.getEndUse().getRadioChargeOption().toString())).findAny();
        if (raOptional.isPresent()) {
            int indexOfRadioCharge = IntStream.range(0, radioCharges.size())
                    .filter(i -> raOptional.get().equals(radioCharges.get(i)))
                    .findAny().orElse(0);
            executeJavascript(javascriptExecutor, raOptional.get()
                    .getRadiochargeName(), raOptional.get()
                            .getRadiochargeId(), HtmlTag.RADIO_CHARGE, "26", indexOfRadioCharge);

        }

        type = new TypeToken<List<Television>>() {

        }.getType();
        List<Television> televisions = JsonUtils.fromJSONTOGivenFile(assetDir, "television", type);
        Optional<Television> teOptional = televisions.stream()
                .filter(m -> m.getTelevisionName().equalsIgnoreCase(solarForm.getEndUse().getTelevision().toString())).findAny();
        if (teOptional.isPresent()) {
            int indexOfTelevision = IntStream.range(0, televisions.size())
                    .filter(i -> teOptional.get().equals(televisions.get(i)))
                    .findAny().orElse(0);
            executeJavascript(javascriptExecutor, teOptional.get()
                    .getTelevisionName(), teOptional.get()
                            .getTelevisionId(), HtmlTag.TELEVISION_DIV, "28", indexOfTelevision);
        }
    }

    private String doClickAndMessage() {
        webDriver.findElement(By.id(HtmlTag.FIRST_BTN_SAVED)).click();
        try {
            Thread.sleep(3000);
            return webDriver.findElement(By.id(HtmlTag.MESSAGE_TAG)).getText();
        } catch (Exception e) {
            return doClickAndMessage();
        }

    }

    private String searchForPanel(String panelManufacturerName) {
        Document doc = Jsoup.parse(webDriver.getPageSource());
        Elements elements = doc.select("div#mainContentPlaceHolder_cb_SP_MANU_ID_chzn")
                .select("ul.chzn-results").select("li");
        return elements.stream().filter(element -> element.text().equalsIgnoreCase(panelManufacturerName))
                .findAny().get().id();
    }

    private String searchForPanelModel(String panelModelName) {
        Document doc = Jsoup.parse(webDriver.getPageSource());
        Elements elements = doc.select("div#mainContentPlaceHolder_cb_SP_MODEL_ID_chzn")
                .select("ul.chzn-results").select("li");
        return elements.stream().filter(element -> element.text().equalsIgnoreCase(panelModelName))
                .findAny().get().id();
    }

    private String searchForPanelModelWithSelectTag(String panelModelName) {
        Document doc = Jsoup.parse(webDriver.getPageSource());
        Elements elements = doc.select("select#mainContentPlaceHolder_cb_SP_MODEL_ID")
                .select("option");
        return elements.stream().filter(element -> element.text().equalsIgnoreCase(panelModelName))
                .findAny().get().attr("value");
    }

    private String searchForDistrict(String districtName) {
        Document doc = Jsoup.parse(webDriver.getPageSource());
        Elements elements = doc.select("div#mainContentPlaceHolder_cb_DISTRICT_ID_chzn")
                .select("ul.chzn-results").select("li");
        return elements.stream().filter(element -> element.text().equalsIgnoreCase(districtName))
                .findAny().get().id();
    }

    private String searchForVPNP(String addressName) {
        Document doc = Jsoup.parse(webDriver.getPageSource());
        Elements elements = doc.select("div#mainContentPlaceHolder_cb_VDCNP_ID_chzn")
                .select("ul.chzn-results").select("li");
        return elements.stream().filter(element -> element.text().equalsIgnoreCase(addressName))
                .findAny().get().id();
    }

    private String searchForVPNPPermanent(String addressName) {
        Document doc = Jsoup.parse(webDriver.getPageSource());
        Elements elements = doc.select("div#mainContentPlaceHolder_cb_S_VDCNP_ID_chzn")
                .select("ul.chzn-results").select("li");
        return elements.stream().filter(element -> element.text().equalsIgnoreCase(addressName))
                .findAny().get().id();
    }

    private String searchForBatteryManufacturer(String batteryManufacturerName) {
        Document doc = Jsoup.parse(webDriver.getPageSource());
        Elements elements = doc.select("div#mainContentPlaceHolder_cb_BT_MANU_ID_chzn")
                .select("ul.chzn-results").select("li");
        return elements.stream().filter(element -> element.text().equalsIgnoreCase(batteryManufacturerName))
                .findAny().get().id();
    }

    private String searchForBatteryModel(String batteryModelName) {
        Document doc = Jsoup.parse(webDriver.getPageSource());
        Elements elements = doc.select("div#mainContentPlaceHolder_cb_BT_MODEL_ID_chzn")
                .select("ul.chzn-results").select("li");
        return elements.stream().filter(element -> element.text().equalsIgnoreCase(batteryModelName))
                .findAny().get().id();
    }

    private String searchForBatteryModelValue(String batteryModelName) {
        Document doc = Jsoup.parse(webDriver.getPageSource());
        Elements elements = doc.select("select#mainContentPlaceHolder_cb_BT_MODEL_ID")
                .select("option");
        return elements.stream().filter(element -> element.text().equalsIgnoreCase(batteryModelName))
                .findAny().get().attr("value");

    }

    private String searchForValue(String bdaTypeName, String html) {

        Document doc = Jsoup.parse(webDriver.getPageSource());
        Elements elements = doc.select(html)
                .select("option");
        return elements.stream().filter(element -> element.text().equalsIgnoreCase(bdaTypeName))
                .findAny().get().attr("value");
    }

    private String searchForId(String name, String html) {
        Document doc = Jsoup.parse(webDriver.getPageSource());
        Elements elements = doc.select(html)
                .select("ul.chzn-results").select("li");
        return elements.stream().filter(element -> element.text().equalsIgnoreCase(name))
                .findAny().get().id();
    }
}
