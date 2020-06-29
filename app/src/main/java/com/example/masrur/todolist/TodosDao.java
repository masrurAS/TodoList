package com.example.masrur.todolist;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface TodosDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Todos todos);

    @Query("DELETE FROM todos_table")
    void deleteAll();

    @Query("SELECT * FROM todos_table ORDER BY todo ASC")
    LiveData<List<Todos>> getAll();

    @Query("UPDATE todos_table SET done = 1 WHERE todo = :todo")
    void setDone(String todo);

    @Query("UPDATE todos_table SET done = 0 WHERE todo = :todo")
    void setUnDone(String todo);
}
