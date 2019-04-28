package co.alexdev.winy.core.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import co.alexdev.winy.core.model.wines.ProductMatches;

@Dao
public interface WinesDao {

    @Query("SELECT * FROM TABLE_PRODUCT_MATCHES WHERE `query` = :food")
    LiveData<List<ProductMatches>> loadWines(String food);

    @Insert
    void insert(List<ProductMatches> productMatches);
}
