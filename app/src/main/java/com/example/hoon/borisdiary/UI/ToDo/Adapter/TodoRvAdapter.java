package com.example.hoon.borisdiary.UI.ToDo.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoon.borisdiary.Bean.TODO;
import com.example.hoon.borisdiary.R;

import java.util.ArrayList;

public class TodoRvAdapter extends RecyclerView.Adapter<TodoRvAdapter.ViewHolder> {
    private ArrayList<TODO> items;
    private Context context;

    public TodoRvAdapter(ArrayList<TODO> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_todo;
        Button btnClose;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_todo = itemView.findViewById(R.id.todo_tv);
            btnClose = itemView.findViewById(R.id.todo_btn_close);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context,R.layout.item_todo,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tv_todo.setText(items.get(i).getTodo());
        viewHolder.tv_todo.setTag(items.get(i).getId());
        viewHolder.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //다이얼로그 띄우기
                Toast.makeText(context,"해당 close 클릭",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}