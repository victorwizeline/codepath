package com.codepath.simpletodo.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.simpletodo.R;
import com.codepath.simpletodo.database.DatabaseManager;
import com.codepath.simpletodo.models.Item;

import java.util.Arrays;
import java.util.List;

public class ItemActivity extends AppCompatActivity {

    private EditText etTitle, etNote;
    private Spinner spinner;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        setupActionBar();
        setupViews();
        setItem();
        setupSpinner();
        final DatabaseManager databaseManager = new DatabaseManager(this);

        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.title = etTitle.getText().toString();
                item.note = etNote.getText().toString();
                databaseManager.insertItem(item);
            }
        });
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupViews() {
        etTitle = (EditText) findViewById(R.id.etTitle);
        etNote = (EditText) findViewById(R.id.etNote);
        spinner = (Spinner) findViewById(R.id.spinner);
    }

    private void setItem() {
        if (getIntent().getSerializableExtra("item") == null) {
            item = new Item();
        } else {
            item = (Item) getIntent().getSerializableExtra("item");
        }
    }

    private void setupSpinner() {
        List<String> priorities = Arrays.asList("High", "Medium", "Low");
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, priorities);
        spinner.setAdapter(adapter);
        if (item.priority != null) {
            spinner.setSelection(item.priority);
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item.priority = adapterView.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
