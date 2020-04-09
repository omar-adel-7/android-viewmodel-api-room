package com.example.androidviewmodel;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.gsonparserfactory.GsonParserFactory;
import com.example.androidviewmodel.di.AppModule;
import com.example.androidviewmodel.di.DaggerMyComponent;
import com.example.androidviewmodel.di.MyComponent;
import com.example.androidviewmodel.di.MyModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;

public class MyApp extends Application {

    private MyComponent mMyComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        // Adding an Network Interceptor for Debugging purpose :
        OkHttpClient okHttpClient = new OkHttpClient() .newBuilder()
                 .build();
        AndroidNetworking.initialize(getApplicationContext(),okHttpClient);
        Gson gson = new GsonBuilder().create();
        AndroidNetworking.setParserFactory(new GsonParserFactory(gson));

        mMyComponent = createMyComponent();

    }

    private MyComponent createMyComponent() {
        return DaggerMyComponent
                .builder()
                .myModule(new MyModule())
                .appModule(new AppModule(this))
                .build();

    }

    public MyComponent getMyComponent() {
        return mMyComponent;
    }

}
