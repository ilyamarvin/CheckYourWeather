package com.ilyamarvin.checkyourweather.Retrofit;

import com.ilyamarvin.checkyourweather.Retrofit.CurrentWeatherData.CurrentWeatherData;
import com.ilyamarvin.checkyourweather.Retrofit.OneCallAPI.OneCallAPIWeatherData;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("weather?appid=56e9c4879ae3062c3a25116d0a29f652&units=metric")
    Call<CurrentWeatherData> getWeatherData(@Query("q") String name);

    @GET("weather")
    Observable<CurrentWeatherData> getWeatherDataByCityName(@Query("q") String name,
                                                      @Query("appid") String appid,
                                                      @Query("units") String units);

    @GET("weather")
    Observable<CurrentWeatherData> getWeatherByCoord(@Query("lat") String lat,
                                                     @Query("lon") String lon,
                                                     @Query("appid") String appid,
                                                     @Query("units") String units);

    @GET("onecall?&exclude=minutely,hourly,alerts")
    Call<OneCallAPIWeatherData> oneCallWeatherData(@Query("lat") String lat,
                                                   @Query("lon") String lon,
                                                   @Query("appid") String appid,
                                                   @Query("units") String units);
}