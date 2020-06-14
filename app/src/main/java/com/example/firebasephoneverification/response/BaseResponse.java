package com.example.firebasephoneverification.response;

import com.example.firebasephoneverification.model.Student;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseResponse {
    @SerializedName("status")
    private Boolean status;
    @SerializedName("code")
    private Long code;
    @SerializedName("exits")
    private Boolean exits;
    @SerializedName("message")
    private List<String> message;
    @SerializedName("token")
    private String token;

    @SerializedName("data")
    private Student data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Boolean getExits() {
        return exits;
    }

    public void setExits(Boolean exits) {
        this.exits = exits;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Student getData() {
        return data;
    }

    public void setData(Student data) {
        this.data = data;
    }
}
