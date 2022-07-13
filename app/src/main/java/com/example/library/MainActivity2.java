package com.example.library;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity2 extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addbtn;

    MyDatabaseHelper myDB;
    ArrayList<String> deckId, deckTitle;
    CustomAdapter2 customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        recyclerView=  findViewById(R.id.recyclerView);
        addbtn= findViewById(R.id.addbtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity2.this, AddActivity2.class );
                startActivity(intent);

            }
        });

        myDB= new MyDatabaseHelper(MainActivity2.this);
        deckId= new ArrayList<>();
        deckTitle= new ArrayList<>();
        storeDataInArrays();
        customAdapter= new CustomAdapter2(MainActivity2.this,this,deckId,deckTitle);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity2.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            recreate();
        }
    }

    void storeDataInArrays()
    {
        Cursor cursor = myDB.readDeck();
        if(cursor.getCount()==0)
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while(cursor.moveToNext())
            {
                deckId.add(cursor.getString(0));
                deckTitle.add(cursor.getString(1));
            }

        }
    }
    void storeDataInArrays1()
    {
        Cursor cursor = myDB.readDeckSort();
        if(cursor.getCount()==0)
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while(cursor.moveToNext())
            {
                deckId.add(cursor.getString(0));
                deckTitle.add(cursor.getString(1));
            }

        }
    }
    void confirmDeleteDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete entire deck");
        builder.setMessage("Are you sure you want to delete entire deck ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper mydb= new MyDatabaseHelper(MainActivity2.this);
                mydb.deleteAllDeck();
                deckId.clear();
                deckTitle.clear();
                storeDataInArrays();
                customAdapter= new CustomAdapter2(MainActivity2.this,MainActivity2.this,deckId,deckTitle);
                recyclerView.setAdapter(customAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity2.this));
                Toast.makeText(MainActivity2.this, "Deleted all decks", Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu2,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all)
        {
            confirmDeleteDialog();
        }
        if(item.getItemId() == R.id.sort1)
        {
            deckId.clear();
            deckTitle.clear();
            storeDataInArrays();
            customAdapter= new CustomAdapter2(MainActivity2.this,this,deckId,deckTitle);
            recyclerView.setAdapter(customAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity2.this));
            Toast.makeText(this, "Sort by Last added", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.sort3)
        {
            deckId.clear();
            deckTitle.clear();
            storeDataInArrays1();
            customAdapter= new CustomAdapter2(MainActivity2.this,this,deckId,deckTitle);
            recyclerView.setAdapter(customAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity2.this));
            Toast.makeText(this, "Sort by Last added", Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);
    }
}