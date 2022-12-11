package com.example.finalproject;

import static com.example.finalproject.LoginPage.RemoteService.BASE_URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalproject.LoginPage.RemoteService;
import com.example.finalproject.LoginPage.UserStatusVO;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment {

    Toast t;
    Context context;
    Fragment todayMissionFragment;
    Fragment recommendFragment;
    BottomNavigationView bottomNavigationView;
    UserPointVO userPointVO;
    Retrofit retrofit;
    RemoteService service;
    String user_id;
    String user_point;
    String userID;
    Bundle bundle;
    ImageView imageView;
    TextView myCharLevel;
    ConstraintLayout cardViewBackground;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup v=(ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RemoteService.class);

        TextView recommend_board=(TextView) v.findViewById(R.id.recommend_board_text_1);
        TextView today_mission=(TextView) v.findViewById(R.id.today_mission_text);
        TextView mission_perfection = (TextView) v.findViewById(R.id.mission_perfection);
        TextView char_level = (TextView) v.findViewById(R.id.char_level);
        myCharLevel=v.findViewById(R.id.my_chara_level);

        userID = getArguments().getString("userID");
        bundle = new Bundle();
        bundle.putString("userID",userID);

        imageView=(ImageView) v.findViewById(R.id.character_image);
        cardViewBackground=v.findViewById(R.id.cardView_background);

//        Glide.with(this).load(R.raw.ozoni_egg).into(imageView);
        Call<UserPointVO> userPointVOCall = service.userPoint(userID);
        userPointVOCall.enqueue(new Callback<UserPointVO>() {
            @Override
            public void onResponse(Call<UserPointVO> call, Response<UserPointVO> response) {
                userPointVO = response.body();
                user_point = String.valueOf(userPointVO.getUser_point());
                mission_perfection.setText(user_point);
            }
            @Override
            public void onFailure(Call<UserPointVO> call, Throwable t) {
            }
        });


        recommend_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment boardFragment=new BoardFragment();
                FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, boardFragment).commit();
            }
        });
        today_mission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment missionFragment=new com.example.finalproject.MissionFragment();
                FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, missionFragment).commit();
            }
        });

        //캐릭터 선택창으로
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Char_Select_Activity.class);
                intent.putExtra("userID",userID);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        Call<User_charVO> charCall = service.userCharRead(userID);
        charCall.enqueue(new Callback<User_charVO>() {
            @Override
            public void onResponse(Call<User_charVO> call, Response<User_charVO> response) {
                context= container.getContext();
                User_charVO UVO = response.body();
                if(UVO.getCharacter_sort().equals("0")){
                        t=Toast.makeText(getContext(), "캐릭터를 선택해주세요", Toast.LENGTH_LONG);
                        t.setGravity(Gravity.CENTER,0,+100);
                        t.show();

                    imageView.setClickable(true);
                }else {
//                    imageView.setClickable(false);
                }
                if(UVO.getCharacter_grade().equals("1")){
                    myCharLevel.setText("LV.1");
                }else if(UVO.getCharacter_grade().equals("2")){
                    myCharLevel.setText("LV.2");
                }else if(UVO.getCharacter_grade().equals("3")){
                    myCharLevel.setText("LV.3");
                }else if(UVO.getCharacter_grade().equals("4")){
                    myCharLevel.setText("LV.4");
                }

                if(UVO.getCharacter_sort().equals("1")){
                    cardViewBackground.setBackgroundColor(Color.parseColor("#9A63A8"));
                }else if(UVO.getCharacter_sort().equals("2")){
                    cardViewBackground.setBackgroundColor(Color.parseColor("#5FB36D"));
                }else if(UVO.getCharacter_sort().equals("3")){
                    cardViewBackground.setBackgroundColor(Color.parseColor("#E58BA9"));
                }else if(UVO.getCharacter_sort().equals("4")){
                    cardViewBackground.setBackgroundColor(Color.parseColor("#B7F1FF"));
                }else if(UVO.getCharacter_sort().equals("5")){
                    cardViewBackground.setBackgroundColor(Color.parseColor("#E6C72C"));
                }

                String strImage = UVO.getCharacter_image();
                if (strImage != null && !strImage.equals("")) {
                    Glide.with(context).load("http://192.168.0.108:8088/api/display?fileName=" + strImage).into(imageView);
                }




            }

            @Override
            public void onFailure(Call<User_charVO> call, Throwable t) {

            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recommendFragment=new com.example.finalproject.Recommend_Board_List_Fragment();
        todayMissionFragment=new com.example.finalproject.Today_Mission_Fragment();

        FragmentTransaction childTransaction = getChildFragmentManager().beginTransaction();

        childTransaction.replace(R.id.today_mission_container, todayMissionFragment);
        childTransaction.replace(R.id.recommend_board_container, recommendFragment);

        childTransaction.commit();
        todayMissionFragment.setArguments(bundle);
        recommendFragment.setArguments(bundle);



    }


}