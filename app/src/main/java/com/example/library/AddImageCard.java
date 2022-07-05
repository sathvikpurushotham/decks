package com.example.library;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddImageCard extends AppCompatActivity {
    Button addBtn, selectImageBtn;
    ImageView imgView;
    TextView title;
    int SELECT_PICTURE = 200;
    Bitmap selectedImageBitmap = null;
    String pid,ptitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image_card);
        addBtn= findViewById(R.id.updateImgCard);
        imgView=findViewById(R.id.imageView);
        title=findViewById(R.id.title_input3);
        selectImageBtn= findViewById(R.id.selectImageBtn);
        title.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launches photo picker for videos only in single select mode.
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                launchSomeActivity.launch(intent);
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIntent().hasExtra("pid"))
                {
                    pid=getIntent().getStringExtra("pid");
                    ptitle=getIntent().getStringExtra("ptitle");
                }
                else
                {
                    Toast.makeText(AddImageCard.this, "No data", Toast.LENGTH_SHORT).show();
                }
                if(title.getText().toString().trim().equals("") ||selectedImageBitmap==null)
                {
                    Toast.makeText(AddImageCard.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    selectedImageBitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
                    byte[] bytesImage = byteArrayOutputStream.toByteArray();
                    MyDatabaseHelper myDB= new MyDatabaseHelper(AddImageCard.this);
                    myDB.addImageCard(title.getText().toString().trim(),bytesImage,0,pid);
                    Intent intent= new Intent( AddImageCard.this,MainActivity.class );
                    intent.putExtra("pid",String.valueOf(pid));
                    intent.putExtra("ptitle",String.valueOf(ptitle));
                    startActivity(intent);
                }

            }
        });
    }
    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // do your operation from here....
                    if (data != null
                            && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        try {
                            selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        imgView.setImageBitmap(selectedImageBitmap);
                    }
                }
            });

}