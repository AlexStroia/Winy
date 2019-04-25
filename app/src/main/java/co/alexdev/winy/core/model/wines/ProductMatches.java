package co.alexdev.winy.core.model.wines;

import java.util.Objects;

public class ProductMatches {

    public final int id = 0;
    public final String description = "";
    public final String price = "";
    public final String imageUrl = "";
    public final double averageRating = 0.0;
    public final int ratingCount = 0;
    public final double score = 0.0;
    public final  String link = "";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductMatches that = (ProductMatches) o;
        return id == that.id &&
                Double.compare(that.averageRating, averageRating) == 0 &&
                ratingCount == that.ratingCount &&
                Double.compare(that.score, score) == 0 &&
                Objects.equals(description, that.description) &&
                Objects.equals(price, that.price) &&
                Objects.equals(imageUrl, that.imageUrl) &&
                Objects.equals(link, that.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, price, imageUrl, averageRating, ratingCount, score, link);
    }
}
