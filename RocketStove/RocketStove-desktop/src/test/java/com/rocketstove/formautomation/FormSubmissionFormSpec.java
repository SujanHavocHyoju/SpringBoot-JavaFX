package com.rocketstove.formautomation;

import org.junit.Assert;
import org.testng.annotations.Test;

public class FormSubmissionFormSpec {
//    public void shouldUpload() {
//        //List<AggregateForm> aggregateForms = LocalJsonUtils.loadAggregateForm();
//        PropertiesFileLoaderUtils.loadPropertiesFile();
//        aggregateForms.forEach(a -> {
//            try {
//                FormAutomation.uploadAll(a, null);
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (DocumentException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//    }
    @Test
    public void dateTest(){
        boolean test = "01/06/2017".matches("[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}");
        Assert.assertTrue(test);
    }
}
