package co.alexdev.winy.core.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import co.alexdev.winy.core.model.wines.PairingText;

@Dao
public interface PairingTextDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PairingText pairingText);

    @Query("SELECT * FROM TABLE_PAIRING_DESCRIPTION where food =:query")
    LiveData<PairingText> loadPairedTextByFood(String query);

    @Query("DELETE FROM TABLE_PAIRING_DESCRIPTION where food =:query")
    void deleteAll(String query);

}
