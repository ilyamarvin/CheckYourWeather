package com.ilyamarvin.checkyourweather;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ilyamarvin.checkyourweather.CardView.CardAdapter;
import com.ilyamarvin.checkyourweather.CardView.CardViewModel;
import com.ilyamarvin.checkyourweather.Retrofit.ApiInterface;
import com.ilyamarvin.checkyourweather.Retrofit.Data.MainData;
import com.ilyamarvin.checkyourweather.Retrofit.Data.WeatherData;
import com.ilyamarvin.checkyourweather.RoomData.Card;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;

    private CardViewModel cardViewModel;

    private TextView temperature, mainWeather, descWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        CardAdapter cardAdapter = new CardAdapter();
        recyclerView.setAdapter(cardAdapter);

        cardViewModel = ViewModelProviders.of(this).get(CardViewModel.class);
        cardViewModel.getAllCards().observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {
                cardAdapter.setCards(cards);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                cardViewModel.delete(cardAdapter.getCardAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Card deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void getWeatherData(String name) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<MainData> mainDataCall = apiInterface.getMainData(name);
        Call<WeatherData> weatherDataCall = apiInterface.getWeatherData(name);

        temperature = findViewById(R.id.temp_now);

        mainDataCall.enqueue(new Callback<MainData>() {
            @Override
            public void onResponse(Call<MainData> call, Response<MainData> response) {
                try {
                    temperature.setText(response.body().getMain().getTemp() + " C");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MainData> call, Throwable t) {

            }
        });

        mainWeather = findViewById(R.id.weather_main);
        descWeather = findViewById(R.id.weather_desc);

        weatherDataCall.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                try {
                    mainWeather.setText(response.body().getWeather().getMain());
                    descWeather.setText(response.body().getWeather().getDescription());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_card_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_card) {
            Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
            startActivityForResult(intent, ADD_NOTE_REQUEST);
        }

        if (id == R.id.delete_all_cards) {
            cardViewModel.deleteAllCards();
            Toast.makeText(this, "All cards deleted", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String cityName = data.getStringExtra(AddCardActivity.EXTRA_CITY_NAME);

            getWeatherData(cityName);

            Card card = new Card(1614502800, cityName, "light snow", "Snow", "13n", -8.86, -4.22, -4.83, -8.2);
            cardViewModel.insert(card);

            Toast.makeText(this, "Card saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Card not saved", Toast.LENGTH_SHORT).show();
        }
    }
}

