package com.example.library;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;

public class ImageCardView extends AppCompatActivity {
    TextView questiontv,counttv;
    ImageView imgView;
    String id,title,count;
    Button reveal,update,reset;
    Bitmap bitmapImage;
    byte [] bytesImage=null;
    private boolean zoomOut =  false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_card_view);
        questiontv = findViewById(R.id.question_tv);
        counttv = findViewById(R.id.count_tv);
        imgView = findViewById(R.id.imageView2);
        reveal= findViewById(R.id.revealBtn);
        update= findViewById(R.id.updateViewBtn);
        reset = findViewById(R.id.resetCountBtn);
        getIntentData();

        reveal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgView.setVisibility(View.VISIBLE);
                counttv.setVisibility(View.VISIBLE);
                MyDatabaseHelper mydb= new MyDatabaseHelper(ImageCardView.this);
                int c=Integer.valueOf(count)+1;
                count=String.valueOf(c);
                mydb.updateImageCard1(title,bytesImage,c,id);
                counttv.setText(count);
                reveal.setEnabled(false);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ImageCardView.this,UpdateImageActivity.class);
                intent.putExtra("id",String.valueOf(id));
                intent.putExtra("title",String.valueOf(title));
//                intent.putExtra("answer",String.valueOf(answer));
                intent.putExtra("count",String.valueOf(count));
                startActivity(intent);
            }
        });
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageCardView.this, Test.class);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bytes = stream.toByteArray();
                intent.putExtra("BMP",bytes);
                intent.putExtra("title",String.valueOf(title));
                startActivity(intent);
            }
        });
    }

    void getIntentData()
    {
        if(getIntent().hasExtra("id"))
        {
            id=getIntent().getStringExtra("id");
            title=getIntent().getStringExtra("title");
//            answer=getIntent().getStringExtra("answer");
            count=getIntent().getStringExtra("count");

            questiontv.setText(title);
            MyDatabaseHelper mydb= new MyDatabaseHelper(ImageCardView.this);
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