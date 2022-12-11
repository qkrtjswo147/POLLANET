package com.example.finalproject;

import static com.example.finalproject.LoginPage.RemoteService.BASE_URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.finalproject.LoginPage.RemoteService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Notice_Read_Activity extends AppCompatActivity {

    Toolbar toolbar;
    Retrofit retrofit;
    RemoteService service;
    TextView read_date,read_title,read_id,read_content,notice_code;
    Intent intent;
    String NoticeList;
    BoardVO vo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_read);

        toolbar=(Toolbar) findViewById(R.id.board_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        intent = getIntent();
        NoticeList = intent.getStringExtra("noticode");

        read_date = findViewById(R.id.notice_read_date);
        read_title = findViewById(R.id.notice_read_title);
        read_id = findViewById(R.id.notice_read_id);
        read_content = findViewById(R.id.notice_read_content);
        notice_code = findViewById(R.id.notice_code);

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RemoteService.class);

        Call<BoardVO> call=service.boardRead(Integer.parseInt(NoticeList));
        call.enqueue(new Callback<BoardVO>() {
            @Override
            public void onResponse(Call<BoardVO> call, Response<BoardVO> response) {
                BoardVO vo = response.body();
                notice_code.setText(Integer.toString(vo.getBoard_code()));
                read_date.setText(vo.getB_update_Date());
                read_title.setText(vo.getB_title());
                read_id.setText(vo.getB_user_id());
                read_content.setText(vo.getB_content());

            }

            @Override
            public void onFailure(Call<BoardVO> call, Throwable t) {

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