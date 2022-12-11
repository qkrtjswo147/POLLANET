package com.example.finalproject;

public class BoardHeartVO extends BoardVO{
    private int h_code;
    private String h_user_id;
    private int h_board_code;
    private String h_sort;

    public int getH_code() {
        return h_code;
    }

    public void setH_code(int h_code) {
        this.h_code = h_code;
    }

    public String getH_user_id() {
        return h_user_id;
    }

    public void setH_user_id(String h_user_id) {
        this.h_user_id = h_user_id;
    }

    public int getH_board_code() {
        return h_board_code;
    }

    public void setH_board_code(int h_board_code) {
        this.h_board_code = h_board_code;
    }

    public String getH_sort() {
        return h_sort;
    }

    public void setH_sort(String h_sort) {
        this.h_sort = h_sort;
    }
}
