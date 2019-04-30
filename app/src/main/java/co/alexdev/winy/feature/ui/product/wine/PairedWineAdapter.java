package co.alexdev.winy.feature.ui.product.wine;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import co.alexdev.winy.core.util.DiffCallbacks;
import co.alexdev.winy.databinding.ItemPairedWinesBinding;
import co.alexdev.winy.feature.ui.product.wine.uimodel.PairedWinesViewModel;

public class PairedWineAdapter extends ListAdapter<PairedWinesViewModel, PairedWineAdapter.PairedWinesViewHolder> {

    protected PairedWineAdapter() {
        super(new DiffCallbacks.PairedWinesDiff());
    }

    @NonNull
    @Override
    public PairedWinesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PairedWinesViewHolder(ItemPairedWinesBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull PairedWinesViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class PairedWinesViewHolder extends RecyclerView.ViewHolder {

        private ItemPairedWinesBinding binding;

        public PairedWinesViewHolder(ItemPairedWinesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(PairedWinesViewModel pairedWinesViewModel) {
            binding.setViewModel(pairedWinesViewModel);
        }
    }
}
