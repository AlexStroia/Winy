package co.alexdev.winy.feature.ui.product.wine.uimodel;

import java.util.Objects;

import co.alexdev.winy.core.model.wines.ProductMatches;

public class ProductMatchesViewModel {
    public int id;
    public String description;
    public String price;
    public String imageUrl;
    public double averageRating;
    public int ratingCount;
    public String food;
    public String pairedWines;
    public double score;
    public String link;

    public ProductMatchesViewModel(int id, String description, String price, String imageUrl, double averageRating, int ratingCount, String food, String pairedWines, double score, String link) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.averageRating = averageRating;
        this.ratingCount = ratingCount;
        this.food = food;
        this.pairedWines = pairedWines;
        this.score = score;
        this.link = link;
    }

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
                pairedWines.equals(that.pairedWines) &&
                link.equals(that.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, price, imageUrl, averageRating, ratingCount, food, pairedWines, score, link);
    }
}
