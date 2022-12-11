package com.example.finalproject;

import static com.example.finalproject.LoginPage.RemoteService.BASE_URL;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalproject.LoginPage.RemoteService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Weekly_Mission_List_Fragment extends Fragment {

    Context context;
    Retrofit retrofit;
    RemoteService service;
    Bundle bundle;
    String user_id;
    RecyclerView recyclerView;
    List<MissionVO> Wcategorylist = new ArrayList<>();
    Weekly_Mission_Adapter weeklyAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_weekly_mission_list, container, false);

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RemoteService.class);
        user_id = getArguments().getString("userID");

        Call<List<MissionVO>> wMissionList = service.Wcategorylist();
        wMissionList.enqueue(new Callback<List<MissionVO>>() {
            @Override
            public void onResponse(Call<List<MissionVO>> call, Response<List<MissionVO>> response) {
                Wcategorylist = response.body();

                recyclerView = viewGroup.findViewById(R.id.WMlist);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager);


                weeklyAdapter = new Weekly_Mission_Adapter(getContext(), Wcategorylist, "weeklylist", user_id);
                recyclerView.setAdapter(weeklyAdapter);
            }

            @Override
            public void onFailure(Call<List<MissionVO>> call, Throwable t) {

            }
        });


        return viewGroup;
    }
}