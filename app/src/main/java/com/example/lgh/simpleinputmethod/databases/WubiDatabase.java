package com.example.lgh.simpleinputmethod.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.lgh.simpleinputmethod.util.FileManager;
import com.example.lgh.simpleinputmethod.activity.MainActivity;
import com.example.lgh.simpleinputmethod.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WubiDatabase {
    private static WubiDatabase instance;
    private Context context;
    private static final String DB_NAME = "wubi_dict_86.db";
    private SQLiteDatabase db;
    private WubiDatabase(Context context){
        this.context = context;
        File file = context.getDatabasePath(DB_NAME);
        if(!file.exists()){
            Log.d(MainActivity.TAG, file.getAbsolutePath() + " doesn't exits");
            try {
                FileManager.copyFileInRawToDatabaseLocation(R.raw.wubi_dict_86, DB_NAME, context);
            } catch (IOException e) {
                Log.d(MainActivity.TAG, "Database doesn't exist!");
                e.printStackTrace();
            }
        }

        db = SQLiteDatabase.openDatabase(file.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);

    }

    public static WubiDatabase getInstance(Context context){
        if(instance == null){
           instance = new WubiDatabase(context);
        }
        return instance;
    }

    public List<String> getValues(String code){
        Cursor cursor = db.query("wubi_code_map", new String[]{"code", "value"}, "code like ?", new String[]{code + "%"}, null, null, null, "20");
        ArrayList<String>values = new ArrayList<String>();
        if(cursor != null && cursor.getCount() > 0){
            while(cursor.moveToNext()){
                String cod = cursor.getString(0);
                String val = cursor.getString(1);
//                if(values.contains(val))continue;
//                if(cod.equals(code)){
//                    values.add(0, val);
//                }
//                else{
                    values.add(val);
//                }
            }
        }
        cursor.close();
        return values;
    }

    public SQLiteDatabase getDatabase(){
        return db;
    }
}
