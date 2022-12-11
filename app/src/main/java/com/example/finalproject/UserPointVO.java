package com.example.finalproject;

import com.example.finalproject.LoginPage.UserStatusVO;

public class UserPointVO extends UserStatusVO {
    private String user_point_id;
    private int user_point;
    private String user_grade;
    private String user_point_Date;
    private String user_grade_Date;
    public String getUser_point_id() {
        return user_point_id;
    }
    public void setUser_point_id(String user_point_id) {
        this.user_point_id = user_point_id;
    }
    public int getUser_point() {
        return user_point;
    }
    public void setUser_point(int user_point) {
        this.user_point = user_point;
    }
    public String getUser_grade() {
        return user_grade;
    }
    public void setUser_grade(String user_grade) {
        this.user_grade = user_grade;
    }
    public String getUser_point_Date() {
        return user_point_Date;
    }
    public void setUser_point_Date(String user_point_Date) {
        this.user_point_Date = user_point_Date;
    }
    public String getUser_grade_Date() {
        return user_grade_Date;
    }
    public void setUser_grade_Date(String user_grade_Date) {
        this.user_grade_Date = user_grade_Date;
    }
    @Override
    public String toString() {
        return "UserPointVO [user_point_id=" + user_point_id + ", user_point=" + user_point + ", user_grade="
                + user_grade + ", user_point_Date=" + user_point_Date + ", user_grade_Date=" + user_grade_Date + "]";
    }
}
