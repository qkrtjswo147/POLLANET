package com.example.finalproject;

import static com.example.finalproject.LoginPage.RemoteService.BASE_URL;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.LoginPage.RemoteService;
import com.example.finalproject.LoginPage.UserStatusVO;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Character_List_Adapter extends RecyclerView.Adapter<Character_List_Adapter.ViewHolder> {
    Context context;
    List<CharacterVO> CharList;
    String user_id;
    Retrofit retrofit;
    RemoteService service;


    public Character_List_Adapter(Context context, List<CharacterVO> CharList, String user_id) {
        this.context = context;
        this.CharList = CharList;
        this.user_id = user_id;

    }


    @NonNull
    @Override
    public Character_List_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_chara_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Character_List_Adapter.ViewHolder holder, int position) {

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RemoteService.class);

        CharacterVO CharVO = CharList.get(position);
        String char_image = CharVO.getCharacter_image();
        if (char_image != null && !char_image.equals("")) {
            Picasso.with(context).load("http://192.168.0.108:8088/api/display?fileName=" + CharVO.getCharacter_image()).into(holder.char_image);
        } else {
            holder.char_image.setImageResource(R.drawable.ic_baseline_image_24);
        }
        holder.char_code.setText(CharVO.getCharacter_code());
        holder.char_name.setText(CharVO.getCharacter_name());
        holder.char_grade.setText(CharVO.getCharacter_grade());
        holder.char_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder box = new AlertDialog.Builder(v.getContext(), R.style.AlertDialogTheme);
                box.setTitle("캐릭터 선택");
                box.setMessage(CharVO.getCharacter_name() + "을 선택하시겠습니까? \n캐릭터는 한번만 선택 가능합니다.");
                box.setPositiveButton("선택", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserStatusVO usVO = new UserStatusVO();
                        usVO.setUser_id(user_id);
                        usVO.setCharacter_sort(CharVO.getCharacter_sort());
                        Call<Void> userCall = service.userCharSort(usVO);
                        userCall.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Intent intent = new Intent(v.getContext(), MainActivity.class);
                                intent.putExtra("image", CharVO.getCharacter_image());
                                intent.putExtra("name", CharVO.getCharacter_name());
                                intent.putExtra("userID", user_id);
                                context.startActivity(intent);
                                ((Activity)context).finish();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });


                    }
                });
                box.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                box.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return CharList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView char_code, char_grade, char_name;
        ImageView char_image;
        CardView char_Card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            char_code = itemView.findViewById(R.id.char_code);
            char_grade = itemView.findViewById(R.id.char_grade);
            char_name = itemView.findViewById(R.id.char_name);
            char_image = itemView.findViewById(R.id.item_chara_image);
            char_Card = itemView.findViewById(R.id.char_Card);

        }
    }
}
