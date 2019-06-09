package co.alexdev.winy.feature.ui.product.dish;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import co.alexdev.winy.DishFragmentBinding;
import co.alexdev.winy.WinyApplication;
import co.alexdev.winy.core.di.WinyComponent;
import co.alexdev.winy.core.repository.DishRepository;
import co.alexdev.winy.core.util.AnalyticsManager;
import co.alexdev.winy.core.util.factory.BaseViewModelFactory;
import co.alexdev.winy.feature.BaseFragment;
import co.alexdev.winy.feature.util.KeyboardManager;


public class DishFragment extends BaseFragment {

    @Inject
    DishRepository repository;
    @Inject
    KeyboardManager keyboardManager;
    @Inject
    AnalyticsManager analyticsManager;
    private DishFragmentBinding binding;
    private DishViewModel viewModel;
    private DishAdapter dishAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        WinyComponent component = WinyApplication.getDaggerComponent();
        component.inject(this);
        BaseViewModelFactory factory = new BaseViewModelFactory(repository, analyticsManager);
        viewModel = ViewModelProviders.of(this, factory).get(DishViewModel.class);
        binding = DishFragmentBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);

        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);

        setRecyclerView();
        binding.autoCompleteTextViewFood.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            viewModel.wine = textView.getText().toString();
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                onSearchPressed();
            }
            return false;
        });

        binding.autoCompleteTextViewFood.setOnItemClickListener((adapterView, view, i, l) -> {
            String query = adapterView.getItemAtPosition(i).toString();
            viewModel.wine = query;
            onSearchPressed();
        });

        setRecyclerView();
        return binding.getRoot();
    }

    private void onSearchPressed() {
        viewModel.onSearchPressed().observe(this, data -> {
            binding.adView.setVisibility(View.VISIBLE);
            switch (data.status) {
                case LOADING:
                    binding.progressBar.setVisibility(View.VISIBLE);
                    break;

                case ERROR:
                    binding.progressBar.setVisibility(View.GONE);
                    if (data.message != null) {
                        Snackbar.make(binding.coordinator, data.message,
                                Snackbar.LENGTH_LONG).show();
                    }
                    break;

                case SUCCESS:
                    animateAlpha(binding.include, false);
                    binding.progressBar.setVisibility(View.GONE);
                    binding.autoCompleteTextViewFood.clearFocus();
                    viewModel.setProductsDish();

                    viewModel.dishes.observe(this.getActivity(), dishItemViewModels -> {
                        binding.cardview.setVisibility(View.VISIBLE);
                        binding.tvPairingWineDescription.setText(viewModel.description);
                        dishAdapter.submitList(dishItemViewModels);
                    });

                    keyboardManager.hideKeyboard();
                    binding.autoCompleteTextViewFood.getText().clear();
                    break;
            }

        });
    }


    public void setRecyclerView() {
        dishAdapter = new DishAdapter();
        binding.rvFoods.setLayoutManager(new LinearLayoutManager(this.getActivity(), RecyclerView.VERTICAL, false));
        binding.rvFoods.setAdapter(dishAdapter);
        binding.rvFoods.setHasFixedSize(true);
    }
}
