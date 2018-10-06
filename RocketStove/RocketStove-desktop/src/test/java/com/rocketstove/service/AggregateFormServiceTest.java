/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.service;

import com.rocketstove.drive.GoogleCloudService;
import com.rocketstove.entity.StarterFormEntity;
import com.rocketstove.repository.StarterFormRepository;
import java.io.File;
import java.io.IOException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

/**
 *
 * @author bibek
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AggregateFormServiceTest {
    @Autowired
    private AggregateFormService aggregateFormService;
    @Autowired
    private StarterFormRepository starterFormRepository;
    @Autowired
    private GoogleCloudService googleCloudService;
    //@org.junit.Test
    public void isNotNull() throws IOException{
        assertNotNull(this.aggregateFormService);
        assertNotNull(this.googleCloudService);
        File[] files = new File("/home/bibek/Desktop/rocketstove/files").listFiles();
        assertNotNull(files);
        assertEquals(7, files.length);
        for (File file : files) {
            this.aggregateFormService.processFormByFile(file);
        }
    }
}
