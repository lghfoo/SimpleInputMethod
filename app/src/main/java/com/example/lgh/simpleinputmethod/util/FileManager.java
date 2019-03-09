package com.example.lgh.simpleinputmethod.util;

import android.content.Context;
import android.os.Environment;

import com.example.lgh.simpleinputmethod.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileManager {
//    public final static String INTERNAL_FILE_PATH = "/data/data/" + BuildConfig.APPLICATION_ID + "/";
    public final static void copyFileInRawToInternalStorage(int rawId, String filenameOut, Context context) throws IOException {
        InputStream is = context.getResources().openRawResource(rawId);
        File fileOut = new File(context.getFilesDir(), filenameOut);
        OutputStream os = new FileOutputStream(fileOut);
        byte buffer[] = new byte[2048];
        int len = 0;
        while((len = is.read(buffer)) > 0){
            os.write(buffer, 0, len);
        }
        os.close();
        is.close();
    }

    public final static void copyFileInRawToDatabaseLocation(int rawId, String filenameOut, Context context) throws IOException {
        InputStream is = context.getResources().openRawResource(rawId);
        File fileOut = context.getDatabasePath(filenameOut);
        fileOut.getParentFile().mkdirs();
        fileOut.createNewFile();
        OutputStream os = new FileOutputStream(fileOut);
        byte buffer[] = new byte[2048];
        int len = 0;
        while((len = is.read(buffer)) > 0){
            os.write(buffer, 0, len);
        }
        os.close();
        is.close();
    }
}
