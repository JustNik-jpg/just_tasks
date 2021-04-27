package com.justnik.justtasks.dagger;

import androidx.lifecycle.ViewModelProvider;

import com.justnik.justtasks.view.viewmodel.TaskViewModelFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(TaskViewModelFactory daggerViewModelFactory);

//    Both are same (above is same as below using @Provides annotation).
//    @Provides
//    static ViewModelProvider.Factory bindViewModelFactory2(ViewModelProviderFactory factory) {
//        return factory;
//    }


}
