package com.ilyamarvin.checkyourweather;

import androidx.appcompat.app.AppCompatActivity;

import com.ilyamarvin.checkyourweather.Retrofit.ApiInterface;
import com.ilyamarvin.checkyourweather.Retrofit.Data.WeatherData;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button addCityButton;
    private TextView tempText, descText, humidityText;
    private EditText textField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addCityButton = findViewById(R.id.add_button);
        tempText = findViewById(R.id.temp_text);
        descText = findViewById(R.id.desc_text);
        humidityText = findViewById(R.id.humidity_text);
        textField = findViewById(R.id.enter_city_name);

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