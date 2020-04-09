package com.example.androidviewmodel.data;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.androidviewmodel.MyApp;
import com.example.androidviewmodel.R;
import com.example.androidviewmodel.data.Remote.Retrofit.RetrofitApiError;
import com.example.androidviewmodel.data.Remote.Retrofit.RetrofitApiHelper;
import com.example.androidviewmodel.data.beans.Hero;
import com.example.androidviewmodel.data.local.CacheApi;
import com.example.androidviewmodel.data.local.CacheApiDatabase;
import com.general.ui.utils.CustomProgressBar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.androidviewmodel.data.Remote.Retrofit.RetrofitApiHelper.API_GET_ALL_PARAMS;
import static com.example.androidviewmodel.data.Remote.Retrofit.RetrofitApiHelper.API_GET_ALL_URL;
import static com.example.androidviewmodel.data.Remote.Retrofit.RetrofitApiHelper.BASE_URL;
import static com.example.androidviewmodel.data.local.CacheApi.insertInCache;

public class DataManager implements RetrofitApiError {



    @Inject RetrofitApiHelper retrofitApiHelper;
    @Inject CacheApiDatabase cacheApiDatabase;
    @Inject CustomProgressBar customProgressBar;

     public DataManager( Context context  )
    {
        ((MyApp)context).getMyComponent().inject(this);
         retrofitApiHelper.setRetrofitApiError(this);
    }

    public LiveData<List<Hero>> getHerosRetrofit(AppCompatActivity appCompatActivity   , boolean hasCache) {

        beforeNetworkCall(appCompatActivity);

        MutableLiveData<List<Hero>> heroList=    new MutableLiveData<List<Hero>>();
        //we will load it asynchronously from server in this method
        if (checkInternet(appCompatActivity))
        {
            Call<List<Hero>> call = retrofitApiHelper.getAPIService(appCompatActivity).getHeroes();
            call.enqueue(new Callback<List<Hero>>() {
                @Override
                public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response) {
                    afterNetworkCall(appCompatActivity);
                    if(hasCache)
                    {
                        insertInCache(cacheApiDatabase,API_GET_ALL_URL,API_GET_ALL_PARAMS,response.body().getClass().getName()
                                , Hero.class.getName(),new Gson().toJson(response.body()));
                    }
                    //  heroList.setValue(response.body());//correct
                    heroList.postValue(response.body());//correct
                }

                @Override
                public void onFailure(Call<List<Hero>> call, Throwable t) {
                    onResponseError(appCompatActivity,call.request(),t);
                }
            });

        }
        else
        {
            noNetworkConnection(appCompatActivity);
            if(hasCache)
            {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CacheApi cacheApi = cacheApiDatabase.cacheApiDao().getObjectSync(API_GET_ALL_URL,API_GET_ALL_PARAMS);
                        if (cacheApi != null
                        ) {
                            heroList.postValue((List<Hero>) CacheApi.loadDataFromCache(cacheApi));
                            afterNetworkCall(appCompatActivity);
                        }
                    }
                }).start();
            }

        }
        return heroList ;
    }


    public LiveData<List<Hero>> getHerosFastNetworking(  AppCompatActivity appCompatActivity  , boolean hasCache) {

        beforeNetworkCall(appCompatActivity);

        MutableLiveData<List<Hero>> heroList=    new MutableLiveData<List<Hero>>();
        //we will load it asynchronously from server in this method
        if (checkInternet(appCompatActivity))
        {
            AndroidNetworking.get(BASE_URL+"marvel")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsObjectList(Hero.class, new ParsedRequestListener<ArrayList<Hero>>() {

                        @Override
                        public void onResponse(ArrayList<Hero> response) {
                            afterNetworkCall(appCompatActivity);
                         // do anything with response
                            if(hasCache)
                            {
                                insertInCache(cacheApiDatabase,API_GET_ALL_URL,API_GET_ALL_PARAMS,response.getClass().getName()
                                        , Hero.class.getName(),new Gson().toJson(response));
                            }
                            // heroList.setValue(response);//correct
                            heroList.postValue(response);//correct
                        }

                        @Override
                        public void onError(ANError anError) {
                            // handle error
                        }
                    });



        }
        else
        {
            noNetworkConnection(appCompatActivity);
            if(hasCache)
            {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CacheApi cacheApi = cacheApiDatabase.cacheApiDao().getObjectSync(API_GET_ALL_URL,API_GET_ALL_PARAMS);
                        if (cacheApi != null
                        ) {
                            heroList.postValue((List<Hero>) CacheApi.loadDataFromCache(cacheApi));
                            afterNetworkCall(appCompatActivity);
                        }
                    }
                }).start();
            }
        }
        return heroList ;
    }

    @Override
    public void onInternetUnavailable(AppCompatActivity appCompatActivity ,Request request) {
        Log.e("url",request.url().url().toString());
        afterNetworkCall(appCompatActivity);
    }

    @Override
    public void onResponseError(AppCompatActivity appCompatActivity  , Request request,Throwable t) {
        afterNetworkCall(appCompatActivity);
        Log.e("url",request.url().url().toString());

    }

    public static boolean checkInternet(AppCompatActivity appCompatActivity ) {
        ConnectivityManager cm = (ConnectivityManager) appCompatActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }


    private void beforeNetworkCall(AppCompatActivity appCompatActivity) {
        customProgressBar.show(appCompatActivity,
                true, null, appCompatActivity.getString(R.string.wait), false, null,
                false, 0, R.style.MyProgressDialogStyle);
    }

    private void noNetworkConnection(AppCompatActivity appCompatActivity) {
        appCompatActivity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(appCompatActivity, appCompatActivity.getString(R.string.no_intrnet_connection), Toast.LENGTH_LONG).show();
            }
        });

        customProgressBar.dismissProgress(appCompatActivity);
    }

    private void afterNetworkCall(AppCompatActivity appCompatActivity) {
        customProgressBar.dismissProgress(appCompatActivity);
    }
}
