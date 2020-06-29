package com.example.masrur.todolist;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodosListAdapter extends RecyclerView.Adapter<TodosListAdapter.TodosViewHolder> {

    private final LayoutInflater mInflater;
    private List<Todos> mTodoss; // Cached copy of Todoss
    private final MainActivity app;

    TodosListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        app = (MainActivity) context;
    }

    @Override
    public TodosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new TodosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TodosViewHolder holder, int position) {
        if (mTodoss != null) {
            final Todos current = mTodoss.get(position);
            Boolean done = current.getDone();
            holder.TodosItemView.setText(current.getTodos());
            CheckBox ch = holder.TodosCheckBox;
            ch.setChecked(done);
            if (done) {
                holder.TodosItemView.setTypeface(null, Typeface.BOLD_ITALIC);
            } else {
                holder.TodosItemView.setTypeface(null, Typeface.NORMAL);
            }

            ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                    app.getModel().setDone(current, isChecked);
                }
            });
        } else {
            // Covers the case of data not being ready yet.
            holder.TodosItemView.setText("No Todos");
        }
    }

    void setTodoss(List<Todos> Todoss){
        mTodoss = Todoss;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mTodoss has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mTodoss != null)
            return mTodoss.size();
        else return 0;
    }

    class TodosViewHolder extends RecyclerView.ViewHolder {
        private final TextView TodosItemView;
        private final CheckBox TodosCheckBox;

        private TodosViewHolder(View itemView) {
            super(itemView);
            TodosItemView = itemView.findViewById(R.id.textView);
            TodosCheckBox = itemView.findViewById(R.id.checkbox);
        }
    }
}