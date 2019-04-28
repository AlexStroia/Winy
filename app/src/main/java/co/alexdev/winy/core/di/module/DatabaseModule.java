package co.alexdev.winy.core.di.module;

import android.content.Context;

import androidx.room.Room;

import javax.inject.Singleton;

import co.alexdev.winy.core.database.WinesDao;
import co.alexdev.winy.core.database.WinyDatabase;
import dagger.Module;
import dagger.Provides;

@Module(includes = ContextModule.class)
public class DatabaseModule {
    private static final String DATABASE_NAME = "WINY_DATABASE";

    @Singleton
    @Provides
    public WinyDatabase provideDatabase(Context context) {
        return Room.databaseBuilder(context, WinyDatabase.class, WinyDatabase.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    public WinesDao providesWinesDao(WinyDatabase winyDatabase) {
        return winyDatabase.provideWinesDao();
    }
}
