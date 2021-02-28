package com.ilyamarvin.checkyourweather.CardView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ilyamarvin.checkyourweather.R;
import com.ilyamarvin.checkyourweather.RoomData.Card;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardHolder> {

    private List<Card> cards = new ArrayList<>();

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        return new CardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {
        Card currentCard = cards.get(position);
        holder.textViewNameCity.setText(currentCard.getName_city());
        holder.textViewWeatherMain.setText(currentCard.getMain());
        holder.textViewWeatherDescription.setText(currentCard.getDescription());
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
        notifyDataSetChanged();
    }

    class CardHolder extends RecyclerView.ViewHolder {
        private TextView textViewNameCity;
        private TextView textViewWeatherMain;
        private TextView textViewWeatherDescription;

        public CardHolder(@NonNull View itemView) {
            super(itemView);
            textViewNameCity = itemView.findViewById(R.id.city_name);
            textViewWeatherMain = itemView.findViewById(R.id.weather_main);
            textViewWeatherDescription = itemView.findViewById(R.id.weather_desc);
        }
    }

}
