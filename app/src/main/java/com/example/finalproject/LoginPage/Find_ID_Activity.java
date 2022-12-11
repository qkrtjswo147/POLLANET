package com.example.finalproject.LoginPage;

import static com.example.finalproject.LoginPage.RemoteService.BASE_URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.finalproject.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Find_ID_Activity extends AppCompatActivity {

    Toast t;
    Retrofit retrofit;
    RemoteService service;
    Button idprev,idfindbtn;
    EditText username,userTel;
    AlertDialog dialog;
    ProgressBar progressBar;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);

        toolbar=findViewById(R.id.find_id_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        progressBar = (ProgressBar) findViewById(R.id.progressBar);//프로그레스바

        username = findViewById(R.id.username);
        userTel = findViewById(R.id.userTel);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        service = retrofit.create(RemoteService.class);

        //로그인 버튼을 누른경우 로그인페이지로 이동
        idprev = findViewById(R.id.idprev);
        idprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                startActivity(new Intent(Find_ID_Activity.this, LoginActivity.class));
            }
        });

        //아이디찾기
        idfindbtn = findViewById(R.id.idfindbtn);
        idfindbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = username.getText().toString().trim();
                String tel = userTel.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    username.setError("이름을 입력해주세요");
                    return;
                }
                if (TextUtils.isEmpty(tel)) {
                    userTel.setError("휴대폰 번호를 입력해주세요");
                    return;
                }

                Call<String> call = service.searchId(name,tel);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        for (int i = 0; i<30; i++){
                            t=Toast.makeText(getApplicationContext(),"아이디는 "+response.body()+" 입니다.",Toast.LENGTH_LONG);
                            t.setGravity(Gravity.CENTER,0,+250);
                            t.show();
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                    }
                });

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