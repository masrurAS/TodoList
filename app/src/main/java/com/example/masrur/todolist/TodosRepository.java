package com.example.masrur.todolist;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TodosRepository {
    private TodosDao mTodosDao;
    private LiveData<List<Todos>> mAllTodoss;

    TodosRepository(Application application) {
        TodosRoomDatabase db = TodosRoomDatabase.getDatabase(application);
        mTodosDao = db.TodosDao();
        mAllTodoss = mTodosDao.getAll();
    }

    LiveData<List<Todos>> getAll() {
        return mAllTodoss;
    }

    public void insert (Todos Todos) {
        new insertAsyncTask(mTodosDao).execute(Todos);
    }
    public void deletesAll () {
        new deletesAllAsyncTask(mTodosDao).execute();
    }
    public void setDone (Todos todos, Boolean done) {
        new setDoneAsyncTask(mTodosDao, todos).execute(done);
    }

    private static class insertAsyncTask extends AsyncTask<Todos, Void, Void> {

        private TodosDao mAsyncTaskDao;

        insertAsyncTask(TodosDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Todos... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deletesAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private TodosDao mAsyncTaskDao;

        deletesAllAsyncTask(TodosDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class setDoneAsyncTask extends AsyncTask<Boolean, Void, Void> {

        private TodosDao mAsyncTaskDao;
        private Todos mtodos;

        setDoneAsyncTask(TodosDao dao, Todos todos) {
            mAsyncTaskDao = dao;
            mtodos = todos;
        }

        @Override
        protected Void doInBackground(final Boolean... params) {
            Boolean done = params[0];
            if (done) {
                mAsyncTaskDao.setDone(mtodos.getTodos());
            } else {
                mAsyncTaskDao.setUnDone(mtodos.getTodos());
            }
            return null;
        }
    }
}
