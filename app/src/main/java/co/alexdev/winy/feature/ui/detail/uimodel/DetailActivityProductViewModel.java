package co.alexdev.winy.feature.ui.detail.uimodel;

import java.util.Objects;

import co.alexdev.winy.feature.ui.product.wine.uimodel.ProductMatchesViewModel;

public class DetailActivityProductViewModel {
    public int id;
    public String description;
    public String price;
    public String imageUrl;
    public String averageRating;
    public String ratingCount;
    public boolean isAddedToFavorite;
    public String food;
    public String title;
    public String score;
    public String link;

    public DetailActivityProductViewModel(int id, String description, String price, String imageUrl, String averageRating, String ratingCount, Boolean isAddedToFavorite, String food, String title, String score, String link) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.averageRating = averageRating;
        this.ratingCount = ratingCount;
        this.isAddedToFavorite = isAddedToFavorite;
        this.food = food;
        this.title = title;
        this.score = score;
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductMatchesViewModel)) return false;
        ProductMatchesViewModel that = (ProductMatchesViewModel) o;
        return id == that.id &&
                price.equals(that.price) &&
                imageUrl.equals(that.imageUrl) &&
                averageRating.equals(that.averageRating) &&
                ratingCount.equals(that.ratingCount) &&
                food.equals(that.food) &&
                title.equals(that.title) &&
                score.equals(that.score) &&
                link.equals(that.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, imageUrl, averageRating, ratingCount, food, title, score, link);
    }
}
