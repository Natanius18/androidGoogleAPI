package com.ccc.google_sheets_api;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ItemDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertItem(Item item);

    @Query("SELECT * FROM Item")
    public Item getItems();
}
