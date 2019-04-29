package co.alexdev.winy.core.util;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.alexdev.winy.core.model.wines.ProductMatches;

@Singleton
public class DatabaseUtils {

    @Inject
    public DatabaseUtils() {
    }

    public String extractWinesToString(List<String> wines) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < wines.size(); i++) {
            if (i != wines.size() - 1) {
                String wine = wines.get(i).toUpperCase();
                stringBuilder.append(wine).append(", ");
            } else {
                stringBuilder.append(wines);
            }
        }
        return stringBuilder.toString();
    }

    public List<ProductMatches> appendWinesToProductMatches(String wines, String food, List<ProductMatches> productMatches) {
        List<ProductMatches> products = new ArrayList<>();
        for (ProductMatches product : productMatches) {
            product.pairedWines = wines;
            product.food = food;
            products.add(product);
        }
        return products;
    }
}
