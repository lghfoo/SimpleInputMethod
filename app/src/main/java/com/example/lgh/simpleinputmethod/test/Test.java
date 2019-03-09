package com.example.lgh.simpleinputmethod.test;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.lgh.simpleinputmethod.activity.MainActivity;
import com.example.lgh.simpleinputmethod.databases.SymbolDatabase;
import com.example.lgh.simpleinputmethod.databases.WubiDatabase;
import com.example.lgh.simpleinputmethod.model.Symbol;

import java.util.List;

public class Test {
    public static void testFileManager(){

    }

    public static void testDatabase(Context context){
        //Test1：检查五笔数据库搜索是否正常
//        WubiDatabase db = WubiDatabase.getInstance(context);
//        List<String> values = db.getValues("qw");
//        for(String value : values) {
//            Log.d(MainActivity.TAG, value);
//        }

        //Test2：检查符号数据库
//        SymbolDatabase db = SymbolDatabase.getInstance(context);
//        List<Symbol>symbols = db.getValues("常用");
//        for(Symbol symbol : symbols){
//            Log.d(MainActivity.TAG, symbol.toString());
//        }
//        symbols = db.getValues("中文");
//        for(Symbol symbol : symbols){
//            Log.d(MainActivity.TAG, symbol.toString());
//        }


        //Test3：能否使用with as语句
        //结果：可以
//        SQLiteDatabase db = WubiDatabase.getInstance(context).getDatabase();
//        Cursor cursor = db.rawQuery("with tmp as (select * from wubi_code_map where length(code)=1) select * from tmp", null);
//        if(cursor != null && cursor.getCount() > 0){
//            while(cursor.moveToNext()){
//                String code = cursor.getString(0);
//                String val = cursor.getString(1);
//                Log.d(MainActivity.TAG, code + " " + val);
//            }
//        }
    }
}
