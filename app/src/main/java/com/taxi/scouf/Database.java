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
    private String TABLE = "DRIVER";
    String name;
    int ver;
    Cursor cursor;

    public Database(@Nullable Context context) {
        super(context, "Scouf", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        System.out.println("onCreate");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + "( ID INTEGER PRIMARY KEY AUTOINCREMENT,  " + COLUMN0 + " TEXT , " + COLUMN1 + " TEXT, " + COLUMN2 + " TEXT, " + COLUMN3 + " TEXT, " + COLUMN4 + " TEXT, " + COLUMN5 + " TEXT, " + COLUMN6 + " TEXT  )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    
    public Boolean addOne(ArrayList arrayList, int pos, Boolean bool){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        ArrayList arrayList1 = (ArrayList) arrayList.clone();
        String query = "DELETE";
        int count;

        if (bool) {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            cursor = sqLiteDatabase.rawQuery("SELECT DRIVERS"+ pos + " FROM "+ TABLE, null);
            //set the row to null
            //if (cursor.moveToFirst()) {
                sqLiteDatabase.rawQuery("update "+TABLE+ " set DRIVERS" + pos +" = Null", null);
            //}

            System.out.println("update values: "+ arrayList1);
            for (int i = 0; i < arrayList.size(); i++) {
                contentValues.put("DRIVERS"+ pos, arrayList1.get(i).toString());
                database.update(TABLE, contentValues, "ID = "+ (i+1) , null);
            }
            System.out.println();
        } else {

            for (int i = 0; i < arrayList.size(); i++) {
                contentValues.put("DRIVERS" + pos, arrayList1.get(i).toString());
                database.insert(TABLE, null, contentValues);
            }
        }
        if (bool) {
            cursor.close();

        }

        database.close();
        return true;
    }

    public ArrayList getData(int pos) {
        ArrayList<String> arrayList2 = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        ContentValues cv = new ContentValues();
        cursor = db.rawQuery(" SELECT * FROM " + TABLE, null);

        if (cursor.moveToFirst()){
            do {

                arrayList2.add(cursor.getString(pos + 1));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        arrayList2.removeAll(Collections.singleton(null));
        return arrayList2;

    }


}
