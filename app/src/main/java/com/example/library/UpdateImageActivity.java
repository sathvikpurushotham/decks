package com.example.library;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UpdateImageActivity extends AppCompatActivity {
    Button addBtn, selectImageBtn, deleteBtn;
    ImageView imgView;
    TextView titletv;
    int SELECT_PICTURE = 200;
    Bitmap selectedImageBitmap = null;
    String pid,ptitle;
    String title,id,count;
    byte [] bytesImage=null;
    byte[] bytesImage1=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_image);
        addBtn= findViewById(R.id.updateImgCard);
        imgView=findViewById(R.id.imageView);
        titletv=findViewById(R.id.title_input3);
        selectImageBtn= findViewById(R.id.selectImageBtn);
        deleteBtn= findViewById(R.id.deleteImageBtn);
        getIntentData();
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
                MyDatabaseHelper mydb= new MyDatabaseHelper(UpdateImageActivity.this);
                if(bytesImage1!=null)
                {
                    mydb.updateImageCard(titletv.getText().toString(),bytesImage1,Integer.valueOf(count),id);
                    Toast.makeText(UpdateImageActivity.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                }
                else if(bytesImage!=null)
                {
                    mydb.updateImageCard(titletv.getText().toString(),bytesImage,Integer.valueOf(count),id);
                    Toast.makeText(UpdateImageActivity.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();

            }
        });
    }

    void confirmDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete"+title+" ?");
        builder.setMessage("Are you sure you want to delete "+title+" ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper mydb= new MyDatabaseHelper(UpdateImageActivity.this);
                mydb.deleteImageRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create().show();
    }

    void getIntentData()
    {
        if(getIntent().hasExtra("id"))
        {
            id=getIntent().getStringExtra("id");
            title=getIntent().getStringExtra("title");
//            answer=getIntent().getStringExtra("answer");
            count=getIntent().getStringExtra("count");

            titletv.setText(title);
            MyDatabaseHelper mydb= new MyDatabaseHelper(UpdateImageActivity.this);

            bytesImage= mydb.readImageTable1(id);
            if (bytesImage!=null)
            {
                Bitmap bitmapImage = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);
                imgView.setImageBitmap(bitmapImage);
            }
            else
            {
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "No data ", Toast.LENGTH_SHORT).show();
        }
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
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    selectedImageBitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
                    bytesImage1 = byteArrayOutputStream.toByteArray();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                imgView.setImageBitmap(selectedImageBitmap);
            }
        }
    });
}