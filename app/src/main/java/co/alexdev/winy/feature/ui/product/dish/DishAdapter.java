package co.alexdev.winy.feature.ui.product.dish;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import co.alexdev.winy.databinding.ItemDishBinding;
import co.alexdev.winy.feature.ui.product.dish.uimodel.DishItemViewModel;

public class DishAdapter extends ListAdapter<DishItemViewModel, DishAdapter.ViewHolder> {

    protected DishAdapter(@NonNull DiffUtil.ItemCallback<DishItemViewModel> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemDishBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ItemDishBinding binding;

        public ViewHolder(ItemDishBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(DishItemViewModel viewModel) {
            binding.setViewModel(viewModel);
        }
    }
}
