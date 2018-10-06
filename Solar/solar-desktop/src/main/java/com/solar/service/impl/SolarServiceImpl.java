/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solar.service.impl;

import com.solar.domain.CameraType;
import com.solar.domain.StarterForm;
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
import com.solar.repository.AttachmentRepo;
import com.solar.repository.BankingRepo;
import com.solar.repository.BatteryTechnicalRepo;
import com.solar.repository.ChargeControllerRepo;
import com.solar.repository.CurrentAddressRepo;
import com.solar.repository.EndUseRepo;
import com.solar.repository.GeneralTechnicalRepo;
import com.solar.repository.InvestmentRepo;
import com.solar.repository.LampTechnicalRepo;
import com.solar.repository.OwnerRepo;
import com.solar.repository.PanelTechnicalRepo;
import com.solar.repository.PermanentAddressRepo;
import com.solar.repository.SolarFormRepository;
import com.solar.service.SolarFormService;
import com.solar.ui.Form;
import com.solar.ui.SubmittedForm;
import com.solar.utils.ImageUtils;
import com.solar.utils.LocalJsonUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author bibek
 */
@Slf4j
@Service
class SolarServiceImpl implements SolarFormService {

    @Value("${google.cloud.download}")
    private String downloadDir;
    @Value("${img_folder}")
    private String imageFolder;
    @Autowired
    private SolarFormRepository solarFormRepository;
    @Autowired
    private AttachmentRepo attachmentRepo;
    @Autowired
    private BankingRepo bankingRepo;
    @Autowired
    private BatteryTechnicalRepo batteryTechnicalRepo;
    @Autowired
    private ChargeControllerRepo chargeControllerRepo;
    @Autowired
    private CurrentAddressRepo currentAddressRepo;
    @Autowired
    private EndUseRepo endUseRepo;
    @Autowired
    private GeneralTechnicalRepo generalTechnicalRepo;
    @Autowired
    private InvestmentRepo investmentRepo;
    @Autowired
    private LampTechnicalRepo lampTechnicalRepo;
    @Autowired
    private OwnerRepo ownerRepo;
    @Autowired
    private PanelTechnicalRepo panelTechnicalRepo;
    @Autowired
    private PermanentAddressRepo permanentAddressRepo;

    @Override
    public boolean saveOrUpdateForm(Optional<SolarForm> solarForm) {
        String solarId = solarForm.get().getSolarId();
        if (solarForm.isPresent()) {
            SolarForm persistSolarForm = solarForm.get();
            if (persistSolarForm.getOwner() != null) {
                Owner retrieveOwner = this.ownerRepo.findBySolarId(solarForm.get().getSolarId());
                if (retrieveOwner != null) {
                    persistSolarForm.getOwner().setId(retrieveOwner.getId());
                }
                this.ownerRepo.save(persistSolarForm.getOwner());
            }
            if (persistSolarForm.getBanking() != null) {
                Banking retrieveBanking = this.bankingRepo.findBySolarId(solarForm.get().getSolarId());
                if (retrieveBanking != null) {
                    persistSolarForm.getBanking().setId(retrieveBanking.getId());
                }
                this.bankingRepo.save(persistSolarForm.getBanking());
            }
            if (persistSolarForm.getBatteryTechnical() != null) {
                BatteryTechnical retrieveBatteryTechnical = this.batteryTechnicalRepo.findBySolarId(solarId);
                if (retrieveBatteryTechnical != null) {
                    persistSolarForm.getBatteryTechnical().setId(retrieveBatteryTechnical.getId());
                }
                this.batteryTechnicalRepo.save(persistSolarForm.getBatteryTechnical());
            }
            if (persistSolarForm.getChargeControllerTechnical() != null) {
                ChargeControllerTechnical chargeControllerTechnical = this.chargeControllerRepo.findBySolarId(solarId);
                if (chargeControllerTechnical != null) {
                    persistSolarForm.getChargeControllerTechnical().setId(chargeControllerTechnical.getId());
                }
                this.chargeControllerRepo.save(persistSolarForm.getChargeControllerTechnical());
            }
            if (persistSolarForm.getCurrentAddress() != null) {
                CurrentAddress currentAddress = this.currentAddressRepo.findBySolarId(solarId);
                if (currentAddress != null) {
                    persistSolarForm.getCurrentAddress().setId(currentAddress.getId());
                }
                this.currentAddressRepo.save(persistSolarForm.getCurrentAddress());
            }
            if (persistSolarForm.getEndUse() != null) {
                EndUse endUse = this.endUseRepo.findBySolarId(solarId);
                if (endUse != null) {
                    persistSolarForm.getEndUse().setId(endUse.getId());
                }
                this.endUseRepo.save(persistSolarForm.getEndUse());
            }
            if (persistSolarForm.getGeneralTechnical() != null) {
                GeneralTechnical generalTechnical = this.generalTechnicalRepo.findBySolarId(solarId);
                if (generalTechnical != null) {
                    persistSolarForm.getGeneralTechnical().setId(generalTechnical.getId());
                }
                this.generalTechnicalRepo.save(persistSolarForm.getGeneralTechnical());
            }
            if (persistSolarForm.getInvestmentEntity() != null) {
                Investment investment = this.investmentRepo.findBySolarId(solarId);
                if (investment != null) {
                    persistSolarForm.getInvestmentEntity().setId(investment.getId());
                }
                this.investmentRepo.save(persistSolarForm.getInvestmentEntity());
            }
            if (persistSolarForm.getLampTechnical() != null) {
                LampTechnical lampTechnical = lampTechnicalRepo.findBySolarId(solarId);
                if (lampTechnical != null) {
                    persistSolarForm.getLampTechnical().setId(lampTechnical.getId());
                }
                this.lampTechnicalRepo.save(persistSolarForm.getLampTechnical());
            }
            if (persistSolarForm.getPanelTechnical() != null) {
                PanelTechnical panelTechnical = this.panelTechnicalRepo.findBySolarId(solarId);
                if (panelTechnical != null) {
                    persistSolarForm.getPanelTechnical().setId(panelTechnical.getId());
                }
                this.panelTechnicalRepo.save(persistSolarForm.getPanelTechnical());
            }
            if (persistSolarForm.getPermanentAddress() != null) {
                PermanentAddress permanentAddress = this.permanentAddressRepo.findBySolarId(solarId);
                if (permanentAddress != null) {
                    persistSolarForm.getPermanentAddress().setId(permanentAddress.getId());
                }
                this.permanentAddressRepo.save(persistSolarForm.getPermanentAddress());

            }
            if (persistSolarForm.getAttachment() != null) {
                Attachment attachment = this.attachmentRepo.findBySolarId(solarId);
                if (attachment != null) {
                    persistSolarForm.getAttachment().setId(attachment.getId());
                }
                this.attachmentRepo.save(persistSolarForm.getAttachment());
            }
            return this.solarFormRepository.save(persistSolarForm) != null;
        }
        return false;
    }

    @Override
    public void processFormByFile(File file) {
      
        log.info("processing files {}",file.getAbsolutePath());
        StarterForm starterForm = LocalJsonUtils.loadStarterForm(file);
        SolarForm solarFormRetrieve = this.solarFormRepository.findBySolarId(starterForm.getSolarId());
        if (solarFormRetrieve == null) {
            SolarForm solarForm = new SolarForm();
            solarForm.setSolarId(starterForm.getSolarId());
            this.solarFormRepository.save(solarForm);
            Attachment attachment = new Attachment();
            downloadFiles(starterForm);
            attachment.setApplicationFrontImage(starterForm.getsAFormFirstImg());
            attachment.setApplicationBackImage(starterForm.getsAFormSecondImg());
            attachment.setBatteryVoucherImage(starterForm.getBatteryVoucherImage());
            attachment.setBillInvoiceImage(starterForm.getBillInvoiceImage());
            attachment.setMuchulkaImage(starterForm.getMuchulkaImage());
            attachment.setSolarId(starterForm.getSolarId());
            this.attachmentRepo
                    .save(attachment);
            FileUtils.deleteQuietly(file);
        }else{
            FileUtils.deleteQuietly(file);
        }
    }

    private void downloadFiles(StarterForm starterForm) {
        if (StringUtils.isNotBlank(starterForm.getsAFormFirstImg())) {
            String applicationFirstFormImage = starterForm.getsAFormFirstImg();
            String applicationSecondFormImage = starterForm.getsAFormSecondImg();
            String solarId = starterForm.getSolarId();
            String pathToUpload = imageFolder + File.separator + solarId;
            String imageFileName = CameraType.APPLICATION_FIRST_FORM.getFileName();
            String secondFileName = CameraType.APPLICATION_SECOND_FORM.getFileName();
            String billingInvoiceName = CameraType.BILLING_INVOICE.getFileName();
            String batteryInvoiceName = CameraType.BATTERY_VOUCHER.getFileName();
            String muchulkaName = CameraType.MUCHULKA.getFileName();
            File subsidyFrontFile, subsidyBackFile, batteryVoucherImageFile, billingInvoiceImageFile,
                    muchulkaImageFileName = null;
            try {
                if (StringUtils.isNotBlank(applicationFirstFormImage)) {
                    subsidyFrontFile = ImageUtils.writeImageEncodeStringFileRotate(pathToUpload, imageFileName, applicationFirstFormImage);
                    starterForm.setsAFormFirstImg(subsidyFrontFile.getAbsolutePath());
                }
                if (StringUtils.isNotBlank(applicationSecondFormImage)) {
                    subsidyBackFile = ImageUtils.writeImageEncodeStringFileRotate(pathToUpload, secondFileName, applicationSecondFormImage);
                    starterForm.setsAFormSecondImg(subsidyBackFile.getAbsolutePath());
                }
                if (StringUtils.isNotBlank(starterForm.getBatteryVoucherImage())) {
                    batteryVoucherImageFile = ImageUtils.writeImageEncodeStringFileRotate(pathToUpload,
                            batteryInvoiceName, starterForm
                                    .getBatteryVoucherImage());
                    starterForm.setBatteryVoucherImage(batteryVoucherImageFile.getAbsolutePath());
                }
                if (StringUtils.isNotBlank(starterForm.getBillInvoiceImage())) {
                    billingInvoiceImageFile = ImageUtils.writeImageEncodeStringFileRotate(pathToUpload,
                            billingInvoiceName,
                            starterForm.getBillInvoiceImage());
                    starterForm.setBillInvoiceImage(billingInvoiceImageFile.getAbsolutePath());
                }
                if (StringUtils.isNotBlank(starterForm.getMuchulkaImage())) {
                    muchulkaImageFileName = ImageUtils.writeImageEncodeStringFileRotate(pathToUpload, muchulkaName, applicationSecondFormImage);
                    starterForm.setMuchulkaImage(muchulkaImageFileName.getAbsolutePath());
                }

            } catch (IOException ex) {
                log.info(ex.getMessage());
            }
        }

    }

    @Override
    public int downloadFile() {
//        File file = new File(downloadDir);
//        File[] files = file.listFiles((File pathname) -> FilenameUtils.getExtension(pathname.getName()).equalsIgnoreCase("json"));
//        return files.length;
        return solarFormRepository.findAll().size();
    }

    @Override
    public int reviewedCount() {
        return this.solarFormRepository.reviewCount();
    }

    @Override
    public int failedCount() {
        return this.solarFormRepository.failedCount();
    }

    @Override
    public int uploadedToServerCount() {
        return this.solarFormRepository.uploadedToServerCount();
    }

    @Override
    public int succeedCount() {
        return this.solarFormRepository.succeedCount();
    }

    @Override
    public int automationReadyCount() {
        return this.solarFormRepository.automationReady();
    }

    @Override
    public List<Form> reviewList() {
        List<Form> forms = new ArrayList<>();
        List<SolarForm> solarForms = this.solarFormRepository
                .findAllReview();
        int i = 0;
        for (SolarForm a : solarForms) {
            if (a != null) {
                String solarId = a.getSolarId();
                if (StringUtils.isNotBlank(solarId)) {
                    Owner owner = this.ownerRepo.findBySolarId(solarId);
                    if (owner != null) {
                        forms.add(new Form(++i, solarId, provideRealValueOrDash(owner.getName()),
                                provideRealValueOrDash(owner.getCitizenShipNumber()),
                                provideRealValueOrDash(owner.getCitizenshipDistrict())
                        ));
                    } else {
                        forms.add(new Form(++i, solarId, "-", "-", "-"));
                    }

                }
            }
        }
        return forms;
    }

    @Override
    public List<SolarForm> automationList() {
        List<SolarForm> solrForms = this.solarFormRepository.findByIsAutomationReady(true);
        List<SolarForm> reSolarForms = new ArrayList<>();
        solrForms.forEach(s -> {
            reSolarForms.add(this.findBySolarId(s.getSolarId()));
        });
        log.info("solar form list size " + reSolarForms.size());
        return reSolarForms;
    }

    @Override
    public List<Form> automationFormList() {
        List<SolarForm> solarForms = this.solarFormRepository.findByIsAutomationReady(true);
        int i = 0;
        List<Form> forms = new ArrayList<>();
        for (SolarForm a : solarForms) {
            if (a != null) {
                String solarId = a.getSolarId();
                if (StringUtils.isNotBlank(solarId)) {
                    Owner owner = this.ownerRepo.findBySolarId(solarId);
                    if (owner != null) {
                        forms.add(new Form(++i, solarId, provideRealValueOrDash(owner.getName()),
                                provideRealValueOrDash(owner.getCitizenShipNumber()),
                                provideRealValueOrDash(owner.getCitizenshipDistrict())
                        ));
                    } else {

                        if (a.isWebAutomationSucceed()) {
                            this.webAutomationSucceed(a);
                        } else {
                            this.reviewedFormOnceAgain(a);
                        }

                    }

                }
            }
        }
        return forms;
    }

    @Override
    public List<SolarForm> uploadedToServer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SubmittedForm> succeedList() {
        log.info("preparing submitted list.");
        List<SolarForm> solarForms = this.solarFormRepository.findAllSucceed();
        List<SubmittedForm> submittedForms = new ArrayList<>();
        int i = 0;
        for (SolarForm solarForm : solarForms) {
            Owner owner = this.ownerRepo.findBySolarId(solarForm.getSolarId());
            if (owner != null) {
                submittedForms.add(new SubmittedForm(++i, solarForm.getSolarWebId(),
                        solarForm.getSolarId(),
                        owner.getName(), owner.getCitizenShipNumber(),
                        owner.getCitizenshipDistrict()));
            }
        }
        log.info("submitted form size :" + solarForms.size());
        return submittedForms;
    }

    @Override
    public List<Form> failedAutomationList() {
        List<Form> forms = new ArrayList<>();

        List<SolarForm> solarForms = this.solarFormRepository.findByIsFailedToAutomated(true);
        int i = 0;
        for (SolarForm a : solarForms) {
            if (a != null) {
                String solarId = a.getSolarId();
                if (StringUtils.isNotBlank(solarId)) {
                    Owner owner = this.ownerRepo.findBySolarId(solarId);
                    if (owner != null) {
                        forms.add(new Form(++i, solarId, provideRealValueOrDash(owner.getName()),
                                provideRealValueOrDash(owner.getCitizenShipNumber()),
                                provideRealValueOrDash(owner.getCitizenshipDistrict())
                        ));
                    } else {
                        forms.add(new Form(++i, solarId, "-", "-", "-"));
                    }

                }
            }
        }
        return forms;
    }

    @Override
    public SolarForm findBySolarId(String solarId) {
        SolarForm solarForm = this.solarFormRepository.findBySolarId(solarId);
        solarForm.setOwner(this.ownerRepo.findBySolarId(solarId));
        solarForm.setCurrentAddress(this.currentAddressRepo.findBySolarId(solarId));
        solarForm.setPermanentAddress(this.permanentAddressRepo.findBySolarId(solarId));
        solarForm.setGeneralTechnical(this.generalTechnicalRepo.findBySolarId(solarId));
        solarForm.setPanelTechnical(this.panelTechnicalRepo.findBySolarId(solarId));
        solarForm.setBatteryTechnical(this.batteryTechnicalRepo.findBySolarId(solarId));
        solarForm.setChargeControllerTechnical(this.chargeControllerRepo.findBySolarId(solarId));
        solarForm.setLampTechnical(this.lampTechnicalRepo.findBySolarId(solarId));

        Attachment attachment = this.attachmentRepo.findBySolarId(solarId);
        Investment investment = this.investmentRepo.findBySolarId(solarId);
        Banking banking = this.bankingRepo.findBySolarId(solarId);
        EndUse endUse = this.endUseRepo.findBySolarId(solarId);
        solarForm.setBanking(banking);
        solarForm.setEndUse(endUse);
        solarForm.setInvestmentEntity(investment);
        solarForm.setAttachment(attachment);
        return solarForm;
    }

    @Override
    public void deleteBySolarId(String solarId) {
        SolarForm solarForm = this.findBySolarId(solarId);
        if (solarForm != null) {
            this.solarFormRepository.delete(solarForm);
            this.ownerRepo.delete(solarForm.getOwner());
            this.currentAddressRepo.delete(solarForm.getCurrentAddress());
            this.permanentAddressRepo.delete(solarForm.getPermanentAddress());
            this.generalTechnicalRepo.delete(solarForm.getGeneralTechnical());
            this.panelTechnicalRepo.delete(solarForm.getPanelTechnical());
            this.batteryTechnicalRepo.delete(solarForm.getBatteryTechnical());
            this.chargeControllerRepo.delete(solarForm.getChargeControllerTechnical());
            this.lampTechnicalRepo.delete(solarForm.getLampTechnical());
            this.attachmentRepo.delete(solarForm.getAttachment());
            this.investmentRepo.delete(solarForm.getInvestmentEntity());
            this.bankingRepo.delete(solarForm.getBanking());
            this.endUseRepo.delete(solarForm.getEndUse());
        }

    }

    @Override
    public void reviewedFormOnceAgain(SolarForm solarForm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void webAutomationSucceed(SolarForm solarForm) {
        solarForm.setWebAutomationSucceed(true);
        solarForm.setMessage(null);
        solarForm.setAutomationReady(false);
        solarForm.setReviewed(true);
        solarForm.setFailedToAutomated(false);
        solarForm.setFailedAt(0);
        solarForm.setUploadedToServer(true);
        solarForm.setCompleted(true);
        log.info("web automation success");
        SolarForm retrieveSolarForm = this.solarFormRepository.findBySolarId(solarForm.getSolarId());
        if (retrieveSolarForm != null) {
            solarForm.setId(retrieveSolarForm.getId());
            this.solarFormRepository.save(solarForm);
        }
    }

    @Override
    public void failedToAutomation(SolarForm solarForm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean passedFirstForm(SolarForm solarForm) {
        SolarForm retrieveSolarForm = this.solarFormRepository.findBySolarId(solarForm.getSolarId());
        solarForm.setId(retrieveSolarForm.getId());
        return this.solarFormRepository.save(solarForm) != null;
    }

    @Override
    public void failedAutomationAtFirstPage(SolarForm solarForm) {
        solarForm.setFailedAt(0);
        solarForm.setFailedToAutomated(true);
        solarForm.setReviewed(true);
        solarForm.setAutomationReady(false);
        solarForm.setSolarWebHtmlId(null);
        solarForm.setSolarWebId(null);
        this.solarFormRepository.save(solarForm);
    }

    @Override
    public void failedAutomationAtAttachment(SolarForm solarForm) {
        if (solarForm.getMessage() == null) {
            solarForm.setMessage("Image is missing or corrupted, please review");
        }
        solarForm.setReviewed(true);
        solarForm.setFailedToAutomated(true);
        solarForm.setUploadedToServer(true);
        solarForm.setFailedAt(1);
        solarForm.setAutomationReady(false);
        solarForm.setWebAutomationSucceed(false);
        this.saveOrUpdateForm(Optional.of(solarForm));
    }

    @Override
    public void failedAutomationAtSecondForm(SolarForm solarForm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void failedAutomationAtSecondServerWith404(SolarForm solarForm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String provideRealValueOrDash(String value) {
        if (StringUtils.isNotBlank(value)) {
            return value;
        } else {
            return "-";
        }
    }

    @Override
    public boolean saveImages(Attachment attachment) {
        return this.attachmentRepo.save(attachment) != null;
    }

}
