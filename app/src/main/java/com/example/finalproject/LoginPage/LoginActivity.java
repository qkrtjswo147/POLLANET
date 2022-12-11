package com.example.finalproject.LoginPage;

import static com.example.finalproject.LoginPage.RemoteService.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalproject.MainActivity;
import com.example.finalproject.R;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    EditText ID;
    EditText password;
    Button btn_Login;
    TextView signiup, findid, findpassword;
    UserInfoVO userInfovo;
    ProgressBar progressBar;
    CheckBox auto_login_checkBox, remembercheckBox;
    Context mContext;
    Retrofit retrofit;
    RemoteService service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        boolean boo = false;

        mContext = this;

        progressBar = (ProgressBar) findViewById(R.id.progressBar);//프로그레스바


        ID = findViewById(R.id.idCode);
        password = findViewById(R.id.password);

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RemoteService.class);

        //로그인한 아이디 비밀번호 기억하기
        remembercheckBox = findViewById(R.id.remembercheckbox);
//        //File이란 파일로 저장해둔 값을 가져오기위한 설정
        SharedPreferences sf = getSharedPreferences("File", MODE_PRIVATE);
        //text1에 값이 있으면 가져오고 두번째 인자는 없을경우 가져오는 값이다.
        String text1 = sf.getString("text1", "");
        String text2 = sf.getString("text2", "");
        // text!가 그냥 공백이 아니라면 아이디 저장이된 상태이기 때문에 체크박스를
        // 체크 상태로 변환한다
        if (!text1.equals("")) {
            remembercheckBox.setChecked(true);
        }
        if (!text2.equals(""))
            remembercheckBox.setChecked(true);
        ID.setText(text1);
        password.setText(text2);


        //회원가입페이지로 이동
        signiup = findViewById(R.id.signup);
        signiup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        //아이디 찾기 페이지로 이동
        findid = findViewById(R.id.findid);
        findid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, Find_ID_Activity.class));
            }
        });
        //비밀번호 찾기 페이지로 이동
        findpassword = findViewById(R.id.findpassword);
        findpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, Find_Password_Activity.class));
            }
        });
        //자동 로그인 체크
        auto_login_checkBox = findViewById(R.id.auto_login_checkbox);
        boo = shared_preferences.getBoolean(mContext, "check");
        if (boo) {
            auto_login_checkBox.setChecked(true);
        }
        auto_login_checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auto_login_checkBox.isChecked()) {
                    shared_preferences.setBoolean(mContext, "check", auto_login_checkBox.isChecked());
                } else {
                    shared_preferences.setBoolean(mContext, "check", auto_login_checkBox.isChecked());
                    shared_preferences.clear_user(mContext);
                }
            }
        });

        //로그인버튼 눌럿을때
        try {
            btn_Login = findViewById(R.id.btn_Login);
            btn_Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = ID.getText().toString().trim();
                    String Password = password.getText().toString().trim();
                    if (TextUtils.isEmpty(id)) {
                        ID.setError("아이디를 입력해주세요");
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

                    if (auto_login_checkBox.isChecked()){
                        shared_preferences.setBoolean(mContext, "check", auto_login_checkBox.isChecked());
                        UserInfoVO userInfoVO = new UserInfoVO();
                        userInfoVO.setId(id);
                        userInfoVO.setPassword(Password);
                        Call<Integer> call1 = service.login(userInfoVO);
                        call1.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                if (response.body().equals(1)){
                                    shared_preferences.set_user_email(LoginActivity.this, userInfoVO.getId());
                                    if (shared_preferences.get_user_email(LoginActivity.this) != null) {
                                        progressBar.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);//액티비티 스택제거
                                        Toast.makeText(getApplicationContext(), "자동로그인 체크 되었습니다.", Toast.LENGTH_SHORT).show();
                                        intent.putExtra("user_Id", userInfoVO.getId());
                                        startActivity(intent);
                                        finish();
                                    }
                                }else if (response.body().equals(2)) {
                                    Toast.makeText(getApplicationContext(), "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                                }else if (response.body().equals(0)) {
                                    Toast.makeText(getApplicationContext(), "아이디를 확인해주세요", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {

                            }
                        });
                    }else {
                        UserInfoVO userInfoVO1 = new UserInfoVO();
                        userInfoVO1.setId(id);
                        userInfoVO1.setPassword(Password);
                        Call<Integer> call = service.login(userInfoVO1);
                        call.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                if (response.body().equals(1)) {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    progressBar.setVisibility(View.VISIBLE);
                                    Toast.makeText(getApplicationContext(), "로그인 정보를 기억합니다.", Toast.LENGTH_SHORT).show();
                                    intent.putExtra("user_Id", userInfoVO1.getId());
                                    startActivity(intent);
                                    finish();
                                } else if (response.body().equals(2)) {
                                    Toast.makeText(getApplicationContext(), "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                                } else if (response.body().equals(0)) {
                                    Toast.makeText(getApplicationContext(), "아이디를 확인해주세요", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {

                            }
                        });
                    }

                }
            });
        } catch (NullPointerException e) {
        }

// 이 밑으론 카카오로그인 관련 코드
        //카카오 해시키 알기
        Log.d("KeyHash", getKeyHash());
        //카카오로그인 버튼
        ImageButton kakao_login_button = (ImageButton) findViewById(R.id.kakao_login_button);
        kakao_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)) {
                    login();
                } else {
                    accountLogin();
                }
            }
        });
    }

    //아이디 비밀번호 저장 체크시 앱 재실행시 아이디 비밀번호 저장되어있음
    public void erase(View view) {
        ID.setText("");
        password.setText("");
    }

    public void confirm(View view) {

    }


    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences = getSharedPreferences("File", MODE_PRIVATE);
        //저장을 하기위해 editor를 이용하여 값을 저장시켜준다.
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //체크 박스에 체크가 됬다면 아이디를 저장한다.
        if (remembercheckBox.isChecked()) {
            final String text1 = ID.getText().toString();
            editor.putString("text1", text1);
        } else {
            editor.putString("text1", "");
        }
        // 값을 다 넣었으면 commit으로 완료한다.
        editor.commit();
        if (remembercheckBox.isChecked()) {
            final String text2 = password.getText().toString();
            editor.putString("text2", text2);
        } else {
            editor.putString("text2", "");
        }
        // 값을 다 넣었으면 commit으로 완료한다.
        editor.commit();
    }

    //카카오톡 아이디로 로그인할 경우
    public void login() {
        String TAG = "login()";
        UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, (oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                Toast.makeText(getApplicationContext(), "로그인되었습니다.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                getUserInfo();
            }
            return null;
        });
    }

    //카카오톡 아이디가 없어 카카오톡에 등록된 이메일로 로그인 할 경우
    public void accountLogin() {
        String TAG = "accountLogin()";
        UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, (oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                Toast.makeText(getApplicationContext(), "로그인되었습니다.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                getUserInfo();
            }
            return null;
        });
    }

    //카카오 로그인한 정보에따라 log에 나오도록 표시해 놓았음 추 후 DB 추가시 참고
    public void getUserInfo() {
        String TAG = "getUserInfo()";
        UserApiClient.getInstance().me((user, meError) -> {
            if (meError != null) {
                Log.e(TAG, "사용자 정보 요청 실패", meError);
            } else {
                Log.i(TAG, user.toString());
                {
                    Log.i(TAG, "사용자 정보 요청 성공" +
                            "\n회원번호: " + user.getId() +
                            "\n이메일: " + user.getKakaoAccount().getEmail() +
                            "\n닉네임: " + user.getKakaoAccount().getProfile().getNickname());
                }
                Account user1 = user.getKakaoAccount();
            }
            return null;
        });
    }

    // 키해시 얻기
    public String getKeyHash() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            if (packageInfo == null) return null;
            for (Signature signature : packageInfo.signatures) {
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    return android.util.Base64.encodeToString(md.digest(), Base64.NO_WRAP);
                } catch (NoSuchAlgorithmException e) {
                    Log.w("getKeyHash", "Unable to get MessageDigest. signature=" + signature, e);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("getPackageInfo", "Unable to getPackageInfo");
        }
        return null;
    }

}