package co.alexdev.winy.core.di.module;

import android.content.Context;

import androidx.room.Room;

import javax.inject.Singleton;

import co.alexdev.winy.core.database.DishDao;
import co.alexdev.winy.core.database.PairedWinesDao;
import co.alexdev.winy.core.database.PairingTextDao;
import co.alexdev.winy.core.database.WinesDao;
import co.alexdev.winy.core.database.WinyDatabase;
import co.alexdev.winy.core.di.SingletoneScope;
import dagger.Module;
import dagger.Provides;

@Module(includes = ContextModule.class)
public class DatabaseModule {

    @Provides
    @SingletoneScope
    public WinyDatabase provideDatabase(Context context) {
        return Room.databaseBuilder(context, WinyDatabase.class, WinyDatabase.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @SingletoneScope
    public WinesDao providesWinesDao(WinyDatabase winyDatabase) {
        return winyDatabase.provideWinesDao();
    }

    @Provides
    @SingletoneScope
    public PairedWinesDao providePairedWinesDao(WinyDatabase winyDatabase) {
        return winyDatabase.providePairedWinesDao();
    }

    @Provides
    @SingletoneScope
    public PairingTextDao providePairingTextDao(WinyDatabase winyDatabase) {
        return winyDatabase.providePairingTextDao();
    }

    @Provides
    @SingletoneScope
    public DishDao provideDishDao(WinyDatabase winyDatabase) {
        return winyDatabase.provideDishDao();
    }
}
