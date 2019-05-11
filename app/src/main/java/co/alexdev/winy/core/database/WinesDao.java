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
    LiveData<List<ProductMatches>> loadAllWines();

    @Query("SELECT * FROM TABLE_PRODUCT_MATCHES where title = :title")
    LiveData<List<ProductMatches>> loadWineByTitle(String title);

    @Query("SELECT * FROM TABLE_PRODUCT_MATCHES where title LIKE '%' || :characters|| '%'")
    LiveData<List<ProductMatches>> loadWinesByCharacters(String characters);

    @Query("SELECT food from TABLE_PAIRED_WINES")
    LiveData<List<String>> loadAllFoodNames();

    @Query("SELECT * FROM TABLE_PRODUCT_MATCHES WHERE id=:id")
    LiveData<ProductMatches> loadWineById(int id);

    @Query("UPDATE TABLE_PRODUCT_MATCHES SET isAddedToFavorite = :isAddedToFavorite WHERE id = :id")
    void update(int id, boolean isAddedToFavorite);

    @Query("SELECT * FROM TABLE_PRODUCT_MATCHES WHERE food = :food and id != :id LIMIT 3")
    LiveData<List<ProductMatches>> loadOtherProductMatches(String food, int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<ProductMatches> productMatches);
}
