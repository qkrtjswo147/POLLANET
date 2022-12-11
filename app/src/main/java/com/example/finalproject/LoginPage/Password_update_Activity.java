package com.example.finalproject.LoginPage;

import static com.example.finalproject.LoginPage.RemoteService.BASE_URL;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.finalproject.Edit_User_Activity;
import com.example.finalproject.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Password_update_Activity extends AppCompatActivity {

    Retrofit retrofit;
    RemoteService service;
    EditText password_update, password_update_confirm, id;
    Button btnpassmodify;
    ProgressBar progressBar;
    String user_id;
    Intent intent;
    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_update);

        myToolbar = (Toolbar) findViewById(R.id.Edit_user_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("비밀번호수정");

        progressBar = (ProgressBar) findViewById(R.id.progressBar);//프로그레스바

        password_update = findViewById(R.id.password_update);//변경하는비밀번호
        password_update_confirm = findViewById(R.id.password_update_confirm);//변경하는 비밀번호 확인
        btnpassmodify = findViewById(R.id.btnpassmodify);//비밀번호 변경 버튼
        final InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        intent = getIntent();
        user_id = intent.getStringExtra("user_Id");

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        service = retrofit.create(RemoteService.class);


        //변경하는 비밀번호 확인하기
        password_update_confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String passupdate = password_update.getText().toString().trim();
                String passupdate_confirm = password_update_confirm.getText().toString();
                if (!passupdate.equals(passupdate_confirm)) {
                    password_update_confirm.setError("비밀번호가 일치하지않습니다.");
                } else {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치합니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //비밀번호 변경 버튼을 클릭한 경우
        btnpassmodify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passupdate = password_update.getText().toString().trim();
                String passupdate_confirm = password_update_confirm.getText().toString().trim();

                if (TextUtils.isEmpty(passupdate)) {
                    password_update.setError("변경하실 비밀번호를 입력해주세요");
                    return;
                }
                if (passupdate.length() < 8) {
                    password_update.setError("비밀번호는 8자리 이상입니다.");
                    return;
                }
                if (TextUtils.isEmpty(passupdate_confirm)) {
                    password_update_confirm.setError("비밀번호 확인을 입력해주세요");
                    return;
                }
                if (!passupdate.equals(passupdate_confirm)) {
                    password_update.setError("비밀번호가 일치하지 않습니다.");
                    password_update.setText("");
                    password_update.setText("");
                    return;
                }
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                UserInfoVO userInfoVO = new UserInfoVO();
                userInfoVO.setPassword(passupdate);
                userInfoVO.setId(user_id);
                Call<Void> call = service.updatePassword(userInfoVO);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        progressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "비밀번호 재설정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        intent = new Intent(getApplicationContext(), Edit_User_Activity.class);
                        intent.putExtra("user_Id", user_id);
                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

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