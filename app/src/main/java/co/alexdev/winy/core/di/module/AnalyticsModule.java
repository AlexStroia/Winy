package co.alexdev.winy.core.di.module;

import android.content.Context;

import co.alexdev.winy.core.util.AnalyticsManager;
import dagger.Module;
import dagger.Provides;

@Module(includes = ContextModule.class)
public class AnalyticsModule {

    @Provides
    public AnalyticsManager provideAnalytics(Context context) {
        return new AnalyticsManager(context);
    }
}
