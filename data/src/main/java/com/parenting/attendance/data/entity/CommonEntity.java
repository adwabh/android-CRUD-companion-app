package com.parenting.attendance.data.entity;

/**
 * Created by adwait on 21/03/18.
 */

public abstract class CommonEntity {

    protected String responseCode;

    protected String Message;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
