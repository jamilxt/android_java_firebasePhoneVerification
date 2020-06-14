package com.example.firebasephoneverification.request;

import com.example.firebasephoneverification.model.StudentCollege;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentRequest {
    private String name;
    private String phone;
    private String picture;
    private String roll;
    private String reg;
    private String board;
    @SerializedName("passing_year")
    private Long passingYear;
    private String group;
    private String quotes;
    @SerializedName("payment_type")
    private String paymentType;
    @SerializedName("txr_id")
    private String txrId;
    private String amount;
    @SerializedName("college_ids")
    private List<Long> studentColleges;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public Long getPassingYear() {
        return passingYear;
    }

    public void setPassingYear(Long passingYear) {
        this.passingYear = passingYear;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getQuotes() {
        return quotes;
    }

    public void setQuotes(String quotes) {
        this.quotes = quotes;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getTxrId() {
        return txrId;
    }

    public void setTxrId(String txrId) {
        this.txrId = txrId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public List<Long> getStudentColleges() {
        return studentColleges;
    }

    public void setStudentColleges(List<Long> studentColleges) {
        this.studentColleges = studentColleges;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", picture='" + picture + '\'' +
                ", roll='" + roll + '\'' +
                ", reg='" + reg + '\'' +
                ", board='" + board + '\'' +
                ", passingYear=" + passingYear +
                ", group='" + group + '\'' +
                ", quotes='" + quotes + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", txrId='" + txrId + '\'' +
                ", amount='" + amount + '\'' +
                ", studentColleges=" + studentColleges +
                '}';
    }
}
