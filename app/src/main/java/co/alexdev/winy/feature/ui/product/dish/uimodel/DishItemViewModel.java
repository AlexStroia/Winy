package co.alexdev.winy.feature.ui.product.dish.uimodel;

import java.util.Objects;

public class DishItemViewModel {

    public String id;
    public String description;
    public String food;

    public DishItemViewModel(String id, String description, String food) {
        this.id = id;
        this.description = description;
        this.food = food;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DishItemViewModel)) return false;
        DishItemViewModel that = (DishItemViewModel) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(description, that.description) &&
                Objects.equals(food, that.food);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, food);
    }
}
