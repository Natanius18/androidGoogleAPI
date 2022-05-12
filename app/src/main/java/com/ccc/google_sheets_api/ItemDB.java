package com.ccc.google_sheets_api;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Item.class},version=1)
public abstract class ItemDB extends RoomDatabase {
    public static ItemDB instance;
    public abstract ItemDAO itemDAO();
}