package com.example.finalproject;

import static com.example.finalproject.LoginPage.RemoteService.BASE_URL;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.finalproject.LoginPage.RemoteService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoardFragment extends Fragment {
    RecyclerView list;
    RemoteService remoteService;
    Retrofit retrofit;
    BoardAdapter adapter;
    List<BoardVO> array = new ArrayList<>();
    SearchView searchView;
    FloatingActionButton fButton;
    String user_id;
    Bundle bundle;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_board, container, false);

        user_id = getArguments().getString("userID");
        bundle = new Bundle();
        bundle.putString("userID", user_id);

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        remoteService = retrofit.create(RemoteService.class);
        swipeRefreshLayout = v.findViewById(R.id.swipe_Refresh_Layout);

        fButton = v.findViewById(R.id.board_toInsert_button);
        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Board_Insert_Activity.class);
                intent.putExtra("user_Id", user_id);
                startActivity(intent);
            }
        });
        searchView = (SearchView) v.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Call<List<BoardVO>> call = remoteService.boardList(newText);
                call.enqueue(new Callback<List<BoardVO>>() {
                    @Override
                    public void onResponse(Call<List<BoardVO>> call, Response<List<BoardVO>> response) {
                        array = response.body();

                        list = v.findViewById(R.id.board_list);
                        adapter = new BoardAdapter(getContext(), array, user_id);
                        list.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<BoardVO>> call, Throwable t) {
                    }
                });


                return false;
            }
        });


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //레트로핏 정의
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        remoteService = retrofit.create(RemoteService.class);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Call<List<BoardVO>> call = remoteService.boardList("");
                call.enqueue(new Callback<List<BoardVO>>() {
                    @Override
                    public void onResponse(Call<List<BoardVO>> call, Response<List<BoardVO>> response) {
                        array = response.body();

                        list = view.findViewById(R.id.board_list);
                        adapter = new BoardAdapter(getContext(), array, user_id);
                        list.setAdapter(adapter);

                        Toast.makeText(getContext(), "새로고침", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);

                    }

                    @Override
                    public void onFailure(Call<List<BoardVO>> call, Throwable t) {
                    }
                });
            }
        });
    }

}