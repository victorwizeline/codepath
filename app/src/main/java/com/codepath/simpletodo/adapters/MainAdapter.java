package com.codepath.simpletodo.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.simpletodo.R;
import com.codepath.simpletodo.activities.TodoActivity;
import com.codepath.simpletodo.models.Todo;
import com.codepath.simpletodo.utils.Constants;

import java.util.List;

/**
 * Created by Mariano on 03/03/17.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolderTodo> {

    private List<Todo> todos;

    public MainAdapter(List<Todo> todos) {
        this.todos = todos;
    }

    @Override
    public ViewHolderTodo onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolderTodo(view);
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolderTodo holder, int position) {
        holder.bind(todos.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), TodoActivity.class);
                intent.putExtra(Constants.TODO, todos.get(holder.getAdapterPosition()));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    class ViewHolderTodo extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvNote;
        private View viewColor;

        ViewHolderTodo(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvNote = (TextView) itemView.findViewById(R.id.tvNote);
            viewColor = itemView.findViewById(R.id.viewColor);
        }

        void bind(Todo todo) {
            tvTitle.setText(todo.title);
            tvNote.setText(todo.note);
            int color = 0;
            switch (todo.priority) {
                case 0:
                    color = R.color.orange;
                    break;
                case 1:
                    color = R.color.amber;
                    break;
                case 2:
                    color = R.color.teal;
                    break;
            }
            viewColor.setBackgroundResource(color);
        }
    }
}
