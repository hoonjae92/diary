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

public class TodoCompeletedRvAdapter extends RecyclerView.Adapter<TodoCompeletedRvAdapter.ViewHolder> {
    private ArrayList<TODO> items;
    private Context context;

    public TodoCompeletedRvAdapter(ArrayList<TODO> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_com_todo;
        TextView tv_com_date;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_com_todo = itemView.findViewById(R.id.compeleted_todo_tv);
            tv_com_date = itemView.findViewById(R.id.compeleted_todo_date);
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
        viewHolder.tv_com_todo.setText(items.get(i).getTodo());
        viewHolder.tv_com_date.setTag(items.get(i).getId());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}