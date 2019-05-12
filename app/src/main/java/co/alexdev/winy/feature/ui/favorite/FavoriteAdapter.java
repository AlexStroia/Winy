package co.alexdev.winy.feature.ui.favorite;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import co.alexdev.winy.core.util.DiffCallbacks;
import co.alexdev.winy.databinding.ItemFavoriteWineBinding;
import co.alexdev.winy.feature.ui.favorite.uimodel.FavoriteItemViewModel;

public class FavoriteAdapter extends ListAdapter<FavoriteItemViewModel, FavoriteAdapter.FavoriteViewHolder> {

    protected FavoriteAdapter() {
        super(new DiffCallbacks.FavoriteItemsDiff());
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteViewHolder(ItemFavoriteWineBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {

    }

    interface OnFavoriteItemClick {
        void onFavoriteItemClick(int id, ImageView imageView);
    }

    static class FavoriteViewHolder extends RecyclerView.ViewHolder {

        private ItemFavoriteWineBinding binding;

        public FavoriteViewHolder(ItemFavoriteWineBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setViewModel(FavoriteItemViewModel favoriteItemViewModel) {
            binding.setViewModel(favoriteItemViewModel);
        }
    }
}
