/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solar.formautomation;

import com.solar.exception.SolarException;
import com.solar.response.Response;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author SUJAN
 */
@Component
public class LoginFormAutomation {
    @Value("${driver.location}")
    private String driverLocation;
    @Value("${login.username}")
    private String username;
    @Value("${login.password}")
    private String password;
    @Value("${login.url}")
    private String url;
    private static final String LOGIN_ERROR_MESSAGE = "Login failed! Credentials you provided do not match.";

    public Response doLogin() {
        try {
            WebDriver webDriver = Selenium.getInstance(driverLocation);
            webDriver.get(url);
            Thread.sleep(5000);
            WebElement usernameElement = webDriver.findElement(By.id(HtmlTag.USERNAME_INPUT_ID));
            usernameElement.sendKeys(username);
            WebElement passwordElement = webDriver.findElement(By.id(HtmlTag.PASSWORD_INPUT_ID));
            passwordElement.sendKeys(password);
            WebElement loginButton = webDriver.findElement(By.id(HtmlTag.LOGIN_BUTTON_ID));
            loginButton.click();
            
            String msg = webDriver.findElement(By.id("l_Msg")).getText();
            if (StringUtils.isNotBlank(msg)) {
                if (msg.trim().equalsIgnoreCase(LOGIN_ERROR_MESSAGE)) {
                    return Response.loginFailed(LOGIN_ERROR_MESSAGE);
                }
            }
        } catch (SolarException | InterruptedException  |NoSuchElementException e) {
            if (e instanceof SocketException) {
                return Response.loginFailed("Internet connection is very poor. Please try it after some time.");
            }
            if (e instanceof SocketTimeoutException) {
                return Response.loginFailed("Internet connection is very poor. Please try it after some time.");
            }
            if(e instanceof NoSuchElementException){
                return Response.success();
            }
        }
        return Response.success();
    }
}
