package co.alexdev.winy.feature.ui.detail;

import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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
import co.alexdev.winy.feature.ui.product.wine.uimodel.ProductMatchesViewModel;

public class DetailActivity extends AppCompatActivity {

    private static final String WINE_ID = "WINE_ID";
    @Inject
    WinePairingRepository repository;
    private ActivityDetailBinding binding;
    private DetailViewModelFactory factory;
    private DetailActivityViewModel viewModel;
    private boolean isExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        WinyComponent winyComponent = DaggerWinyComponent.builder().contextModule(new ContextModule(this))
                .build();
        winyComponent.inject(this);
        binding.setLifecycleOwner(this);

        if (getIntent() != null && getIntent().hasExtra(WINE_ID)) {
            factory = new DetailViewModelFactory(repository, getIntent().getIntExtra(WINE_ID, 0));
            viewModel = ViewModelProviders.of(this, factory).get(DetailActivityViewModel.class);
            binding.setViewModel(viewModel);

            binding.btnShow.setOnClickListener(view -> {
                isExpanded = !isExpanded;
                setShowMoreText(isExpanded);
                expandCollapseAnimation(isExpanded);
            });

            viewModel.productMatchesViewModelLiveData.observe(this, new Observer<ProductMatchesViewModel>() {
                @Override
                public void onChanged(ProductMatchesViewModel productMatchesViewModel) {
                    Log.d("DetailActivity", "Value" + productMatchesViewModel.isAddedToFavorite);
                }
            });
        }
    }

    public static void startActivity(Context context, int id, ImageView imageView, TextView textView) {
        ProductActivity productActivity = (ProductActivity) context;
        Pair imagePair = Pair.create(imageView, imageView.getTransitionName());
        Pair textPair = Pair.create(textView, textView.getTransitionName());

        ActivityOptions optionsCompat = ActivityOptions.makeSceneTransitionAnimation(productActivity, imagePair, textPair);
        productActivity.startActivity(new Intent(productActivity, DetailActivity.class).putExtra(WINE_ID, id), optionsCompat.toBundle());
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

    private void setShowMoreText(boolean isExpanded) {
        binding.btnShow.setText(isExpanded ? R.string.show_less : R.string.show_more);
    }
}
