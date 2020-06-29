package com.example.masrur.todolist;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TodosViewModel mTodosViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final TodosListAdapter adapter = new TodosListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewTodoActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });

        mTodosViewModel = ViewModelProviders.of(this).get(TodosViewModel.class);

        mTodosViewModel.getAll().observe(this, new Observer<List<Todos>>() {
            @Override
            public void onChanged(@Nullable final List<Todos> todos) {
                // Update the cached copy of the words in the adapter.
                adapter.setTodoss(todos);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_deletes) {
            mTodosViewModel.deletesAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Todos todos = new Todos(data.getStringExtra(NewTodoActivity.EXTRA_REPLY));
            mTodosViewModel.insert(todos);
        }
    }

    public TodosViewModel getModel() {
        return mTodosViewModel;
    }


}
