package com.ilyamarvin.checkyourweather.RoomData;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Card.class}, version = 1)
public abstract class CardDatabase extends RoomDatabase {

    private static CardDatabase instance;

    public abstract CardDataAccessObject cardDAO();

    public static synchronized CardDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CardDatabase.class, "card_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
