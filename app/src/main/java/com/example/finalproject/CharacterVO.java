package com.example.finalproject;

import com.example.finalproject.LoginPage.UserStatusVO;

public class CharacterVO extends UserStatusVO {

    private String character_code;
    private String character_name;
    private String character_image;
    private String character_grade;

    public String getCharacter_code() {
        return character_code;
    }

    public void setCharacter_code(String character_code) {
        this.character_code = character_code;
    }

    public String getCharacter_name() {
        return character_name;
    }

    public void setCharacter_name(String character_name) {
        this.character_name = character_name;
    }

    public String getCharacter_image() {
        return character_image;
    }

    public void setCharacter_image(String character_image) {
        this.character_image = character_image;
    }

    public String getCharacter_grade() {
        return character_grade;
    }

    public void setCharacter_grade(String character_grade) {
        this.character_grade = character_grade;
    }


}
