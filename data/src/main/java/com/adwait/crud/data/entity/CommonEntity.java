package com.adwait.crud.data.entity;

/**
 * Created by adwait on 21/03/18.
 */

public abstract class CommonEntity {

    protected String responseCode;

    protected String message;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public interface ResponseCodes {
        String OK = "200";
    }
}
