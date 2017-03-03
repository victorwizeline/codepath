package com.codepath.simpletodo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.simpletodo.R;
import com.codepath.simpletodo.adapters.TodoAdapter;
import com.codepath.simpletodo.database.DatabaseManager;
import com.codepath.simpletodo.interfaces.DatabaseListener;
import com.codepath.simpletodo.models.Item;

import java.util.List;

public class TodoActivity extends AppCompatActivity implements DatabaseListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        setDatabaseManager();
    }

    private void setDatabaseManager() {
        DatabaseManager databaseManager = new DatabaseManager(this);
        databaseManager.getItemList(this);
    }

    @Override
    public void onListReady(List<Item> items) {
        RecyclerView rvItems = (RecyclerView) findViewById(R.id.rvItems);
        rvItems.setAdapter(new TodoAdapter(items));
        rvItems.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                startActivity(new Intent(this, EditItemActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
