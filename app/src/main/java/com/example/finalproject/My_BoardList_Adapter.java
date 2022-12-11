package com.example.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.LoginPage.RemoteService;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

import retrofit2.Retrofit;

public class My_BoardList_Adapter extends RecyclerView.Adapter<My_BoardList_Adapter.ViewHolder> {
    Context context;
    List<BoardVO> myBoard;
    String user_id;
    Retrofit retrofit;
    RemoteService service;

    public My_BoardList_Adapter(Context context, List<BoardVO> myBoard, String user_id){
        this.context = context;
        this.myBoard = myBoard;
        this.user_id = user_id;
    }

    @NonNull
    @Override
    public My_BoardList_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_board_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull My_BoardList_Adapter.ViewHolder holder, int position) {
        final BoardVO vo=myBoard.get(position);
        holder.title.setText(vo.getB_title());
        holder.userid.setText(vo.getB_user_id());
        holder.recommend_total.setText(String.valueOf(vo.getB_recommend()));
        holder.commend_total.setText(String.valueOf(vo.getB_comment_count()));
        //holder.image.setImageBitmap(BitmapFactory.decodeFile(vo.getB_image()));
        holder.date.setText(vo.getB_register_Date());

        if(vo.getB_category().equals("1")) {
            holder.category.setText("자유");
        }else if(vo.getB_category().equals("2")){
            holder.category.setText("미션 추천");
        }else if(vo.getB_category().equals("3")){
            holder.category.setText("신고");
        }else if(vo.getB_category().equals("4")){
            holder.category.setText("공지사항");
        }else {
            holder.category.setText("카테고리");
        }

        //게시판 이미지 불러오기
        String image = vo.getB_image();
        if (image != null && !image.equals("")){
            Picasso.with(context).load("http://192.168.0.108:8088/api/display?fileName=" + vo.getB_image()).into(holder.image);
        }else {
            holder.image.setImageResource(R.drawable.ic_baseline_image_24);
        }
        //리스트 클릭한 경우 리드페이지 이동
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Board_Read_Activity.class);
                intent.putExtra("id", vo.getBoard_code());
                intent.putExtra("user_id", user_id);
                ((Activity)context).startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myBoard.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView image;
        TextView category, title, userid, recommend_total, commend_total, date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.board_list_item_image);
            category=itemView.findViewById(R.id.board_list_item_category);
            title=itemView.findViewById(R.id.board_list_item_title);
            userid=itemView.findViewById(R.id.board_list_item_userid);
            recommend_total=itemView.findViewById(R.id.board_list_item_recommend_total);
            commend_total=itemView.findViewById(R.id.board_list_item_commend_total);
            date=itemView.findViewById(R.id.board_list_item_date);
            cardView=itemView.findViewById(R.id.board_list_item_card);

        }
    }
}
