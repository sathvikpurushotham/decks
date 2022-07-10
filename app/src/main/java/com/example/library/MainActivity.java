package com.example.library;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorWindow;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addbtn, addbtn2;

    MyDatabaseHelper myDB;
    ArrayList<String> cardId, cardTitle, cardAnswer, cardCount;
    CustomAdapter customAdapter;
    String pid,ptitle;
    boolean warn0=false,warn1= false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=  findViewById(R.id.recyclerView);
        addbtn= findViewById(R.id.addbtn);
        addbtn2=findViewById(R.id.addbtn2);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, AddActivity.class );
                intent.putExtra("pid",String.valueOf(pid));
                intent.putExtra("ptitle",String.valueOf(ptitle));
                startActivity(intent);
            }
        });
        addbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, AddImageCard.class );
                intent.putExtra("pid",String.valueOf(pid));
                intent.putExtra("ptitle",String.valueOf(ptitle));
                startActivity(intent);
            }
        });
        if(getIntent().hasExtra("pid"))
        {
            pid=getIntent().getStringExtra("pid");
            ptitle=getIntent().getStringExtra("ptitle");
            MainActivity.this.setTitle("Deck: "+ptitle);
        }
        else
        {
            Toast.makeText(this, "No data ", Toast.LENGTH_SHORT).show();
        }

        myDB= new MyDatabaseHelper(MainActivity.this);
        cardId= new ArrayList<>();
        cardTitle= new ArrayList<>();
        cardAnswer= new ArrayList<>();
        cardCount= new ArrayList<>();
        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }

//        storeDataInArrays(pid);
//        storeDataInArrays5(pid);
        readAllIdDefault(pid);
        if(warn0)
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        customAdapter= new CustomAdapter(MainActivity.this,this,cardId,cardTitle,cardAnswer,cardCount);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            recreate();
        }
    }

    void readAllDefault(String pid)
    {
        Cursor cursor = myDB.ViewAll(pid);
        if(cursor.getCount()==0)
        {
//            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
            warn0=true;
        }
        else
        {
            while(cursor.moveToNext())
            {
                cardId.add(cursor.getString(0));
                cardTitle.add(cursor.getString(1));
                try {
                    cardAnswer.add(cursor.getString(2));
                }catch (Exception e)
                {
                    cardAnswer.add("SwitchToImg");
                }

                cardCount.add(cursor.getString(3));
            }

        }
    }
    void readAllDesc(String pid)
    {
        Cursor cursor = myDB.ViewAllDesc(pid);
        if(cursor.getCount()==0)
        {
//            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
            warn0=true;
        }
        else
        {
            while(cursor.moveToNext())
            {
                cardId.add(cursor.getString(0));
                cardTitle.add(cursor.getString(1));
                try {
                    cardAnswer.add(cursor.getString(2));
                }catch (Exception e)
                {
                    cardAnswer.add("SwitchToImg");
                }

                cardCount.add(cursor.getString(3));
            }

        }
    }
    void readAllIdDefault(String pid)
    {
        Cursor cursor = myDB.ViewAllIdDefault(pid);
        if(cursor.getCount()==0)
        {
//            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
            warn0=true;
        }
        else
        {
            while(cursor.moveToNext())
            {
                cardId.add(cursor.getString(0));
                cardTitle.add(cursor.getString(1));
                try {
                    cardAnswer.add(cursor.getString(2));
                }catch (Exception e)
                {
                    cardAnswer.add("SwitchToImg");
                }

                cardCount.add(cursor.getString(3));
            }

        }
    }
    void readAllIdDesc(String pid)
    {
        Cursor cursor = myDB.ViewAllIdDesc(pid);
        if(cursor.getCount()==0)
        {
//            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
            warn0=true;
        }
        else
        {
            while(cursor.moveToNext())
            {
                cardId.add(cursor.getString(0));
                cardTitle.add(cursor.getString(1));
                try {
                    cardAnswer.add(cursor.getString(2));
                }catch (Exception e)
                {
                    cardAnswer.add("SwitchToImg");
                }

                cardCount.add(cursor.getString(3));
            }

        }
    }
    void storeDataInArrays(String pid)
    {
        Cursor cursor = myDB.readAllData(pid);
        if(cursor.getCount()==0)
        {
//            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
            warn0=true;
        }
        else
        {
            while(cursor.moveToNext())
            {
                cardId.add(cursor.getString(0));
                cardTitle.add(cursor.getString(1));
                cardAnswer.add(cursor.getString(2));
                cardCount.add(cursor.getString(3));
            }

        }
    }


    void storeDataInArrays2(String pid)
    {
        Cursor cursor = myDB.sortByCount(pid);
        if(cursor.getCount()==0)
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while(cursor.moveToNext())
            {

                cardId.add(cursor.getString(0));
                cardTitle.add(cursor.getString(1));
                cardAnswer.add(cursor.getString(2));
                cardCount.add(cursor.getString(3));
            }

        }
    }
    void storeDataInArrays3(String pid)
    {
        Cursor cursor = myDB.sortByCount1(pid);
        if(cursor.getCount()==0)
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while(cursor.moveToNext())
            {

                cardId.add(cursor.getString(0));
                cardTitle.add(cursor.getString(1));
                cardAnswer.add(cursor.getString(2));
                cardCount.add(cursor.getString(3));
            }

        }
    }
    void storeDataInArrays4(String pid)
    {
        Cursor cursor = myDB.sortByCount2(pid);
        if(cursor.getCount()==0)
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while(cursor.moveToNext())
            {

                cardId.add(cursor.getString(0));
                cardTitle.add(cursor.getString(1));
                cardAnswer.add(cursor.getString(2));
                cardCount.add(cursor.getString(3));
            }

        }
    }
    void storeDataInArrays5(String pid)
    {
        Cursor cursor = myDB.readImageTable(pid);
        if(cursor.getCount()==0)
        {
//            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
            warn1=true;
        }
        else
        {
            while(cursor.moveToNext())
            {

                cardId.add(cursor.getString(0));
                cardTitle.add(cursor.getString(1));
                cardAnswer.add("SwitchToImg");
                cardCount.add(cursor.getString(3));
            }

        }
    }
    void storeDataInArrays6(String pid)
    {
        Cursor cursor = myDB.sortByImgCount(pid);
        if(cursor.getCount()==0)
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while(cursor.moveToNext())
            {

                cardId.add(cursor.getString(0));
                cardTitle.add(cursor.getString(1));
                cardAnswer.add("SwitchToImg");
                cardCount.add(cursor.getString(3));
            }

        }
    }
    void storeDataInArrays7(String pid)
    {
        Cursor cursor = myDB.sortByImgCount1(pid);
        if(cursor.getCount()==0)
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while(cursor.moveToNext())
            {

                cardId.add(cursor.getString(0));
                cardTitle.add(cursor.getString(1));
                cardAnswer.add("SwitchToImg");
                cardCount.add(cursor.getString(3));
            }

        }
    }
    void storeDataInArrays8(String pid)
    {
        Cursor cursor = myDB.sortByImgCount2(pid);
        if(cursor.getCount()==0)
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while(cursor.moveToNext())
            {

                cardId.add(cursor.getString(0));
                cardTitle.add(cursor.getString(1));
                cardAnswer.add("SwitchToImg");
                cardCount.add(cursor.getString(3));
            }

        }
    }



    void confirmDeleteAllDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete all cards");
        builder.setMessage("Are you sure you want to delete all cards in "+ptitle+" ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper mydb= new MyDatabaseHelper(MainActivity.this);
                mydb.deleteAll(pid);
                cardId.clear();
                cardTitle.clear();
                cardAnswer.clear();
                cardCount.clear();

                customAdapter= new CustomAdapter(MainActivity.this,MainActivity.this,cardId,cardTitle,cardAnswer,cardCount);
                recyclerView.setAdapter(customAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                Toast.makeText(MainActivity.this, "Delete all", Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    void confirmDeleteDeck()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete entire deck");
        builder.setMessage("Are you sure you want to delete entire deck ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper mydb= new MyDatabaseHelper(MainActivity.this);
                mydb.deleteDeck(pid);
                Toast.makeText(MainActivity.this, "Deck "+ptitle+" deleted", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all)
        {
            confirmDeleteAllDialog();
        }

        else if(item.getItemId() == R.id.sort)
        {
            cardId.clear();
            cardTitle.clear();
            cardAnswer.clear();
            cardCount.clear();
            readAllDefault(pid);
            customAdapter= new CustomAdapter(MainActivity.this,this,cardId,cardTitle,cardAnswer,cardCount);
            recyclerView.setAdapter(customAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            Toast.makeText(this, "Sort by count asc", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.sort1)
        {
            cardId.clear();
            cardTitle.clear();
            cardAnswer.clear();
            cardCount.clear();
            readAllIdDefault(pid);
            customAdapter= new CustomAdapter(MainActivity.this,this,cardId,cardTitle,cardAnswer,cardCount);
            recyclerView.setAdapter(customAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            Toast.makeText(this, "Default Sort", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.sort4)
        {
            cardId.clear();
            cardTitle.clear();
            cardAnswer.clear();
            cardCount.clear();
            readAllIdDefault(pid);
            customAdapter= new CustomAdapter(MainActivity.this,this,cardId,cardTitle,cardAnswer,cardCount);
            recyclerView.setAdapter(customAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            Toast.makeText(this, "Sort by first added", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.sort2)
        {
            cardId.clear();
            cardTitle.clear();
            cardAnswer.clear();
            cardCount.clear();
            readAllDesc(pid);
            customAdapter= new CustomAdapter(MainActivity.this,this,cardId,cardTitle,cardAnswer,cardCount);
            recyclerView.setAdapter(customAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            Toast.makeText(this, "Sort by count desc", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.sort3)
        {
            cardId.clear();
            cardTitle.clear();
            cardAnswer.clear();
            cardCount.clear();
            readAllIdDesc(pid);
            customAdapter= new CustomAdapter(MainActivity.this,this,cardId,cardTitle,cardAnswer,cardCount);
            recyclerView.setAdapter(customAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            Toast.makeText(this, "Sort by last added", Toast.LENGTH_SHORT).show();
        }
        else if (item.getItemId()==R.id.delete_deck)
        {
            confirmDeleteDeck();
        }
        return super.onOptionsItemSelected(item);
    }
}