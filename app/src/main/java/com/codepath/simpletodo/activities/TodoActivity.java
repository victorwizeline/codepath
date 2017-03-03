package com.codepath.simpletodo.activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.codepath.simpletodo.R;
import com.codepath.simpletodo.database.DatabaseManager;
import com.codepath.simpletodo.models.Todo;
import com.codepath.simpletodo.utils.Constants;

import java.util.Arrays;
import java.util.List;

public class TodoActivity extends AppCompatActivity {

    private DatabaseManager databaseManager;
    private Todo todo;

    private EditText etTitle, etNote;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        setupActionBar();
        defaultSetup();
        setupTodo();
        setupSpinner();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            if (getIntent().getSerializableExtra(Constants.TODO) == null) {
                actionBar.setTitle("New Todo");
            } else {
                actionBar.setTitle("Edit Todo");

            }
        }
    }

    private void defaultSetup() {
        databaseManager = new DatabaseManager(this);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etNote = (EditText) findViewById(R.id.etNote);
        spinner = (Spinner) findViewById(R.id.spinner);
    }

    private void setupTodo() {
        if (getIntent().getSerializableExtra(Constants.TODO) == null) {
            todo = new Todo();
        } else {
            todo = (Todo) getIntent().getSerializableExtra(Constants.TODO);
            etTitle.setText(todo.title);
            etNote.setText(todo.note);
            spinner.setSelection(todo.priority);
            setActionBarColor();
        }
    }

    private void setActionBarColor() {
        int primaryColor = 0;
        int colorPrimaryDark = 0;
        switch (todo.priority) {
            case 0:
                primaryColor = R.color.orange;
                colorPrimaryDark = R.color.orangeDark;
                break;
            case 1:
                primaryColor = R.color.amber;
                colorPrimaryDark = R.color.amberDark;
                break;
            case 2:
                primaryColor = R.color.teal;
                colorPrimaryDark = R.color.tealDark;
                break;
        }
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(primaryColor)));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, colorPrimaryDark));
        }
    }

    private void setupSpinner() {
        List<String> priorities = Arrays.asList(Constants.HIGH, Constants.MEDIUM, Constants.LOW);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, priorities);
        spinner.setAdapter(adapter);
        if (todo.priority != null) {
            spinner.setSelection(todo.priority);
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                todo.priority = adapterView.getSelectedItemPosition();
                setActionBarColor();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void saveTodo() {
        String title = etTitle.getText().toString();
        String note = etNote.getText().toString();
        if (title.length() < 1) {
            etTitle.setError(getString(R.string.enter_title));
            etTitle.requestFocus();
            return;
        }
        if (note.length() < 1) {
            etNote.setError(getString(R.string.enter_description));
            etNote.requestFocus();
            return;
        }
        todo.title = title;
        todo.note = note;

        if (getIntent().getSerializableExtra(Constants.TODO) == null) {
            databaseManager.insertTodo(todo);
        } else {
            databaseManager.updateTodo(todo);
        }
        Toast.makeText(this, R.string.todo_saved, Toast.LENGTH_LONG).show();
        finish();
    }

    private void deleteItem() {
        if (todo.id != null) {
            databaseManager.deleteTodo(todo.id);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        MenuItem deleteTodo = menu.findItem(R.id.delete_todo);
        if (todo.id != null) {
            deleteTodo.setVisible(true);
        } else {
            deleteTodo.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.save_todo:
                saveTodo();
                return true;
            case R.id.delete_todo:
                deleteItem();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
