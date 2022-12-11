package com.example.finalproject;

import static com.example.finalproject.LoginPage.RemoteService.BASE_URL;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.finalproject.LoginPage.RemoteService;
import java.io.File;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Board_Insert_Activity extends AppCompatActivity {
    String strImage1;
    Toolbar toolbar;
    EditText title, content;
    ImageView image;
    Button button;
    Retrofit retrofit;
    RemoteService remoteService;
    Intent intent;
    String userId;
    CheckBox checkBox;
    String category_result="";

    private static final int CALL_CAMERA = 0;
    private static final int CALL_GALLERY = 1;
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
                strImage1 = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                cursor.close();
                image.setImageBitmap(BitmapFactory.decodeFile(strImage1));
                break;

            case CALL_CAMERA:   //카메라
                Bitmap bitmap = BitmapFactory.decodeFile(strImage1);
                image.setImageBitmap(bitmap);
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_insert);

        toolbar=(Toolbar) findViewById(R.id.board_insert_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("게시글 등록");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent = getIntent();
        userId = intent.getStringExtra("user_Id");

        title=findViewById(R.id.board_insert_title);
        content=findViewById(R.id.board_insert_content);
        image=findViewById(R.id.board_insert_image);
        button=findViewById(R.id.board_insert_button);
        checkBox=findViewById(R.id.mission_propose_check);

        category_result="1";

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()){
                    category_result="2";
                }else{
                }

            }
        });

        retrofit=new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        remoteService=retrofit.create(RemoteService.class);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder box=new AlertDialog.Builder(Board_Insert_Activity.this, R.style.AlertDialogTheme);
                box.setTitle("이미지 선택 경로를 선택하세요.");
                box.setMessage("...");
                box.setPositiveButton("앨범", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, CALL_GALLERY);
                    }
                });
                box.setNegativeButton("카메라", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            //카메라 파일명(/storage/emulated/0/Android/data/패키지명/files/Pictures/파일명)
                            File storageDir=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                            File file=File.createTempFile("img_", ".jpg", storageDir);
                            strImage1 = file.getAbsolutePath();
                            Uri uriFile = FileProvider.getUriForFile(Board_Insert_Activity.this, getPackageName(), file);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFile); startActivityForResult(intent, CALL_CAMERA);
                        }catch(Exception e) {

                        }
                    }
                });
                box.show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editTitle= title.getText().toString();
                String editContent=content.getText().toString();

                File file=new File(strImage1);
                RequestBody reqImage=RequestBody.create(
                        MediaType.parse("multipart/form-data"),file);
                MultipartBody.Part partImage=MultipartBody.Part.createFormData("b_image",file.getName(),reqImage);

                RequestBody reqTitle=RequestBody.create(
                        MediaType.parse("multipart/form-data"),editTitle);
                RequestBody reqContent=RequestBody.create(
                        MediaType.parse("multipart/form-data"),editContent);
                RequestBody userID=RequestBody.create(
                        MediaType.parse("multipart/form-data"),userId);
                RequestBody reqCategory=RequestBody.create(
                        MediaType.parse("multipart/form-data"),category_result);


                BoardVO boardVO=new BoardVO();
                boardVO.setB_title(editTitle);
                boardVO.setB_content(editContent);
                boardVO.setB_user_id(userId);
                boardVO.setB_image(strImage1);

                boardVO.setB_category(category_result);

                Call<Void> call=remoteService.boardInsert(reqTitle, reqContent, partImage, userID, reqCategory);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(Board_Insert_Activity.this, "게시글이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
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