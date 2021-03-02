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
    private OnCardClickListener listener;

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
        holder.textViewMainWeather.setText(currentCard.getMain());
        holder.textViewTemp.setText((currentCard.getTemp_now())+ " °C");
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
        notifyDataSetChanged();
    }

    public Card getCardAt(int position) {
        return cards.get(position);
    }

    class CardHolder extends RecyclerView.ViewHolder {
        private TextView textViewNameCity, textViewMainWeather, textViewTemp;

        public CardHolder(@NonNull View itemView) {
            super(itemView);
            textViewNameCity = itemView.findViewById(R.id.city_name);
            textViewMainWeather = itemView.findViewById(R.id.weather_main);
            textViewTemp = itemView.findViewById(R.id.weather_temp);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onCardClick(cards.get(position));

                    }
                }
            });
        }
    }

    public interface OnCardClickListener {
        void onCardClick(Card card);
    }

    public void setOnCardClickListener(OnCardClickListener listener) {
        this.listener = listener;
    }

}
