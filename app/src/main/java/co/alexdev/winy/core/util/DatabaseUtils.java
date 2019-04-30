package co.alexdev.winy.core.util;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.alexdev.winy.core.model.wines.PairedWines;
import co.alexdev.winy.core.model.wines.ProductMatches;

@Singleton
public class DatabaseUtils {

    @Inject
    public DatabaseUtils() {
    }

    public List<PairedWines> createPairedWinesList(List<String> wines, String food) {
        List<PairedWines> pairedWines = new ArrayList<>();
        for (String wine : wines) {
            pairedWines.add(new PairedWines(food, wine));
        }

        return pairedWines;
    }

    public List<ProductMatches> appendFoodToProductMatches(String food, List<ProductMatches> productMatches) {
        List<ProductMatches> products = new ArrayList<>();
        for (ProductMatches product : productMatches) {
            product.food = food;
            products.add(product);
        }
        return products;
    }
}
