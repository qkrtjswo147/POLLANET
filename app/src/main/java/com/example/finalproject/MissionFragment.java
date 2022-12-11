package com.example.finalproject;

import static com.example.finalproject.LoginPage.RemoteService.BASE_URL;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.LoginPage.RemoteService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MissionFragment extends Fragment {

    Fragment weekly_mission_list_fragment;
    Fragment daily_mission_list_fragment;
    Bundle bundle;
    Retrofit retrofit;
    RemoteService service;
    TextView today_mission_count;
    String user_id;
    Context ct;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup v=(ViewGroup) inflater.inflate(R.layout.fragment_mission, container, false);
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RemoteService.class);
        ct = container.getContext();

        user_id = getArguments().getString("userID");
        ImageView imageView=(ImageView) v.findViewById(R.id.mission_view);
        TextView today_mission_count = (TextView) v.findViewById(R.id.today_mission_count);

        Call<Integer> clearmissionTotal = service.missionClearTotal(user_id);
        clearmissionTotal.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                String clearTotal = Integer.toString(response.body());

                today_mission_count.setText(clearTotal);
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
            }
        });
        Glide.with(ct).load(R.raw.rash_lv2).into(imageView);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        daily_mission_list_fragment=new Daily_Mission_List_Fragment();
        weekly_mission_list_fragment=new Weekly_Mission_List_Fragment();
        bundle = new Bundle();
        bundle.putString("userID",user_id);
        daily_mission_list_fragment.setArguments(bundle);
        weekly_mission_list_fragment.setArguments(bundle);


        FragmentTransaction childTransaction = getChildFragmentManager().beginTransaction();

        childTransaction.replace(R.id.daily_mission_frame, daily_mission_list_fragment);
        childTransaction.replace(R.id.weekly_mission_frame, weekly_mission_list_fragment);
        childTransaction.commit();
    }
}