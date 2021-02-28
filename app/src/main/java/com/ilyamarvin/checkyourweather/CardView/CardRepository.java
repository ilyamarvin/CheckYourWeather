package com.ilyamarvin.checkyourweather.CardView;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ilyamarvin.checkyourweather.RoomData.Card;
import com.ilyamarvin.checkyourweather.RoomData.CardDataAccessObject;
import com.ilyamarvin.checkyourweather.RoomData.CardDatabase;

import java.util.List;

public class CardRepository {

    private CardDataAccessObject cardDAO;
    private LiveData<List<Card>> allCards;

    public CardRepository(Application application) {
        CardDatabase database = CardDatabase.getInstance(application);
        cardDAO = database.cardDAO();
        allCards = cardDAO.getAllCards();
    }

    public void insert(Card card) {
        new InsertCardAsyncTask(cardDAO).execute(card);
    }

    public void delete(Card card) {
        new DeleteCardAsyncTask(cardDAO).execute(card);
    }

    public void deleteAllCards() {
        new DeleteAllCardsAsyncTask(cardDAO).execute();
    }

    public LiveData<List<Card>> getAllCards() {
        return allCards;
    }

    public static class InsertCardAsyncTask extends AsyncTask<Card, Void, Void> {
        private CardDataAccessObject cardDAO;

        private InsertCardAsyncTask(CardDataAccessObject cardDAO) {
            this.cardDAO = cardDAO;
        }
        @Override
        protected Void doInBackground(Card... cards) {
            cardDAO.insert(cards[0]);
            return null;
        }
    }

    public static class DeleteCardAsyncTask extends AsyncTask<Card, Void, Void> {
        private CardDataAccessObject cardDAO;

        private DeleteCardAsyncTask(CardDataAccessObject cardDAO) {
            this.cardDAO = cardDAO;
        }
        @Override
        protected Void doInBackground(Card... cards) {
            cardDAO.delete(cards[0]);
            return null;
        }
    }

    public static class DeleteAllCardsAsyncTask extends AsyncTask<Void, Void, Void> {
        private CardDataAccessObject cardDAO;

        private DeleteAllCardsAsyncTask(CardDataAccessObject cardDAO) {
            this.cardDAO = cardDAO;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            cardDAO.deleteAllCards();
            return null;
        }
    }

}
