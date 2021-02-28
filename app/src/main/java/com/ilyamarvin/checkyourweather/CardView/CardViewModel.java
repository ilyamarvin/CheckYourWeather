package com.ilyamarvin.checkyourweather.CardView;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ilyamarvin.checkyourweather.RoomData.Card;

import java.util.List;

public class CardViewModel extends AndroidViewModel {
    private CardRepository cardRepository;
    private LiveData<List<Card>> allCards;


    public CardViewModel(@NonNull Application application) {
        super(application);
        cardRepository = new CardRepository(application);
        allCards = cardRepository.getAllCards();

    }

    public void insert(Card card) {
        cardRepository.insert(card);
    }

    public void delete(Card card) {
        cardRepository.delete(card);
    }

    public void deleteAllCards() {
        cardRepository.deleteAllCards();
    }

    public LiveData<List<Card>> getAllCards() {
        return allCards;
    }
}
