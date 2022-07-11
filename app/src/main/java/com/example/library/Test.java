package com.example.library;

import androidx.appcompat.app.AppCompatActivity;

import android.database.CursorWindow;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Field;

public class Test extends AppCompatActivity {
    ImageView imgView;
    String id,title,count;
    Bitmap bitmapImage;
    byte [] bytesImage=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        imgView=(ImageView) findViewById(R.id.imageView3);

        getIntentData();
        Test.this.setTitle(title);
    }
    void getIntentData()
    {
        if(getIntent().hasExtra("id"))
        {
            id=getIntent().getStringExtra("id");
            title=getIntent().getStringExtra("title");
            MyDatabaseHelper mydb= new MyDatabaseHelper(Test.this);
            try {
                Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
                field.setAccessible(true);
                field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
            } catch (Exception e) {
                e.printStackTrace();
            }
            bytesImage= mydb.readImageTable1(id);
            if (bytesImage!=null)
            {
                bitmapImage = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);
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
}