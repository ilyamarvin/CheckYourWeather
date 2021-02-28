package com.ilyamarvin.checkyourweather.Retrofit;

import com.ilyamarvin.checkyourweather.Retrofit.Data.WeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("weather?appid=56e9c4879ae3062c3a25116d0a29f652&units=metric")
    Call<WeatherData> getWeatherData(@Query("q") String name);
}