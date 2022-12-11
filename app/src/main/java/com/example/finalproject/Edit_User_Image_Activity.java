package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class Edit_User_Image_Activity extends AppCompatActivity {

    private static final int CALL_CAMERA = 0;
    private static final int CALL_GALLERY = 1;

    String strImage1;
    SharedPreferences preferences;
    ImageView profileimage;
    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_image);

        myToolbar = (Toolbar) findViewById(R.id.profile_image_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("이미지 변경");

        preferences = getSharedPreferences("profile", AppCompatActivity.MODE_PRIVATE);
        profileimage = findViewById(R.id.profile_image);
        strImage1 = preferences.getString("image", "");

        if (!strImage1.equals("")) {
            profileimage.setImageBitmap(BitmapFactory.decodeFile(strImage1));
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.album:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CALL_GALLERY);
                break;
            case R.id.camera:
                try {
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //카메라 파일명(/storage/emulated/0/Android/data/패키지명/files/Pictures/파일명)
                    File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    File file = File.createTempFile("img_", ".jpg", storageDir);
                    strImage1 = file.getAbsolutePath();
                    Uri uriFile = FileProvider.getUriForFile(this, getPackageName(), file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFile);
                    startActivityForResult(intent, CALL_CAMERA);
                } catch (Exception e) {

                }
                break;
        }
    }

    //올린 이미지 미리보기
    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case CALL_GALLERY: //앨범
                //앨범 파일명(/storage/emulated/0/DCIM/Camera/파일명)
                Cursor cursor = getContentResolver().query(data.getData(),
                        null, null, null, null);
                cursor.moveToFirst();
                strImage1 = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                cursor.close();
                profileimage.setImageBitmap(BitmapFactory.decodeFile(strImage1));
                break;

            case CALL_CAMERA:   //카메라
                Bitmap bitmap = BitmapFactory.decodeFile(strImage1);
                profileimage.setImageBitmap(bitmap);
                break;
        }
    }

    //이미지 변경후 확인 버튼을 누른경우
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.submit:
                if (strImage1.equals("")) {
                    Toast.makeText(this, "변경할 이미지가 없습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder box = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogTheme));
                    box.setTitle("이미지변경");
                    box.setMessage("이미지를 변경하시겠습니까?");
                    box.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            SharedPreferences.Editor editor = preferences.edit();
                            preferences = getSharedPreferences("profile", AppCompatActivity.MODE_PRIVATE);
                            editor = preferences.edit();
                            editor.putString("image", strImage1);
                            editor.commit();
                            setResult(RESULT_OK);
                            finish();
                        }
                    });
                    box.setNegativeButton("취소", null);
                    AlertDialog alertDialog = box.create();
                    box.show();
                    break;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_submit, menu);
        return super.onCreateOptionsMenu(menu);
    }
}