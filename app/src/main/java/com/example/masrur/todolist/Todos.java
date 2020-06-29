package com.example.masrur.todolist;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todos_table")
public class Todos {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "todo")
    public String mTodo;

    public void setmDone(@NonNull Boolean mDone) {
        this.mDone = mDone ? 1 : 0;
    }

    @NonNull
    @ColumnInfo(name = "done")
    public Integer mDone;

    public Todos(@NonNull String todo) {
        this.mTodo = todo;
        this.mDone = 0;
    }

    public void setDone(){this.setmDone(true);}
    public void setUndone(){this.setmDone(false);}
    public String getTodos(){return this.mTodo;}
    public Boolean getDone(){if (this.mDone == 1) {return true;} else {return false;}}
}
