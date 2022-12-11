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

public class Recommend_Board_List_Fragment extends Fragment {

    Context context;
    Retrofit retrofit;
    RemoteService service;
    Bundle bundle;
    String user_id;
    RecyclerView recyclerView;
    List<BoardVO> revo= new ArrayList<>();
    Recommend_Board_List_Adapter REBAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup=(ViewGroup) inflater.inflate(R.layout.fragment_recommend_list, container, false);

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RemoteService.class);

        user_id = getArguments().getString("userID");
        bundle = new Bundle();
        bundle.putString("userID",user_id);

        Call<List<BoardVO>> REBLvo = service.boardListHeart();

        REBLvo.enqueue(new Callback<List<BoardVO>>() {
            @Override
            public void onResponse(Call<List<BoardVO>> call, Response<List<BoardVO>> response) {
                revo = response.body();
                recyclerView = viewGroup.findViewById(R.id.RBLFlist);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager);

                REBAdapter = new Recommend_Board_List_Adapter(getContext(),revo, user_id);
                recyclerView.setAdapter(REBAdapter);

            }

            @Override
            public void onFailure(Call<List<BoardVO>> call, Throwable t) {

            }
        });

        return viewGroup;
    }
}