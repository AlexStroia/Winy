package co.alexdev.winy.core.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import co.alexdev.winy.core.model.wines.PairedWines;

@Dao
public interface PairedWinesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<PairedWines> pairedWines);

    @Query("SELECT * FROM TABLE_PAIRED_WINES where food =:query")
    LiveData<List<PairedWines>> loadPairedWinesByFood(String query);

    @Query("DELETE FROM TABLE_PAIRED_WINES where food =:query")
    void deleteAll(String query);

}
