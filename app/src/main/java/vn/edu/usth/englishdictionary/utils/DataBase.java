package vn.edu.usth.englishdictionary.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBase extends SQLiteOpenHelper {
    private Context dbContext;
    private SQLiteDatabase db;
    private static final String dbPath = "data/data/vn.edu.usth.englishdictionary/luutru.sqlite";
    private static final String name = "luutru.sqlite";
    private static final int version = 1;

    public DataBase(Context context) {
        super(context, name,null, version);
        this.dbContext = context;
    }

    public void OpenDB() {
        db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void CloseDB() {
        db.close();
    }

    public  void CopyDB2SDCard() throws IOException
    {
        boolean check=false;
        try
        {
            File file=new File(dbPath);
            check=file.exists();
            if(check)
            {
                this.close();
            }
            else
            {
                if (!check)
                {
                    this.getReadableDatabase();
                    InputStream myInput=dbContext.getAssets().open(name);
                    String outFilename=dbPath;
                    OutputStream myOutput=new FileOutputStream(outFilename);
                    byte[]buffer=new byte[1024];
                    int lenght;
                    while ((lenght=myInput.read(buffer))>0)
                    {
                        myOutput.write(buffer,0,lenght);
                    }
                    myOutput.flush();
                    myOutput.close();
                    myInput.close();
                }
            }
        }catch (Exception ex)
        {
            ex.getMessage();
        }
    }
    public int GetCount(String sql)
    {
        OpenDB();
        Cursor cur=db.rawQuery(sql,null);
        int count=cur.getCount();
        CloseDB();
        return  count;
    }

    public void ExecuteSQL(String sql)
    {
        OpenDB();
        db.execSQL(sql);
    }

    public  Cursor getCursor(String sql)
    {
        OpenDB();
        return db.rawQuery(sql,null);

    }
    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
}