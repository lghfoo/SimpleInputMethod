package com.example.lgh.simpleinputmethod.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.lgh.simpleinputmethod.util.FileManager;
import com.example.lgh.simpleinputmethod.activity.MainActivity;
import com.example.lgh.simpleinputmethod.R;
import com.example.lgh.simpleinputmethod.model.Symbol;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SymbolDatabase {
    private static SymbolDatabase instance;
    private Context context;
    private static final String DB_NAME = "symbols.db";
    private SQLiteDatabase db;
    private SymbolDatabase(Context context){
        this.context = context;
        File file = context.getDatabasePath(DB_NAME);
        if(!file.exists()){
            Log.d(MainActivity.TAG, file.getAbsolutePath() + " doesn't exits");
            try {
                FileManager.copyFileInRawToDatabaseLocation(R.raw.symbols, DB_NAME, context);
            } catch (IOException e) {
                Log.d(MainActivity.TAG, "Database doesn't exist!");
                e.printStackTrace();
            }
        }

        db = SQLiteDatabase.openDatabase(file.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);

    }

    public static SymbolDatabase getInstance(Context context){
        if(instance == null){
            instance = new SymbolDatabase(context);
        }
        return instance;
    }

    public List<Symbol> getValues(String tag){
        String selection = "tag=\"" + tag + "\"";
        String limit = null;
        String[] columns = {"value", "priority"};
        boolean isOften = tag.equals(Symbol.SYMBOL_OFTEN);
        if(isOften){
            selection = null;
            limit = "20";
            columns = new String[]{"tag", "value", "priority"};
        }
        Cursor cursor = db.query("symbols", columns, selection, null, null, null, "priority", limit);
        ArrayList<Symbol> values = new ArrayList<>();
        if(cursor != null && cursor.getCount() > 0){
            while(cursor.moveToNext()){
                if(isOften){
                    String t = cursor.getString(0);
                    String val = cursor.getString(1);
                    int pri = cursor.getInt(2);
                    values.add(new Symbol(t, val, pri));
                    if(values.contains(val))continue;
                }
                else{
                    String val = cursor.getString(0);
                    int pri = cursor.getInt(1);
                    values.add(new Symbol(tag, val, pri));
                }
            }
        }
        cursor.close();
        return values;
    }
}
