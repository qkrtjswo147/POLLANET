package com.example.finalproject;

public class User_charVO {
    private String character_grade;
    private String character_code;
    private String user_id;
    private String nickName;
    private String character_name;
    private int user_point;
    private String user_grade;
    private String character_sort;
    private String character_image;

    public String getCharacter_grade() {
        return character_grade;
    }

    public void setCharacter_grade(String character_grade) {
        this.character_grade = character_grade;
    }

    public String getCharacter_code() {
        return character_code;
    }

    public void setCharacter_code(String character_code) {
        this.character_code = character_code;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCharacter_name() {
        return character_name;
    }

    public void setCharacter_name(String character_name) {
        this.character_name = character_name;
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

    public String getCharacter_sort() {
        return character_sort;
    }

    public void setCharacter_sort(String character_sort) {
        this.character_sort = character_sort;
    }

    public String getCharacter_image() {
        return character_image;
    }

    public void setCharacter_image(String character_image) {
        this.character_image = character_image;
    }

    @Override
    public String toString() {
        return "User_charVO{" +
                "character_grade='" + character_grade + '\'' +
                ", character_code='" + character_code + '\'' +
                ", user_id='" + user_id + '\'' +
                ", nickName='" + nickName + '\'' +
                ", character_name='" + character_name + '\'' +
                ", user_point=" + user_point +
                ", user_grade='" + user_grade + '\'' +
                ", character_sort='" + character_sort + '\'' +
                ", character_image='" + character_image + '\'' +
                '}';
    }
}
