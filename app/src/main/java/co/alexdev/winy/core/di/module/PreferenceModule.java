package co.alexdev.winy.core.di.module;

import android.content.Context;

import co.alexdev.winy.core.util.PreferenceManager;
import dagger.Module;
import dagger.Provides;

@Module(includes = ContextModule.class)
public class PreferenceModule {

    @Provides
    public PreferenceManager providePreference(Context context) {
        return new PreferenceManager(context);
    }
}
