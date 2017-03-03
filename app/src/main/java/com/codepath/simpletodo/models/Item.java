package com.codepath.simpletodo.models;

import java.io.Serializable;

/**
 * Created by Mariano on 03/03/17.
 */

public class Item implements Serializable {

    public Integer id;
    public String title;
    public String note;
    public Integer priority;

    public Item() {
    }

    public Item(int id, String title, String note, int priority) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.priority = priority;
    }
}
