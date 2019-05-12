package co.alexdev.winy.feature.ui.product.wine;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import co.alexdev.winy.core.util.DiffCallbacks;
import co.alexdev.winy.databinding.ItemWineBinding;
import co.alexdev.winy.feature.ui.product.wine.uimodel.ProductMatchesViewModel;

public class WineAdapter extends ListAdapter<ProductMatchesViewModel, WineAdapter.WinesViewHolder> {

    private static OnWineClickListener clickListener;

    protected WineAdapter(OnWineClickListener clickListener) {
        super(new DiffCallbacks.WinesDiff());
        WineAdapter.clickListener = clickListener;
    }

    @NonNull
    @Override
    public WinesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WinesViewHolder(ItemWineBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull WinesViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    interface OnWineClickListener {
        void onWineClickListener(int position, ImageView imageView);
    }

    static class WinesViewHolder extends RecyclerView.ViewHolder {

        private ItemWineBinding binding;

        WinesViewHolder(ItemWineBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(view -> clickListener.onWineClickListener(binding.getViewModel().id, binding.ivWine));
        }

        public void bind(ProductMatchesViewModel productMatchesViewModel) {
            binding.setViewModel(productMatchesViewModel);
        }
    }
}
