package co.alexdev.winy.feature.ui.favorite.uimodel;

import java.util.Objects;

public class FavoriteItemViewModel {

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

    public FavoriteItemViewModel(int id, String description, String price, String imageUrl, String averageRating, String ratingCount, Boolean isAddedToFavorite, String food, String title, String score, String link) {
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
        if (!(o instanceof FavoriteItemViewModel)) return false;
        FavoriteItemViewModel that = (FavoriteItemViewModel) o;
        return id == that.id &&
                isAddedToFavorite == that.isAddedToFavorite &&
                Objects.equals(description, that.description) &&
                Objects.equals(price, that.price) &&
                Objects.equals(imageUrl, that.imageUrl) &&
                Objects.equals(averageRating, that.averageRating) &&
                Objects.equals(ratingCount, that.ratingCount) &&
                Objects.equals(food, that.food) &&
                Objects.equals(title, that.title) &&
                Objects.equals(score, that.score) &&
                Objects.equals(link, that.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, price, imageUrl, averageRating, ratingCount, isAddedToFavorite, food, title, score, link);
    }
}