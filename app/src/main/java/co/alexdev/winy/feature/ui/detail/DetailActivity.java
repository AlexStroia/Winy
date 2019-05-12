package co.alexdev.winy.feature.ui.detail;

import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import javax.inject.Inject;

import co.alexdev.winy.R;
import co.alexdev.winy.core.di.DaggerWinyComponent;
import co.alexdev.winy.core.di.WinyComponent;
import co.alexdev.winy.core.di.module.ContextModule;
import co.alexdev.winy.core.repository.WinePairingRepository;
import co.alexdev.winy.core.util.factory.DetailViewModelFactory;
import co.alexdev.winy.databinding.ActivityDetailBinding;
import co.alexdev.winy.feature.ui.detail.uimodel.DetailActivityViewModel;
import co.alexdev.winy.feature.ui.product.ProductActivity;
import co.alexdev.winy.feature.ui.product.wine.DetailWinesAdapter;
import co.alexdev.winy.feature.ui.search.SearchActivity;
import co.alexdev.winy.feature.util.bindings.FabBindings;
import co.alexdev.winy.feature.util.bindings.ImageBindings;

public class DetailActivity extends AppCompatActivity {

    private static final String WINE_ID = "WINE_ID";
    private static final String FOOD_NAME = "FOOD_NAME";
    @Inject
    WinePairingRepository repository;
    private ActivityDetailBinding binding;
    private DetailViewModelFactory factory;
    private DetailActivityViewModel viewModel;
    private DetailWinesAdapter adapter;
    private boolean isExpanded = false;

    public static void startActivity(Context context, int id, String food, ImageView imageView, TextView textView, Bundle transitionBundle) {
        if (context instanceof ProductActivity) {
            ProductActivity productActivity = (ProductActivity) context;

            Pair imagePair = Pair.create(imageView, imageView.getTransitionName());
            Pair textPair = Pair.create(textView, textView.getTransitionName());

            ActivityOptions optionsCompat = ActivityOptions.makeSceneTransitionAnimation(productActivity, imagePair, textPair);
            productActivity.startActivity(new Intent(productActivity, DetailActivity.class)
                    .putExtra(WINE_ID, id)
                    .putExtra(FOOD_NAME, food), optionsCompat.toBundle());
        } else if (context instanceof SearchActivity) {
            context.startActivity(new Intent(context, DetailActivity.class)
                    .putExtra(WINE_ID, id)
                    .putExtra(FOOD_NAME, food), transitionBundle);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        WinyComponent winyComponent = DaggerWinyComponent.builder().contextModule(new ContextModule(this))
                .build();
        winyComponent.inject(this);
        binding.setLifecycleOwner(this);

        if (getIntent() != null) {
            if (getIntent().hasExtra(WINE_ID) && getIntent().hasExtra(FOOD_NAME)) {
                factory = new DetailViewModelFactory(repository, getIntent().getIntExtra(WINE_ID, 0), getIntent().getStringExtra(FOOD_NAME));
                viewModel = ViewModelProviders.of(this, factory).get(DetailActivityViewModel.class);
                binding.setViewModel(viewModel);
                setRecyclerView();
                binding.btnShow.setOnClickListener(view -> {
                    isExpanded = !isExpanded;
                    setShowMoreText(isExpanded);
                    expandCollapseAnimation(isExpanded);
                });
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void expandCollapseAnimation(Boolean expand) {
        ObjectAnimator animation = ObjectAnimator.ofInt(
                binding.tvDescriptionContent,
                "maxLines",
                expand ? binding.tvDescriptionContent.getLineCount() : 2);
        animation.setDuration(300);
        animation.start();
    }

    private void setRecyclerView() {
        adapter = new DetailWinesAdapter(wineId -> viewModel.updateUI(wineId).observe(DetailActivity.this, detailActivityProductViewModel -> {
            binding.tvAverageRatingValue.setText(detailActivityProductViewModel.averageRating);
            binding.tvDescriptionContent.setText(detailActivityProductViewModel.description);
            binding.tvPrice.setText(detailActivityProductViewModel.price);
            binding.tvRatingGrade.setText(detailActivityProductViewModel.ratingCount);
            binding.tvAverageRatingValue.setText(detailActivityProductViewModel.averageRating);
            ImageBindings.setImage(binding.ivWineIcon, detailActivityProductViewModel.imageUrl, null);
            FabBindings.setImageDrawable(binding.fbFavorite, detailActivityProductViewModel.isAddedToFavorite);
            binding.tvWineName.setText(detailActivityProductViewModel.title);
            viewModel.updateRecycler(detailActivityProductViewModel.food, detailActivityProductViewModel.id).observe(DetailActivity.this, list -> {
                adapter.submitList(list);
            });
        }));
        binding.rvOtherWines.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        viewModel.similarDetailProductActivityViewModelLiveData.observe(this, adapter::submitList);

        binding.rvOtherWines.setAdapter(adapter);
    }

    private void setShowMoreText(boolean isExpanded) {
        binding.btnShow.setText(isExpanded ? R.string.show_less : R.string.show_more);
    }
}
