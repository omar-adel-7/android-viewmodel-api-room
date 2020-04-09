package com.example.androidviewmodel.data.Repositories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.androidviewmodel.data.DataManager;
import com.example.androidviewmodel.data.beans.Hero;

import java.util.List;

import javax.inject.Inject;

public class HerosRepository {

    @Inject
    DataManager dataManager;

    @Inject
    public  HerosRepository  ()
    {

    }

    //we will call this method to get the data
    public LiveData<List<Hero>> getHeroes(AppCompatActivity appCompatActivity  , boolean hasCache ) {
        return dataManager.getHerosRetrofit(appCompatActivity,hasCache);
       // return dataManager.getHerosFastNetworking(appCompatActivity,true);
     }
}
