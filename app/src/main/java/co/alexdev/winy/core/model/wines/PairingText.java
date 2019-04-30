package co.alexdev.winy.core.model.wines;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TABLE_PAIRING_DESCRIPTION")
public class PairingText {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String food;
    public String description;

    public PairingText(String food, String description) {
        this.food = food;
        this.description = description;
    }
}
