package com.example.alberto.golfroundtracker;

import android.app.Application;

import java.util.ArrayList;

public class MyApplication extends Application {

    private ArrayList<String> roundData = new ArrayList<String>();

    public ArrayList<String> getRoundData(){
        return this.roundData;
    }

    public void setRoundData(String data){
        roundData.add(data);
    }

}
