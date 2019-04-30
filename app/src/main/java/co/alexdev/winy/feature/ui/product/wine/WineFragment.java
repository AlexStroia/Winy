package co.alexdev.winy.feature.ui.product.wine;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import javax.inject.Inject;

import co.alexdev.winy.R;
import co.alexdev.winy.core.di.DaggerWinyComponent;
import co.alexdev.winy.core.di.WinyComponent;
import co.alexdev.winy.core.di.module.ContextModule;
import co.alexdev.winy.core.repository.WinePairingRepository;
import co.alexdev.winy.core.util.factory.WineViewModelFactory;
import co.alexdev.winy.databinding.FragmentWineBinding;
import co.alexdev.winy.feature.ui.product.wine.uimodel.PairingTextViewModel;
import co.alexdev.winy.feature.ui.product.wine.uimodel.WineFragmentViewModel;
import co.alexdev.winy.feature.util.KeyboardManager;


public class WineFragment extends Fragment {

    private FragmentWineBinding binding;

    @Inject
    WinePairingRepository repository;

    @Inject
    KeyboardManager keyboardManager;

    private WineViewModelFactory factory;
    private WineFragmentViewModel wineFragmentViewModel;
    private PairedWineAdapter pairedWineAdapter;

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

        binding.autoCompleteTextViewWine.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                wineFragmentViewModel.onSearchPressed(textView.getText().toString()).observe(this, data -> {
                    wineFragmentViewModel.food = textView.getText().toString();
                    wineFragmentViewModel.setPairedWinesViewModelList();
                    wineFragmentViewModel.pairedWinesViewModelLiveData.observe(this.getActivity()
                            , content -> {
                                if (content != null && content.size() > 0) {
                                    binding.cardview.setVisibility(View.VISIBLE);
                                    binding.tvPairingWineDescription.setVisibility(View.VISIBLE);
                                    pairedWineAdapter.submitList(content);
                                    wineFragmentViewModel.pairingTextDescription().observe(this,
                                            pairingText -> binding.tvPairingWineDescription.setText(pairingText.description));
                                } else {
                                    binding.cardview.setVisibility(View.GONE);
                                    binding.tvPairingWineDescription.setVisibility(View.GONE);
                                }
                            });

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

            wineFragmentViewModel.onSearchPressed(searchedQuery).observe(this, data -> {
                wineFragmentViewModel.food = searchedQuery;

                wineFragmentViewModel.setPairedWinesViewModelList();

                wineFragmentViewModel.pairedWinesViewModelLiveData.observe(this.getActivity()
                        , content -> {
                            if (content != null && content.size() > 0) {
                                binding.cardview.setVisibility(View.VISIBLE);
                                binding.tvPairingWineDescription.setVisibility(View.VISIBLE);
                                pairedWineAdapter.submitList(content);
                                wineFragmentViewModel.pairingTextDescription().observe(this,
                                        pairingText -> binding.tvPairingWineDescription.setText(pairingText.description));
                            } else {
                                binding.cardview.setVisibility(View.GONE);
                                binding.tvPairingWineDescription.setVisibility(View.GONE);
                            }
                        });
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

    private void setPairedWinesRecyclerView() {
        pairedWineAdapter = new PairedWineAdapter();
        binding.rvWineMatches.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        binding.rvWineMatches.setAdapter(pairedWineAdapter);
    }
}
