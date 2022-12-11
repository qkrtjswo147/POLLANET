package com.example.finalproject;

import static com.example.finalproject.LoginPage.RemoteService.BASE_URL;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.LoginPage.RemoteService;
import com.example.finalproject.LoginPage.UserStatusVO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {

    Context context;
    Retrofit retrofit;
    RemoteService service;
    List<RankingListVO> list;
    String name;


    //랭킹어댑터
    public RankingAdapter(Context context, List<RankingListVO> list,String name) {
        this.context = context;
        this.list = list;
        this.name = name;
    }

    //**뷰홀더
    // item_ranking_list를 화면에 뿌리고 holder에 연결
    // */
    @NonNull
    @Override
    public RankingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_ranking_list,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    //뷰홀더가 재활용 될때 실행되는 메서드
    @Override
    public void onBindViewHolder(@NonNull RankingAdapter.ViewHolder holder, int position) {
        RankingListVO listVo = list.get(position);
        holder.setItem(listVo);

    }
    //아이템 개수 조회
    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //객체선언
        TextView userRanking, userNickname, rankingPoint;
        CircleImageView rankingChar;
        CardView rankingItem;

        //뷰 홀더 클래스
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userRanking = itemView.findViewById(R.id.user_rangking);
            userNickname = itemView.findViewById(R.id.ranking_user_nickname);
            rankingPoint = itemView.findViewById(R.id.ranking_point);
            rankingChar = itemView.findViewById(R.id.ranking_char);
            rankingItem = itemView.findViewById(R.id.ranking_item);
        }
        public void setItem(RankingListVO list) {
            String rank = list.getRank();
            rank = rank.substring(0,rank.length()-2) + "위";
            userRanking.setText(rank);
            userNickname.setText(list.getNickName());
            rankingPoint.setText(list.getUser_point());
            String char_image = list.getCharacter_image();
            if (char_image != null && !char_image.equals("")) {
                Picasso.with(context).load("http://192.168.0.108:8088/api/display?fileName=" + list.getCharacter_image()).into(rankingChar);

            }
        }
    }
}
