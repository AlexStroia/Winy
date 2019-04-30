package co.alexdev.winy.core.model.wines;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "TABLE_PRODUCT_MATCHES")
public class ProductMatches {

    @PrimaryKey
    public int id;
    public String description;
    public String price;
    public String imageUrl;
    public double averageRating;
    public int ratingCount;
    public String food;
    public double score;
    public String link;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductMatches)) return false;
        ProductMatches that = (ProductMatches) o;
        return id == that.id &&
                Double.compare(that.averageRating, averageRating) == 0 &&
                ratingCount == that.ratingCount &&
                Double.compare(that.score, score) == 0 &&
                description.equals(that.description) &&
                price.equals(that.price) &&
                imageUrl.equals(that.imageUrl) &&
                food.equals(that.food) &&
                link.equals(that.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, price, imageUrl, averageRating, ratingCount, food, score, link);
    }
}
