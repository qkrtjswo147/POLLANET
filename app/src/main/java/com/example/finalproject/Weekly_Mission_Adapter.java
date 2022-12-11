package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
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

import java.util.List;

import retrofit2.Retrofit;

public class Weekly_Mission_Adapter extends RecyclerView.Adapter<Weekly_Mission_Adapter.ViewHolder> {

    Context context;
    Retrofit retrofit;
    RemoteService service;
    String name;
    List<MissionVO> Wcategorylist;
    MissionVO weekMissionListVO;
    CardView missionItem;
    String userid;
    //미션 어댑터
    public Weekly_Mission_Adapter(Context context, List<MissionVO> weekMissionList , String name ,String userid) {
        this.context = context;
        this.Wcategorylist = weekMissionList;
        this.name = name;
        this.userid = userid;
    }

    @NonNull
    @Override
    public Weekly_Mission_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_today_misstion_list,parent,false);
        ViewHolder holder = new ViewHolder(view);

        return holder;

    }


    //뷰홀더가 재활용 될때 실행되는 메서드
    @Override
    public void onBindViewHolder(@NonNull Weekly_Mission_Adapter.ViewHolder holder, int position) {
        weekMissionListVO = Wcategorylist.get(position);
        String mission_image=weekMissionListVO.getM_image();
        holder.missionTitle.setText(weekMissionListVO.getM_title());
        int code = weekMissionListVO.getMission_code();
        String strcode = Integer.toString(code);
        holder.missionCode.setText(strcode);

        if(mission_image != null && !mission_image.equals("")){
            Picasso.with(context).load("http://192.168.0.108:8088/api/display?fileName=/"+weekMissionListVO.getM_image()).into(holder.missionImage);
        }else{
            holder.missionImage.setImageResource(R.drawable.login_logo);
        }

        //각각의 미션 아이템을 클릭한 경우 select 액티비티로 이동
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
        return Wcategorylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView missionImage;
        TextView missionTitle,missionCode;
        CardView missionItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            missionImage = itemView.findViewById(R.id.mission_image);
            missionTitle = itemView.findViewById(R.id.mission_title);
            missionItem = itemView.findViewById(R.id.mission_item);
            missionCode = itemView.findViewById(R.id.mission_code);


        }
    }
}
