package com.example.raihansarkar.onlinegeneraldiary;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raiha on 1/27/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    final private String GD_TABLE_NAME="gd_form_table";
    final private String COLUMN1="id";
    final private String COLUMN2="gd_form";
    final private String COLUMN3="mobile";
    final private String COLUMN4="policeStation";
    final private String COLUMN5="email";

    public DatabaseHelper(Context context) {
        super(context,"gdform_db" , null, 14);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table table_name(-----);
        String sql="CREATE TABLE "+GD_TABLE_NAME+"("+COLUMN1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COLUMN2+" TEXT,"+COLUMN3+" TEXT,"+COLUMN4+" TEXT,"+COLUMN5+" TEXT)";
        //CREATE TABLE wish_table ( id TEXT, wish_text TEXT)
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql="drop table if exists "+GD_TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public void userInsertGDForm(String gd_form, String mobile, String policeStation, String email){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values =new ContentValues();
        // values.put(COLUMN1,id);
        values.put(COLUMN2,gd_form);
        values.put(COLUMN3,mobile);
        values.put(COLUMN4,policeStation);
        values.put(COLUMN5,email);
        db.insert(GD_TABLE_NAME,null,values);
        Log.d("dbhelper","TEXT "+gd_form+" Mobile "+mobile+" police station "+policeStation+" email: "+email);
        db.close();

    }

    public List<Item> GetAllData()
    {
        Log.d("dbhelper","Getting Data");
        List<Item> itemlist=new ArrayList<Item>();
        SQLiteDatabase db=this.getReadableDatabase();
        String sql="select * from "+GD_TABLE_NAME;
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor==null)
            Log.d("dbhelper","Empty Result");

        if(cursor.moveToFirst())
        {
            while (cursor.moveToNext())
            {


                int  id=cursor.getInt(0);
                String gdFormString=cursor.getString(1);
                String mobileString=cursor.getString(2);
                String gdStationString=cursor.getString(3);
                String gdEmailString=cursor.getString(4);
                Item item=new Item(id,gdFormString,mobileString,gdStationString,gdEmailString);
                itemlist.add(item);
                Log.d("dbhelper","Id: "+id+" gdFormString: "+gdFormString+"mobileString"+mobileString+"gdStationString"+gdStationString);
            }
        }
        Log.d("dbhelper","List Size: "+itemlist.size());
        db.close();
        return  itemlist;


    }

    public List<Item> GetStationData(String policeStation)
    {
        Log.d("dbhelper","Getting Data");
        List<Item> itemlist=new ArrayList<Item>();
        SQLiteDatabase db=this.getReadableDatabase();
        String sql="select * from "+GD_TABLE_NAME+" WHERE "+COLUMN4+"='"+policeStation+"'";
        Log.d("dbhelper","SQL: "+sql);
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor==null)
            Log.d("dbhelper","Empty Result");

        if(cursor.moveToFirst())
        {
            while (cursor.moveToNext())
            {


                int  id=cursor.getInt(0);
                String gdFormString=cursor.getString(1);
                String mobileString=cursor.getString(2);
                String gdStationString=cursor.getString(3);
                Item item=new Item(id,gdFormString,mobileString,gdStationString);
                itemlist.add(item);
                Log.d("dbhelper","Id: "+id+" gdFormString: "+gdFormString+" mobileString "+mobileString+" gdStationString "+gdStationString);
            }
        }
        Log.d("dbhelper","List Size: "+itemlist.size());
        db.close();
        return  itemlist;


    }

    public void deleteWish(int id)
    {
        try {
            String sql="DELETE FROM "+GD_TABLE_NAME+" WHERE "+COLUMN1+"="+id;
            SQLiteDatabase db=this.getWritableDatabase();
            db.execSQL(sql);
            db.close();
        }catch (Exception e)
        {
            Log.d("database","Error: "+e);
        }
    }

    public boolean updateData(String mobile, String gd_number){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(COLUMN3,gd_number);
        db.update(GD_TABLE_NAME, values, COLUMN3+" = ?",new String[]{mobile});
        return true;
    }
}
