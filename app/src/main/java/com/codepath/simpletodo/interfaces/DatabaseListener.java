package com.codepath.simpletodo.interfaces;

import com.codepath.simpletodo.models.Item;

import java.util.List;

/**
 * Created by Mariano on 03/03/17.
 */

public interface DatabaseListener {
    void onListReady(List<Item> items);
}
