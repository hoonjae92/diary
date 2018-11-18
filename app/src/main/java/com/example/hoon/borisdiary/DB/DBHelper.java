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
    public static final String DATABASE_NAME = "DiaryDB";
    public static final int DB_VERSION = 1;
    //테이블 명
    public static final String COLOR_TABLE = "COLOR_TABLE";
    public static final String TODO_TABLE = "TODO_TABLE";

    //TODO테이블 컬럼
    public static final String COL_ID = "COLUMN_ID";
    public static final String COL_TODO = "COLUMN_TODO";
    public static final String COL_TODO_COLOR = "COLUMN_TODO_COLOR";
    public static final String COL_CREATE_TIMESTAMP = "COLUMN_CREATE_TIMESTAMP";
    public static final String COL_DELETE_TIMESTAMP = "COLUMN_DELETE_TIMESTAMP";
    public static final String COL_STATE = "COLUMN_STATE";

    //COLOR테이블 컬럼
    public static final String COL_COLOR = "COLUMN_COLOR";

    //할 일 상태
    public static final int TODO_STATE_DELETE = 1;
    public static final int TODO_STATE_CREATE = 0;


    private final int[] DefaultCOLOR = {Color.WHITE,Color.GRAY,Color.RED,Color.BLUE,Color.YELLOW,Color.CYAN};

    private Context context;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DB_VERSION);
        this.context = context;
    }

    /** * Database가 존재하지 않을 때, 딱 한번 실행된다. * DB를 만드는 역할을 한다. * @param db */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        StringBuffer sb_todo = new StringBuffer();
        sb_todo.append(" CREATE TABLE IF NOT EXISTS "+TODO_TABLE+" ( ");
        sb_todo.append(  COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb_todo.append(  COL_TODO+" TEXT NOT NULL, ");
        sb_todo.append(  COL_TODO_COLOR+" INTEGER NOT NULL, ");
        sb_todo.append(  COL_STATE+" INTEGER NOT NULL, ");
        sb_todo.append(  COL_CREATE_TIMESTAMP+" TIMESTAMP DEFAULT CURRENT_TIMESTAMP, ");
        sb_todo.append(  COL_DELETE_TIMESTAMP+" DATETIME )");

        StringBuffer sb_color = new StringBuffer();
        sb_color.append(" CREATE TABLE IF NOT EXISTS "+COLOR_TABLE+" ( ");
        sb_color.append(" _ID INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb_color.append(  COL_COLOR+" INTEGER )");

        // SQLite Database로 쿼리 실행
        db.execSQL(sb_todo.toString());
        db.execSQL(sb_color.toString());

        //디폴트 색깔 지정
        for(int i : DefaultCOLOR){
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_COLOR, i);
            long result = db.insert(COLOR_TABLE, null, contentValues);
        }

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
            contentValues.put(COL_STATE, todo.getState());
            long result = db.insert(TODO_TABLE, null, contentValues);
            if(result == -1)
                return false;
            else
                return true;
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
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TODO, todo);
        return db.update(TODO_TABLE, values, COL_ID+"="+id, null) > 0;
    }
    public boolean updateTODO(int id, int state){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_STATE, state);
        return db.update(TODO_TABLE, values, COL_ID+"="+id, null) > 0;
    }
    public boolean updateTODO(int id, String todo, int color){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TODO, todo);
        values.put(COL_TODO_COLOR, color);
        return db.update(TODO_TABLE, values, COL_ID+"="+id, null) > 0;
    }

    // Delete All
    public void deleteAllTODO() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TODO_TABLE, null, null);
    }

    // Delete Column
    public boolean deleteTODO(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TODO_TABLE, COL_ID+"="+id, null) > 0;
    }

    public Cursor selectAllTODO(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.query(TODO_TABLE, null, null, null, null, null, null);
    }

    public Cursor selectAllColor(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.query(COLOR_TABLE, null, null, null, null, null, null);
    }
}