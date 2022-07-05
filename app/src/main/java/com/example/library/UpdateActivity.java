package com.example.library;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    EditText title_input, author_input, pages_input;
    Button updateButton, deleteButton, testButton;

    String id,title, author,pages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        title_input = findViewById(R.id.title_input2);
        author_input=findViewById(R.id.author_input2);
        pages_input=findViewById(R.id.numpages_input2);
        updateButton=findViewById(R.id.updateBtn);
        deleteButton= findViewById(R.id.deleteBtn2);

        getIntentData();

        ActionBar ab= getSupportActionBar();
        if(ab!=null)
        {
            ab.setTitle(title);
        }


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper mydb= new MyDatabaseHelper(UpdateActivity.this);
                mydb.updateData(id,title_input.getText().toString().trim(),author_input.getText().toString().trim(),Integer.valueOf(pages_input.getText().toString().trim()));
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });


    }

    void getIntentData()
    {
        if(getIntent().hasExtra("id"))
        {
            id=getIntent().getStringExtra("id");
            title=getIntent().getStringExtra("title");
            author=getIntent().getStringExtra("answer");
            pages=getIntent().getStringExtra("count");

            title_input.setText(title);
            author_input.setText(author);
            pages_input.setText(pages);
        }
        else
        {
            Toast.makeText(this, "No data ", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete"+title+" ?");
        builder.setMessage("Are you sure you want to delete "+title+" ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper mydb= new MyDatabaseHelper(UpdateActivity.this);
                mydb.deleteRow(id);
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
}