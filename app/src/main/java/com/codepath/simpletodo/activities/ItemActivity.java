package com.codepath.simpletodo.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.codepath.simpletodo.R;
import com.codepath.simpletodo.database.DatabaseManager;
import com.codepath.simpletodo.models.Item;
import com.codepath.simpletodo.utils.Constants;

import java.util.Arrays;
import java.util.List;

public class ItemActivity extends AppCompatActivity {

    private DatabaseManager databaseManager;
    private Item item;

    private EditText etTitle, etNote;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        setupActionBar();
        defaultSetup();
        setupItem();
        setupSpinner();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void defaultSetup() {
        databaseManager = new DatabaseManager(this);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etNote = (EditText) findViewById(R.id.etNote);
        spinner = (Spinner) findViewById(R.id.spinner);
    }

    private void setupItem() {
        if (getIntent().getSerializableExtra(Constants.ITEM) == null) {
            item = new Item();
        } else {
            item = (Item) getIntent().getSerializableExtra(Constants.ITEM);
            etTitle.setText(item.title);
            etNote.setText(item.note);
            spinner.setSelection(item.priority);
        }
    }

    private void setupSpinner() {
        List<String> priorities = Arrays.asList(Constants.HIGH, Constants.MEDIUM, Constants.LOW);
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

    private void saveItem() {
        String title = etTitle.getText().toString();
        String note = etNote.getText().toString();

        if (title.length() < 1) {
            etTitle.setError("Please enter a title");
            etTitle.requestFocus();
            return;
        }

        if (note.length() < 1) {
            etNote.setError("Please enter a description");
            etNote.requestFocus();
            return;
        }

        item.title = title;
        item.note = note;

        if (getIntent().getSerializableExtra(Constants.ITEM) == null) {
            databaseManager.insertItem(item);
        } else {
            databaseManager.updateItem(item);
        }

        Toast.makeText(this, "Todo saved", Toast.LENGTH_LONG).show();
        finish();
    }

    private void deleteItem() {
        if (item.id != null) {
            databaseManager.deleteItem(item.id);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        if (item.id != null) {
            menu.findItem(R.id.delete_item).setVisible(true);
        } else {
            menu.findItem(R.id.delete_item).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.save_item:
                saveItem();
                return true;
            case R.id.delete_item:
                deleteItem();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
