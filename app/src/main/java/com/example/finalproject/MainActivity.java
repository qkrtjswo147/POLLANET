package com.example.finalproject;

import static com.example.finalproject.LoginPage.RemoteService.BASE_URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.LoginPage.LoginActivity;
import com.example.finalproject.LoginPage.RemoteService;
import com.example.finalproject.LoginPage.UserInfoVO;
import com.example.finalproject.LoginPage.UserStatusVO;
import com.example.finalproject.LoginPage.shared_preferences;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private BackKeyHandler backKeyHandler = new BackKeyHandler(this);
    DrawerLayout drawerLayout;
    RelativeLayout mainDrawerLayout;
    Intent intent;
    UserStatusVO userStatusVO;
    UserPointVO userPointVO;
    Bundle bundle;
    ProgressBar progressBar;

    Retrofit retrofit;
    RemoteService service;
    Context mContext, context;
    String userId;  //자동로그인을 체크안하고 로그인한경우의 id값
    String user_id;//자동로그인을 체크하고 로그인한경우의 id값
    String user_point;
    String user;
    TextView profile_nickname, my_point_int;


    //프레그먼트생성
    Fragment boardFragment = new BoardFragment();
    Fragment mapFragment = new MapFragment();
    Fragment missionFragment = new MissionFragment();
    Fragment rankingFragment = new RankingFragment();
    Fragment homeFragment = new HomeFragment();
    Fragment todaymissionFragment = new Today_Mission_Fragment();
    BottomNavigationView bottomNavigationView;
    Toolbar myToolbar;
    CircleImageView update_profile;
    FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);//프로그레스바

        getSupportActionBar().setTitle("POLLANET");
        mContext = this;
        context = this;
        intent = getIntent();
        userId = intent.getStringExtra("user_Id");
        user_id = shared_preferences.get_user_email(MainActivity.this);
        bundle = new Bundle();

        //로그인시 userId와 user_id를 비교하여 로그인한 값을 비교하여 동작
        if (userId != null){
            bundle.putString("userID", userId);
            user = userId;
        }else {
            bundle.putString("userID", user_id);
            user = user_id;
        }

        homeFragment.setArguments(bundle);
        rankingFragment.setArguments(bundle);
        missionFragment.setArguments(bundle);
        boardFragment.setArguments(bundle);
        todaymissionFragment.setArguments(bundle);


        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RemoteService.class);

        update_profile = findViewById(R.id.update_profile);
        drawerLayout = findViewById(R.id.drawerLayout);
        mainDrawerLayout = findViewById(R.id.main_drawer);
        bottomNavigationView = findViewById(R.id.bottom_menu);//바텀바

        //프로필 네임 &포인트 설정
        profile_nickname = findViewById(R.id.profile_nickname);
        my_point_int = findViewById(R.id.my_point_int);

        Call<UserStatusVO> statusVOCall = service.userStatus(user);
        statusVOCall.enqueue(new Callback<UserStatusVO>() {
            @Override
            public void onResponse(Call<UserStatusVO> call, Response<UserStatusVO> response) {
                userStatusVO = response.body();
                profile_nickname.setText(userStatusVO.getNickName());

                if (userStatusVO.getUser_del().equals("1")) {
                    Toast.makeText(getApplicationContext(), "회원탈퇴한 회원입니다", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }

            }

            @Override
            public void onFailure(Call<UserStatusVO> call, Throwable t) {
            }
        });

        Call<UserPointVO> userPointVOCall = service.userPoint(user);
        userPointVOCall.enqueue(new Callback<UserPointVO>() {
            @Override
            public void onResponse(Call<UserPointVO> call, Response<UserPointVO> response) {
                userPointVO = response.body();
                user_point = String.valueOf(userPointVO.getUser_point());
                my_point_int.setText(user_point);
            }

            @Override
            public void onFailure(Call<UserPointVO> call, Throwable t) {
            }
        });


        transaction = getSupportFragmentManager().beginTransaction();

        //로그인이 되면 메인페이지 바텀바에선 홈으로 오게됨
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
        bottomNavigationView.setSelectedItemId(R.id.tab_Home);

        //하단 바 스위치케이스
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab_Mission:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, missionFragment).commit();
                        return true;
                    case R.id.tab_Map:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, mapFragment).commit();
                        return true;
                    case R.id.tab_Home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        return true;
                    case R.id.tab_Board:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, boardFragment).commit();
                        return true;
                    case R.id.tab_Ranking:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, rankingFragment).commit();
                        return true;
                }
                return false;
            }
        });
        onRestart();


        //**햄버거바 스위치케이스
        // 추후 추가할 프래그먼트나 액티비티는 이곳에서 추가 하면됩니다.
        // */
        NavigationView navigationView = findViewById(R.id.drawer_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                transaction = getSupportFragmentManager().beginTransaction();//커밋하기전 begin 필수
                switch (item.getItemId()) {
                    case R.id.profile_update://프로필설정
                        intent = new Intent(getApplicationContext(), Edit_User_Activity.class);
                        intent.putExtra("user_Id", user);

                        startActivity(intent);
                        break;
                    case R.id.my_Mission://내미션
                        transaction.replace(R.id.container, missionFragment).commit();
                        drawerLayout.closeDrawer(mainDrawerLayout);
                        bottomNavigationView.setSelectedItemId(R.id.tab_Mission);
                        break;
                    case R.id.notice://공지사항
                        intent = new Intent(MainActivity.this, NoticeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.my_Writing://내가쓴 게시판 리스트
                        intent = new Intent(MainActivity.this, MyBoardListActivity.class);
                        intent.putExtra("user_Id", user);
                        startActivity(intent);
                        break;
                    case R.id.my_Ranking://나의 랭킹
                        transaction.replace(R.id.container, rankingFragment).commit();
                        drawerLayout.closeDrawer(mainDrawerLayout);
                        bottomNavigationView.setSelectedItemId(R.id.tab_Ranking);
                        break;
                    case R.id.logout://로그아웃
                        AlertDialog.Builder box = new AlertDialog.Builder(new ContextThemeWrapper(MainActivity.this, R.style.AlertDialogTheme));
                        box.setTitle("로그아웃");
                        box.setMessage("로그아웃 하시겠습니까??");
                        box.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                progressBar.setVisibility(View.VISIBLE);
                                shared_preferences.clear_user(MainActivity.this);
                                intent = new Intent(MainActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                        box.setNegativeButton("아니오", null);
                        AlertDialog alertDialog = box.create();
                        alertDialog.show();
                }
                drawerLayout.closeDrawer(mainDrawerLayout);
                return true;
            }
        });
        onRestart();
    }

    //햄버거바 oped/close
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_bar:
                if (drawerLayout.isDrawerOpen(mainDrawerLayout)) {
                    drawerLayout.closeDrawer(mainDrawerLayout);
                } else {
                    drawerLayout.openDrawer(mainDrawerLayout);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        TextView strNickname = findViewById(R.id.profile_nickname);
        SharedPreferences preferences = getSharedPreferences("profile", AppCompatActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String strImage1 = preferences.getString("image", "");

        if (!strImage1.equals("")) {
            CircleImageView profile_image = findViewById(R.id.profile_image);
            profile_image.setImageBitmap(BitmapFactory.decodeFile(strImage1));
        }
    }

    //햄버거바 표시
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //햄버거바  사진설정+닉네임 클릭시 정보변경 페이지 이동
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.profile_nickname:
                intent = new Intent(this, Edit_User_Activity.class);
                intent.putExtra("user_Id", user);
                break;
            case R.id.profile_image:
                intent = new Intent(this, Edit_User_Image_Activity.class);
                break;
        }
        //drawerLayout.closeDrawer(mainDrawerLayout);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        /* 다음 4가지 형태 중 하나 선택해서 사용 */

        //backKeyHandler.onBackPressed();
//        backKeyHandler.onBackPressed("\'뒤로\' 버튼을 두 번 누르면 종료됩니다.\n입력한 내용이 지워집니다.");
        //backKeyHandler.onBackPressed(5);
        backKeyHandler.onBackPressed("한번더 누르시면 앱이 종료됩니다.", 3);
    }
}