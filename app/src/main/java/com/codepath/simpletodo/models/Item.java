package com.codepath.simpletodo.models;

/**
 * Created by Mariano on 03/03/17.
 */

public class Item {

    public String title;
    public String note;
    public Priority priority;

    public enum Priority {
        High,
        Medium,
        Low
    }

    public Item(String title, String note, Priority priority) {
        this.title = title;
        this.note = note;
        this.priority = priority;
    }
}
