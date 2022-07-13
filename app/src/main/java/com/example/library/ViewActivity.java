package com.example.library;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewActivity extends AppCompatActivity {
    String id, title, answer, count;
    TextView questiontv,answertv,counttv;
    Button reveal, update, reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        questiontv= findViewById(R.id.question_tv);
        answertv= findViewById(R.id.answer_tv);
        counttv= findViewById(R.id.count_tv);
        reveal= findViewById(R.id.revealBtn);
        update= findViewById(R.id.updateViewBtn);
        reset = findViewById(R.id.resetCountBtn);
        getIntentData();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count=String.valueOf(0);
                MyDatabaseHelper mydb= new MyDatabaseHelper(ViewActivity.this);
                mydb.updateData(id,title,answer,0);
                counttv.setText("Count: "+count);
            }
        });

        reveal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answertv.setVisibility(View.VISIBLE);
                counttv.setVisibility(View.VISIBLE);
                MyDatabaseHelper mydb= new MyDatabaseHelper(ViewActivity.this);
                int c=Integer.valueOf(count)+1;
                count=String.valueOf(c);
                mydb.updateData2(id,title,answer,c);
                counttv.setText("Count: "+count);
                reveal.setEnabled(false);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewActivity.this,UpdateActivity.class);
                intent.putExtra("id",String.valueOf(id));
                intent.putExtra("title",String.valueOf(title));
                intent.putExtra("answer",String.valueOf(answer));
                intent.putExtra("count",String.valueOf(count));
                startActivity(intent);
                finish();
            }
        });
    }

    void getIntentData()
    {
        if(getIntent().hasExtra("id"))
        {
            id=getIntent().getStringExtra("id");
            title=getIntent().getStringExtra("title");
            answer=getIntent().getStringExtra("answer");
            count=getIntent().getStringExtra("count");

            questiontv.setText("Question: "+title);
            answertv.setText("Answer: "+answer);

        }
        else
        {
            Toast.makeText(this, "No data ", Toast.LENGTH_SHORT).show();
        }
    }
}