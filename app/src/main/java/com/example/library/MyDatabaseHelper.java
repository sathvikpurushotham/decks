package com.example.library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.lang.reflect.Field;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME= "flashcard5.db";
    private static final int DATABASE_VERSION=1;
    private static final String PARENT_TABLE_NAME= "decks";
    private static final String PARENT_COLUMN_ID= "_id";
    private static final String PARENT_TITLE= "deck_title";
    private static final String TABLE_NAME= "cards";
    private static final String COLUMN_ID= "_id";
    private static final String COLUMN_TITLE= "card_title";
    private static final String COLUMN_ANSWER= "card_answer";
    private static final String COLUMN_COUNT= "rev_count";
    private static final String COLUMN_REFER= "deck_id";
    private static final String IMAGE_CARD_TABLE= "img_table";
    private static final String IMG_TABLE_ID= "_id";
    private static final String IMG_TABLE_TITLE= "img_title";
    private static final String IMG_TABLE_IMAGE= "img_img";
    private static final String IMG_COLUMN_COUNT= "rev_count";


    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON");
        String query= "CREATE TABLE "+ PARENT_TABLE_NAME+ " ("+PARENT_COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PARENT_TITLE + " TEXT );";
        db.execSQL(query);
        String query1= "CREATE TABLE "+ TABLE_NAME+ " ("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                                + COLUMN_TITLE + " TEXT, "
                                + COLUMN_ANSWER + " TEXT, "
                                + COLUMN_COUNT +  " INTEGER ,"
                                + COLUMN_REFER+ " INTEGER REFERENCES "+ PARENT_TABLE_NAME+" ( "+ PARENT_COLUMN_ID +" ) ON DELETE CASCADE"
                                +");";
        db.execSQL(query1);
        String query2= "CREATE TABLE "+ IMAGE_CARD_TABLE+ " ("+IMG_TABLE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + IMG_TABLE_TITLE + " TEXT, "
                + IMG_TABLE_IMAGE + " BLOB, "
                + COLUMN_COUNT +  " INTEGER ,"
                + COLUMN_REFER+ " INTEGER REFERENCES "+ PARENT_TABLE_NAME+" ( "+ PARENT_COLUMN_ID +" ) ON DELETE CASCADE"
                +");";
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+PARENT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+IMAGE_CARD_TABLE);
    }

    void addDeck(String title)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(PARENT_TITLE,title);
        long result= db.insert(PARENT_TABLE_NAME,null,cv);
        if(result==-1)
        {
            Toast.makeText(context, "Failed to add deck", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Deck successfully added", Toast.LENGTH_SHORT).show();
        }
    }
    void addImageCard(String title, byte[] bytesImage,int v,String pid)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(IMG_TABLE_TITLE,title);
        cv.put(IMG_TABLE_IMAGE,bytesImage);
        cv.put(IMG_COLUMN_COUNT,v);
        cv.put(COLUMN_REFER,pid);
        long result= db.insert(IMAGE_CARD_TABLE,null,cv);
        if(result==-1)
        {
            Toast.makeText(context, "Failed to add card", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Image Card Successfully Added", Toast.LENGTH_SHORT).show();
        }
    }

    void updateImageCard(String title, byte[] bytesImage,int v,String row_id)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(IMG_TABLE_TITLE,title);
        cv.put(IMG_TABLE_IMAGE,bytesImage);
        cv.put(IMG_COLUMN_COUNT,v);
        long result=db.update(IMAGE_CARD_TABLE,cv, "_id=?", new String[]{row_id});
        if(result==-1)
        {
            Toast.makeText(context, "Failed to update card", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Image Card Successfully Updated", Toast.LENGTH_SHORT).show();
        }
    }
    void updateImageCard1(String title, byte[] bytesImage,int v,String row_id)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(IMG_TABLE_TITLE,title);
        cv.put(IMG_TABLE_IMAGE,bytesImage);
        cv.put(IMG_COLUMN_COUNT,v);
        long result=db.update(IMAGE_CARD_TABLE,cv, "_id=?", new String[]{row_id});
        if(result==-1)
        {
            Toast.makeText(context, "Failed to update card", Toast.LENGTH_SHORT).show();
        }
    }
    Cursor readDeck()
    {
        String query= "SELECT * FROM "+PARENT_TABLE_NAME;
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= null;
        if(db!=null)
        {
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }
    Cursor readDeckSort()
    {
        String query= "SELECT * FROM "+PARENT_TABLE_NAME+" ORDER BY "+PARENT_COLUMN_ID+ " DESC";
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= null;
        if(db!=null)
        {
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }
    void addBook(String title,String answer, int count,String pid)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(COLUMN_TITLE,title);
        cv.put(COLUMN_ANSWER,answer);
        cv.put(COLUMN_COUNT,count);
        cv.put(COLUMN_REFER,Integer.valueOf(pid));
        long result= db.insert(TABLE_NAME,null,cv);
        if(result==-1)
        {
            Toast.makeText(context, "Failed to add card", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Card successfully added", Toast.LENGTH_SHORT).show();
        }
    }
    Cursor readAllData(String pid)
    {
        String query= "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_REFER +" = " +Integer.valueOf(pid);
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= null;
        if(db!=null)
        {
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }
    Cursor sortByCount(String pid)
    {
        String query= "SELECT * FROM "+TABLE_NAME +" WHERE "+COLUMN_REFER +" = " +Integer.valueOf(pid)+" ORDER BY "+COLUMN_COUNT;
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= null;
        if(db!=null)
        {
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }
    Cursor sortByCount1(String pid)
    {
        String query= "SELECT * FROM "+TABLE_NAME +" WHERE "+COLUMN_REFER +" = " +Integer.valueOf(pid)+" ORDER BY "+COLUMN_COUNT+" DESC";
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= null;
        if(db!=null)
        {
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }
    Cursor sortByCount2(String pid)
    {
        String query= "SELECT * FROM "+TABLE_NAME +" WHERE "+COLUMN_REFER +" = " +Integer.valueOf(pid)+" ORDER BY "+COLUMN_ID+" DESC";
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= null;
        if(db!=null)
        {
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }

    Cursor sortByImgCount(String pid)
    {
        String query= "SELECT * FROM "+IMAGE_CARD_TABLE +" WHERE "+COLUMN_REFER +" = " +Integer.valueOf(pid)+" ORDER BY "+IMG_COLUMN_COUNT;
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= null;
        if(db!=null)
        {
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }
    Cursor sortByImgCount1(String pid)
    {
        String query= "SELECT * FROM "+IMAGE_CARD_TABLE +" WHERE "+COLUMN_REFER +" = " +Integer.valueOf(pid)+" ORDER BY "+IMG_COLUMN_COUNT+" DESC";
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= null;
        if(db!=null)
        {
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }
    Cursor sortByImgCount2(String pid)
    {
        String query= "SELECT * FROM "+IMAGE_CARD_TABLE +" WHERE "+COLUMN_REFER +" = " +Integer.valueOf(pid)+" ORDER BY "+IMG_TABLE_ID+" DESC";
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= null;
        if(db!=null)
        {
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }

    Cursor readImageTable(String pid)
    {
        String query= "SELECT * FROM "+IMAGE_CARD_TABLE +" WHERE "+COLUMN_REFER +" = " +Integer.valueOf(pid);
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= null;
        if(db!=null)
        {
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }
    byte[] readImageTable1(String id)
    {
        String query= "SELECT * FROM "+IMAGE_CARD_TABLE +" WHERE "+IMG_TABLE_ID+" = "+Integer.valueOf(id);
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery(query,null);
        byte[] bytesImage=null;
        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            cursor.moveToFirst();
            bytesImage = cursor.getBlob(2);
            cursor.close();
            return bytesImage;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bytesImage;
    }
    void updateData(String row_id,String title, String answer, int count)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE,title);
        cv.put(COLUMN_ANSWER,answer);
        cv.put(COLUMN_COUNT,count);
        long result=db.update(TABLE_NAME,cv, "_id=?", new String[]{row_id});
        if (result ==-1)
        {
            Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
        }
    }
    void resetCountImgTable(String rid)
    {
        String query= "UPDATE "+IMAGE_CARD_TABLE+ " SET "+IMG_COLUMN_COUNT+ " = "+ Integer.valueOf(0) +" WHERE "+IMG_TABLE_ID+" = " +Integer.valueOf(rid);
        SQLiteDatabase db= this.getWritableDatabase();
        db.execSQL(query);
    }
    void updateData2(String row_id,String title, String answer, int count)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE,title);
        cv.put(COLUMN_ANSWER,answer);
        cv.put(COLUMN_COUNT,count);
        long result=db.update(TABLE_NAME,cv, "_id=?", new String[]{row_id});

    }

    void deleteRow(String row_id)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        long result = db.delete(TABLE_NAME,"_id=?",new String[]{row_id});
        if(result == -1)
        {
            Toast.makeText(context,"Failed to delete",Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(context,"Successfully deleted",Toast.LENGTH_SHORT).show();
        }
    }
    void deleteImageRow(String row_id)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        long result = db.delete(IMAGE_CARD_TABLE,"_id=?",new String[]{row_id});
        if(result == -1)
        {
            Toast.makeText(context,"Failed to delete",Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(context,"Successfully deleted",Toast.LENGTH_SHORT).show();
        }
    }
    void deleteAll(String pid)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE "+COLUMN_REFER +" = " +Integer.valueOf(pid));
        db.execSQL("DELETE FROM "+IMAGE_CARD_TABLE+" WHERE "+COLUMN_REFER +" = " +Integer.valueOf(pid));
    }
    void deleteDeck(String row_id)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        long result = db.delete(PARENT_TABLE_NAME,"_id=?",new String[]{row_id});
        if(result == -1)
        {
            Toast.makeText(context,"Failed to delete",Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(context,"Successfully deleted",Toast.LENGTH_SHORT).show();
        }
    }
    void deleteAllDeck()
    {
        SQLiteDatabase db= this.getWritableDatabase();
        db.execSQL("DELETE FROM "+PARENT_TABLE_NAME);
    }
    Cursor ViewAll(String pid)
    {
        String query= "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_REFER +" = " +Integer.valueOf(pid) +" UNION "+ "SELECT * FROM "+IMAGE_CARD_TABLE +" WHERE "+COLUMN_REFER +" = " +Integer.valueOf(pid)+" ORDER BY "+COLUMN_COUNT;
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= null;
        if(db!=null)
        {
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }
    Cursor ViewAllDesc(String pid)
    {
        String query= "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_REFER +" = " +Integer.valueOf(pid) +" UNION "+ "SELECT * FROM "+IMAGE_CARD_TABLE +" WHERE "+COLUMN_REFER +" = " +Integer.valueOf(pid)+" ORDER BY "+COLUMN_COUNT+" DESC ";
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= null;
        if(db!=null)
        {
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }
    Cursor ViewAllIdDefault(String pid)
    {
        String query= "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_REFER +" = " +Integer.valueOf(pid) +" UNION "+ "SELECT * FROM "+IMAGE_CARD_TABLE +" WHERE "+COLUMN_REFER +" = " +Integer.valueOf(pid)+" ORDER BY "+COLUMN_ID;
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= null;
        if(db!=null)
        {
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }
    Cursor ViewAllIdDesc(String pid)
    {
        String query= "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_REFER +" = " +Integer.valueOf(pid) +" UNION "+ "SELECT * FROM "+IMAGE_CARD_TABLE +" WHERE "+COLUMN_REFER +" = " +Integer.valueOf(pid)+" ORDER BY "+COLUMN_ID+" DESC ";
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= null;
        if(db!=null)
        {
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }
}
