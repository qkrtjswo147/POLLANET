package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.LoginPage.RemoteService;
import com.squareup.picasso.Picasso;

import java.nio.charset.StandardCharsets;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class Daily_Mission_Adapter extends RecyclerView.Adapter<Daily_Mission_Adapter.ViewHolder> {

    Context context;
    Retrofit retrofit;
    RemoteService service;
    String name;
    List<MissionVO> Dcategorylist;
    MissionVO dayMissionListVO;
    String userid;
    int missionCode;

    //미션 어댑터
    public Daily_Mission_Adapter(Context context, List<MissionVO> dayMissionList , String name, String userid) {
        this.context = context;
        this.Dcategorylist = dayMissionList;
        this.name = name;
        this.userid = userid;
    }
    //**뷰홀더
    // item_misstion_list를 화면에 뿌리고 holder에 연결
    // */
    @NonNull
    @Override
    public Daily_Mission_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_today_misstion_list,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //뷰홀더가 재활용 될때 실행되는 메서드
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        dayMissionListVO = Dcategorylist.get(position);
        String mission_image=dayMissionListVO.getM_image();
        holder.missionTitle.setText(dayMissionListVO.getM_title());

        if(mission_image != null && !mission_image.equals("")){
            Picasso.with(context).load("http://192.168.0.108:8088/api/display?fileName=/"+dayMissionListVO.getM_image()).into(holder.missionImage);
        }else{
            holder.missionImage.setImageResource(R.drawable.login_logo);
        }

        int code = dayMissionListVO.getMission_code();
        String strcode = Integer.toString(code);
        holder.missionCode.setText(strcode);
        holder.missionItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(v.getContext(),Select_Mission_Activity.class);
                intent.putExtra("code",strcode);
                intent.putExtra("user_Id",userid);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return Dcategorylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //객체선언
        ImageView missionImage;
        TextView missionTitle, missionCode;
        CardView missionItem;

        //뷰 홀더 클래스
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            missionImage = itemView.findViewById(R.id.mission_image);
            missionTitle = itemView.findViewById(R.id.mission_title);
            missionItem = itemView.findViewById(R.id.mission_item);
            missionCode = itemView.findViewById(R.id.mission_code);

        }
    }
}
