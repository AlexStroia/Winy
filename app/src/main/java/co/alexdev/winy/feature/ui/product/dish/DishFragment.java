package co.alexdev.winy.feature.ui.product.dish;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import javax.inject.Inject;

import co.alexdev.winy.DishFragmentBinding;
import co.alexdev.winy.core.di.DaggerWinyComponent;
import co.alexdev.winy.core.di.WinyComponent;
import co.alexdev.winy.core.di.module.ContextModule;
import co.alexdev.winy.core.repository.DishRepository;
import co.alexdev.winy.core.util.factory.BaseViewModelFactory;
import co.alexdev.winy.feature.BaseFragment;
import co.alexdev.winy.feature.util.KeyboardManager;


public class DishFragment extends BaseFragment {

    @Inject
    DishRepository repository;
    @Inject
    KeyboardManager keyboardManager;
    private DishFragmentBinding binding;
    private DishViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        WinyComponent component = DaggerWinyComponent.builder().contextModule(new ContextModule(Objects.requireNonNull(getActivity()))).build();
        component.inject(this);
        BaseViewModelFactory factory = new BaseViewModelFactory(repository);
        viewModel = ViewModelProviders.of(this, factory).get(DishViewModel.class);
        binding = DishFragmentBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);

        binding.autoCompleteTextViewFood.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            viewModel.food = textView.getText().toString();
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.onSearchPressed().observe(this, data -> {
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

                            binding.autoCompleteTextViewFood.getText().clear();
                            break;
                    }

                });

            }
            return false;
        });
        return binding.getRoot();
    }
}
