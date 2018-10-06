/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.service.impl;

import com.google.gson.reflect.TypeToken;
import com.rocketstove.domain.AggregateForm;
import com.rocketstove.domain.Ethnicity;
import com.rocketstove.domain.StarterForm;
import com.rocketstove.entity.AggregateFormEntity;
import com.rocketstove.entity.CurrentAddressEntity;
import com.rocketstove.entity.GeneralInformationEntity;
import com.rocketstove.entity.InvestmentEntity;
import com.rocketstove.entity.PermanentAddressEntity;
import com.rocketstove.entity.StarterFormEntity;
import com.rocketstove.entity.TechnicalEntity;
import com.rocketstove.mapper.AggregateFormMapper;
import com.rocketstove.mapper.CurrentAddressMapper;
import com.rocketstove.mapper.GeneralInformationMapper;
import com.rocketstove.mapper.InvestmentMapper;
import com.rocketstove.mapper.PermanentAddressMapper;
import com.rocketstove.mapper.StarterFormMapper;
import com.rocketstove.mapper.TechnicalMapper;
import com.rocketstove.repository.AggregateFormRespository;
import com.rocketstove.repository.CurrentAddressRespository;
import com.rocketstove.repository.GeneralInformationRespository;
import com.rocketstove.repository.InvestmentRepository;
import com.rocketstove.repository.PermanentAddressRepository;
import com.rocketstove.repository.StarterFormRepository;
import com.rocketstove.repository.TechnicalRepository;
import com.rocketstove.service.AggregateFormService;
import com.rocketstove.ui.Form;
import com.rocketstove.ui.SubmittedForm;
import com.rocketstove.utils.JsonUtils;
import com.rocketstove.utils.LocalJsonUtils;
import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
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
class AggregateFormServiceImpl implements AggregateFormService {

    @Value("${asset_dir}")
    private String assetDir;
    @Value("${google.cloud.download}")
    private String downloadDir;
    @Autowired
    private AggregateFormRespository aggregateFormRespository;
    @Autowired
    private StarterFormRepository starterFormRepository;
    @Autowired
    private AggregateFormMapper aggregateFormMapper;
    @Autowired
    private StarterFormMapper starterFormMapper;
    @Autowired
    private GeneralInformationMapper generalInformationMapper;
    @Autowired
    private GeneralInformationRespository generalInformationRespository;
    @Autowired
    private CurrentAddressRespository currentAddressRespository;
    @Autowired
    private PermanentAddressRepository permanentAddressRepository;
    @Autowired
    private TechnicalRepository technicalRepository;
    @Autowired
    private InvestmentRepository investmentRepository;
    @Autowired
    private CurrentAddressMapper currentAddressMapper;
    @Autowired
    private PermanentAddressMapper permanentAddressMapper;
    @Autowired
    private TechnicalMapper technicalMapper;
    @Autowired
    private InvestmentMapper investmentMapper;

    @Override
    public boolean saveOrUpdateForm(Optional<AggregateForm> aggregateForm) {
        if (aggregateForm.isPresent()) {
            Optional<AggregateFormEntity> aggregateFormEntity
                    = aggregateFormMapper.mapFormToEntity(aggregateForm);
            return this.doSaveForm(aggregateForm.get(), aggregateFormEntity);
        }
        return false;
    }

    @Override
    public void processFormByFile(File file) {
        log.info("processing files {}",file.getAbsolutePath());
        AggregateForm aggregateForm = LocalJsonUtils.loadAggregateForm(file);
        Optional<AggregateFormEntity> aggregateFormEntity
                = aggregateFormMapper.mapFormToEntityFromFile(file);
        this.doSaveForm(aggregateForm, aggregateFormEntity);
    }

    @Override
    public int downloadFile() {
//        File file = new File(downloadDir);
//        File[] files = file.listFiles((File pathname) -> FilenameUtils.getExtension(pathname.getName()).equalsIgnoreCase("json"));
//        return files.length;
        return aggregateFormRespository.findAll().size();
    }

    @Override
    public int reviewedCount() {
        return this.aggregateFormRespository.reviewCount();
    }

    @Override
    public int failedCount() {
        return this.aggregateFormRespository.failedCount();
    }

    @Override
    public int uploadedToServerCount() {
        return this.aggregateFormRespository.uploadedToServerCount();
    }

    @Override
    public int succeedCount() {
        return this.aggregateFormRespository.succeedCount();
    }

    @Override
    public int automationReadyCount() {
        return this.aggregateFormRespository.automationReady();
    }

    @Override
    public List<Form> reviewList() {
        List<Form> forms = new ArrayList<>();
        List<AggregateFormEntity> aggregateFormEntitys = this.aggregateFormRespository
                .findAllReview();
        int i = 0;
        for (AggregateFormEntity a : aggregateFormEntitys) {
            if (a != null) {
                String rocketId = a.getRocketId();
                if (StringUtils.isNotBlank(rocketId)) {
                    GeneralInformationEntity generalInformationEntity = this.generalInformationRespository.findByRocketId(rocketId);
                    if (generalInformationEntity != null) {
                        forms.add(new Form(++i, rocketId, provideRealValueOrDash(generalInformationEntity.getOwnerName()),
                                provideRealValueOrDash(generalInformationEntity.getCitizenShip()),
                                provideRealValueOrDash(generalInformationEntity.getCitizenDistrict())
                        ));
                    } else {
                        forms.add(new Form(++i, rocketId, "-", "-", "-"));
                    }

                }
            }
        }
        return forms;
    }

    @Override
    public void passedFirstForm(AggregateForm aggregateForm) {
        aggregateForm.setUploadedToServer(true);
        aggregateForm.setReviewed(true);
        aggregateForm.setFailedToAutomated(false);
        aggregateForm.setIsWebAutomationSucceed(false);
        aggregateForm.setMessage(null);
        this.saveOrUpdateForm(Optional.of(aggregateForm));
    }

    @Override
    public void failedToAutomation(AggregateForm aggregateForm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void failedAutomationAtFirstPage(AggregateForm aggregateForm) {
        aggregateForm.setRocketWebHtmlId(null);
        aggregateForm.setRocketWebId(null);
        aggregateForm.setReviewed(true);
        aggregateForm.setUploadedToServer(false);
        aggregateForm.setIsAutomationReady(false);
        aggregateForm.setIsFailedToAutomated(true);
        aggregateForm.setIsWebAutomationSucceed(false);
        this.saveOrUpdateForm(Optional.of(aggregateForm));
    }

    @Override
    public void failedAutomationAtAttachment(AggregateForm aggregateForm) {
        if (aggregateForm.getMessage() == null) {
            aggregateForm.setMessage("Image is missing or corrupted, please review");
        }
        aggregateForm.setIsReviewed(true);
        aggregateForm.setIsFailedToAutomated(true);
        aggregateForm.setIsUploadedToServer(true);
        aggregateForm.setFailedAt(1);
        aggregateForm.setIsAutomationReady(false);
        aggregateForm.setIsWebAutomationSucceed(false);
        this.saveOrUpdateForm(Optional.of(aggregateForm));
    }

    @Override
    public void failedAutomationAtSecondForm(AggregateForm aggregateForm) {
        aggregateForm.setIsReviewed(true);
        aggregateForm.setIsFailedToAutomated(true);
        aggregateForm.setIsUploadedToServer(true);
        aggregateForm.setFailedAt(2);
        aggregateForm.setIsAutomationReady(false);
        aggregateForm.setIsWebAutomationSucceed(false);
        this.saveOrUpdateForm(Optional.of(aggregateForm));
    }

    @Override
    public void failedAutomationAtSecondServerWith404(AggregateForm aggregateForm) {
        aggregateForm.setMessage("SAMS server is currently under alot of pressure. Try again later");
        aggregateForm.setIsReviewed(true);
        aggregateForm.setIsFailedToAutomated(true);
        aggregateForm.setIsUploadedToServer(true);
        aggregateForm.setFailedAt(2);
        aggregateForm.setIsAutomationReady(false);
        aggregateForm.setIsWebAutomationSucceed(false);
        this.saveOrUpdateForm(Optional.of(aggregateForm));
    }

    @Override
    public List<Form> failedAutomationList() {
        List<Form> forms = new ArrayList<>();

        List<AggregateFormEntity> aggregateFormEntitys = this.aggregateFormRespository.findByIsFailedToAutomated(true);
        int i = 0;
        for (AggregateFormEntity a : aggregateFormEntitys) {
            if (a != null) {
                String rocketId = a.getRocketId();
                if (StringUtils.isNotBlank(rocketId)) {
                    GeneralInformationEntity generalInformationEntity = this.generalInformationRespository.findByRocketId(rocketId);
                    if (generalInformationEntity != null) {
                        forms.add(new Form(++i, rocketId, provideRealValueOrDash(generalInformationEntity.getOwnerName()),
                                provideRealValueOrDash(generalInformationEntity.getCitizenShip()),
                                provideRealValueOrDash(generalInformationEntity.getCitizenDistrict())
                        ));
                    } else {
                        forms.add(new Form(++i, rocketId, "-", "-", "-"));
                    }

                }
            }
        }
        return forms;

    }

    @Override
    public AggregateForm findByRocketId(String rocketId) {
        List<Ethnicity> ethnicityList = LocalJsonUtils.loadEthinicity(assetDir);
        AggregateFormEntity aggregateFormEntity = this.aggregateFormRespository.findByRocketId(rocketId);
        this.doComposition(aggregateFormEntity, aggregateFormEntity.getRocketId(), ethnicityList);
        String jsonStr = JsonUtils.toJsonObj(aggregateFormEntity);
        return JsonUtils.fromJsonToObj(jsonStr, AggregateForm.class);
    }

    private void doComposition(AggregateFormEntity aggregateFormEntity, String rocketId, List<Ethnicity> ethnicityList) {
        StarterFormEntity starterFormEntity = this.starterFormRepository.findByRocketId(rocketId);
        if (starterFormEntity != null) {
            aggregateFormEntity.setStarterForm(starterFormEntity);
            GeneralInformationEntity generalInformationEntity = this.generalInformationRespository.findByRocketId(rocketId);
            if (generalInformationEntity != null) {
                Ethnicity ethnicity = ethnicityList.stream().filter(e
                        -> e.getEthnicityId().equalsIgnoreCase(generalInformationEntity.getEthinicity())
                )
                        .findAny().orElse(ethnicityList.get(0));
                generalInformationEntity.setEthinicity(ethnicity.getEthnicityName());
                aggregateFormEntity.setGeneralForm(generalInformationEntity);
                aggregateFormEntity.setInvestmentForm(this.investmentRepository.findByRocketId(aggregateFormEntity.getRocketId()));
                aggregateFormEntity.setCurrentAddressForm(this.currentAddressRespository.findByRocketId(aggregateFormEntity.getRocketId()));
                aggregateFormEntity.setPermanentAddressForm(this.permanentAddressRepository.findByRocketId(aggregateFormEntity.getRocketId()));
                aggregateFormEntity.setTechnicalForm(this.technicalRepository.findByRocketId(aggregateFormEntity.getRocketId()));
            }
        }

    }

    private boolean doSaveForm(AggregateForm aggregateForm, Optional<AggregateFormEntity> aggregateFormEntity) {
        Optional<StarterFormEntity> starterFormEntity
                = starterFormMapper.
                        mapFormToEntity(Optional.of(aggregateForm.getStarterForm()));
        boolean isSavedOrUpdate = false;
        if (starterFormEntity.isPresent()) {
            StarterFormEntity starterFormEntityPersist = this.starterFormRepository.save(starterFormEntity.get());
            assert starterFormEntityPersist.
                    getRocketId().equals(aggregateFormEntity.get().getRocketId());
            isSavedOrUpdate =true;
        }
        if (aggregateForm.getGeneralForm() != null) {
            Optional<GeneralInformationEntity> generalInformation = generalInformationMapper.mapFormToEntity(Optional.of(aggregateForm.getGeneralForm()));
            if (generalInformation.isPresent()) {
                GeneralInformationEntity generalInformationEntity = this.generalInformationRespository
                        .save(generalInformation.get());
                assert generalInformation.get()
                        .getRocketId().equalsIgnoreCase(generalInformationEntity.getRocketId());
                isSavedOrUpdate =true;
            }
        }
        if (aggregateForm.getCurrentAddressForm() != null) {
            Optional<CurrentAddressEntity> currentAddress = currentAddressMapper.mapFormToEntity(Optional.of(aggregateForm.getCurrentAddressForm()));

            if (currentAddress.isPresent()) {
                CurrentAddressEntity currentAddressEntity = this.currentAddressRespository.save(currentAddress.get());
                assert currentAddress.get()
                        .getRocketId().equalsIgnoreCase(currentAddressEntity.getRocketId());
                isSavedOrUpdate =true;
            }
        }
        if (aggregateForm.getPermanentAddressForm() != null) {
            Optional<PermanentAddressEntity> permanentAddress = permanentAddressMapper.mapFormToEntity(Optional.of(aggregateForm.getPermanentAddressForm()));
            if (permanentAddress.isPresent()) {
                PermanentAddressEntity permanentAddressEntity = this.permanentAddressRepository.save(permanentAddress.get());
                assert permanentAddress.get().getRocketId().equalsIgnoreCase(permanentAddressEntity.getRocketId());
                isSavedOrUpdate =true;
            }
        }
        if (aggregateForm.getTechnicalForm() != null) {
            Optional<TechnicalEntity> technicalForm = technicalMapper.mapFormToEntity(
                    Optional.of(aggregateForm.getTechnicalForm())
            );

            if (technicalForm.isPresent()) {
                TechnicalEntity technicalEntity = this.technicalRepository.save(technicalForm.get());
                assert technicalEntity.getRocketId().equalsIgnoreCase(technicalForm.get().getRocketId());
                isSavedOrUpdate =true;
            }
        }
        if (aggregateForm.getInvestmentForm() != null) {
            Optional<InvestmentEntity> investmentForm = investmentMapper.mapFormToEntity(Optional
                    .of(aggregateForm.getInvestmentForm()));
            if (investmentForm.isPresent()) {
                InvestmentEntity investmentEntity = this.investmentRepository.save(investmentForm.get());
                assert investmentEntity.getRocketId()
                        .equalsIgnoreCase(investmentForm.get().getRocketId());
                isSavedOrUpdate =true;
            }
        }
        if (aggregateFormEntity.isPresent()) {
            AggregateFormEntity persistEntity = this.aggregateFormRespository.save(aggregateFormEntity.get());
            assert persistEntity.getRocketId().equals(aggregateFormEntity.get().getRocketId());
            isSavedOrUpdate =true;
        }
        return isSavedOrUpdate;
        
    }

    @Override
    public List<AggregateForm> automationList() {
        List<Ethnicity> ethnicityList = LocalJsonUtils.loadEthinicity(assetDir);
        List<AggregateFormEntity> aggregateFormEntitys = this.aggregateFormRespository.findByIsAutomationReady(true);
        aggregateFormEntitys.forEach(a -> {
            this.doComposition(a, a.getRocketId(), ethnicityList);
        });
        String jsonStr = JsonUtils.toJsonList(aggregateFormEntitys);
        Type type = new TypeToken<List<AggregateForm>>() {
        }.getType();
        List<AggregateForm> aggregateForms = JsonUtils.fromJsonToList(jsonStr, type);
        return aggregateForms;
    }

    @Override
    public List<Form> automationFormList() {
        List<AggregateFormEntity> aggregateFormEntitys = this.aggregateFormRespository.findByIsAutomationReady(true);
        int i = 0;
        List<Form> forms = new ArrayList<>();
        for (AggregateFormEntity a : aggregateFormEntitys) {
            if (a != null) {
                String rocketId = a.getRocketId();
                if (StringUtils.isNotBlank(rocketId)) {
                    GeneralInformationEntity generalInformationEntity = this.generalInformationRespository.findByRocketId(rocketId);
                    if (generalInformationEntity != null) {
                        forms.add(new Form(++i, rocketId, provideRealValueOrDash(generalInformationEntity.getOwnerName()),
                                provideRealValueOrDash(generalInformationEntity.getCitizenShip()),
                                provideRealValueOrDash(generalInformationEntity.getCitizenDistrict())
                        ));
                    } else {
                        Optional<AggregateForm> aggregateForm = this.aggregateFormMapper.mapEntityToForm(Optional.of(a));
                        if (aggregateForm.isPresent()) {
                            if (a.isIsWebAutomationSucceed()) {
                                this.webAutomationSucceed(aggregateForm.get());
                            } else {
                                this.reviewedFormOnceAgain(aggregateForm.get());
                            }
                        }

                    }

                }
            }
        }
        return forms;
    }

    @Override
    public void reviewedFormOnceAgain(AggregateForm aggregateForm) {
        aggregateForm.setIsWebAutomationSucceed(false);
        aggregateForm.setMessage(null);
        aggregateForm.setIsAutomationReady(false);
        aggregateForm.setIsReviewed(false);
        aggregateForm.setIsFailedToAutomated(false);
        aggregateForm.setIsUploadedToServer(false);
        this.saveOrUpdateForm(Optional.of(aggregateForm));
    }

    @Override
    public void webAutomationSucceed(AggregateForm aggregateForm) {
        aggregateForm.setIsWebAutomationSucceed(true);
        aggregateForm.setMessage(null);
        aggregateForm.setIsAutomationReady(false);
        aggregateForm.setIsReviewed(true);
        aggregateForm.setIsFailedToAutomated(false);
        aggregateForm.setIsUploadedToServer(true);
        aggregateForm.setIsCompleted(true);
        this.saveOrUpdateForm(Optional.of(aggregateForm));
    }

    @Override
    public void deleteByRocketId(String rocketId) {
        AggregateFormEntity aggregateFormEntity = this.aggregateFormRespository.findByRocketId(rocketId);
        this.aggregateFormRespository.delete(aggregateFormEntity);
        StarterFormEntity starterFormEntity = this.starterFormRepository.findByRocketId(rocketId);
        this.starterFormRepository.delete(starterFormEntity);
        GeneralInformationEntity generalFormEntity = this.generalInformationRespository.findByRocketId(rocketId);
        this.generalInformationRespository.delete(generalFormEntity);
        InvestmentEntity investmentEntity = this.investmentRepository.findByRocketId(rocketId);
        this.investmentRepository.delete(investmentEntity);
        CurrentAddressEntity currentAddressEntity = this.currentAddressRespository.findByRocketId(rocketId);
        this.currentAddressRespository.delete(currentAddressEntity);
        PermanentAddressEntity permanentAddressEntity = this.permanentAddressRepository.findByRocketId(rocketId);
        this.permanentAddressRepository.delete(permanentAddressEntity);
        TechnicalEntity technicalEntity = this.technicalRepository.findByRocketId(rocketId);
        this.technicalRepository.delete(technicalEntity);
    }

    @Override
    public List<SubmittedForm> succeedList() {
        List<AggregateFormEntity> aggregateFormEntitys = this.aggregateFormRespository.findAllSucceed();
        List<SubmittedForm> submittedForms = new ArrayList<>();
        int i = 0;
        for (AggregateFormEntity aggregateFormEntity : aggregateFormEntitys) {
            GeneralInformationEntity generalInformationEntity = this.generalInformationRespository.findByRocketId(aggregateFormEntity.getRocketId());
            if (generalInformationEntity != null) {
                submittedForms.add(new SubmittedForm(++i, aggregateFormEntity.getRocketWebId(),
                        aggregateFormEntity.getRocketId(),
                        generalInformationEntity.getOwnerName(), generalInformationEntity.getCitizenShip(),
                        generalInformationEntity.getCitizenDistrict()));
            }
        }
        return submittedForms;
    }

    @Override
    public List<AggregateForm> uploadedToServer() {
        List<Ethnicity> ethnicityList = LocalJsonUtils.loadEthinicity(assetDir);
        List<AggregateFormEntity> aggregateFormEntitys = this.aggregateFormRespository.findByIsUploadedToServer(true);
        aggregateFormEntitys.forEach(a -> {
            if (!a.isIsFailedToAutomated()) {
                this.doComposition(a, a.getRocketId(), ethnicityList);
            }
        });
        String jsonStr = JsonUtils.toJsonList(aggregateFormEntitys);
        Type type = new TypeToken<List<AggregateForm>>() {
        }.getType();
        List<AggregateForm> aggregateForms = JsonUtils.fromJsonToList(jsonStr, type);
        return aggregateForms;
    }

    private String provideRealValueOrDash(String value) {
        if (StringUtils.isNotBlank(value)) {
            return value;
        } else {
            return "-";
        }
    }

    @Override
    public boolean saveImages(StarterForm starterForm) {
        Optional<StarterFormEntity> starterFormEntity = this.starterFormMapper.mapFormToEntity(Optional.of(starterForm));
        if (starterFormEntity.isPresent()) {
            StarterFormEntity st = this.starterFormRepository.saveAndFlush(starterFormEntity.get());
            return st != null;
        }
        return false;
    }

    @Override
    public StarterForm getStarterForm(String rocketId) {
        StarterFormEntity starterFormEntity = this.starterFormRepository.findByRocketId(rocketId);
        return JsonUtils.fromJsonToObj(JsonUtils.toJsonObj(starterFormEntity), StarterForm.class);
    }

    @Override
    public void unreviewed(String rocketId) {
        AggregateFormEntity aggregateForm = this.aggregateFormRespository.findByRocketId(rocketId);
        if(aggregateForm!=null){
            aggregateForm.setIsReviewed(false);
            aggregateForm.setIsAutomationReady(false);
            this.aggregateFormRespository.save(aggregateForm);
        }
    }
    

}
