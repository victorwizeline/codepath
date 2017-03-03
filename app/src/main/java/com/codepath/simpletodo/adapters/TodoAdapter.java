package com.codepath.simpletodo.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.simpletodo.R;
import com.codepath.simpletodo.activities.ItemActivity;
import com.codepath.simpletodo.models.Item;
import com.codepath.simpletodo.utils.Constants;

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
    public void onBindViewHolder(final ViewHolderItem holder, int position) {
        holder.bind(items.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ItemActivity.class);
                intent.putExtra(Constants.ITEM, items.get(holder.getAdapterPosition()));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    class ViewHolderItem extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvNote;
        private View viewColor;

        ViewHolderItem(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvNote = (TextView) itemView.findViewById(R.id.tvNote);
            viewColor = itemView.findViewById(R.id.viewColor);
        }

        void bind(Item item) {
            tvTitle.setText(item.title);
            tvNote.setText(item.note);
            int color = 0;
            switch (item.priority) {
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
