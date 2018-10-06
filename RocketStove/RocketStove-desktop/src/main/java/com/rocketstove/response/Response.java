/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.response;

/**
 *
 * @author SUJAN
 */
public class Response {

    private Status status;
    private String message;
    private boolean isNext;
    private boolean loginFailed;
    private boolean isFailedToAutomate = false;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isNext() {
        return isNext;
    }

    public void setNext(boolean next) {
        isNext = next;
    }

    private Response(Status status, String message, boolean isNext, boolean loginFailed) {
        this.status = status;
        this.message = message;
        this.isNext = isNext;
        this.loginFailed = loginFailed;
    }

    private Response(Status status, String message, boolean isNext) {
        this.status = status;
        this.message = message;
        this.isNext = isNext;
    }
     public static Response automateSuccess() {
        return new Response(Status.SUCCESSFUL, "Automation Success", true);
    }
    public static Response success() {
        return new Response(Status.SUCCESSFUL, "Login Success", true);
    }

    public static Response loginFailed(String message) {
        return new Response(Status.ERROR, message, false, true);
    }

    public static Response error(String message) {
        return new Response(Status.ERROR, message, false);
    }

    public static Response exitWeb(String message) {
        Response response = new Response(Status.AUTOMATIONFAILED, message, false);
        response.setIsFailedToAutomate(true);
        return response;
    }

    public static Response errorOnAutomation(String message) {
        Response response = new Response(Status.AUTOMATIONFAILED, message, true);
        response.setIsFailedToAutomate(true);
        return response;
    }

    public boolean isLoginFailed() {
        return loginFailed;
    }

    public void setLoginFailed(boolean loginFailed) {
        this.loginFailed = loginFailed;
    }

    public boolean isIsFailedToAutomate() {
        return isFailedToAutomate;
    }

    public void setIsFailedToAutomate(boolean isFailedToAutomate) {
        this.isFailedToAutomate = isFailedToAutomate;
    }

}
