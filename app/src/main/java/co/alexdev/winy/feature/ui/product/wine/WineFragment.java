package co.alexdev.winy.feature.ui.product.wine;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import co.alexdev.winy.R;
import co.alexdev.winy.core.di.DaggerWinyComponent;
import co.alexdev.winy.core.di.WinyComponent;
import co.alexdev.winy.core.di.module.ContextModule;
import co.alexdev.winy.core.model.wines.PairingText;
import co.alexdev.winy.core.repository.WinePairingRepository;
import co.alexdev.winy.core.util.factory.WineViewModelFactory;
import co.alexdev.winy.databinding.FragmentWineBinding;
import co.alexdev.winy.feature.ui.product.wine.uimodel.PairedWinesViewModel;
import co.alexdev.winy.feature.ui.product.wine.uimodel.ProductMatchesViewModel;
import co.alexdev.winy.feature.ui.product.wine.uimodel.WineFragmentViewModel;
import co.alexdev.winy.feature.util.KeyboardManager;
import co.alexdev.winy.feature.util.custom.RecyclerViewDecoration;


public class WineFragment extends Fragment {

    private FragmentWineBinding binding;

    @Inject
    WinePairingRepository repository;

    @Inject
    KeyboardManager keyboardManager;

    private WineViewModelFactory factory;
    private WineFragmentViewModel wineFragmentViewModel;
    private PairedWineAdapter pairedWineAdapter;
    private WineAdapter wineAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wine, container, false);
        WinyComponent component = DaggerWinyComponent.builder().contextModule(new ContextModule(Objects.requireNonNull(getActivity()))).build();
        component.inject(this);
        factory = new WineViewModelFactory(repository);

        wineFragmentViewModel =
                ViewModelProviders.of(this.getActivity(), factory).get(WineFragmentViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setViewModel(wineFragmentViewModel);

        setPairedWinesRecyclerView();
        setWinesRecyclerView();

        binding.autoCompleteTextViewWine.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            wineFragmentViewModel.food = textView.getText().toString();
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                wineFragmentViewModel.onSearchPressed().observe(this, data -> {
                    setViewModelObjectData();
                    observePairedWinesViewModelLiveData();
                    observeProductMatchesViewModelLiveData();

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
                            binding.progressBar.setVisibility(View.GONE);
                            wineFragmentViewModel.setProductMatchesListForSearch();
                            binding.autoCompleteTextViewWine.clearFocus();

                            binding.autoCompleteTextViewWine.getText().clear();
                            break;
                    }
                });
                keyboardManager.hideKeyboard();
                return true;
            }
            return false;
        });

        binding.autoCompleteTextViewWine.setOnItemClickListener((parent, view, position, id) -> {
            final String searchedQuery = parent.getItemAtPosition(position).toString();
            wineFragmentViewModel.food = searchedQuery;

            wineFragmentViewModel.onSearchPressed().observe(this, data -> {

                setViewModelObjectData();

                observePairedWinesViewModelLiveData();
                observeProductMatchesViewModelLiveData();

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
                        binding.progressBar.setVisibility(View.GONE);
                        wineFragmentViewModel.setProductMatchesListForSearch();
                        binding.autoCompleteTextViewWine.clearFocus();

                        binding.autoCompleteTextViewWine.getText().clear();
                        break;
                }
            });
            keyboardManager.hideKeyboard();
        });

        return binding.getRoot();
    }

    private void setViewModelObjectData() {
        wineFragmentViewModel.setPairedWinesViewModelList();
        wineFragmentViewModel.setProductMatchesViewModelList();
    }

    private void observePairedWinesViewModelLiveData() {
        LiveData<List<PairedWinesViewModel>> pairedWinesViewModelLiveData = wineFragmentViewModel.pairedWinesViewModelLiveData;
        pairedWinesViewModelLiveData.observe(this, new Observer<List<PairedWinesViewModel>>() {
            @Override
            public void onChanged(List<PairedWinesViewModel> content) {
                pairedWinesViewModelLiveData.removeObserver(this);
                if (content != null && content.size() > 0) {
                    showContent(true);
                    pairedWineAdapter.submitList(content);
                    LiveData<PairingText> pairingTextLiveData = wineFragmentViewModel.pairingTextDescription();
                    pairingTextLiveData.observe(WineFragment.this,
                            new Observer<PairingText>() {
                                @Override
                                public void onChanged(PairingText pairingText) {
                                    pairingTextLiveData.removeObserver(this);
                                    if (pairingText != null && !TextUtils.isEmpty(pairingText.description)) {
                                        binding.tvPairingWineDescription.setText(pairingText.description);
                                    } else {
                                        showContent(false);
                                    }
                                }
                            });
                } else {
                    showContent(false);
                }
            }
        });
    }

    private void observeProductMatchesViewModelLiveData() {
        LiveData<List<ProductMatchesViewModel>> productMatchesViewModelLiveData = wineFragmentViewModel.productMatchesViewModelLiveData;
        productMatchesViewModelLiveData.observe(this, new Observer<List<ProductMatchesViewModel>>() {
            @Override
            public void onChanged(List<ProductMatchesViewModel> content) {
                if (content != null && content.size() > 0) {
                    productMatchesViewModelLiveData.removeObserver(this);
                    wineAdapter.submitList(content);
                }
            }
        });
    }

    private void showContent(Boolean shouldShow) {
        binding.cardview.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
        binding.tvOurChoice.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
        binding.tvPairingWineDescription.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
    }

    private void setPairedWinesRecyclerView() {
        pairedWineAdapter = new PairedWineAdapter();
        binding.rvWineMatches.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        binding.rvWineMatches.setAdapter(pairedWineAdapter);
    }

    private void setWinesRecyclerView() {
        wineAdapter = new WineAdapter(position -> {

        });
        binding.rvWineRecommendation.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvWineRecommendation.setAdapter(wineAdapter);
        binding.rvWineRecommendation.addItemDecoration(new RecyclerViewDecoration(8));
    }
}
