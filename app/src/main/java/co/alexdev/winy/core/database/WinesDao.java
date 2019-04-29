package co.alexdev.winy.core.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import co.alexdev.winy.core.model.wines.ProductMatches;

@Dao
public interface WinesDao {

    @Query("SELECT * FROM TABLE_PRODUCT_MATCHES WHERE food = :food")
    LiveData<List<ProductMatches>> loadWines(String food);

    @Query("SELECT * FROM TABLE_PRODUCT_MATCHES")
    LiveData<List<ProductMatches>> loadAllWinesFromDatabase();

    @Query("SELECT pairedWines FROM TABLE_PRODUCT_MATCHES WHERE food = :food LIMIT 1")
    LiveData<String> getProductMatches(String food);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<ProductMatches> productMatches);
}
