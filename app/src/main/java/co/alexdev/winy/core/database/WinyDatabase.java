package co.alexdev.winy.core.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import co.alexdev.winy.core.model.dish.Dish;
import co.alexdev.winy.core.model.wines.PairedWines;
import co.alexdev.winy.core.model.wines.PairingText;
import co.alexdev.winy.core.model.wines.ProductMatches;

@Database(entities = {ProductMatches.class, PairedWines.class, PairingText.class, Dish.class}, version = 16,
        exportSchema = false)
public abstract class WinyDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "WINY_DB";

    public abstract WinesDao provideWinesDao();

    public abstract PairedWinesDao providePairedWinesDao();

    public abstract PairingTextDao providePairingTextDao();

    public abstract DishDao provideDishDao();

}
