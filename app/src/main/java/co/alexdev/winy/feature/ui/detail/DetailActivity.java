package co.alexdev.winy.feature.ui.detail;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
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
import co.alexdev.winy.core.util.factory.DetailViewModelFactory;
import co.alexdev.winy.databinding.ActivityDetailBinding;
import co.alexdev.winy.feature.ui.detail.uimodel.DetailActivityViewModel;

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
        }
    }

    public static void startActivity(Context context, int id) {
        context.startActivity(new Intent(context, DetailActivity.class).putExtra(WINE_ID, id));
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
