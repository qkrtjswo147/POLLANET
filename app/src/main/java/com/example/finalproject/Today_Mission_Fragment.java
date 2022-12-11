package com.example.finalproject;

import static com.example.finalproject.LoginPage.RemoteService.BASE_URL;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.LoginPage.RemoteService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Today_Mission_Fragment extends Fragment {
    Context context;
    RecyclerView recyclerView;
    StaggeredGridLayoutManager manager;
    LinearLayout linearLayout;
    CardView cardView1,cardView2,cardView3,cardView4,cardView5,cardView6;
    TextView cardView1Txt,cardView2Txt, cardView3Txt, cardView4Txt, cardView5Txt, cardView6Txt;
    TextView card_missioncode1, card_missioncode2, card_missioncode3, card_missioncode4, card_missioncode5, card_missioncode6;
    Retrofit retrofit;
    ImageView cardViewImg1, cardViewImg2, cardViewImg3, cardViewImg4, cardViewImg5, cardViewImg6;
    String user_id;
    Bundle bundle;
    RemoteService service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup=(ViewGroup) inflater.inflate(R.layout.fragment_today_mission, container, false);

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RemoteService.class);

        user_id = getArguments().getString("userID");
        bundle = new Bundle();
        bundle.putString("userID",user_id);

        cardView1 = (CardView) viewGroup.findViewById(R.id.cardView);
        cardView1Txt = (TextView) viewGroup.findViewById(R.id.cardView1Txt);
        cardViewImg1 = (ImageView) viewGroup.findViewById(R.id.cardViewImg1);
        card_missioncode1 = (TextView) viewGroup.findViewById(R.id.card_missioncode1);

        cardView2 = (CardView) viewGroup.findViewById(R.id.cardView2);
        cardView2Txt = (TextView) viewGroup.findViewById(R.id.cardView2Txt);
        cardViewImg2 = (ImageView) viewGroup.findViewById(R.id.cardViewImg2);
        card_missioncode2 = (TextView) viewGroup.findViewById(R.id.card_missioncode2);

        cardView3 = (CardView) viewGroup.findViewById(R.id.cardView3);
        cardView3Txt = (TextView) viewGroup.findViewById(R.id.cardView3Txt);
        cardViewImg3 = (ImageView) viewGroup.findViewById(R.id.cardViewImg3);
        card_missioncode3 = (TextView) viewGroup.findViewById(R.id.card_missioncode3);

        cardView4 = (CardView) viewGroup.findViewById(R.id.cardView4);
        cardView4Txt = (TextView) viewGroup.findViewById(R.id.cardView4Txt);
        cardViewImg4 = (ImageView) viewGroup.findViewById(R.id.cardViewImg4);
        card_missioncode4 = (TextView) viewGroup.findViewById(R.id.card_missioncode4);

        cardView5 = (CardView) viewGroup.findViewById(R.id.cardView5);
        cardView5Txt = (TextView) viewGroup.findViewById(R.id.cardView5Txt);
        cardViewImg5 = (ImageView) viewGroup.findViewById(R.id.cardViewImg5);
        card_missioncode5 = (TextView) viewGroup.findViewById(R.id.card_missioncode5);

        cardView6 = (CardView) viewGroup.findViewById(R.id.cardView6);
        cardView6Txt = (TextView) viewGroup.findViewById(R.id.cardView6Txt);
        cardViewImg6 = (ImageView) viewGroup.findViewById(R.id.cardViewImg6);
        card_missioncode6 = (TextView) viewGroup.findViewById(R.id.card_missioncode6);


        Call<List<MissionVO>> callMission = service.Dcategorylist();
        callMission.enqueue(new Callback<List<MissionVO>>() {
            @Override
            public void onResponse(Call<List<MissionVO>> call, Response<List<MissionVO>> response) {
                context= container.getContext();
                List<MissionVO> result = response.body();
                MissionVO missionVO1 = result.get(0);
                MissionVO missionVO2 = result.get(1);
                MissionVO missionVO3 = result.get(2);
                MissionVO missionVO4 = result.get(3);
                MissionVO missionVO5 = result.get(4);
                MissionVO missionVO6 = result.get(5);

                cardView1Txt.setText(missionVO1.getM_title());

                String char_image1 = missionVO1.getM_image();
                if (char_image1 != null && !char_image1.equals("")) {
                    Picasso.with(context).load("http://192.168.0.108:8088/api/display?fileName=/" + missionVO1.getM_image()).into(cardViewImg1);
                } else {
                    cardViewImg1.setImageResource(R.drawable.ic_baseline_image_24);
                }
                cardView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context context = v.getContext();
                        Intent intent = new Intent(v.getContext(),Select_Mission_Activity.class);
                        int code = missionVO1.getMission_code();
                        String strcode = Integer.toString(code);
                        intent.putExtra("code",strcode);
                        intent.putExtra("user_Id",user_id);
                        context.startActivity(intent);
                    }
                });

                cardView2Txt.setText(missionVO2.getM_title());
                String char_image2 = missionVO2.getM_image();
                if (char_image2 != null && !char_image2.equals("")) {
                    Picasso.with(context).load("http://192.168.0.108:8088/api/display?fileName=/" + missionVO2.getM_image()).into(cardViewImg2);
                } else {
                    cardViewImg2.setImageResource(R.drawable.ic_baseline_image_24);
                }

                cardView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context context = v.getContext();
                        Intent intent = new Intent(v.getContext(),Select_Mission_Activity.class);
                        int code = missionVO2.getMission_code();
                        String strcode = Integer.toString(code);
                        intent.putExtra("code",strcode);
                        intent.putExtra("user_Id",user_id);
                        context.startActivity(intent);
                    }
                });

                cardView3Txt.setText(missionVO3.getM_title());
                String char_image3 = missionVO3.getM_image();
                if (char_image3 != null && !char_image3.equals("")) {
                    Picasso.with(context).load("http://192.168.0.108:8088/api/display?fileName=/" + missionVO3.getM_image()).into(cardViewImg3);
                } else {
                    cardViewImg3.setImageResource(R.drawable.ic_baseline_image_24);
                }

                cardView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context context = v.getContext();
                        Intent intent = new Intent(v.getContext(),Select_Mission_Activity.class);
                        int code = missionVO3.getMission_code();
                        String strcode = Integer.toString(code);
                        intent.putExtra("code",strcode);
                        intent.putExtra("user_Id",user_id);
                        context.startActivity(intent);
                    }
                });


                cardView4Txt.setText(missionVO4.getM_title());
                String char_image4 = missionVO4.getM_image();
                if (char_image4 != null && !char_image4.equals("")) {
                    Picasso.with(context).load("http://192.168.0.108:8088/api/display?fileName=/" + missionVO4.getM_image()).into(cardViewImg4);
                } else {
                    cardViewImg4.setImageResource(R.drawable.ic_baseline_image_24);
                }

                cardView4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context context = v.getContext();
                        Intent intent = new Intent(v.getContext(),Select_Mission_Activity.class);
                        int code = missionVO4.getMission_code();
                        String strcode = Integer.toString(code);
                        intent.putExtra("code",strcode);
                        intent.putExtra("user_Id",user_id);
                        context.startActivity(intent);
                    }
                });


                cardView5Txt.setText(missionVO5.getM_title());
                String char_image5 = missionVO5.getM_image();
                if (char_image5 != null && !char_image5.equals("")) {
                    Picasso.with(context).load("http://192.168.0.108:8088/api/display?fileName=/" + missionVO5.getM_image()).into(cardViewImg5);
                } else {
                    cardViewImg5.setImageResource(R.drawable.ic_baseline_image_24);
                }

                cardView5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context context = v.getContext();
                        Intent intent = new Intent(v.getContext(),Select_Mission_Activity.class);
                        int code = missionVO5.getMission_code();
                        String strcode = Integer.toString(code);
                        intent.putExtra("code",strcode);
                        intent.putExtra("user_Id",user_id);
                        context.startActivity(intent);
                    }
                });


                cardView6Txt.setText(missionVO6.getM_title());
                String char_image6 = missionVO6.getM_image();
                if (char_image6 != null && !char_image6.equals("")) {
                    Picasso.with(context).load("http://192.168.0.108:8088/api/display?fileName=/" + missionVO6.getM_image()).into(cardViewImg6);
                } else {
                    cardViewImg6.setImageResource(R.drawable.ic_baseline_image_24);
                }
                cardView6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context context = v.getContext();
                        Intent intent = new Intent(v.getContext(),Select_Mission_Activity.class);
                        int code = missionVO6.getMission_code();
                        String strcode = Integer.toString(code);
                        intent.putExtra("code",strcode);
                        intent.putExtra("user_Id",user_id);
                        context.startActivity(intent);
                    }
                });
            }
            @Override
            public void onFailure(Call<List<MissionVO>> call, Throwable t) {
            }
        });

        return viewGroup;
    }
}