package com.justnik.justtasks.dagger;

import android.app.Application;
import android.content.Context;

import com.justnik.justtasks.TaskRepository;

import javax.inject.Inject;

public class TaskApplication extends Application {
    protected ApplicationComponent applicationComponent;

    @Inject
    TaskRepository taskRepository;

    public static TaskApplication get(Context context){
        return (TaskApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);
    }

    public ApplicationComponent getComponent(){
        return applicationComponent;
    }
}

