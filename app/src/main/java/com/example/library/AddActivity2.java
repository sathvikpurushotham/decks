package com.example.library;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity2 extends AppCompatActivity {
    EditText titleInput,answerInput;
    int pagesInput;

    Button addCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add2);
        titleInput= findViewById(R.id.title_input);
        answerInput= findViewById(R.id.answerInput);
        pagesInput= 0;
        addCard= findViewById(R.id.addCardBtn);
        titleInput.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(titleInput.getText().toString().trim().equals("") )
                {
                    Toast.makeText(AddActivity2.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    MyDatabaseHelper myDB= new MyDatabaseHelper(AddActivity2.this);
                    myDB.addDeck(titleInput.getText().toString().trim());
                }

            }
        });
    }
}