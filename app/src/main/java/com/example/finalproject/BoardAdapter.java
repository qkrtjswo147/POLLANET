package com.example.finalproject;

import static com.example.finalproject.LoginPage.RemoteService.BASE_URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
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

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {
    Context context;
    List<BoardVO> array;
    String user_id;


    public BoardAdapter(Context context, List<BoardVO> array, String user_id){
        this.context= context;
        this.array=array;
        this.user_id=user_id;
    }


    @NonNull
    @Override
    public BoardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_board_list, parent, false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BoardAdapter.ViewHolder holder, int position) {

        final BoardVO vo=array.get(position);
        holder.title.setText(vo.getB_title());
        holder.userid.setText(vo.getB_user_id());

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

        holder.recommend_total.setText(String.valueOf(vo.getB_heart()));
        holder.commend_total.setText(String.valueOf(vo.getB_comment_count()));
        //holder.image.setImageBitmap(BitmapFactory.decodeFile(vo.getB_image()));
        holder.date.setText(vo.getB_register_Date());

        //게시판 이미지 불러오기
        String image = vo.getB_image();
        if (image != null && !image.equals("")){
            Picasso.with(context).load("http://192.168.0.108:8088/api/display?fileName=" + vo.getB_image()).into(holder.image);
        }else {
            holder.image.setImageResource(R.drawable.ic_baseline_image_24);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Board_Read_Activity.class);
                intent.putExtra("id", vo.getBoard_code());
                intent.putExtra("user_id", user_id);
                context.startActivity(intent);
                ((Activity)context).recreate();
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
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
