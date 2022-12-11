package com.example.finalproject;

import static com.example.finalproject.LoginPage.RemoteService.BASE_URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.LoginPage.LoginActivity;
import com.example.finalproject.LoginPage.Password_update_Activity;
import com.example.finalproject.LoginPage.RemoteService;
import com.example.finalproject.LoginPage.UserStatusVO;
import com.example.finalproject.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Edit_User_Activity extends AppCompatActivity {

    int NickDupli = 1; //중복체크 버튼눌렀는지 확인 . 0은중복 체크안함 1은 체크함(닉네임)

    private AlertDialog dialog;
    Retrofit retrofit;
    RemoteService service;
    EditText username, id, email, userTel, nickname;
    TextView unregister;
    Button nickCheck, btnEdit, reset, btn_pass_update;
    Toolbar myToolbar;
    ProgressBar progressBar;
    Intent intent;
    String userId;  //자동로그인을 체크안하고 로그인한경우의 id값
    String user_id;//자동로그인을 체크하고 로그인한경우의 id값

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        myToolbar=(Toolbar) findViewById(R.id.Edit_user_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("회원정보수정");

        progressBar = (ProgressBar) findViewById(R.id.progressBar);//프로그레스바

        intent = getIntent();
        userId = intent.getStringExtra("user_Id");
        username = findViewById(R.id.username);//유저이름
        id = findViewById(R.id.idCode);//유저아이디는 고정
        email = findViewById(R.id.email);//유저이메일
        userTel = findViewById(R.id.userTel);//유저 전화번호
        nickname = findViewById(R.id.nickName);//유저 닉네임

        nickCheck = findViewById(R.id.nickCheck);//닉네임 중복체크 버튼

        //레트로핏 서비스
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RemoteService.class);
        id.setText(userId);
        Call<UserStatusVO> callUser = service.userStatus(userId);
        callUser.enqueue(new Callback<UserStatusVO>() {
            @Override
            public void onResponse(Call<UserStatusVO> call, Response<UserStatusVO> response) {
                UserStatusVO userStatusVO = response.body();
                username.setText(userStatusVO.getName());
                nickname.setText(userStatusVO.getNickName());
                email.setText(userStatusVO.getEmail());
                userTel.setText(userStatusVO.getTel());
            }

            @Override
            public void onFailure(Call<UserStatusVO> call, Throwable t) {

            }
        });


        //닉네임 중복체크
        nickCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nickName = nickname.getText().toString().trim();

            }
        });
        //취소하기 버튼을 클릭한경우
        reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("user_Id", userId);
                startActivity(intent);
                finish();
            }
        });

        //비밀번호 변경 버튼을 클릭한 경우
        btn_pass_update = findViewById(R.id.btn_pass_update);
        btn_pass_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Password_update_Activity.class);
                intent.putExtra("user_Id", userId);
                startActivity(intent);
            }
        });

        //닉네임 중복체크
        nickCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nickName = nickname.getText().toString().trim();
                Call<Integer> callId = service.duplicationNick(nickName);
                callId.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        NickDupli = (int) response.body();
                        if (nickName.length() > 8) {
                            nickname.setError("닉네임은 8자리 까지만 입력가능합니다.");
                            return;
                        }
                        if (NickDupli == 1) {
                            Toast.makeText(getApplicationContext(), "이미 있는 닉네임입니다.", Toast.LENGTH_SHORT).show();
                        } else if (NickDupli == 0) {
                            Toast.makeText(getApplicationContext(), "사용가능한 닉네임입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                    }
                });
            }
        });
        //닉네임 중복체크
        nickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                NickDupli = 0;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                NickDupli = 1;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = username.getText().toString().trim();
                final String Email = email.getText().toString().trim();
                final String userPhone = userTel.getText().toString();
                String nickName = nickname.getText().toString().trim();


                if (nickName.length() > 8) {
                    nickname.setError("닉네임은 8자리 까지만 입력가능합니다.");
                    return;
                }
                if (NickDupli == 1) {
                    Toast.makeText(getApplicationContext(), "닉네임 중복체크를 해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserStatusVO userStatusVO = new UserStatusVO();
                userStatusVO.setUser_id(userId);
                userStatusVO.setName(userName);
                userStatusVO.setNickName(nickName);
                userStatusVO.setEmail(Email);
                userStatusVO.setTel(userPhone);
                AlertDialog.Builder box = new AlertDialog.Builder(new ContextThemeWrapper(Edit_User_Activity.this, R.style.AlertDialogTheme));
                box.setTitle("회원수정");
                box.setMessage("수정하시겠습니까?");
                box.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Call<Void> userUpdateCall = service.userupdate(userStatusVO);
                        userUpdateCall.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                progressBar.setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(), "정보가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("userId", userId);
                                startActivity(intent);
                                finish();
                            }
                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                    }
                });
                box.setNegativeButton("취소", null);
                AlertDialog alertDialog = box.create();
                alertDialog.show();
            }
        });


        //회원탈퇴 버튼을 누른경우
        unregister = findViewById(R.id.unregister);
        unregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder box = new AlertDialog.Builder(new ContextThemeWrapper(Edit_User_Activity.this, R.style.AlertDialogTheme));
                box.setTitle("회원탈퇴");
                box.setMessage("POLLANET과 헤어지시겠습니까?");
                box.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserStatusVO userStatusVO = new UserStatusVO();
                        userStatusVO.setUser_id(userId);
                        userStatusVO.setUser_del("1");
                        userStatusVO.setName("탈퇴회원");
                        userStatusVO.setNickName("탈퇴회원");
                        Call<Void> userdelCall = service.userdel(userStatusVO);
                        userdelCall.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                progressBar.setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(), "탈퇴되었습니다", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.putExtra("userId", userId);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                    }
                });
                box.setNegativeButton("아니오", null);
                AlertDialog alertDialog = box.create();
                alertDialog.show();
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