package co.alexdev.winy.core.util;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import javax.inject.Singleton;

import co.alexdev.winy.feature.ui.detail.uimodel.DetailActivityProductViewModel;
import co.alexdev.winy.feature.ui.product.wine.uimodel.PairedWinesViewModel;
import co.alexdev.winy.feature.ui.product.wine.uimodel.ProductMatchesViewModel;
import co.alexdev.winy.feature.ui.search.uimodel.SearchActivityProductViewModel;

@Singleton
public class DiffCallbacks {

    public static class WinesDiff extends DiffUtil.ItemCallback<ProductMatchesViewModel> {

        @Override
        public boolean areItemsTheSame(@NonNull ProductMatchesViewModel oldItem,
                                       @NonNull ProductMatchesViewModel newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProductMatchesViewModel oldItem,
                                          @NonNull ProductMatchesViewModel newItem) {
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

    public static class DetailWinesDiff extends DiffUtil.ItemCallback<DetailActivityProductViewModel> {

        @Override
        public boolean areItemsTheSame(@NonNull DetailActivityProductViewModel oldItem, @NonNull DetailActivityProductViewModel newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull DetailActivityProductViewModel oldItem, @NonNull DetailActivityProductViewModel newItem) {
            return oldItem.equals(newItem);
        }
    }

    public static class SearchWinesDiff extends DiffUtil.ItemCallback<SearchActivityProductViewModel> {

        @Override
        public boolean areItemsTheSame(@NonNull SearchActivityProductViewModel oldItem, @NonNull SearchActivityProductViewModel newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull SearchActivityProductViewModel oldItem, @NonNull SearchActivityProductViewModel newItem) {
            return oldItem.equals(newItem);
        }
    }
}
