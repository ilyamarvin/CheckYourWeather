package com.ilyamarvin.checkyourweather.RoomData;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CardDataAccessObject {

    @Insert
    void insert(Card card);

    @Delete
    void delete(Card card);

    @Query("DELETE FROM card_table")
    void deleteAllCards();

    @Query("SELECT * FROM card_table ORDER BY id DESC")
    LiveData<List<Card>> getAllCards();
}
