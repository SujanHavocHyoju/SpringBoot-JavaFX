/*
 * To change this license header and  choose License Headers in Project Properties.
 * To change this template file and  choose Tools | Templates
 * and open the template in the editor.
 */
package com.solar.repository;

import com.solar.entity.SolarForm;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author bibek
 */
public interface SolarFormRepository extends JpaRepository<SolarForm , Long>{
    @Query("Select count(a) From SolarForm a where a.isReviewed=false and  "
            + "a.isUploadedToServer=false and  "
            + "a.isAutomationReady=false and  "
            + "a.isWebAutomationSucceed=false")
    int reviewCount();
    @Query("Select count(p) from SolarForm p where p.isFailedToAutomated=true")
    public int failedCount();
    @Query("Select count(p) from SolarForm p where p.isUploadedToServer=true")
    public int uploadedToServerCount();
    public List<SolarForm> findByIsAutomationReady(boolean isAutomationReady);
    @Query("Select a From SolarForm a where a.isReviewed=false and  "
            + "a.isUploadedToServer=false and  "
            + "a.isAutomationReady=false and  "
            + "a.isWebAutomationSucceed=false")
    public List<SolarForm> findAllReview();
    @Query("Select a From SolarForm a where a.isFailedToAutomated=true and a.isUploadedToServer=false")
    public List<SolarForm> failedAutomationList();
    public SolarForm findBySolarId(String solarId);
    public List<SolarForm> findByIsUploadedToServer(boolean isUploadedToServer);
    public List<SolarForm> findByIsFailedToAutomated(boolean b);
    @Query("Select count(a) From SolarForm a where "
            + "a.isWebAutomationSucceed=true")
    public int succeedCount();
    @Query("Select a From SolarForm a where "
            + "a.isWebAutomationSucceed=true")
    public List<SolarForm> findAllSuccessForm();
    @Query("Select count(a) From SolarForm a where "
            + "a.isAutomationReady=true")
    int automationReady();
    @Query("Select a From SolarForm a where "
            + "a.isWebAutomationSucceed=true")
    List<SolarForm> findAllSucceed();
 }
