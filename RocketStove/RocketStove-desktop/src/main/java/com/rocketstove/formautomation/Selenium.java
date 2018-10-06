/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.formautomation;

import com.rocketstove.exception.RocketStoveException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author SUJAN
 */
public class Selenium {
    private static volatile WebDriver webDriver;
    private Selenium() {
    }

    public static WebDriver getInstance() throws RocketStoveException{
        WebDriver webDriver = Selenium.webDriver;
        if (webDriver == null) {
            synchronized (Selenium.class) {
                webDriver = Selenium.webDriver;
                if (webDriver == null) {
                    System.setProperty("webdriver.chrome.driver", System.getProperty("driver.location"));
                    Selenium.webDriver = webDriver = new ChromeDriver();
                }
            }
        }
        return webDriver;
    }
    public static WebDriver getInstance(String driverLocation) throws RocketStoveException{
        WebDriver webdriver = Selenium.webDriver;
        if (webdriver == null) {
            synchronized (Selenium.class) {
                webdriver = Selenium.webDriver;
                if (webdriver == null) {
                    System.setProperty("webdriver.chrome.driver", driverLocation);
                    Selenium.webDriver = webdriver = new ChromeDriver();
                }
            }
        }
        return webdriver;
    }
}
