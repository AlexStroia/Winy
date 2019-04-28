package co.alexdev.winy.core.di.module;

import android.content.Context;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private Context context;

    @Inject
    public ContextModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    public Context getContext() {
        return context;
    }
}
