package com.example.finalproject;

import static com.example.finalproject.LoginPage.RemoteService.BASE_URL;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.LoginPage.RemoteService;

import java.io.File;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Pati_Mission_Activity extends AppCompatActivity {

    private static final int CALL_CAMERA = 0;
    private static final int CALL_GALLERY = 1;

    String strImage;
    Retrofit retrofit;
    RemoteService service;
    Button btn_pati_mission,album;
    EditText pati_mission_title,user_pati_mission_title,user_pati_mission_content;
    ImageView pati_mission_image;
    SharedPreferences preferences1;
    Toolbar toolbar;
    Intent intent;
    String userid;
    File file;
    int code;
    String missionCode;
    MissionVO vo;
    MultipartBody.Part Bmp;



    //이미지 업로드
    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode !=RESULT_OK) return;
        switch (requestCode) {
            case CALL_GALLERY: //앨범
                //앨범 파일명(/storage/emulated/0/DCIM/Camera/파일명)
                Cursor cursor = getContentResolver().query(data.getData(),
                        null, null, null, null);
                cursor.moveToFirst();
                strImage = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                cursor.close();
                pati_mission_image.setImageBitmap(BitmapFactory.decodeFile(strImage));
                break;

            case CALL_CAMERA:   //카메라
                Bitmap bitmap = BitmapFactory.decodeFile(strImage);
                pati_mission_image.setImageBitmap(bitmap);
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pati_mission);

        toolbar=(Toolbar) findViewById(R.id.pati_mission_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RemoteService.class);
        intent = getIntent();
        missionCode = intent.getStringExtra("code");
        code = Integer.parseInt(missionCode);
        userid = intent.getStringExtra("user_Id");

        pati_mission_title = findViewById(R.id.pati_mission_title);
        user_pati_mission_content = findViewById(R.id.user_pati_mission_content);
        user_pati_mission_title = findViewById(R.id.user_pati_mission_title);
        pati_mission_image = findViewById(R.id.pati_mission_image);


        Call<MissionVO> callmission = service.missionRead(code);
        callmission.enqueue(new Callback<MissionVO>() {
            @Override
            public void onResponse(Call<MissionVO> call, Response<MissionVO> response) {
                vo = response.body();
                pati_mission_title.setText(vo.getM_title());
            }

            @Override
            public void onFailure(Call<MissionVO> call, Throwable t) {

            }
        });

        preferences1 = getSharedPreferences("profile1", AppCompatActivity.MODE_PRIVATE);
        strImage = preferences1.getString("image1", "");

        if (!strImage.equals("")) {
            pati_mission_image.setImageBitmap(BitmapFactory.decodeFile(strImage));
        }
        album = findViewById(R.id.album);
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CALL_GALLERY);
            }

        });




        btn_pati_mission = findViewById(R.id.btn_user_pati_mission);
        btn_pati_mission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String umContent = user_pati_mission_content.getText().toString().trim();
                String umTitle = user_pati_mission_title.getText().toString().trim();
                MissionUserVO UMvo = new MissionUserVO();
                UMvo.setUm_user_id(userid);
                UMvo.setMission_code(code);
                UMvo.setUm_content(umContent);
                UMvo.setUm_title(umTitle);
                UMvo.setUm_get_point(vo.getM_get_point());
                RequestBody mission_code = RequestBody.create(MediaType.parse("multipart/form-data"),Integer.toString(UMvo.getMission_code()));
                RequestBody um_user_id = RequestBody.create(MediaType.parse("multipart/form-data"),UMvo.getUm_user_id());
                RequestBody um_content = RequestBody.create(MediaType.parse("multipart/form-data"),UMvo.getUm_content());
                RequestBody um_title = RequestBody.create(MediaType.parse("multipart/form-data"),UMvo.getUm_title());
                RequestBody um_get_point = RequestBody.create(MediaType.parse("multipart/form-data"),Integer.toString(UMvo.getUm_get_point()));

                File file = new File(strImage);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
                MultipartBody.Part Bmp = MultipartBody.Part.createFormData("um_image",file.getName(),requestBody);
                Call<Void> umissionInsert = service.umInsert(mission_code,um_user_id,um_content,um_title,um_get_point,Bmp);
                umissionInsert.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(Pati_Mission_Activity.this,"미션참여가 완료되었습니다.",Toast.LENGTH_SHORT).show();
                        intent =new Intent(Pati_Mission_Activity.this,MainActivity.class);
                        intent.putExtra("user_Id",userid);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                    }
                });

            }
        });

    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(okhttp3.MultipartBody.FORM, descriptionString);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

