/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.ui;

import com.rocketstove.domain.AggregateForm;
import com.rocketstove.domain.TerminalInformation;
import com.rocketstove.utils.LocalJsonUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author bibek
 */
public class TerminaInformationIntializer {
    
    public static TerminalInformation terminalInformation;
    public static List<AggregateForm> reviewedOrAutomationAggregateForms
            = new ArrayList<>();
    public static List<AggregateForm> uploadedAggregateForms = new ArrayList<>();
    public static List<AggregateForm> failedAutomatedList = new ArrayList<>();
    public static List<AggregateForm> reviewedList = new ArrayList<>();

    public static AggregateForm findByRocketIdWhichFailed(String stoveId) {
        AggregateForm aggregateForm = failedAutomatedList.stream()
                .filter(a -> a.getRocketId().equalsIgnoreCase(stoveId))
                .findAny().orElse(failedAutomatedList.get(0));
        return aggregateForm;
    }

    private TerminaInformationIntializer() {

    }

    private static TerminalInformation getInstance() {
        synchronized (TerminalInformation.class) {
            if (terminalInformation == null) {
                return new TerminalInformation();
            }
        }
        return terminalInformation;
    }

    public static void starter() {
//        try {
//            PropertiesFileLoaderUtils.loadPropertiesFile();
//        } catch (IOException ex) {
//            Logger.getLogger(TerminaInformationIntializer.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public static void login(String url, String username, String password) {
        terminalInformation = getInstance();
        terminalInformation.setLoginUrl(url);
        terminalInformation.setPassword(password);
        terminalInformation.setUsername(username);
    }

//    public static void loadAndDownloadGoogleDrive() {
//        new Thread(() -> {
//            try {
//                //GoogleCloudUtils.downloadedFile();
//                //doFileProcessing();
//            } catch (IOException ex) {
//                Logger.getLogger(TerminaInformationIntializer.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }).start();
//
//    }

    public static void doFileProcessing() {
//        reviewedOrAutomationAggregateForms.clear();
//        uploadedAggregateForms.clear();
//        failedAutomatedList.clear();
//        reviewedList.clear();
        File file = new File(System.getProperty("google.cloud.download"));
        File[] files = file.listFiles((File pathname) -> FilenameUtils.getExtension(pathname.getName()).equalsIgnoreCase("json"));
        for (File fileToAgg : files) {
            AggregateForm aggregateForm
                    = LocalJsonUtils.loadAggregateForm(fileToAgg);
            

//            aggregateForm.setFileName(fileToAgg.getAbsolutePath());
//            if (!aggregateForm.isUploadedToServer() && !aggregateForm.isFailedToAutomated()) {
//                reviewedOrAutomationAggregateForms.add(aggregateForm);
//            }
//            if (aggregateForm.isFailedToAutomated()) {
//                failedAutomatedList.add(aggregateForm);
//            }
//            if (aggregateForm.isUploadedToServer()) {
//                uploadedAggregateForms.add(aggregateForm);
//            }
//            if (aggregateForm.isReviewed()) {
//                reviewedList.add(aggregateForm);
//            }
        }
    }

    public static AggregateForm findByRocketId(String rocketId) {
        return reviewedOrAutomationAggregateForms.stream().filter(a -> a.getRocketId().equalsIgnoreCase(rocketId))
                .findAny().orElse(reviewedOrAutomationAggregateForms.get(0));
    }

    public static void updateAggregateForm(AggregateForm aggregateFormUpdate) {
        AggregateForm aggregateFormOld = reviewedOrAutomationAggregateForms.stream().filter(a -> a.getRocketId().equals(aggregateFormUpdate.getRocketId()))
                .findAny().orElse(reviewedOrAutomationAggregateForms.get(0));
        reviewedOrAutomationAggregateForms.remove(aggregateFormOld);
        reviewedOrAutomationAggregateForms.add(aggregateFormUpdate);
    }

    public static int getDownloadFile() {
        File file = new File(System.getProperty("google.cloud.download"));
        File[] files = file.listFiles((File pathname) -> FilenameUtils.getExtension(pathname.getName()).equalsIgnoreCase("json"));
        return files.length;
    }
}
