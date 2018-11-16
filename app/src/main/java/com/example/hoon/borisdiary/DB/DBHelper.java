package com.example.hoon.borisdiary.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.widget.Toast;

import com.example.hoon.borisdiary.Bean.TODO;

public class DBHelper extends SQLiteOpenHelper {
    public static DBHelper sqLiteHelper = null;
    public static final String DATABASE_NAME = "Diary.db";
    public static final int DB_VERSION = 1;
    public static final String COLOR_TABLE = "COLOR_TABLE";
    public static final String TODO_TABLE = "TODO_TABLE";
    public static final String COL_TODO = "COLUMN_TODO";
    public static final String COL_TODO_COLOR = "COLUMN_TODO_COLOR";
    public static final String COL_COLOR = "COLUMN_COLOR";

    private final int[] DefaultCOLOR = {Color.WHITE,Color.GRAY,Color.RED,Color.BLUE,Color.YELLOW,Color.CYAN};

    private Context context;
    private SQLiteDatabase db;

    public static DBHelper getInstance(Context context){ // 싱글턴 패턴으로 구현하였다.
        if(sqLiteHelper == null){
            sqLiteHelper = new DBHelper(context);
        }
        return sqLiteHelper;
    }

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DB_VERSION);
        db = this.getWritableDatabase();
        this.context = context;
    }

    /** * Database가 존재하지 않을 때, 딱 한번 실행된다. * DB를 만드는 역할을 한다. * @param db */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        StringBuffer sb_todo = new StringBuffer();
        sb_todo.append(" CREATE TABLE "+TODO_TABLE+" ( ");
        sb_todo.append(" _ID INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb_todo.append(  COL_TODO+" TEXT, ");
        sb_todo.append(  COL_TODO_COLOR+" TEXT, ");
        sb_todo.append(" COLUMN_TIMESTAMP TIMESTAMP DEFAULT CURRENT_TIMESTAMP )");

        StringBuffer sb_color = new StringBuffer();
        sb_color.append(" CREATE TABLE "+COLOR_TABLE+" ( ");
        sb_color.append(" _ID INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb_color.append(  COL_COLOR+" INTEGER )");

        // SQLite Database로 쿼리 실행
        db.execSQL(sb_todo.toString());
        db.execSQL(sb_color.toString());
        DefaultCOLOR();
    }

    /** * Application의 버전이 올라가서 * Table 구조가 변경되었을 때 실행된다.* @param db * @param oldVersion * @param newVersion */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + COLOR_TABLE);
        onCreate(db);
    } /** * */

        public boolean insertTODO(TODO todo){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_TODO, todo.getTodo());
            contentValues.put(COL_TODO_COLOR, todo.getTodo_color());
            long result = db.insert(TODO_TABLE, null, contentValues);
            if(result == -1)
                return false;
            else
                return true;
        }

    public void DefaultCOLOR(){
        for(int i : DefaultCOLOR){
            insertCOLOR(i);
        }
    }

    public boolean insertCOLOR(int color){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_COLOR, color);
        long result = db.insert(TODO_TABLE, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean updateTODO(int id, String todo){
        ContentValues values = new ContentValues();
        values.put(COL_TODO, todo);
        return db.update(TODO_TABLE, values, "_id=" + id, null) > 0;
    }

    // Delete All
    public void deleteAllTODO() {
        db.delete(TODO_TABLE, null, null);
    }

    // Delete Column
    public boolean deleteTODO(long id){
        return db.delete(TODO_TABLE, "_id="+id, null) > 0;
    }

    public Cursor selectAllTODO(){
        return db.query(TODO_TABLE, null, null, null, null, null, null);
    }

    public Cursor selectAllColor(){
        return db.query(COLOR_TABLE, null, null, null, null, null, null);
    }
}