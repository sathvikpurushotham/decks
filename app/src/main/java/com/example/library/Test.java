package com.example.library;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class Test extends AppCompatActivity {
    ImageView imgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        imgView=(ImageView) findViewById(R.id.imageView3);
        byte[] bytes = getIntent().getByteArrayExtra("BMP");
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        imgView.setImageBitmap(bitmap);

        Test.this.setTitle(getIntent().getStringExtra("title"));
    }
}