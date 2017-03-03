package com.codepath.simpletodo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.simpletodo.R;
import com.codepath.simpletodo.models.Item;

import java.util.List;

/**
 * Created by Mariano on 03/03/17.
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolderItem> {

    private List<Item> items;

    public TodoAdapter(List<Item> items) {
        this.items = items;
    }

    @Override
    public ViewHolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolderItem(view);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(ViewHolderItem holder, int position) {
        holder.bind(items.get(position));
    }

    class ViewHolderItem extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvNote;

        ViewHolderItem(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvNote = (TextView) itemView.findViewById(R.id.tvNote);
        }

        void bind(Item item) {
            tvTitle.setText(item.title);
            tvNote.setText(item.note);
            int color = 0;
            switch (item.priority) {
                case 0:
                    color = R.color.colorAccent;
                    break;
                case 1:
                    color = R.color.colorPrimary;
                    break;
                case 2:
                    color = R.color.colorPrimaryDark;
                    break;
            }
            tvTitle.setBackgroundColor(itemView.getResources().getColor(color));
            tvNote.setBackgroundColor(itemView.getResources().getColor(color));
        }
    }
}
