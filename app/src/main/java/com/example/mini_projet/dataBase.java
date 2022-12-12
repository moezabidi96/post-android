package com.example.mini_projet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dataBase extends SQLiteOpenHelper {
    private static final String DB_Name="Product.db";






    public dataBase (Context context){
        super(context,DB_Name,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table savedProduct (id text primary key) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS savedProduct");
        onCreate(db);
    }

    public boolean insertData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        long result = db.insert("savedProduct", null, contentValues);
        return result != -1;
    }
    public Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query="Select * FROM  savedProduct" ;
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }
    public Integer deleteproduct (String id) {

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete("savedProduct",
                "id = ? ",
                new String[] { id });
    }
}
