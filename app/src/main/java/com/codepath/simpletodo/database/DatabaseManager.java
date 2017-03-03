package com.codepath.simpletodo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.codepath.simpletodo.interfaces.DatabaseListener;
import com.codepath.simpletodo.models.Item;

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

    public void insertItem(Item item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", item.title);
        contentValues.put("note", item.note);
        int priority = 0;
        switch (item.priority) {
            case 0:
                priority = 0;
                break;
            case 1:
                priority = 1;
                break;
            case 2:
                priority = 2;
                break;
        }
        contentValues.put("priority", priority);
        database.insert("items", null, contentValues);
    }

    public void deleteItem(int id) {
        database.delete("items", "id=" + id + "", null);
    }

    public void updateItem(Item item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", item.title);
        contentValues.put("note", item.note);
        int priority = 0;
        switch (item.priority) {
            case 0:
                priority = 0;
                break;
            case 1:
                priority = 1;
                break;
            case 2:
                priority = 2;
                break;
        }
        contentValues.put("priority", priority);
        database.update("items", contentValues, "id=" + item.id + "", null);
    }

    public void getItemList(DatabaseListener databaseListener) {
        List<Item> items = new ArrayList<>();
        Cursor cursor = database.rawQuery(" SELECT * FROM items", null);
        if (cursor.moveToFirst()) {
            do {
                items.add(new Item(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        databaseListener.onListReady(items);
    }
}
