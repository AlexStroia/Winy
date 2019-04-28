package co.alexdev.winy.core.util;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import co.alexdev.winy.core.model.wines.ProductMatches;

@Singleton
public class DatabaseUtils {

    public String exctractWinesToString(List<String> wines) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < wines.size(); i++) {
            if (i != wines.size() - 1) {
                stringBuilder.append(wines.get(i)).append(",");
            } else {
                stringBuilder.append(wines.get(i));
            }
        }
        return stringBuilder.toString();
    }

    public List<ProductMatches> appendWinesToProductMatches(String wines, List<ProductMatches> productMatches) {
        List<ProductMatches> products = new ArrayList<>();
        for (ProductMatches product : productMatches) {
            product.pairedWines = wines;
            products.add(product);
        }
        return products;
    }
}
