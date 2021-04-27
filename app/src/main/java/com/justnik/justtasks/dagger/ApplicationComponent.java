package com.justnik.justtasks.dagger;

import android.app.Application;
import android.content.Context;

import com.justnik.justtasks.TaskRepository;
import com.justnik.justtasks.annotation.ApplicationContext;
import com.justnik.justtasks.taskdb.TaskDAO;
import com.justnik.justtasks.view.fragments.TaskAddFragment;
import com.justnik.justtasks.view.fragments.TaskDetailFragment;
import com.justnik.justtasks.view.fragments.TaskListFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class,ViewModelFactoryModule.class,TaskViewModelModule.class})
public interface ApplicationComponent {
    void inject(TaskApplication taskApplication);
    void inject(TaskListFragment listFragment);
    void inject(TaskDetailFragment detailFragment);
    void inject(TaskAddFragment addFragment);

    @ApplicationContext
    Context getContext();

    Application getApplication();

    TaskDAO getDAO();

    TaskRepository getTaskRepository();
}