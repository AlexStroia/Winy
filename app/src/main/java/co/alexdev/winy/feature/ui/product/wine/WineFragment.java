package co.alexdev.winy.feature.ui.product.wine;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.Objects;

import javax.inject.Inject;

import co.alexdev.winy.R;
import co.alexdev.winy.core.di.DaggerWinyComponent;
import co.alexdev.winy.core.di.WinyComponent;
import co.alexdev.winy.core.di.module.ContextModule;
import co.alexdev.winy.core.repository.WinePairingRepository;
import co.alexdev.winy.core.util.factory.WineViewModelFactory;
import co.alexdev.winy.databinding.FragmentWineBinding;
import co.alexdev.winy.feature.ui.product.wine.uimodel.WineFragmentViewModel;


public class WineFragment extends Fragment {

    private FragmentWineBinding binding;
    @Inject
    WinePairingRepository repository;
    private WineViewModelFactory factory;
    private WineFragmentViewModel wineFragmentViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wine, container, false);
        WinyComponent component = DaggerWinyComponent.builder().contextModule(new ContextModule(Objects.requireNonNull(getActivity()))).build();
        component.inject(this);
        factory = new WineViewModelFactory(repository);

        wineFragmentViewModel = ViewModelProviders.of(this, factory).get(WineFragmentViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setViewModel(wineFragmentViewModel);

        binding.autoCompleteTextViewWine.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                wineFragmentViewModel.onSearchPressed(textView.getText().toString()).observe(this, data -> {
                    wineFragmentViewModel.searchedQuery = textView.getText().toString();
                    switch (data.status) {
                        case LOADING:
                            binding.progressBar.setVisibility(View.VISIBLE);
                            break;

                        case ERROR:
                            binding.progressBar.setVisibility(View.GONE);
                            //TODO - Use Coordinator layout and show a snackbar
                            Toast.makeText(this.getActivity(), data.message, Toast.LENGTH_LONG).show();
                            break;

                        case SUCCESS:
                            binding.progressBar.setVisibility(View.GONE);
                            wineFragmentViewModel.setProductMatchesIfSearchedSuccess();
                            break;
                    }
                });
                return true;
            }
            return false;
        });

        return binding.getRoot();
    }
}
