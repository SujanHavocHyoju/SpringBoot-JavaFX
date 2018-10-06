/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solar.service;

import com.solar.entity.Attachment;
import com.solar.entity.SolarForm;
import com.solar.ui.Form;
import com.solar.ui.SubmittedForm;
import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author bibek
 */
public interface SolarFormService {
    boolean saveOrUpdateForm(Optional<SolarForm> solarForm);
    void processFormByFile(File file);
    int downloadFile();
    int reviewedCount();
    int failedCount();
    int uploadedToServerCount();
    int succeedCount();
    int automationReadyCount();
    List<Form> reviewList();
    List<SolarForm> automationList();
    List<Form> automationFormList();
    List<SolarForm> uploadedToServer();
    List<SubmittedForm> succeedList();
    List<Form> failedAutomationList();
    SolarForm findBySolarId(String solarId);
    void deleteBySolarId(String solarId);
    void reviewedFormOnceAgain(SolarForm solarForm);
    void webAutomationSucceed(SolarForm  solarForm);
    void failedToAutomation(SolarForm aggregateForm);
    boolean passedFirstForm(SolarForm aggregateForm);
    void failedAutomationAtFirstPage(SolarForm solarForm);
    void failedAutomationAtAttachment(SolarForm solarForm);
    void failedAutomationAtSecondForm(SolarForm solarForm);
    void failedAutomationAtSecondServerWith404(SolarForm solarForm);
    boolean saveImages(Attachment attachment);
}
