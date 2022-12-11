package com.example.finalproject;

import static com.example.finalproject.LoginPage.RemoteService.BASE_URL;

import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.finalproject.LoginPage.RemoteService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Daily_Mission_List_Fragment extends Fragment {

    Context context;
    Retrofit retrofit;
    RemoteService service;
    RecyclerView recyclerView;
    CardView missionItem;
    Daily_Mission_Adapter DailyAdapter;
    List<MissionVO> Dcategorylist = new ArrayList<>();
    Bundle bundle;
    String user_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_daily_mission__list_, container, false);

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RemoteService.class);

        user_id = getArguments().getString("userID");

        Call<List<MissionVO>> dMissionList = service.Dcategorylist();
        dMissionList.enqueue(new Callback<List<MissionVO>>() {
            @Override
            public void onResponse(Call<List<MissionVO>> call, Response<List<MissionVO>> response) {
                Dcategorylist = response.body();

                recyclerView = viewGroup.findViewById(R.id.DMlist);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager);

                DailyAdapter = new Daily_Mission_Adapter(getContext(), Dcategorylist, "Dailylist", user_id);
                recyclerView.setAdapter(DailyAdapter);
                DailyAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<MissionVO>> call, Throwable t) {
            }
        });


        return viewGroup;
    }
}