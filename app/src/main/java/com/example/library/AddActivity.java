package com.example.library;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    EditText titleInput,answerInput;
    int pagesInput;
    String pid,ptitle;
    Button addCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        titleInput= findViewById(R.id.title_input);
        answerInput= findViewById(R.id.answerInput);
        pagesInput= 0;
        addCard= findViewById(R.id.addCardBtn);
        titleInput.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIntent().hasExtra("pid"))
                {
                    pid=getIntent().getStringExtra("pid");
                    ptitle=getIntent().getStringExtra("ptitle");
                }
                else
                {
                    Toast.makeText(AddActivity.this, "No data", Toast.LENGTH_SHORT).show();
                }
                if(titleInput.getText().toString().trim().equals("") || answerInput.getText().toString().trim().equals(""))
                {
                    Toast.makeText(AddActivity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else
                {


                    MyDatabaseHelper myDB= new MyDatabaseHelper(AddActivity.this);
                    myDB.addBook(titleInput.getText().toString().trim(),answerInput.getText().toString().trim(),pagesInput,pid);
                    Intent intent= new Intent( AddActivity.this,MainActivity.class );
                    intent.putExtra("pid",String.valueOf(pid));
                    intent.putExtra("ptitle",String.valueOf(ptitle));
                    startActivity(intent);
//                startActivity(starterIntent);
//                finish();
                }
            }
        });
    }
}