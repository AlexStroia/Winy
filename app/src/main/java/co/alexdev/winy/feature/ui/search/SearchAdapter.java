package co.alexdev.winy.feature.ui.search;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import co.alexdev.winy.core.util.DiffCallbacks;
import co.alexdev.winy.databinding.ItemSearchWineBinding;
import co.alexdev.winy.feature.ui.search.uimodel.SearchActivityProductViewModel;

public class SearchAdapter extends ListAdapter {

    private static OnSearchItemClickListener listener;

    protected SearchAdapter(OnSearchItemClickListener listener) {
        super(new DiffCallbacks.SearchWinesDiff());
        SearchAdapter.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    interface OnSearchItemClickListener {
        void onSearchItemClick(int id);
    }

    static class SearchWinesViewHolder extends RecyclerView.ViewHolder {

        private ItemSearchWineBinding binding;

        public SearchWinesViewHolder(ItemSearchWineBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(view -> listener.onSearchItemClick(binding.getViewModel().id));
        }

        public void bind(SearchActivityProductViewModel viewModel) {
            binding.setViewModel(viewModel);
        }
    }
}
