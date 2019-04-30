package co.alexdev.winy.core.util;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import javax.inject.Singleton;

import co.alexdev.winy.core.model.wines.ProductMatches;
import co.alexdev.winy.feature.ui.product.wine.uimodel.PairedWinesViewModel;

@Singleton
public class DiffCallbacks {

    public static class WinesDiff extends DiffUtil.ItemCallback<ProductMatches> {

        @Override
        public boolean areItemsTheSame(@NonNull ProductMatches oldItem, @NonNull ProductMatches newItem) {
            return oldItem.id== newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProductMatches oldItem, @NonNull ProductMatches newItem) {
            return oldItem.equals(newItem);
        }
    }

    public static class PairedWinesDiff extends DiffUtil.ItemCallback<PairedWinesViewModel> {

        @Override
        public boolean areItemsTheSame(@NonNull PairedWinesViewModel oldItem,
                                       @NonNull PairedWinesViewModel newItem) {
            return oldItem.description.equals(newItem.description);
        }

        @Override
        public boolean areContentsTheSame(@NonNull PairedWinesViewModel oldItem,
                                          @NonNull PairedWinesViewModel newItem) {
            return oldItem.equals(newItem);
        }
    }
}
