package com.example.masrur.todolist;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Todos.class}, version = 1, exportSchema = false)
public abstract class TodosRoomDatabase extends RoomDatabase {

    public abstract TodosDao TodosDao();
    private static TodosRoomDatabase INSTANCE;

    static TodosRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TodosRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodosRoomDatabase.class, "todos_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TodosDao mDao;
        String[] Todos = {};

        PopulateDbAsync(TodosRoomDatabase db) {
            mDao = db.TodosDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            mDao.deleteAll();

            for (int i = 0; i <= Todos.length - 1; i++) {
                Todos Todo = new Todos(Todos[i]);
                mDao.insert(Todo);
            }
            return null;
        }
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
        new RoomDatabase.Callback(){

            @Override
            public void onOpen (@NonNull SupportSQLiteDatabase db){
                super.onOpen(db);
                new PopulateDbAsync(INSTANCE).execute();
            }
        };
}
