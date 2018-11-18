package com.example.hoon.borisdiary.UI.Main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoon.borisdiary.Bean.TODO;
import com.example.hoon.borisdiary.DB.DBHelper;
import com.example.hoon.borisdiary.R;
import com.example.hoon.borisdiary.UI.DiaryApplication;
import com.example.hoon.borisdiary.UI.ToDo.Adapter.TodoCompeletedRvAdapter;
import com.example.hoon.borisdiary.UI.ToDo.Adapter.TodoRvAdapter;
import com.example.hoon.borisdiary.UI.ToDo.TODO_AddActivity;
import com.example.hoon.borisdiary.UI.ToDo.TODO_ModifyActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public final static int TYPE_TODO = 0;
    public final static int TYPE_COMPELETED = 1;
    private DBHelper mDB;
    private LinearLayout mLayout_ToDo_Add;
    private LinearLayout mLayout_ToDo_Compeleted;
    private ArrayList<TODO> mToDoList;
    private ArrayList<TODO> mDeleteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //초기화
        mDB = DiaryApplication.getDB();
        mToDoList = new ArrayList<TODO>();
        mDeleteList = new ArrayList<TODO>();
        getToDoList(mToDoList,mDeleteList);

        //해당 뷰 바인딩한다.
        Button btnFab = (Button) findViewById(R.id.btnFAB);
        mLayout_ToDo_Add = (LinearLayout) findViewById(R.id.layout_todo_add);
        mLayout_ToDo_Compeleted = (LinearLayout) findViewById(R.id.layout_completed_todo);
        AddVeiw_ToDoList(mToDoList);
        AddVeiw_CompeletedList(mDeleteList);

        //처리 이벤트
        btnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TODO_AddActivity.class);
                finish();
                startActivity(intent);
            }
        });

    }


    public void getToDoList(ArrayList<TODO> todolist,ArrayList<TODO> deletelist){
        todolist.clear(); deletelist.clear();

        Cursor result = mDB.selectAllTODO();
        if (result.moveToFirst()) {
            do {
                TODO todo = new TODO();
                int state = result.getInt(result.getColumnIndex(DBHelper.COL_STATE));
                todo.setTodo(result.getString(result.getColumnIndex(DBHelper.COL_TODO)));
                todo.setTodo_color(result.getInt(result.getColumnIndex(DBHelper.COL_TODO_COLOR)));
                todo.setState(state);
                todo.setDelete_time(result.getString(result.getColumnIndex(DBHelper.COL_DELETE_TIMESTAMP)));
                todo.setId(result.getInt(result.getColumnIndex(DBHelper.COL_ID)));
                todo.setCreate_time(result.getString(result.getColumnIndex(DBHelper.COL_CREATE_TIMESTAMP)));

                if(state==DBHelper.TODO_STATE_DELETE){
                    deletelist.add(todo);
                }
                else if(state == DBHelper.TODO_STATE_CREATE)
                    todolist.add(todo);
                else
                    Log.d("getToDoList"," SQLITE STATE ERROR ");

            } while (result.moveToNext());
        }

    }

    public void AddVeiw_ToDoList(ArrayList<TODO> list){
        for(int i=0;i<list.size();i++){
            //뷰 바인딩
            LinearLayout view = (LinearLayout)View.inflate(this,R.layout.item_todo,null);
            CardView cardView = view.findViewById(R.id.item_layout);
            TextView todo_tv = view.findViewById(R.id.todo_tv);
            ImageButton todo_btn_close = view.findViewById(R.id.todo_btn_close);

            //이벤트 및 데이터 처리
            view.setOnClickListener(this);
            todo_btn_close.setOnClickListener(this);

            TODO data = list.get(i);
            todo_tv.setText(data.getTodo());
            cardView.setCardBackgroundColor(data.getTodo_color());

            todo_btn_close.setTag(R.string.todo_id,data.getId());
            todo_btn_close.setTag(R.string.todo_contents,data.getTodo());
            todo_btn_close.setTag(R.string.todo_color,data.getTodo_color());
            view.setTag(TYPE_TODO);
            mLayout_ToDo_Add.addView(view);
        }
    }

    public void AddVeiw_CompeletedList(ArrayList<TODO> list){

        for(int i=0;i<list.size();i++) {
            //뷰 바인딩
            LinearLayout view = (LinearLayout)View.inflate(this,R.layout.item_compeleted_todo,null);
            CardView cardView = view.findViewById(R.id.item_layout);
            TextView compeleted_tv = view.findViewById(R.id.compeleted_todo_tv);
            TextView date_tv = view.findViewById(R.id.compeleted_todo_date);

            //이벤트 및 데이터 처리
            view.setOnClickListener(this);

            TODO data = list.get(i);
            compeleted_tv.setText(data.getTodo());
            date_tv.setText(data.getCreate_time());
            cardView.setCardBackgroundColor(data.getTodo_color());

            view.setTag(TYPE_COMPELETED);
            compeleted_tv.setTag(R.string.todo_id, data.getId());
            compeleted_tv.setTag(R.string.todo_contents, data.getTodo());
            mLayout_ToDo_Compeleted.addView(view);
        }
    }

    @Override
    public void onClick(View v) {
        //삭제버튼 누를시
        if(v.getClass()==AppCompatImageButton.class){
            final int id = (int)v.getTag(R.string.todo_id);
            final String todo =(String)v.getTag(R.string.todo_contents);

            DiaryApplication.DiaryDialog(MainActivity.this, "'" + todo + "'\n삭제하시겠습니까?", null, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //상태 변경
                    boolean result = mDB.updateTODO(id,DBHelper.TODO_STATE_DELETE);
                    if(result){
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        finish();
                        startActivity(intent);
                    }
                }
            });
        //수정버튼 누를때
        }else if(v.getClass()==LinearLayout.class){
            int type = (int)v.getTag();
            String todo;
            Intent intent;
            switch (type){
                case TYPE_TODO:
                    ImageButton imageButton = v.findViewById(R.id.todo_btn_close);

                    int todo_id = (int)imageButton.getTag(R.string.todo_id);
                    int todo_color = (int)imageButton.getTag(R.string.todo_color);
                    todo =(String)imageButton.getTag(R.string.todo_contents);

                    intent= new Intent(MainActivity.this, TODO_ModifyActivity.class);
                    intent.putExtra(DBHelper.COL_ID,todo_id);
                    intent.putExtra(DBHelper.COL_TODO,todo);
                    intent.putExtra(DBHelper.COL_TODO_COLOR,todo_color);
                    startActivity(intent);
                    break;

                case TYPE_COMPELETED:
                    TextView textView = v.findViewById(R.id.compeleted_todo_tv);
                    final int compeleted_id = (int)textView.getTag(R.string.todo_id);
                    todo =(String)textView.getTag(R.string.todo_contents);

                    DiaryApplication.DiaryDialog(MainActivity.this, "'" + todo + "'\n삭제하시겠습니까?", null, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //상태 변경
                            boolean result = mDB.deleteTODO(compeleted_id);
                            if(result){
                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                finish();
                                startActivity(intent);
                            }
                        }
                    });
                    break;
            }

        }
    }

}
