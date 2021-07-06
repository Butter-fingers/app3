package com.taxi.scouf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;

public class Database extends SQLiteOpenHelper {

    private String COLUMN0 = "DRIVERS0";
    private String COLUMN1 = "DRIVERS1";
    private String COLUMN2 = "DRIVERS2";
    private String COLUMN3 = "DRIVERS3";
    private String COLUMN4 = "DRIVERS4";
    private String COLUMN5 = "DRIVERS5";
    private String COLUMN6 = "DRIVERS6";
    private String TABLE6 = "DRIVER6";
    private String TABLE0 = "DRIVER0";
    private String TABLE1 = "DRIVER1";
    private String TABLE2 = "DRIVER2";
    private String TABLE3 = "DRIVER3";
    private String TABLE4 = "DRIVER4";
    private String TABLE5 = "DRIVER5";
    int ver;
    Cursor cursor;

    public Database(@Nullable Context context) {
        super(context, "Scouf.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        System.out.println("onCreate");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE0 + "( ID INTEGER PRIMARY KEY AUTOINCREMENT,  " + COLUMN0 + " TEXT )");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE1 + "( ID INTEGER PRIMARY KEY AUTOINCREMENT,  " + COLUMN1 + " TEXT  )");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE2 + "( ID INTEGER PRIMARY KEY AUTOINCREMENT,  " + COLUMN2 + " TEXT  )");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE3 + "( ID INTEGER PRIMARY KEY AUTOINCREMENT,  " + COLUMN3 + " TEXT  )");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE4 + "( ID INTEGER PRIMARY KEY AUTOINCREMENT,  " + COLUMN4 + " TEXT  )");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE5 + "( ID INTEGER PRIMARY KEY AUTOINCREMENT,  " + COLUMN5 + " TEXT  )");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE6 + "( ID INTEGER PRIMARY KEY AUTOINCREMENT,  " + COLUMN6 + " TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    
    public Boolean addOne(ArrayList arrayList, int pos, int table){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();



        System.out.println("done");
        for (int i = 0; i < arrayList.size(); i++) {
            contentValues.put("DRIVERS" + pos, arrayList.get(i).toString());
            database.insert("TABLE"+table, null, contentValues);
        }



        database.close();
        //sqLiteDatabase.close();
        return true;
        /*ArrayList arrayList1 = (ArrayList) arrayList.clone();
        ArrayList<String> list1 = getData(pos) ;
        //list1.removeAll(Collections.singleton(null));
        int list = 0;
        int aID = 1;
        String query = "DELETE";

        //get the lane names
        ArrayList<String> lanes = getData(5);
        //lanes.removeAll(Collections.singleton(null));
        //get users
        ArrayList<String> user = getData(6);
        //user.removeAll(Collections.singleton(null));


        //System.out.println("array: "+id+  " pos: "+ pos + " lane: "+ getData(5) );
        int count;

        if (bool) {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.isOpen();
            //cursor = sqLiteDatabase.rawQuery("SELECT DRIVERS"+ pos + " FROM "+ TABLE, null);
            //set the row to null
            //if (cursor.moveToFirst()) {

            if (pos == 0) {
                list = 0;
            } else if (pos == 1) {

                list = getData(0).size() ;
            } else if (pos == 2) {

                list = getData(0).size() + getData(1).size();
            } else if (pos == 3) {

                list = getData(2).size() + getData(1).size() + getData(0).size();
            } else if (pos == 4) {
                list = getData(3).size() + getData(2).size() + getData(1).size() + getData(0).size()  ;
            }
            int id = lanes.size() + user.size() + list ;
            for (int i = 0 ; i < arrayList.size(); i++) {

                String name = arrayList.get(i).toString();
                database = this.getWritableDatabase();
                database.rawQuery("DELETE FROM " + TABLE + " WHERE ID  = "+ (id+(i+1))  , null);

            }/*
            for (int i = 0; i < arrayList.size(); i++) {
                contentValues.put("DRIVERS" + pos, arrayList1.get(i).toString());
                database.update(TABLE, contentValues, null,null);
            }
            //}


            System.out.println("update values: "+ arrayList1);
            for (int i = 0; i < arrayList.size(); i++) {
                contentValues.put("DRIVERS" + pos, arrayList1.get(i).toString());
                database.insert(TABLE, null, contentValues);
            }



        } else {
*/

    }

    public ArrayList getData(int pos) {
        ArrayList<String> arrayList2 = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        ContentValues cv = new ContentValues();
        cursor = db.rawQuery(" SELECT * FROM " + "TABLE"+pos, null);

        if (cursor.moveToFirst()){
            do {

                arrayList2.add(cursor.getString( 1));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        arrayList2.removeAll(Collections.singleton(null));
        return arrayList2;

    }


}
