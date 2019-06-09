package co.alexdev.winy.core.util;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import javax.inject.Singleton;

import co.alexdev.winy.core.di.SingletoneScope;
import co.alexdev.winy.feature.ui.detail.uimodel.DetailActivityProductViewModel;
import co.alexdev.winy.feature.ui.favorite.uimodel.FavoriteItemViewModel;
import co.alexdev.winy.feature.ui.product.dish.uimodel.DishItemViewModel;
import co.alexdev.winy.feature.ui.product.wine.uimodel.PairedWinesViewModel;
import co.alexdev.winy.feature.ui.product.wine.uimodel.ProductMatchesViewModel;
import co.alexdev.winy.feature.ui.search.uimodel.SearchProductViewModel;

@SingletoneScope
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

    public static class SearchWinesDiff extends DiffUtil.ItemCallback<SearchProductViewModel> {

        @Override
        public boolean areItemsTheSame(@NonNull SearchProductViewModel oldItem, @NonNull SearchProductViewModel newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull SearchProductViewModel oldItem, @NonNull SearchProductViewModel newItem) {
            return oldItem.equals(newItem);
        }
    }

    public static class FavoriteItemsDiff extends DiffUtil.ItemCallback<FavoriteItemViewModel> {

        @Override
        public boolean areItemsTheSame(@NonNull FavoriteItemViewModel oldItem, @NonNull FavoriteItemViewModel newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull FavoriteItemViewModel oldItem, @NonNull FavoriteItemViewModel newItem) {
            return oldItem.equals(newItem);
        }
    }

    public static class DishItemsDiff extends DiffUtil.ItemCallback<DishItemViewModel> {

        @Override
        public boolean areItemsTheSame(@NonNull DishItemViewModel oldItem, @NonNull DishItemViewModel newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull DishItemViewModel oldItem, @NonNull DishItemViewModel newItem) {
            return oldItem.equals(newItem);
        }
    }
}
