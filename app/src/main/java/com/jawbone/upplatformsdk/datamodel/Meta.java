package com.jawbone.upplatformsdk.datamodel;

import com.google.gson.annotations.SerializedName;

public class Meta {
    @SerializedName("code")
    public Integer code;
    @SerializedName("error_detail")
    public String errorDetail;
    @SerializedName("error_type")
    public String errorType;
    @SerializedName("error_user_msg")
    public String errorUserMsg;
    @SerializedName("message")
    public String message;
    @SerializedName("time")
    public Integer time;
    @SerializedName("user_xid")
    public String userXid;

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserXid() {
        return this.userXid;
    }

    public void setUserXid(String userXid) {
        this.userXid = userXid;
    }

    public Integer getTime() {
        return this.time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getErrorType() {
        return this.errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorDetail() {
        return this.errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    public String getErrorUserMsg() {
        return this.errorUserMsg;
    }

    public void setErrorUserMsg(String errorUserMsg) {
        this.errorUserMsg = errorUserMsg;
    }
}
