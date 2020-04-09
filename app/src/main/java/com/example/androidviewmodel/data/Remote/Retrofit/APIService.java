package com.example.androidviewmodel.data.Remote.Retrofit;

import com.example.androidviewmodel.data.beans.Hero;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {


    @GET("marvel")
    Call<List<Hero>> getHeroes();


}
