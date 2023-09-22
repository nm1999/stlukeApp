package com.chaplaincy.stlukeapp.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {

    private final String TABLE_NAME = "user";
    private final String TABLE_NOTES = "notes";
    private final String firstname_key = "firstname";
    private final String surname_key = "surname";
    private final String email_key = "email";
    private final String contact_key = "contact";
    private static final int DATABASE_VERSION = 2;
    private static final int OLDER_VERSION = 1;

    public DBhelper(@Nullable Context context) {
        super(context, "stluke",null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+"(firstname TEXT,surname TEXT,email TEXT,contact TEXT)");
        db.execSQL("create table "+TABLE_NOTES+"(id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,versus TEXT,notes TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if (i < DATABASE_VERSION ){
            db.execSQL("ALTER TABLE notes ADD COLUMN sync_state INTEGER DEFAULT 0");
            db.execSQL("ALTER TABLE notes ADD COLUMN deleted INTEGER DEFAULT 0");
            db.execSQL("ALTER TABLE notes ADD COLUMN editted INTEGER DEFAULT 0");

//            db.execSQL("create table "+TABLE_NOTES+"(id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,versus TEXT,notes TEXT)");

        }
//        db.execSQL("drop table if exists "+TABLE_NAME);
//        db.execSQL("drop table if exists "+TABLE_NOTES);
    }

    public boolean insertnotes(String title,String versus,String notes,int sync_state){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title",title);
        contentValues.put("versus",versus);
        contentValues.put("notes",notes);
        contentValues.put("sync_state",sync_state);

        long res = db.insert(TABLE_NOTES,null,contentValues);
        if (res==-1){
            return false;
        }else{
            return true;
        }
    }

    public boolean insertuser(String firstname,String surname,String email, String contact){
        SQLiteDatabase mydb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(firstname_key,firstname);
        contentValues.put(surname_key,surname);
        contentValues.put(email_key,email);
        contentValues.put(contact_key,contact);
        long result =  mydb.insert(TABLE_NAME,null,contentValues);

        if (result ==-1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("select * from "+TABLE_NAME,null);
        return cursor;
    }

    public Cursor getNotes(){
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor pointer = mydb.rawQuery("select * from notes ORDER BY id DESC",null);
        return pointer;
    }

    public boolean delete(String id){
        SQLiteDatabase mydb = this.getWritableDatabase();
        long res = mydb.delete(TABLE_NOTES, "id=?", new String[]{id});

        if (res==-1){
            return false;
        }else{
            return true;
        }
    }

    public boolean editnote(String id,String title, String versus,String notes){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title",title);
        contentValues.put("versus",versus);
        contentValues.put("notes",notes);

        long res = db.update(TABLE_NOTES,contentValues,"id=?", new String[]{id});
        if (res ==-1){
            return false;
        }else{
            return true;
        }
    }
}
