package co.alexdev.winy.core.model.wines;

import java.util.Objects;

public class ProductMatches {

    private int id;
    private String description;
    private String price;
    private String imageUrl;
    private double averageRating;
    private int ratingCount;
    private double score;
    private String link;

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public double getScore() {
        return score;
    }

    public String getLink() {
        return link;
    }

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
