package com.decimalab.sff.response;

import com.decimalab.sff.model.Division;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DivisionResponse {
    @SerializedName("status")
    private Boolean status;
    @SerializedName("code")
    private Long code;
    @SerializedName("message")
    private List<String> message;
    @SerializedName("data")
    private List<Division> data;

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


    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }


    public List<Division> getData() {
        return data;
    }

    public void setData(List<Division> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DivisionResponse{" +
                "status=" + status +
                ", code=" + code +
                ", message=" + message +
                ", data=" + data +
                '}';
    }
}
