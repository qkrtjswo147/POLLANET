package com.example.finalproject;

import static com.example.finalproject.LoginPage.RemoteService.BASE_URL;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.LoginPage.RemoteService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NoticeActivity extends AppCompatActivity {

    Toolbar toolbar;
    Context context;
    Retrofit retrofit;
    RemoteService service;
    RecyclerView recyclerView;
    NoticeAdapter noticeAdapter;
    List<BoardVO> NoticeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        toolbar=(Toolbar) findViewById(R.id.notice_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");



        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RemoteService.class);

        recyclerView = (RecyclerView) findViewById(R.id.NList);

        Call<List<BoardVO>> notiList =  service.boardNoticeList();
        notiList.enqueue(new Callback<List<BoardVO>>() {
            @Override
            public void onResponse(Call<List<BoardVO>> call, Response<List<BoardVO>> response) {
                NoticeList =response.body();
                LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(layoutManager);
                noticeAdapter = new NoticeAdapter(context, NoticeList,"NoticeList");
                recyclerView.setAdapter(noticeAdapter);
            }

            @Override
            public void onFailure(Call<List<BoardVO>> call, Throwable t) {
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}