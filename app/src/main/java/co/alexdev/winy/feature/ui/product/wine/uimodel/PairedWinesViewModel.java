package co.alexdev.winy.feature.ui.product.wine.uimodel;

import java.util.Objects;

public class PairedWinesViewModel {
    public String description;

    PairedWinesViewModel(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PairedWinesViewModel that = (PairedWinesViewModel) o;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }
}
