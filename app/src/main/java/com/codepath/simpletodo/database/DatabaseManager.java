package com.codepath.simpletodo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.codepath.simpletodo.interfaces.DatabaseListener;
import com.codepath.simpletodo.models.Todo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mariano on 03/03/17.
 */

public class DatabaseManager {

    private SQLiteDatabase database;

    public DatabaseManager(Context context) {
        DatabaseTodo databaseTodo = new DatabaseTodo(context);
        database = databaseTodo.getWritableDatabase();
    }

    public void insertTodo(Todo todo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", todo.title);
        contentValues.put("note", todo.note);
        contentValues.put("priority", todo.priority);
        database.insert("todos", null, contentValues);
    }

    public void deleteTodo(int id) {
        database.delete("todos", "id=" + id + "", null);
    }

    public void updateTodo(Todo todo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", todo.title);
        contentValues.put("note", todo.note);
        contentValues.put("priority", todo.priority);
        database.update("todos", contentValues, "id=" + todo.id + "", null);
    }

    public void getItemList(DatabaseListener databaseListener) {
        List<Todo> todos = new ArrayList<>();
        Cursor cursor = database.rawQuery(" SELECT * FROM todos ORDER BY priority", null);
        if (cursor.moveToFirst()) {
            do {
                todos.add(new Todo(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        databaseListener.onListReady(todos);
    }
}
