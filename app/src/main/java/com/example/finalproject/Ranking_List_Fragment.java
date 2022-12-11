package com.example.finalproject;

import static com.example.finalproject.LoginPage.RemoteService.BASE_URL;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalproject.LoginPage.RemoteService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Ranking_List_Fragment extends Fragment {

    Context context;
    Retrofit retrofit;
    RemoteService service;
    List<RankingListVO> list = new ArrayList<>();
    RecyclerView recyclerView;
    RankingAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_ranking_list, container, false);
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RemoteService.class);


        Call<List<RankingListVO>> callList = service.rankList();
        callList.enqueue(new Callback<List<RankingListVO>>() {
            @Override
            public void onResponse(Call<List<RankingListVO>> call, Response<List<RankingListVO>> response) {
                context = container.getContext();
                list = response.body();
                recyclerView = v.findViewById(R.id.Rlist);
                adapter = new RankingAdapter(getContext(), list, "list");
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<RankingListVO>> call, Throwable t) {
            }
        });



        return v;
    }
}