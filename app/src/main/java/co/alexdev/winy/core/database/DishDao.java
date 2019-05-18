package co.alexdev.winy.core.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import co.alexdev.winy.core.model.dish.Dish;

@Dao
public interface DishDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Dish> productMatches);

    @Query("SELECT * FROM TABLE_DISH WHERE searchedQuery = :searchedQuery")
    LiveData<List<Dish>> loadDish(String searchedQuery);

    @Query("SELECT searchedQuery FROM TABLE_DISH")
    LiveData<List<String>> loadAutocompleteNames();

    @Query("DELETE FROM TABLE_DISH where searchedQuery =:query")
    void deleteAll(String query);
}