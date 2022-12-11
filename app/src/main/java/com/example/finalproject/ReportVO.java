package com.example.finalproject;

public class ReportVO {
    private int report_code;
    private String report_content;

    public int getReport_code() {
        return report_code;
    }

    public void setReport_code(int report_code) {
        this.report_code = report_code;
    }

    public String getReport_content() {
        return report_content;
    }

    public void setReport_content(String report_content) {
        this.report_content = report_content;
    }

    @Override
    public String toString() {
        return "ReportVO{" +
                "report_code=" + report_code +
                ", report_content='" + report_content + '\'' +
                '}';
    }
}
