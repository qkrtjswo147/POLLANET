package com.example.finalproject;


import static com.example.finalproject.LoginPage.RemoteService.BASE_URL;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.LoginPage.RemoteService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    Context context;
    List<CommentVO> array;
    String user_id;
    RemoteService remoteService;
    Retrofit retrofit;
    String[] report;


    public CommentAdapter(Context context, List<CommentVO> array, String user_id) {
        this.context = context;
        this.array = array;
        this.user_id = user_id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_board_comment_list, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        remoteService = retrofit.create(RemoteService.class);

        CommentVO vo = array.get(position);
        holder.id.setText(vo.getC_user_id());
        holder.comment.setText(vo.getC_comment());
        holder.date.setText(vo.getB_register_Date());
        holder.commentDelete.setText(vo.getC_del());
        holder.commentUpdate.setText(vo.getC_comment());
        holder.commentReport.setText(vo.getComment_code());

        if (user_id.equals(vo.getC_user_id())) {
            holder.commentDelete.setVisibility(View.VISIBLE);
            holder.commentDelete.setText("삭제");
            holder.commentDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    AlertDialog.Builder box = new AlertDialog.Builder(v.getContext(), R.style.AlertDialogTheme);
                    box.setTitle("댓글삭제");
                    box.setMessage("댓글을 삭제하시겠습니까?");
                    box.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int code = Integer.parseInt(vo.getComment_code());
                            Call<Void> delCall = remoteService.commentDelete(code);
                            delCall.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                    holder.comment.setText("삭제된 댓글입니다.");
                                    holder.commentDelete.setVisibility(View.GONE);
                                }
                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                }
                            });
                        }
                    });
                    box.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    box.show();
                }
            });
        }

        holder.commentReport.setText("신고");
        holder.commentReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Call<List<ReportVO>> reportList = remoteService.reportList();
                reportList.enqueue(new Callback<List<ReportVO>>() {
                    @Override
                    public void onResponse(Call<List<ReportVO>> call, Response<List<ReportVO>> response) {
                        ArrayList array = new ArrayList(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<ReportVO>> call, Throwable t) {

                    }
                });
                AlertDialog.Builder box = new AlertDialog.Builder(v.getContext(), R.style.AlertDialogTheme);
                report = v.getResources().getStringArray(R.array.report);
                box.setTitle("신고");
                box.setItems(report, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int code1 = which+1;    //리폿코드
                        int code = Integer.parseInt(vo.getComment_code());//코맨트코드
                        UserReportVO uvo = new UserReportVO();
                        uvo.setReport_code(code1);
                        uvo.setReporter_id(user_id);
                        uvo.setReport_comment_code(code);
                        Call<Void> report = remoteService.CommentReport(uvo);
                        report.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                AlertDialog.Builder box = new AlertDialog.Builder(v.getContext(), R.style.AlertDialogTheme);
                                box.setTitle("신고");
                                box.setMessage("신고가 완료되었습니다.");
                                box.setPositiveButton("확인",null);
                                box.show();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                    }
                });
                box.setNegativeButton("취소",null);
                box.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, comment, date, commentDelete, commentUpdate, commentReport;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.comment_id);
            comment = itemView.findViewById(R.id.board_comment);
            date = itemView.findViewById(R.id.comment_date);
            commentDelete = itemView.findViewById(R.id.comment_delete);
            commentUpdate = itemView.findViewById(R.id.comment_update);
            commentReport = itemView.findViewById(R.id.comment_report);

        }
    }
}
