package com.justnik.justtasks.dagger;

import android.app.Application;
import android.content.Context;

import com.justnik.justtasks.annotation.ApplicationContext;
import com.justnik.justtasks.taskdb.TaskDAO;
import com.justnik.justtasks.taskdb.TaskDB;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides
    TaskDAO provideDB(@ApplicationContext Context context) {
        return TaskDB.getDatabase(context).taskDAO();
    }

}
