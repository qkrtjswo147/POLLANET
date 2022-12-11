package com.example.finalproject.LoginPage;

import com.example.finalproject.BoardHeartVO;
import com.example.finalproject.BoardVO;
import com.example.finalproject.CharacterVO;
import com.example.finalproject.CommentVO;
import com.example.finalproject.MissionUserVO;
import com.example.finalproject.MissionVO;
import com.example.finalproject.RankingListVO;
import com.example.finalproject.ReportVO;
import com.example.finalproject.UserPointVO;
import com.example.finalproject.UserReportVO;
import com.example.finalproject.User_charVO;


import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RemoteService {
    public static final String BASE_URL = "http://192.168.0.108:8088";

    //로그인
    @POST("/api/user/userLogin")
    Call<Integer> login(@Body UserInfoVO userInfoVO);

    //회원가입
    @POST("/api/user/userRegister")
    Call<Void> Register(@Body UserStatusVO userStatusVO);

    //Id중복체크크
    @GET("/api/user/userDuplicationId/{str}")
    Call<Integer> duplicationID(@Path("str") String Id);

    //닉네임 중복체크
    @GET("/api/user/userDuplicationNickname/{str}")
    Call<Integer> duplicationNick(@Path("str") String NickName);

    //Id찾기
    @GET("/api/user/userSearchId")
    Call<String> searchId(@Query("name") String name, @Query("tel") String tel);

    //비밀번호 찾기 재설정
    @POST("/api/user/updatepassword")
    Call<Void> updatePassword(@Body UserInfoVO userInfovo);

    //유저검색(userinfo)
    @GET("/api/user/userRead/{user_Id}")
    Call<UserInfoVO> userInfo(@Path("user_Id") String user_Id);

    //유저검색(userStatus)
    @GET("/api/user/userReadSt/{user_id}")
    Call<UserStatusVO> userStatus(@Path("user_id") String user_id);

    //유저 포인트 검색
    @GET("/api/point/read/{user_point_id}")
    Call<UserPointVO> userPoint(@Path("user_point_id") String user_point_id);

    //유저 랭킹
    @GET("/api/point/rank/{user_point_id}")
    Call<Integer> myRank(@Path("user_point_id") String user_point_id);

    //미션6개
    @GET("/api/mission/list?m_keyword=&m_start=4&m_number=6")
    Call<List<MissionVO>> missionList();

    //회원수정
    @POST("/api/user/userupdate")
    Call<Void> userupdate(@Body UserStatusVO userStatusVO);

    //회원탈퇴
    @POST("/api/user/userdelupdate")
    Call<Void> userdel(@Body UserStatusVO userStatusVO);

    //회원랭킹
    @GET("/api/point/rankList")
    Call<List<RankingListVO>> rankList();

    //일일미션
    @GET("/api/mission/categorylist?m_category=&m_sort=d&m_start=0&m_number=6")
    Call<List<MissionVO>> Dcategorylist();

    //주간미션
    @GET("/api/mission/categorylist?m_category=&m_sort=w&m_start=0&m_number=6")
    Call<List<MissionVO>> Wcategorylist();

    //게시판 리스트
    @GET("/api/board/listand")
    Call<List<BoardVO>> boardList(@Query("b_title") String b_title);

    //특정 게시글 정보
    @GET("/api/board/read/{board_code}")
    Call<BoardVO> boardRead(@Path("board_code") int board_code);

    //게시글인서트
    @Multipart
    @POST("/api/board/insert")
    Call<Void> boardInsert(@Part("b_title") RequestBody b_title,
                           @Part("b_content") RequestBody b_content,
                           @Part MultipartBody.Part b_image,
                           @Part("b_user_id") RequestBody b_user_id,
                           @Part("b_category") RequestBody b_category);

    //게시글 삭제
    @POST("/api/board/delete/{board_code}")
    Call<Void> boardDelete(@Path("board_code") int board_code, @Body BoardVO boardvo);

    //게시글 수정
    @Multipart
    @POST("/api/board/update/{board_code}")
    Call<Void> boardUpdate(@Part("b_title") RequestBody b_title,
                           @Part("b_content") RequestBody b_content,
                           @Part MultipartBody.Part b_image,
                           @Part("b_user_id") RequestBody b_user_id,
                           @Part("b_category") RequestBody b_category,
                           @Path("board_code") int board_code);

    //미션정보read
    @GET("/api/mission/read/{mission_code}")
    Call<MissionVO> missionRead(@Path("mission_code")int missionCode);

    //미션완료정보read
    @GET("/api/mission/clearMission/{um_user_id}/{mission_code}")
    Call<Integer> missionClear(@Path("um_user_id")String user_id , @Path("mission_code")int mission_code);

    //미션완료정보Total
    @GET("/api/mission/clearMissionTotal/{um_user_id}")
    Call<Integer> missionClearTotal(@Path("um_user_id")String user_id);

    //미션참가등록
    @Multipart
    @POST("/api/umission/insert")
    Call<Void>umInsert(@Part("mission_code")RequestBody mission_code,
                       @Part("um_user_id")RequestBody um_user_id,
                       @Part("um_content")RequestBody um_content,
                       @Part("um_title")RequestBody um_title,
                       @Part("um_get_point")RequestBody um_get_point,
                       @Part MultipartBody.Part um_image);

    //공지사항 리스트
    @GET("/api/board/noticeList?page=1&num=10")
    Call<List<BoardVO>> boardNoticeList();

    @POST("/api/board/recommend/{board_code}")
    Call<Void>recommend_board(@Path("board_code")int board_code, BoardVO boardVO);

    //특정 댓글 리스트
    @GET("/api/comment/list/{board_Code}")
    Call<List<CommentVO>> commentRead(@Path("board_Code")int board_Code);

    //전체 댓글 리스트
    @GET("/api/comment/allList?pape=1&num=20")
    Call<List<CommentVO>> commentList();

    //댓글 등록
    @POST("/api/comment/insert")
    Call<Void> commentInsert (@Body CommentVO commentVO);

    //댓글삭제
    @POST("/api/comment/delete/{comment_code}")
    Call<Void> commentDelete(@Path("comment_code")int comment_code);

    //댓글수정 //폐기되었습니다. 추후 수정 넣을경우 해당 call쓰면됨
    @POST("/api/comment/update")
    Call<Void> commentUpdate(@Body CommentVO commentVO);

    //신고 리모트
    //신고 리스트 아이템
    @GET("/api/report/list")
     Call<List<ReportVO>> reportList();

    //게시글 신고
    @POST("/api/reportuser/boardReport")
    Call<Void> BoardReport(@Body UserReportVO userReportVO);

    //댓글 신고
    @POST("/api/reportuser/commentReport")
    Call<Void> CommentReport(@Body UserReportVO userReportVO);

    //내가쓴 게시글
    @GET("/api/board/userBoardList/{b_user_id}")
    Call<List<BoardVO>> userBoardList(@Path("b_user_id") String b_user_id, @Query("page") int page, @Query("num") int num);

    //전체 캐릭터 리스트
    @GET("/api/character/listG1")
    Call<List<CharacterVO>> charList();

    //캐릭터read
    @GET("/api/character/userchar/{user_id}")
    Call<User_charVO> userCharRead(@Path("user_id") String user_id);

    //유저 캐릭터 sort
    @POST("/api/user/usercharsort")
    Call<Void> userCharSort(@Body UserStatusVO userStatusVO);
    //내 랭킹
    @GET("/api/point/userrankread/{user_id}")
    Call<RankingListVO> userRankread(@Path("user_id") String user_id);

    //좋아요
    @POST("/api/boardheart/read")
    Call<Integer> userHeart(@Body BoardHeartVO boardHeartVO);

    //좋아요 누른경우
    @POST("/api/board/heart/{h_user_id}")
    Call<Void> heart(@Body BoardVO boardVO, @Path("h_user_id") String h_user_id);

    @POST("/api/board/heartCancel/{h_user_id}")
    Call<Void> heartCancel(@Body BoardVO boardVO, @Path("h_user_id") String h_user_id);

    @GET("/api/board/listheart")
    Call<List<BoardVO>> boardListHeart();

}