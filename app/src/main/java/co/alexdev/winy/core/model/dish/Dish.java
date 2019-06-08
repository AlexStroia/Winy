package co.alexdev.winy.core.model.dish;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "TABLE_DISH")
public class Dish {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String searchedQuery;
    public String description;
    public String food;

    public Dish(String searchedQuery, String description, String food) {
        this.searchedQuery = searchedQuery;
        this.description = description;
        this.food = food;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dish)) return false;
        Dish dish = (Dish) o;
        return id == dish.id &&
                Objects.equals(searchedQuery, dish.searchedQuery) &&
                Objects.equals(food, dish.food);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, searchedQuery, food);
    }
}
