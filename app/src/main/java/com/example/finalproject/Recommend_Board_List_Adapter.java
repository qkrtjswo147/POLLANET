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

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

public class Recommend_Board_List_Adapter extends RecyclerView.Adapter<Recommend_Board_List_Adapter.ViewHolder> {

    Context context;
    List<BoardVO> revo;
    String user_id;

    public Recommend_Board_List_Adapter(Context context, List<BoardVO> revo, String user_id){
        this.context=context;
        this.revo=revo;
        this.user_id=user_id;

    }

    @NonNull
    @Override
    public Recommend_Board_List_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.item_recommend_board_list,parent, false);
        ViewHolder holder = new ViewHolder(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Recommend_Board_List_Adapter.ViewHolder holder, int position) {
        final BoardVO boardvo=revo.get(position);



        String reimage = boardvo.getB_image();
        if(reimage != null && !reimage.equals("")){
            Picasso.with(context).load("http://192.168.0.108:8088/api/display?fileName="+boardvo.getB_image()).into(holder.board_image);
        }else{
            holder.board_image.setImageResource(R.drawable.login_logo);
        }
        int code = boardvo.getBoard_code();
        String strcode = Integer.toString(code);
        holder.board_code.setText(strcode);
        if(boardvo.getB_category().equals("1")) {
            holder.board_category.setText("자유");
        }else if(boardvo.getB_category().equals("2")){
            holder.board_category.setText("미션 추천");
        }else if(boardvo.getB_category().equals("3")){
            holder.board_category.setText("신고");
        }else if(boardvo.getB_category().equals("4")){
            holder.board_category.setText("공지사항");
        }else {
            holder.board_category.setText("카테고리");
        }
        holder.board_title.setText(boardvo.getB_title());
        holder.board_userid.setText(boardvo.getB_user_id());
        holder.board_date.setText(boardvo.getB_register_Date());

        holder.cardViewRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Board_Read_Activity.class);
                intent.putExtra("id", boardvo.getBoard_code());
                intent.putExtra("user_id", user_id);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return revo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardViewRB;
        TextView  board_category, board_title, board_userid,board_date,board_code;
        ImageView board_image;
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewRB=itemView.findViewById(R.id.cardViewRB);
            board_category=itemView.findViewById(R.id.recommend_board_category);
            board_title=itemView.findViewById(R.id.recommend_board_title);
            board_userid=itemView.findViewById(R.id.recommend_board_userid);
            board_date=itemView.findViewById(R.id.recommend_board_date);
            board_code=itemView.findViewById(R.id.recommend_board_code);
            board_image=itemView.findViewById(R.id.recommend_board_image);





        }
    }
}
