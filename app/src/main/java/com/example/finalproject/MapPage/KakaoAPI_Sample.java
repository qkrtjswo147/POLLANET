package com.example.finalproject.MapPage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface KakaoAPI_Sample {
    @GET("v2/local/search/keyword.json")
    Call<com.example.finalproject.MapPage.ResultSearchKeyword> getSearchLocation(
            @Header("Authorization") String token,
            @Query("query") String query
    );
}
