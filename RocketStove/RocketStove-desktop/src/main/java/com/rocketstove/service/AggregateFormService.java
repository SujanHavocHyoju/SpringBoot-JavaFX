/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.service;

import com.rocketstove.domain.AggregateForm;
import com.rocketstove.domain.StarterForm;
import com.rocketstove.ui.Form;
import com.rocketstove.ui.SubmittedForm;
import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author bibek
 */
public interface AggregateFormService {
    boolean saveOrUpdateForm(Optional<AggregateForm> aggregateForm);
    void processFormByFile(File file);
    int downloadFile();
    int reviewedCount();
    int failedCount();
    int uploadedToServerCount();
    int succeedCount();
    int automationReadyCount();
    List<Form> reviewList();
    List<AggregateForm> automationList();
    List<Form> automationFormList();
    List<AggregateForm> uploadedToServer();
    List<SubmittedForm> succeedList();
    List<Form> failedAutomationList();
    AggregateForm findByRocketId(String rocketId);
    void deleteByRocketId(String rocketId);
    void reviewedFormOnceAgain(AggregateForm aggregateForm);
    void webAutomationSucceed(AggregateForm  aggregateForm);
    void failedToAutomation(AggregateForm aggregateForm);
    void passedFirstForm(AggregateForm aggregateForm);
    void failedAutomationAtFirstPage(AggregateForm aggregateForm);
    void failedAutomationAtAttachment(AggregateForm aggregateForm);
    void failedAutomationAtSecondForm(AggregateForm aggregateForm);
    void failedAutomationAtSecondServerWith404(AggregateForm aggregateForm);
    boolean saveImages(StarterForm starterForm);
    StarterForm getStarterForm(String rocketId);

    public void unreviewed(String rocketId);
}
