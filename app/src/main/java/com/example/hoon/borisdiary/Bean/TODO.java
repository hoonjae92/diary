package com.example.hoon.borisdiary.Bean;

public class TODO {
    private int id;
    private String todo;
    private String todo_color;
    private String create_time;
    private String delete_time;
    private int state;

    public String getTodo_color() {
        return todo_color;
    }

    public void setTodo_color(String todo_color) {
        this.todo_color = todo_color;
    }
    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDelete_time() {
        return delete_time;
    }

    public void setDelete_time(String delete_time) {
        this.delete_time = delete_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getStatus() {
        return state;
    }

    public void setStatus(int state) {
        this.state = state;
    }
}
