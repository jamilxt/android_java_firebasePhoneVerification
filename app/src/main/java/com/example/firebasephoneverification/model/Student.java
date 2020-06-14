package com.example.firebasephoneverification.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Student {

    private String amount;
    private String board;
    private List<College> colleges;
    private String group;
    private Long id;
    private String name;
    @SerializedName("passing_year")
    private Long passingYear;
    @SerializedName("payment_type")
    private String paymentType;
    private String phone;
    private String picture;
    private String quotes;
    private String reg;
    private String roll;
    @SerializedName("txr_id")
    private String txrId;


    public Student(String amount, String board, List<College> colleges, String group, Long id, String name, Long passingYear, String paymentType, String phone, String picture, String quotes, String reg, String roll, String txrId) {
        this.amount = amount;
        this.board = board;
        this.colleges = colleges;
        this.group = group;
        this.id = id;
        this.name = name;
        this.passingYear = passingYear;
        this.paymentType = paymentType;
        this.phone = phone;
        this.picture = picture;
        this.quotes = quotes;
        this.reg = reg;
        this.roll = roll;
        this.txrId = txrId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public List<College> getColleges() {
        return colleges;
    }

    public void setColleges(List<College> colleges) {
        this.colleges = colleges;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPassingYear() {
        return passingYear;
    }

    public void setPassingYear(Long passingYear) {
        this.passingYear = passingYear;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
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

    public String getQuotes() {
        return quotes;
    }

    public void setQuotes(String quotes) {
        this.quotes = quotes;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getTxrId() {
        return txrId;
    }

    public void setTxrId(String txrId) {
        this.txrId = txrId;
    }


    @Override
    public String toString() {
        return "Data{" +
                "amount='" + amount + '\'' +
                ", board='" + board + '\'' +
                ", colleges=" + colleges +
                ", group='" + group + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", passingYear=" + passingYear +
                ", paymentType='" + paymentType + '\'' +
                ", phone='" + phone + '\'' +
                ", picture='" + picture + '\'' +
                ", quotes='" + quotes + '\'' +
                ", reg='" + reg + '\'' +
                ", roll='" + roll + '\'' +
                ", txrId='" + txrId + '\'' +
                '}';
    }
}
