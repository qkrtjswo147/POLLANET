package com.example.finalproject.LoginPage;

import static com.example.finalproject.LoginPage.RemoteService.BASE_URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

public class Find_Password_Activity extends AppCompatActivity {

    Retrofit retrofit;
    RemoteService service;
    EditText id, userTel, editPassword, editPasswordconfirm;
    Button passfindbtn, btnPassChange;
    InputMethodManager inputMethodManager;
    ProgressBar progressBar;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);

        toolbar=findViewById(R.id.find_password_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        progressBar = (ProgressBar) findViewById(R.id.progressBar);//프로그레스바

        id = findViewById(R.id.idCode); //유저아이디
        userTel = findViewById(R.id.userTel);//유저 휴대폰번호
        editPassword = findViewById(R.id.editPassword);//비밀번호변경
        editPasswordconfirm = findViewById(R.id.editPasswordconfirm);//비밀번호확인
        passfindbtn = findViewById(R.id.passfindbtn); //비밀번호 찾기 버튼
        btnPassChange = findViewById(R.id.btnPassChange);//비밀번호 변경버튼

        final InputMethodManager manager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE) ;


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        service = retrofit.create(RemoteService.class);

        //로그인 버튼을 누른경우 로그인페이지로 이동
        Button passprev = findViewById(R.id.passprev);
        passprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                startActivity(new Intent(Find_Password_Activity.this, LoginActivity.class));
            }
        });
        //새로운 비밀번호 확인컴펌
        editPasswordconfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String editPass = editPassword.getText().toString();
                String editPassConfirm = editPasswordconfirm.getText().toString();
                if (!editPass.equals(editPassConfirm)) {
                    editPasswordconfirm.setError("비밀번호가 일치하지않습니다.");
                } else {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치합니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        //비밀번호 찾기 버튼을 누른경우
        passfindbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ID = id.getText().toString().trim();
                String userPhone = userTel.getText().toString().trim();

                if (TextUtils.isEmpty(ID)) {
                    id.setError("아이디를 입력해주세요");
                    return;
                }
                if (TextUtils.isEmpty(userPhone)) {
                    userTel.setError("휴대폰번호를 입력하세요");
                    return;
                }

                if (ID.equals(userPhone)) {  //변경해야할곳
                    editPassword.setVisibility(View.GONE);
                    editPasswordconfirm.setVisibility(View.GONE);
                    btnPassChange.setVisibility(View.GONE);
                } else {
                    editPassword.setVisibility(View.VISIBLE);
                    editPasswordconfirm.setVisibility(View.VISIBLE);
                    btnPassChange.setVisibility(View.VISIBLE);
                }
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                btnPassChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String ID = id.getText().toString().trim();
                        String editpass = editPassword.getText().toString();
                        String editpassConfirm = editPasswordconfirm.getText().toString();
                        UserInfoVO userInfoVO = new UserInfoVO();
                        userInfoVO.setPassword(editpass);
                        userInfoVO.setId(ID);

                        if (TextUtils.isEmpty(editpass)) {
                            editPassword.setError("재설정할 비밀번호를 입력해주세요");
                            return;
                        }
                        if (editpass.length() < 8) {
                            editPassword.setError("비밀번호는 8자리 이상입니다.");
                            return;
                        }
                        if (TextUtils.isEmpty(editpassConfirm)) {
                            editPasswordconfirm.setError("비밀번호 확인을 입력해주세요");
                            return;
                        }
                        if (!editpass.equals(editpassConfirm)) {
                            editPassword.setError("비밀번호가 일치하지 않습니다.");
                            editPassword.setText("");
                            editPassword.setText("");
                            return;
                        }

                        Call<Void> call = service.updatePassword(userInfoVO);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast.makeText(getApplicationContext(), "비밀번호 재설정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                finish();

                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });


                    }
                });
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}