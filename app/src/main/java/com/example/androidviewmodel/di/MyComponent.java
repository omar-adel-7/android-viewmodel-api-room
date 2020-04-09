package com.example.androidviewmodel.di;

import com.example.androidviewmodel.data.DataManager;
import com.example.androidviewmodel.ui.Main.HeroesViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MyModule.class, AppModule.class})
public interface MyComponent {
    void inject(HeroesViewModel heroesViewModel);
      void inject(DataManager dataManager);
}
