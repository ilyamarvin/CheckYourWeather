package com.ilyamarvin.checkyourweather.RoomData;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "card_table")
public class Card {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private long date;
    private String name_city, main, icon;
    private double temp_now;

    public Card(long date, String name_city, String main, String icon, double temp_now) {
        this.date = date;
        this.name_city = name_city;
        this.main = main;
        this.icon = icon;
        this.temp_now = temp_now;
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

    public String getMain() {
        return main;
    }

    public String getIcon() {
        return icon;
    }

    public double getTemp_now() {
        return temp_now;
    }
}
