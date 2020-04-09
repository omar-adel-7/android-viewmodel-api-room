package com.example.androidviewmodel.di;

import android.content.Context;

import androidx.room.Room;

import com.example.androidviewmodel.data.DataManager;
import com.example.androidviewmodel.data.Remote.Retrofit.RetrofitApiHelper;
import com.example.androidviewmodel.data.local.CacheApiDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MyModule {

     @Provides
    @Singleton
    static DataManager providesDataManager(Context context )
    {
     return new DataManager(context);
    }


    //  provides annotation in module for RetrofitApiHelper , use it or use constructor injection  for RetrofitApiHelper
    //  or both if you want but of course not necessary
    @Provides
    @Singleton
    static RetrofitApiHelper providesRetrofitApiHelper()
    {
        return new RetrofitApiHelper();
    }


    @Provides
    @Singleton
    static CacheApiDatabase providesCacheApiDatabase(Context context)
    {
        return Room.databaseBuilder(context.getApplicationContext(),
                CacheApiDatabase.class, "CacheApis.db")
                // using main thread only for updateCacheApi and query CacheApis in CacheApiManagerUtil
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }
}
