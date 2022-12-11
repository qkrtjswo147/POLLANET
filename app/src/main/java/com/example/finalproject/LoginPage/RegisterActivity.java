package com.example.finalproject.LoginPage;

import static com.example.finalproject.LoginPage.RemoteService.BASE_URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalproject.MainActivity;
import com.example.finalproject.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    int NickDupli = 1; //중복체크 버튼눌렀는지 확인 . 0은중복 체크안함 1은 체크함(닉네임)
    int IdDupli = 1; //중복체크 버튼눌렀는지 확인 . 0은중복 체크안함 1은 체크함(id)

    EditText username, id, email, password, passwordconfirm, userTel, nickname;
    Button btnRegister, reset, idCheck, nickCheck;
    TextView AlreadyHaveAccount;
    ProgressBar progressBar;
    Retrofit retrofit;
    RemoteService service;
    String ID = "";
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar=findViewById(R.id.register_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        btnRegister = findViewById(R.id.btnRegister);//가입하기 버튼
        progressBar = (ProgressBar) findViewById(R.id.progressBar);//프로그레스바

        //회원가입
        username = findViewById(R.id.username);//유저 이름
        id = findViewById(R.id.idCode);//유저 ID
        email = findViewById(R.id.email);//유저 이메일
        password = findViewById(R.id.password);//유저 비밀번호
        passwordconfirm = findViewById(R.id.passwordconfirm);//비밀번호 확인
        userTel = findViewById(R.id.userTel);//휴대폰
        nickname = findViewById(R.id.nickName);//닉네임
        //중복체크버튼
        idCheck = findViewById(R.id.idCheck);//ID중복확인 버튼
        nickCheck = findViewById(R.id.nickCheck);//닉네임 중복확인버튼

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RemoteService.class);


        //Id 중복체크
        idCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ID = id.getText().toString().trim();
                Call<Integer> callId = service.duplicationID(ID);
                callId.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        IdDupli = (int) response.body();

                        if (IdDupli == 1) {
                            Toast.makeText(getApplicationContext(), "이미 있는 ID입니다.", Toast.LENGTH_SHORT).show();
                        } else if (IdDupli == 0) {
                            Toast.makeText(getApplicationContext(), "사용가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                    }
                });

            }
        });

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


        id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                IdDupli = 1;

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        nickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                NickDupli = 1;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //비밀번호 확인 컨펌란
        passwordconfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String Password = password.getText().toString();
                String Passwordconfirm = passwordconfirm.getText().toString();
                if (!Password.equals(Passwordconfirm)) {
                    passwordconfirm.setError("비밀번호가 일치하지않습니다.");
                } else {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치합니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //이미 아이디가 있을경우 로그인 페이지로 이동
        AlreadyHaveAccount = findViewById(R.id.AlreadyHaveAccount);
        AlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        //취소하기 버튼을 누른경우 로그인 페이지로 이동
        reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });


        //가입하기 버튼을 누른경우
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = username.getText().toString().trim();
                final String Email = email.getText().toString().trim();
                String Password = password.getText().toString().trim();
                String Passwordconfirm = passwordconfirm.getText().toString().trim();
                final String userPhone = userTel.getText().toString();
                String nickName = nickname.getText().toString().trim();
                String ID = id.getText().toString().trim();

                if (TextUtils.isEmpty(userName)) {
                    username.setError("이름을 입력해주세요");
                    return;
                }
                if (TextUtils.isEmpty(ID)) {
                    id.setError("아이디를 입력해주세요");
                    return;
                }
                if (IdDupli == 1) {
                    Toast.makeText(getApplicationContext(), "아이디 중복체크를 해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(Email)) {
                    email.setError("이메일을 입력해주세요");
                    return;
                }
                if (TextUtils.isEmpty(Password)) {
                    password.setError("비밀번호를 입력해주세요");
                    return;
                }
                if (Password.length() < 8) {
                    password.setError("비밀번호는 8자리 이상입니다.");
                    return;
                }
                if (TextUtils.isEmpty(Passwordconfirm)) {
                    passwordconfirm.setError("비밀번호 확인을 입력해주세요");
                    return;
                }
                if (!Password.equals(Passwordconfirm)) {
                    password.setError("비밀번호가 일치하지 않습니다.");
                    password.setText("");
                    passwordconfirm.setText("");
                    return;
                }
                if (TextUtils.isEmpty(userPhone)) {
                    userTel.setError("휴대폰 번호를 입력해주세요");
                    return;
                }
                if (TextUtils.isEmpty(nickName)) {
                    nickname.setError("닉네임을 입력해주세요");
                    return;
                }
                if (NickDupli == 1) {
                    Toast.makeText(getApplicationContext(), "닉네임 중복체크를 해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                UserStatusVO userStatusVO = new UserStatusVO();
                userStatusVO.setId(ID);
                userStatusVO.setPassword(Password);
                userStatusVO.setUser_id(ID);
                userStatusVO.setName(userName);
                userStatusVO.setNickName(nickName);
                userStatusVO.setEmail(Email);
                userStatusVO.setTel(userPhone);
                AlertDialog.Builder box = new AlertDialog.Builder(new ContextThemeWrapper(RegisterActivity.this, R.style.AlertDialogTheme));
                box.setTitle("회원가입");
                box.setMessage("회원가입을 하시겠습니까?");
                box.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Call<Void> call = service.Register(userStatusVO);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                progressBar.setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(), "회원가입 되었습니다.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
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
                box.show();
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