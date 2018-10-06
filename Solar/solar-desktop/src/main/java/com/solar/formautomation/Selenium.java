package com.solar.formautomation;

import com.solar.exception.SolarException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class Selenium {
    private static volatile WebDriver webDriver;
    private Selenium() {
    }

    public static WebDriver getInstance() throws SolarException{
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
    public static WebDriver getInstance(String driverLocation) throws SolarException{
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
