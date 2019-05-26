package co.alexdev.winy.feature.ui.favorite;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.kennyc.view.MultiStateView;

import java.util.Objects;

import javax.inject.Inject;

import co.alexdev.winy.core.di.DaggerWinyComponent;
import co.alexdev.winy.core.di.WinyComponent;
import co.alexdev.winy.core.di.module.ContextModule;
import co.alexdev.winy.core.repository.WinesRepository;
import co.alexdev.winy.core.util.factory.BaseViewModelFactory;
import co.alexdev.winy.databinding.FragmentFavoriteBinding;
import co.alexdev.winy.feature.BaseFragment;
import co.alexdev.winy.feature.ui.detail.DetailActivity;
import co.alexdev.winy.feature.ui.favorite.uimodel.FavoriteViewModel;
import co.alexdev.winy.feature.util.RecyclerDecoration;

public class FavoriteFragment extends BaseFragment {

    @Inject
    WinesRepository winesRepository;
    private FragmentFavoriteBinding binding;
    private FavoriteViewModel viewModel;
    public static final String TAG = "FavoriteFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        WinyComponent component = DaggerWinyComponent.builder().contextModule(new ContextModule(Objects.requireNonNull(getActivity()))).build();
        component.inject(this);
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);

        BaseViewModelFactory factory = new BaseViewModelFactory(winesRepository);
        viewModel = ViewModelProviders.of(this, factory).get(FavoriteViewModel.class);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        setRecycler();
    }

    private void setRecycler() {
        FavoriteAdapter adapter = new FavoriteAdapter(((id, food, imageView) -> {
            DetailActivity.startActivity(this.getContext(), id, food, imageView, null);
        }));
        binding.rvFavorite.setLayoutManager(new GridLayoutManager(this.getActivity(), 2));
        binding.rvFavorite.setAdapter(adapter);
        binding.rvFavorite.addItemDecoration(new RecyclerDecoration(8));

        viewModel.loadFavProducts().observe(this, favoriteItemViewModels -> {
            if (favoriteItemViewModels.isEmpty()) {
                binding.multistate.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            } else {
                adapter.submitList(favoriteItemViewModels);
            }
        });
    }
}
