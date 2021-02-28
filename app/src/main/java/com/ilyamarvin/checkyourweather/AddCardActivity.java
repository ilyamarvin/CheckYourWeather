package com.ilyamarvin.checkyourweather;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ilyamarvin.checkyourweather.Retrofit.ApiInterface;
import com.ilyamarvin.checkyourweather.Retrofit.Data.WeatherData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddCardActivity extends AppCompatActivity {
    public static final String EXTRA_CITY_NAME = "com.ilyamarvin.checkyourweather.EXTRA_CITY_NAME";
    private EditText enterCityName;
    private Button addCityButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        setTitle("Добавить город");

        addCityButton = findViewById(R.id.add_button);
        enterCityName = findViewById(R.id.enter_city_name);

        addCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCard();
            }
        });
    }


    private void saveCard() {
        String cityName = enterCityName.getText().toString();
        if (cityName.trim().isEmpty()) {
            Toast.makeText(this, "Пожалуйста, введите название города", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_CITY_NAME, cityName);

        setResult(RESULT_OK, data);
        finish();
    }


}
