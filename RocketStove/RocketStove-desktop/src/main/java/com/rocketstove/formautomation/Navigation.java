/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.formautomation;

import com.rocketstove.html.HtmlTag;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author SUJAN
 */
public class Navigation {

    public static WebDriver doNewApplication(String url, boolean isNew) {
        try {
            WebDriver webDriver = Selenium.getInstance();
            webDriver.get(url);
            Thread.sleep(5000);
            if (isNew) {
                WebElement element = webDriver.findElement(By.id(HtmlTag.CREATE_NEW_APPLICATION));

                element.click();
            }
            return webDriver;
        } catch (InterruptedException ex) {
            Logger.getLogger(Navigation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public static void doNewApplication(WebDriver webDriver, String url) {
        webDriver.get(url);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Navigation.class.getName()).log(Level.SEVERE, null, ex);
        }
        WebElement element = webDriver.findElement(By.id(HtmlTag.CREATE_NEW_APPLICATION));
        element.click();
    }
}
