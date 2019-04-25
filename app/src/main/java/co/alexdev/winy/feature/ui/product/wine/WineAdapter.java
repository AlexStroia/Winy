package co.alexdev.winy.feature.ui.product.wine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import co.alexdev.winy.core.model.wines.ProductMatches;
import co.alexdev.winy.core.util.DiffCallbacks;
import co.alexdev.winy.databinding.ItemWineBinding;

public class WineAdapter extends ListAdapter<ProductMatches, WineAdapter.WinesViewHolder> {

    protected WineAdapter() {
        super(new DiffCallbacks.WinesDiff());
    }

    @NonNull
    @Override
    public WinesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WinesViewHolder(ItemWineBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull WinesViewHolder holder, int position) {

    }

    static class WinesViewHolder extends RecyclerView.ViewHolder {

        public WinesViewHolder(ItemWineBinding binding) {
            super(binding.getRoot());
        }

        public void bind() {
            
        }
    }
}
