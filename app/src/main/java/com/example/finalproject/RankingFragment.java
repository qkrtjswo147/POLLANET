package com.example.finalproject;

import static com.example.finalproject.LoginPage.RemoteService.BASE_URL;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.finalproject.LoginPage.RemoteService;
import com.squareup.picasso.Picasso;

import java.nio.charset.StandardCharsets;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RankingFragment extends Fragment {

    Context context;
    String userId;  //자동로그인을 체크안하고 로그인한경우의 id값
    String user_id;//자동로그인을 체크하고 로그인한경우의 id값
    Retrofit retrofit;
    RemoteService service;
    Fragment Ranking_List_Fragment;
    RecyclerView Rlist;
    TextView my_Rank, topNickname, secondNickname, thirdNickname, topPoint, secondPoint, thirdPoint;
    CircleImageView top_image, second_image, third_image,profile_image;
    RankingListVO rankingVO0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_ranking, container, false);

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RemoteService.class);
        user_id = getArguments().getString("userID");
        my_Rank = (TextView) v.findViewById(R.id.my_Ranking_score);
        topNickname = (TextView) v.findViewById(R.id.ranking_top_nickname);
        secondNickname = (TextView) v.findViewById(R.id.ranking_second_nickname);
        thirdNickname = (TextView) v.findViewById(R.id.ranking_third_nickname);
        topPoint = (TextView) v.findViewById(R.id.ranking_top_point);
        secondPoint = (TextView) v.findViewById(R.id.ranking_second_point);
        thirdPoint = (TextView) v.findViewById(R.id.ranking_third_point);
        top_image = (CircleImageView) v.findViewById(R.id.ranking_top_image);
        second_image = (CircleImageView) v.findViewById(R.id.ranking_second_image);
        third_image = (CircleImageView) v.findViewById(R.id.ranking_third_image);
        profile_image = (CircleImageView) v.findViewById(R.id.my_Ranking_profile_image);

        Call<RankingListVO> rankCall = service.userRankread(user_id);
        rankCall.enqueue(new Callback<RankingListVO>() {
            @Override
            public void onResponse(Call<RankingListVO> call, Response<RankingListVO> response) {
                context= container.getContext();
                rankingVO0 = response.body();
                String mrank = rankingVO0.getRank();
                String mrank1 = mrank.substring(0,mrank.length()-2) + "위";
                my_Rank.setText(mrank1);
                rankingVO0.getCharacter_image();
                String char_image0 = rankingVO0.getCharacter_image();
                if (char_image0 != null && !char_image0.equals("")) {
                    Picasso.with(context).load("http://192.168.0.108:8088/api/display?fileName=" + rankingVO0.getCharacter_image()).into(profile_image);
                }
            }

            @Override
            public void onFailure(Call<RankingListVO> call, Throwable t) {

            }
        });




        Call<List<RankingListVO>> calltopRanking = service.rankList();
        calltopRanking.enqueue(new Callback<List<RankingListVO>>() {
            @Override
            public void onResponse(Call<List<RankingListVO>> call, Response<List<RankingListVO>> response) {
                List<RankingListVO> result = response.body();
                RankingListVO rankingVO1 = result.get(0);
                RankingListVO rankingVO2 = result.get(1);
                RankingListVO rankingVO3 = result.get(2);
                context= container.getContext();




                String char_image = rankingVO1.getCharacter_image();
                if (char_image != null && !char_image.equals("")) {
                    Picasso.with(context).load("http://192.168.0.108:8088/api/display?fileName=" + rankingVO1.getCharacter_image()).into(top_image);
                }
                String char_image1 = rankingVO2.getCharacter_image();
                if (char_image != null && !char_image.equals("")) {
                    Picasso.with(context).load("http://192.168.0.108:8088/api/display?fileName=" + rankingVO2.getCharacter_image()).into(second_image);
                }
                String char_image2 = rankingVO3.getCharacter_image();
                if (char_image != null && !char_image.equals("")) {
                    Picasso.with(context).load("http://192.168.0.108:8088/api/display?fileName=" + rankingVO3.getCharacter_image()).into(third_image);
                }

                topNickname.setText(rankingVO1.getNickName());
                secondNickname.setText(rankingVO2.getNickName());
                thirdNickname.setText(rankingVO3.getNickName());


                topPoint.setText(rankingVO1.getUser_point());
                secondPoint.setText(rankingVO2.getUser_point());
                thirdPoint.setText(rankingVO3.getUser_point());


            }

            @Override
            public void onFailure(Call<List<RankingListVO>> call, Throwable t) {
            }
        });
        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Ranking_List_Fragment = new Ranking_List_Fragment();
        FragmentTransaction childTransaction = getChildFragmentManager().beginTransaction();
        childTransaction.replace(R.id.ranking_list, Ranking_List_Fragment).commit();

    }


}