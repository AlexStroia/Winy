package co.alexdev.winy.core.model.wines;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "TABLE_PRODUCT_MATCHES")
public class ProductMatches {

    @PrimaryKey
    public int id = 0;
    public String description = "";
    public String price = "";
    public String imageUrl = "";
    public double averageRating = 0.0;
    public int ratingCount = 0;
    public String query = "";
    public String pairedWines;
    public double score = 0.0;
    public String link = "";

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
                query.equals(that.query) &&
                pairedWines.equals(that.pairedWines) &&
                link.equals(that.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, price, imageUrl, averageRating, ratingCount, query, pairedWines, score, link);
    }
}
