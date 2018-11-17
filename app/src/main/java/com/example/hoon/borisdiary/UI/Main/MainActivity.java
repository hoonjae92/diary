package com.example.hoon.borisdiary.UI.Main;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hoon.borisdiary.Bean.TODO;
import com.example.hoon.borisdiary.DB.DBHelper;
import com.example.hoon.borisdiary.R;
import com.example.hoon.borisdiary.UI.DiaryApplication;
import com.example.hoon.borisdiary.UI.ToDo.TODO_AddActivity;

public class MainActivity extends AppCompatActivity {
    private DBHelper mDB;
    private Context mContext;
    private RecyclerView mRv_todo;
    private RecyclerView mRv_completed_todo;

//    Test
    int no;
    String Sno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //초기화
        mContext = getApplicationContext();
        mDB = DiaryApplication.getDB();

        //해당 뷰 바인딩한다.
        mRv_todo = findViewById(R.id.rv_todo);
        mRv_completed_todo = findViewById(R.id.rv_completed_todo);
        Button btnFab = (Button) findViewById(R.id.btnFAB);

        //처리 이벤트
        btnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TODO_AddActivity.class);
                startActivity(intent);
            }
        });

    }
    public void Test(){
        TODO bean_todo = new TODO();
        bean_todo.setTodo("배우람아아아아");

        if(mDB.insertTODO(bean_todo)==false){
            Toast.makeText(mContext,"삽입 에러", Toast.LENGTH_SHORT).show();
        }

        Cursor todo = mDB.selectAllTODO();
        Cursor color = mDB.selectAllColor();

        if (todo.moveToFirst()) {
            do {
                Sno=todo.getString(todo.getColumnIndex(DBHelper.COL_TODO));
            } while (todo.moveToNext());
        }

        if (color.moveToFirst()) {
            do {
                no=color.getInt(color.getColumnIndex(DBHelper.COL_COLOR));
            } while (color.moveToNext());
        }
    }


}
