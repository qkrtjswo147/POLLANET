package com.example.finalproject;

import static com.example.finalproject.LoginPage.RemoteService.BASE_URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

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

public class Char_Select_Activity extends AppCompatActivity {

    Intent intent;
    String user_id;
    Character_List_Adapter CharacterListAdapter;
    RecyclerView itemcharlist;
    Toolbar toolbar;
    List<CharacterVO> CharList = new ArrayList<>();
    StaggeredGridLayoutManager manager;
    Retrofit retrofit;
    RemoteService remoteService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_select);

        intent = getIntent();
        user_id = intent.getStringExtra("userID");

        toolbar=findViewById(R.id.char_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("캐릭터 선택");

        retrofit=new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        remoteService=retrofit.create(RemoteService.class);

        Call<List<CharacterVO>> charCall = remoteService.charList();
        charCall.enqueue(new Callback<List<CharacterVO>>() {
            @Override
            public void onResponse(Call<List<CharacterVO>> call, Response<List<CharacterVO>> response) {
                CharList = response.body();

                manager=new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                itemcharlist = findViewById(R.id.itemcharlist);
                CharacterListAdapter =new Character_List_Adapter(Char_Select_Activity.this,CharList, user_id);
                itemcharlist.setLayoutManager(manager);
                itemcharlist.setAdapter(CharacterListAdapter);
            }

            @Override
            public void onFailure(Call<List<CharacterVO>> call, Throwable t) {
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