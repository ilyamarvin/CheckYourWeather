package com.ilyamarvin.checkyourweather;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;
import com.ilyamarvin.checkyourweather.CardView.CardAdapter;
import com.ilyamarvin.checkyourweather.CardView.CardViewModel;
import com.ilyamarvin.checkyourweather.Common.CommonInfo;
import com.ilyamarvin.checkyourweather.Retrofit.ApiInterface;
import com.ilyamarvin.checkyourweather.Retrofit.CurrentWeatherData.CurrentWeatherData;
import com.ilyamarvin.checkyourweather.Retrofit.RetrofitClient;
import com.ilyamarvin.checkyourweather.RoomData.Card;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    private LinearLayout linearLayoutActivityMain;

    private TextView temperature, mainWeather, dateWeather;

    private ImageView imageWeather;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        CardAdapter cardAdapter = new CardAdapter();
        recyclerView.setAdapter(cardAdapter);

        linearLayoutActivityMain = findViewById(R.id.activity_main_screen);

        // Request permission
        Dexter.withActivity(this).
                withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {

                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            buildLocationRequest();
                            buildLocationCallback();

                            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }

                            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
                            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                        }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        Snackbar.make(linearLayoutActivityMain, "Доступ запрещен", Snackbar.LENGTH_LONG).show();
                    }
                }).check();

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

        cardAdapter.setOnCardClickListener(new CardAdapter.OnCardClickListener() {
            @Override
            public void onCardClick(Card card) {

            }
        });
    }

    private void buildLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);

                CommonInfo.current_location = locationResult.getLastLocation();

                Log.d("Location ", locationResult.getLastLocation().getLatitude()+ "/"+locationResult.getLastLocation().getLongitude());
            }
        };
    }

    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10.0f);
    }

    private void getWeatherData(String name) {

        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);

        Call<CurrentWeatherData> weatherDataCall = apiInterface.getWeatherData(name);

        mainWeather = findViewById(R.id.weather_main);
        imageWeather = findViewById(R.id.image_weather);
        temperature = findViewById(R.id.weather_temp);

        weatherDataCall.enqueue(new Callback<CurrentWeatherData>() {
            @Override
            public void onResponse(Call<CurrentWeatherData> call, Response<CurrentWeatherData> response) {
                try {
                    temperature.setText(Double.toString(response.body().getMain().getTemp()));
                    mainWeather.setText(response.body().getWeather().get(1).toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CurrentWeatherData> call, Throwable t) {

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


            Card card = new Card(1614502800, cityName, "Snow", "13n", 0.1);
            getWeatherData(cityName);
            cardViewModel.insert(card);

            Toast.makeText(this, "Card saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Card not saved", Toast.LENGTH_SHORT).show();
        }
    }
}

