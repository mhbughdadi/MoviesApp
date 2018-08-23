package com.example.mohamed.movieapp.data;

import java.io.Serializable;

/**
 * Created by Mohamed on 11/24/2016.
 */


public class TrailerData implements Serializable {
    private String key;
    private  String name;

    public void setName(String name) {
        this.name = name;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public TrailerData (String name, String key)
    {
        this.name=name;
        this.key=key;


    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
