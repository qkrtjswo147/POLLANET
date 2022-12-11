package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

    Context context;
    List<BoardVO> NoticeList;
    BoardVO NoticeListVO;
    String name;


    public NoticeAdapter(Context context, List<BoardVO> NoticeVO, String name) {
        this.context = context;
        this.NoticeList = NoticeVO;
        this.name = name;

    }

    @NonNull
    @Override
    public NoticeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 파라미터로 전달되는 뷰그룹 객체는 각 아이템을 위한 뷰그룹 객체이므로
        // XML 레이아웃을 인플레이션하여 이 뷰그룹 객체에 전달한다.
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_notice_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeAdapter.ViewHolder holder, int position) {
        NoticeListVO = NoticeList.get(position);
        holder.notice_title.setText(NoticeListVO.getB_title());
        holder.notice_id.setText(NoticeListVO.getB_user_id());
        holder.notice_insertDate.setText(NoticeListVO.getB_update_Date());
        holder.notice_content.setText(NoticeListVO.getB_content());
        holder.notice_code.setText(String.valueOf(NoticeListVO.getBoard_code()));

        holder.notice_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent =new Intent(v.getContext(),Notice_Read_Activity.class);
                intent.putExtra("noticode",String.valueOf(NoticeListVO.getBoard_code()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return NoticeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView notice_title, notice_id,notice_insertDate,notice_content, notice_code;
        RelativeLayout notice_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notice_title = itemView.findViewById(R.id.notice_title);
            notice_id = itemView.findViewById(R.id.notice_id);
            notice_insertDate = itemView.findViewById(R.id.notice_insertDate);
            notice_content = itemView.findViewById(R.id.notice_content);
            notice_item = itemView.findViewById(R.id.notice_item);
            notice_code = itemView.findViewById(R.id.notice_code);


        }
    }
}
