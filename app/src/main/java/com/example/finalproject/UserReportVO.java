package com.example.finalproject;

public class UserReportVO {

    private int user_report_code;
    private String reporter_id;
    private int report_board_code;
    private int report_comment_code;
    private int report_code;

    public int getUser_report_code() {
        return user_report_code;
    }

    public void setUser_report_code(int user_report_code) {
        this.user_report_code = user_report_code;
    }

    public String getReporter_id() {
        return reporter_id;
    }

    public void setReporter_id(String reporter_id) {
        this.reporter_id = reporter_id;
    }

    public int getReport_board_code() {
        return report_board_code;
    }

    public void setReport_board_code(int report_board_code) {
        this.report_board_code = report_board_code;
    }

    public int getReport_comment_code() {
        return report_comment_code;
    }

    public void setReport_comment_code(int report_comment_code) {
        this.report_comment_code = report_comment_code;
    }

    public int getReport_code() {
        return report_code;
    }

    public void setReport_code(int report_code) {
        this.report_code = report_code;
    }

    @Override
    public String toString() {
        return "UserReportVO{" +
                "user_report_code=" + user_report_code +
                ", reporter_id='" + reporter_id + '\'' +
                ", report_board_code=" + report_board_code +
                ", report_comment_code=" + report_comment_code +
                ", report_code=" + report_code +
                '}';
    }
}
