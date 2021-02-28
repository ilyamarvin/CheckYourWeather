package com.ilyamarvin.checkyourweather.RoomData;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Card.class}, version = 1)
public abstract class CardDatabase extends RoomDatabase {

    private static CardDatabase instance;

    public abstract CardDataAccessObject cardDAO();

    public static synchronized CardDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CardDatabase.class, "card_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDatabaseAsyncTask(instance).execute();
        }
    };

    private static class PopulateDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {
        private CardDataAccessObject cardDAO;

        private PopulateDatabaseAsyncTask(CardDatabase db) {
            cardDAO = db.cardDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            cardDAO.insert(new Card(1614502800, "Moscow", "light snow", "Snow", "13n", -8.86, -4.22, -4.83 ,-8.2));
            return null;
        }
    }

}
