package co.alexdev.winy.feature.ui.search;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import javax.inject.Inject;

import co.alexdev.winy.R;
import co.alexdev.winy.core.di.DaggerWinyComponent;
import co.alexdev.winy.core.di.WinyComponent;
import co.alexdev.winy.core.di.module.ContextModule;
import co.alexdev.winy.core.repository.WinePairingRepository;
import co.alexdev.winy.core.util.factory.BaseViewModelFactory;
import co.alexdev.winy.databinding.ActivitySearchBinding;
import co.alexdev.winy.feature.ui.search.uimodel.SearchActivityViewModel;

public class SearchActivity extends AppCompatActivity {


    @Inject
    WinePairingRepository repository;
    private ActivitySearchBinding binding;
    private BaseViewModelFactory factory;
    private SearchActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        WinyComponent winyComponent = DaggerWinyComponent.builder().contextModule(new ContextModule(this))
                .build();
        winyComponent.inject(this);
        factory = new BaseViewModelFactory(repository);
        viewModel = ViewModelProviders.of(this, factory).get(SearchActivityViewModel.class);

        binding.setViewModel(viewModel);
    }
}
