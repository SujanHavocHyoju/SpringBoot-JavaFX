/*
 * To change this license header and  choose License Headers in Project Properties.
 * To change this template file and  choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.repository;

import com.rocketstove.entity.AggregateFormEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author bibek
 */
public interface AggregateFormRespository extends JpaRepository<AggregateFormEntity , Long>{
    @Query("Select count(a) From AggregateFormEntity a where a.isReviewed=false and  "
            + "a.isUploadedToServer=false and  "
            + "a.isAutomationReady=false and  "
            + "a.isWebAutomationSucceed=false")
    int reviewCount();
    @Query("Select count(p) from AggregateFormEntity p where p.isFailedToAutomated=true")
    public int failedCount();
    @Query("Select count(p) from AggregateFormEntity p where p.isUploadedToServer=true")
    public int uploadedToServerCount();
    public List<AggregateFormEntity> findByIsAutomationReady(boolean isAutomationReady);
    @Query("Select a From AggregateFormEntity a where a.isReviewed=false and  "
            + "a.isUploadedToServer=false and  "
            + "a.isAutomationReady=false and  "
            + "a.isWebAutomationSucceed=false")
    public List<AggregateFormEntity> findAllReview();
    @Query("Select a From AggregateFormEntity a where a.isFailedToAutomated=true and a.isUploadedToServer=false")
    public List<AggregateFormEntity> failedAutomationList();
    public AggregateFormEntity findByRocketId(String rocketId);
    public List<AggregateFormEntity> findByIsUploadedToServer(boolean isUploadedToServer);
    public List<AggregateFormEntity> findByIsFailedToAutomated(boolean b);
    @Query("Select count(a) From AggregateFormEntity a where "
            + "a.isWebAutomationSucceed=true")
    public int succeedCount();
    @Query("Select a From AggregateFormEntity a where "
            + "a.isWebAutomationSucceed=true")
    public List<AggregateFormEntity> findAllSuccessForm();
    @Query("Select count(a) From AggregateFormEntity a where "
            + "a.isAutomationReady=true")
    int automationReady();
    @Query("Select a From AggregateFormEntity a where "
            + "a.isWebAutomationSucceed=true")
    List<AggregateFormEntity> findAllSucceed();
 }
