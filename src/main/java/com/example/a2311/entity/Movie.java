package com.example.a2311.entity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("movie")
public class Movie {

    private String id;
    @Indexed //mongodb索引
    private String name;
    private String price;
    private String time;
    private String durtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDurtime() {
        return durtime;
    }

    public void setDurtime(String durtime) {
        this.durtime = durtime;
    }
}
