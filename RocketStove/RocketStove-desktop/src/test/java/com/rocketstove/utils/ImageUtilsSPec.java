/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.utils;

import com.itextpdf.text.DocumentException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.testng.annotations.Test;

/**
 *
 * @author bibek
 */
public class ImageUtilsSPec {
    @Test
    public void test(){
        System.err.println(ImageUtils.spaceBetweenEachLetter("JMC-R-002137"));
    }
    @Test
    public void rocketIdShouldbeAtItpoints() throws IOException, DocumentException {
        String rocketId = "JMC-R-002137";
        String path = "D:\\RocketStove\\sizetest";
        String prefix = "idk";
        File[] files = new File(path).listFiles();
        ImageUtils.toPdf(path, prefix, rocketId, rocketId, new ArrayList<>(Arrays.asList(files)));
    }
}
