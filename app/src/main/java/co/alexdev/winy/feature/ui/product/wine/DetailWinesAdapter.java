package co.alexdev.winy.feature.ui.product.wine;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import co.alexdev.winy.core.util.DiffCallbacks;
import co.alexdev.winy.databinding.ItemDetailActivityWineBinding;
import co.alexdev.winy.feature.ui.detail.uimodel.DetailActivityProductViewModel;

public class DetailWinesAdapter extends ListAdapter<DetailActivityProductViewModel, DetailWinesAdapter.DetailWinesViewHolder> {

    private static OnWineClickListener clickListener;

    public DetailWinesAdapter(OnWineClickListener clickListener) {
        super(new DiffCallbacks.DetailWinesDiff());
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public DetailWinesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DetailWinesViewHolder(ItemDetailActivityWineBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull DetailWinesViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public interface OnWineClickListener {
        void onWineClickListener(int position, ImageView imageView, TextView textView);
    }

    static class DetailWinesViewHolder extends RecyclerView.ViewHolder {

        private ItemDetailActivityWineBinding binding;

        DetailWinesViewHolder(ItemDetailActivityWineBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(view -> clickListener.onWineClickListener(binding.getViewModel().id, binding.ivWine, binding.tvProductMatchesDescription));
        }

        public void bind(DetailActivityProductViewModel detailProductViewModel) {
            binding.setViewModel(detailProductViewModel);
        }
    }
}
