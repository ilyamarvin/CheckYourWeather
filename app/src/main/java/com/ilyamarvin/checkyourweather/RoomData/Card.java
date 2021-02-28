package com.ilyamarvin.checkyourweather.RoomData;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "card_table")
public class Card {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private long date;
    private String name_city, description, main, icon;
    private double morning_temp, day_temp, evening_temp, night_temp;

    public Card(long date, String name_city, String description, String main, String icon, double morning_temp, double day_temp, double evening_temp, double night_temp) {
        this.date = date;
        this.name_city = name_city;
        this.description = description;
        this.main = main;
        this.icon = icon;
        this.morning_temp = morning_temp;
        this.day_temp = day_temp;
        this.evening_temp = evening_temp;
        this.night_temp = night_temp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public long getDate() {
        return date;
    }

    public String getName_city() {
        return name_city;
    }

    public String getDescription() {
        return description;
    }

    public String getMain() {
        return main;
    }

    public String getIcon() {
        return icon;
    }

    public double getMorning_temp() {
        return morning_temp;
    }

    public double getDay_temp() {
        return day_temp;
    }

    public double getEvening_temp() {
        return evening_temp;
    }

    public double getNight_temp() {
        return night_temp;
    }
}
