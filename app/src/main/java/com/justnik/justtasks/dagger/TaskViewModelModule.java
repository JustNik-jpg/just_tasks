package com.justnik.justtasks.dagger;

import androidx.lifecycle.ViewModel;

import com.justnik.justtasks.annotation.ViewModelKey;
import com.justnik.justtasks.view.viewmodel.TaskViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class TaskViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TaskViewModel.class)
    public abstract ViewModel bindsAuthViewModel(TaskViewModel viewModel);
}