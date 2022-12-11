package com.example.finalproject;

import com.example.finalproject.LoginPage.UserInfoVO;
import com.example.finalproject.LoginPage.UserStatusVO;

import java.util.Date;

public class BoardVO extends UserInfoVO {
    private int board_code;                                                //게시글 코드
    private String b_user_id;                                            //유저 아이디
    private String b_register_Date;                                        //게시글 등록일
    private String b_update_Date;                                            //게시글 수정일
    private String b_title;                                                //게시글 제목
    private String b_content;                                            //게시글 내용
    private String b_image;                                                //첨부 사진
    private String b_del;                                                //strImage1 여부(1:삭제 0:유지)
    private int b_report;                                                //게시글 신고 갯수
    private int b_recommend;
    private int b_comment_count;
    private String b_category;
    private int b_report_content;
    //getter and setter
    private int b_heart; //좋아요 수

    public int getB_report_content() {
        return b_report_content;
    }

    public void setB_report_content(int b_report_content) {
        this.b_report_content = b_report_content;
    }

    public int getB_heart() {
        return b_heart;
    }

    public void setB_heart(int b_heart) {
        this.b_heart = b_heart;
    }

    public String getB_register_Date() {
        return b_register_Date;
    }

    public void setB_register_Date(String b_register_Date) {
        this.b_register_Date = b_register_Date;
    }

    public String getB_update_Date() {
        return b_update_Date;
    }

    public void setB_update_Date(String b_update_Date) {
        this.b_update_Date = b_update_Date;
    }

    public int getBoard_code() {
        return board_code;
    }

    public void setBoard_code(int board_code) {
        this.board_code = board_code;
    }

    public String getB_user_id() {
        return b_user_id;
    }

    public void setB_user_id(String b_user_id) {
        this.b_user_id = b_user_id;
    }

    public String getB_title() {
        return b_title;
    }

    public void setB_title(String b_title) {
        this.b_title = b_title;
    }

    public String getB_content() {
        return b_content;
    }

    public void setB_content(String b_content) {
        this.b_content = b_content;
    }

    public String getB_image() {
        return b_image;
    }

    public void setB_image(String b_image) {
        this.b_image = b_image;
    }

    public String getB_del() {
        return b_del;
    }

    public void setB_del(String b_del) {
        this.b_del = b_del;
    }

    public int getB_report() {
        return b_report;
    }

    public void setB_report(int b_report) {
        this.b_report = b_report;
    }

    public int getB_recommend() {
        return b_recommend;
    }

    public void setB_recommend(int b_recommend) {
        this.b_recommend = b_recommend;
    }

    public int getB_comment_count() {
        return b_comment_count;
    }

    public void setB_comment_count(int b_comment_count) {
        this.b_comment_count = b_comment_count;
    }

    public String getB_category() {
        return b_category;
    }

    public void setB_category(String b_category) {
        this.b_category = b_category;
    }

    @Override
    public String toString() {
        return "BoardVO{" +
                "board_code=" + board_code +
                ", b_user_id='" + b_user_id + '\'' +
                ", b_register_Date=" + b_register_Date +
                ", b_update_Date=" + b_update_Date +
                ", b_title='" + b_title + '\'' +
                ", b_content='" + b_content + '\'' +
                ", b_image='" + b_image + '\'' +
                ", b_del='" + b_del + '\'' +
                ", b_report=" + b_report +
                ", b_recommend=" + b_recommend +
                ", b_comment_count=" + b_comment_count +
                ", b_category='" + b_category + '\'' +
                '}';
    }
}

