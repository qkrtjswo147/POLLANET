package com.example.finalproject;

public class CommentVO extends BoardVO {
    private String comment_code;                                        //댓글 코드
    private String c_user_id;                                            //작성자 아이디
    private String c_register_Date;                                        //댓글 작성일
    private String c_comment;                                                //댓글 내용
    private String c_del;                                                //댓글 삭제 여부 (삭제:1, 유지:0)
    private int c_report;                                                //댓글 신고 수
    private String c_update_Date;                                            //댓글 수정일
    private int board_Code;                                            //댓글 달린 게시글 코드
    private int c_recommend;

    //getter and setter

    public String getComment_code() {
        return comment_code;
    }

    public int getC_recommend() {
        return c_recommend;
    }

    public void setC_recommend(int c_recommend) {
        this.c_recommend = c_recommend;
    }

    public String getC_comment() {
        return c_comment;
    }

    public void setC_comment(String c_comment) {
        this.c_comment = c_comment;
    }

    public void setComment_code(String comment_code) {
        this.comment_code = comment_code;
    }

    public String getC_user_id() {
        return c_user_id;
    }

    public void setC_user_id(String c_user_id) {
        this.c_user_id = c_user_id;
    }

    public String getC_register_Date() {
        return c_register_Date;
    }

    public void setC_register_Date(String c_register_Date) {
        this.c_register_Date = c_register_Date;
    }

    public String getC_del() {
        return c_del;
    }

    public void setC_del(String c_del) {
        this.c_del = c_del;
    }

    public int getC_report() {
        return c_report;
    }

    public void setC_report(int c_report) {
        this.c_report = c_report;
    }

    public String getC_update_Date() {
        return c_update_Date;
    }

    public void setC_update_Date(String c_update_Date) {
        this.c_update_Date = c_update_Date;
    }

    public int getBoard_Code() {
        return board_Code;
    }

    public void setBoard_Code(int board_Code) {
        this.board_Code = board_Code;
    }

    @Override
    public String toString() {
        return "CommentVO [comment_code=" + comment_code + ", c_user_id=" + c_user_id + ", c_register_Date="
                + c_register_Date + ", c_comment=" + c_comment + ", c_del=" + c_del + ", c_report=" + c_report
                + ", c_update_Date=" + c_update_Date + ", board_Code=" + board_Code + "]";
    }


    //toString
}
