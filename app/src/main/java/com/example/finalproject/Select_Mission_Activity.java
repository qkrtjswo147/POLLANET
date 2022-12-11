package com.example.finalproject;

import static com.example.finalproject.LoginPage.RemoteService.BASE_URL;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.LoginPage.RemoteService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Select_Mission_Activity extends AppCompatActivity {

    Context context;
    Retrofit retrofit;
    RemoteService service;
    TextView selectTitle, selectCode;
    Intent intent;
    String missionCode;
    Button btn_mission_join;
    String userid;
    ImageView mission_image;
    int code;
    int clearcode;
    Toolbar toolbar;
    MissionVO vo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mission);

        toolbar=findViewById(R.id.select_mission_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent = getIntent();
        missionCode = intent.getStringExtra("code");
        userid = intent.getStringExtra("user_Id");

        code = Integer.parseInt(missionCode);

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RemoteService.class);

        selectTitle = findViewById(R.id.selectTitle);
        mission_image = findViewById(R.id.select_mission_image);

        Call<MissionVO> callmission = service.missionRead(code);
        callmission.enqueue(new Callback<MissionVO>() {
            @Override
            public void onResponse(Call<MissionVO> call, Response<MissionVO> response) {
                vo = response.body();
                selectTitle.setText(vo.getM_title());
                String strImage = vo.getM_content_image();
                if (strImage != null && !strImage.equals("")) {
                    Picasso.with(context).load("http://192.168.0.108:8088/api/display?fileName=/" + strImage).into(mission_image);
                }
            }

            @Override
            public void onFailure(Call<MissionVO> call, Throwable t) {

            }
        });




        btn_mission_join = findViewById(R.id.btn_mission_join);
        Call<Integer> callCelarcode = service.missionClear(userid,code);
        callCelarcode.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.body()==0){
                    btn_mission_join.setText("미션 참여하기");
                }else {
                    btn_mission_join.setText("이미 참여한 미션입니다");
                    btn_mission_join.setEnabled(false);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
        btn_mission_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Select_Mission_Activity.this,Pati_Mission_Activity.class);
                intent.putExtra("user_Id",userid);
                intent.putExtra("code",missionCode);
                startActivity(intent);
                finish();

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