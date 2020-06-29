package com.example.masrur.todolist;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TodosViewModel extends AndroidViewModel {

    private TodosRepository mRepository;

    private LiveData<List<Todos>> mAllTodoss;

    public TodosViewModel (Application application) {
        super(application);
        mRepository = new TodosRepository(application);
        mAllTodoss = mRepository.getAll();
    }

    LiveData<List<Todos>> getAll() { return mAllTodoss; }

    public void insert(Todos Todos) { mRepository.insert(Todos); }

    public void deletesAll() { mRepository.deletesAll(); }

    public void setDone(Todos todos, Boolean done) {
        mRepository.setDone(todos, done);
    }
}