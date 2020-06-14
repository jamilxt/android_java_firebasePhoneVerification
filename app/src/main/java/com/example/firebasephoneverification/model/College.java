package com.example.firebasephoneverification.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class College {

    @SerializedName("college_id")
    private Long collegeId;
    @SerializedName("college_name")
    private String collegeName;
    @Expose
    private Long id;
    @SerializedName("user_id")
    private Long userId;

    public Long getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Long collegeId) {
        this.collegeId = collegeId;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "College{" +
                "collegeId=" + collegeId +
                ", collegeName='" + collegeName + '\'' +
                ", id=" + id +
                ", userId=" + userId +
                '}';
    }
}
