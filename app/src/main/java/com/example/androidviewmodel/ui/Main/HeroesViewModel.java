package com.example.androidviewmodel.ui.Main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.androidviewmodel.MyApp;
import com.example.androidviewmodel.data.Repositories.HerosRepository;
import com.example.androidviewmodel.data.beans.Hero;

import java.util.List;

import javax.inject.Inject;

public class HeroesViewModel extends AndroidViewModel  {

    @Inject
    HerosRepository herosRepository;
    //this is the data that we will fetch asynchronously
    private LiveData<List<Hero>> heroList;

    public HeroesViewModel(@NonNull Application application) {
        super(application);
        ((MyApp)application).getMyComponent().inject(this);
     }

    //we will call this method to get the data
    public LiveData<List<Hero>> getHeroes(AppCompatActivity appCompatActivity  , boolean hasCache ) {
     heroList=herosRepository.getHeroes(appCompatActivity,hasCache);
    return heroList;
    }

}
