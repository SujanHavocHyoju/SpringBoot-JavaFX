/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solar.formautomation;

import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author SUJAN
 */
@Slf4j
@Component
public class Navigation {

    @Value("${card.url}")
    private String cardUrl;

    public WebDriver goToNewCard() {
        try {
            WebDriver webDriver = Selenium.getInstance();
            webDriver.get(cardUrl);
            Thread.sleep(5000);
            WebElement element = webDriver.findElement(By.id(HtmlTag.CREATE_NEW_APPLICATION));
            element.click();

            return webDriver;
        } catch (InterruptedException ex) {
            Logger.getLogger(Navigation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public WebDriver goToSolarCard() {
        log.info("ready to move on to new card");
        try {
            WebDriver webDriver = Selenium.getInstance();
            webDriver.get(cardUrl);
            Thread.sleep(5000);
            return webDriver;
        } catch (InterruptedException ex) {
            log.info(ex.getMessage());
        }
        return null;
    }
}
