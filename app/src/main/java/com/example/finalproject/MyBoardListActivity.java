package com.example.finalproject;

import static com.example.finalproject.LoginPage.RemoteService.BASE_URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.finalproject.LoginPage.RemoteService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyBoardListActivity extends AppCompatActivity {
    Intent intent;
    String user_id;
    Toolbar toolbar;
    RecyclerView myBoardList;
    My_BoardList_Adapter myBoardListAdapter;
    List<BoardVO> myBoard=new ArrayList<>();
    Bundle bundle;
    Retrofit retrofit;
    RemoteService remoteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_board_list);

        intent = getIntent();
        user_id = intent.getStringExtra("user_Id");
        bundle = new Bundle();
        bundle.putString("userID",user_id);

        toolbar=findViewById(R.id.my_board_list_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("내가 쓴 게시글");

        retrofit=new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        remoteService=retrofit.create(RemoteService.class);

        Call<List<BoardVO>> myBoardCall = remoteService.userBoardList(user_id,1, 10);
        myBoardCall.enqueue(new Callback<List<BoardVO>>() {
            @Override
            public void onResponse(Call<List<BoardVO>> call, Response<List<BoardVO>> response) {
                myBoard = response.body();

                myBoardList = findViewById(R.id.my_board_list);
                myBoardListAdapter = new My_BoardList_Adapter(MyBoardListActivity.this,myBoard, user_id);
                myBoardList.setAdapter(myBoardListAdapter);

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