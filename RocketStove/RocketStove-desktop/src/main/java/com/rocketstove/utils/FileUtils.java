/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.utils;

import java.io.File;
import java.util.List;

/**
 *
 * @author bibek
 */
public class FileUtils {

    private static long calculateDirectory(File directory) {
        return directory.length();
    }

    public static double calculateDirectoryInMegaBytes(File directory) {
        return format(calculateDirectory(directory));
    }

    private static double format(double bytes) {
        return bytes/(1024*1024);
    }
    public static void listf(String directoryName, List<File> files) {
        File directory = new File(directoryName);
        // Get all the files from a directory.
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()&&file.getName().endsWith(".json")) {
                files.add(file);
            } else if (file.isDirectory()) {
                listf(file.getAbsolutePath(), files);
            }
        }
    }
}
