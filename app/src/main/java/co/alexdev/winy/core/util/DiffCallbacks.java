package co.alexdev.winy.core.util;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import javax.inject.Singleton;

import co.alexdev.winy.core.model.wines.ProductMatches;

@Singleton
public class DiffCallbacks {

    public static class WinesDiff extends DiffUtil.ItemCallback<ProductMatches> {

        @Override
        public boolean areItemsTheSame(@NonNull ProductMatches oldItem, @NonNull ProductMatches newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProductMatches oldItem, @NonNull ProductMatches newItem) {
            return oldItem.equals(newItem);
        }
    }
}
