package com.ilyamarvin.checkyourweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ilyamarvin.checkyourweather.CardView.CardAdapter;
import com.ilyamarvin.checkyourweather.CardView.CardViewModel;
import com.ilyamarvin.checkyourweather.Retrofit.ApiInterface;
import com.ilyamarvin.checkyourweather.Retrofit.Data.WeatherData;
import com.ilyamarvin.checkyourweather.RoomData.Card;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private CardViewModel cardViewModel;

    private Button addCityButton;
    private TextView tempText, descText, humidityText;
    private EditText textField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        CardAdapter cardAdapter = new CardAdapter();
        recyclerView.setAdapter(cardAdapter);

        addCityButton = findViewById(R.id.add_button);
        textField = findViewById(R.id.enter_city_name);
        cardViewModel = ViewModelProviders.of(this).get(CardViewModel.class);
        cardViewModel.getAllCards().observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {
                cardAdapter.setCards(cards);
            }
        });

        addCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWeatherData(textField.getText().toString().trim());

            }
        });
    }

        private void getWeatherData(String name) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

            Call<WeatherData> call = apiInterface.getWeatherData(name);

            call.enqueue(new Callback<WeatherData>() {
                @Override
                public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                    try {
                        tempText.setText("Temp " + response.body().getMain().getTemp() + " C");
                        descText.setText("Feels Like " + response.body().getMain().getFeels_like());
                        humidityText.setText("Humidity " + response.body().getMain().getHumidity());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<WeatherData> call, Throwable t) {

                }
            });

        }


        @Override
        public void onBackPressed() {
            super.onBackPressed();
        }
}