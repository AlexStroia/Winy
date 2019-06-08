package co.alexdev.winy.core.model.wines;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TABLE_PRODUCT_MATCHES")
public class ProductMatches {

    @PrimaryKey
    public int id;
    public String description;
    public String price;
    public String imageUrl;
    public String averageRating;
    public String ratingCount;
    public String title;
    public boolean isAddedToFavorite;
    public String food;
    public String score;
    public String link;
}
