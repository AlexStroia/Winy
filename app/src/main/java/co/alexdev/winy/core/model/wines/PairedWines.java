package co.alexdev.winy.core.model.wines;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "TABLE_PAIRED_WINES")
public class PairedWines {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String food;
    public String description;

    public PairedWines(String food, String description) {
        this.food = food;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PairedWines that = (PairedWines) o;
        return id == that.id &&
                Objects.equals(food, that.food) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, food, description);
    }
}
