package co.alexdev.winy.feature.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Slide;
import android.transition.Transition;
import android.view.MenuItem;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import javax.inject.Inject;

import co.alexdev.winy.BaseActivity;
import co.alexdev.winy.R;
import co.alexdev.winy.WinyApplication;
import co.alexdev.winy.core.di.WinyComponent;
import co.alexdev.winy.core.repository.WinesRepository;
import co.alexdev.winy.core.util.AnalyticsManager;
import co.alexdev.winy.core.util.PreferenceManager;
import co.alexdev.winy.core.util.factory.BaseViewModelFactory;
import co.alexdev.winy.databinding.ActivitySearchBinding;
import co.alexdev.winy.feature.ui.detail.DetailActivity;
import co.alexdev.winy.feature.ui.product.ProductActivity;
import co.alexdev.winy.feature.ui.search.uimodel.SearchActivityViewModel;

public class SearchActivity extends BaseActivity {

    @Inject
    WinesRepository repository;
    @Inject
    AnalyticsManager analyticsManager;
    @Inject
    PreferenceManager preferenceManager;

    private ActivitySearchBinding binding;
    private BaseViewModelFactory factory;
    private SearchActivityViewModel viewModel;

    public static void startActivity(Context context) {
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((ProductActivity) context);
        context.startActivity(new Intent(context, SearchActivity.class), optionsCompat.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAnimation();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        WinyComponent winyComponent = WinyApplication.getDaggerComponent();
        winyComponent.inject(this);
        binding.setLifecycleOwner(this);
        factory = new BaseViewModelFactory(repository, analyticsManager, preferenceManager);
        viewModel = ViewModelProviders.of(this, factory).get(SearchActivityViewModel.class);

        setRecyclerView();
        binding.setViewModel(viewModel);
    }

    private void setAnimation() {
        Transition slide = new Slide(GravityCompat.END).setInterpolator(new AccelerateDecelerateInterpolator());
        getWindow().setEnterTransition(slide);
    }

    private void setRecyclerView() {
        SearchAdapter searchAdapter = new SearchAdapter((id, food) -> {
            Bundle transitionBundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle();
            DetailActivity.startActivity(this, id, food, null, transitionBundle);
        });
        binding.rvWines.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rvWines.setAdapter(searchAdapter);
        binding.rvWines.setHasFixedSize(true);

        binding.atSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    binding.rvWines.setAdapter(null);
                    animateAlpha(binding.include, true);
                    return;
                }
                viewModel.searchProductByName(charSequence.toString()).observe(SearchActivity.this, data -> {
                    binding.rvWines.setAdapter(searchAdapter);
                    animateAlpha(binding.include, false);
                    searchAdapter.submitList(data);
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
