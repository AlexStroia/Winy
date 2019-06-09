package co.alexdev.winy.feature.ui.product.settings;


import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ShareCompat;
import androidx.lifecycle.ViewModelProviders;

import java.util.Objects;

import co.alexdev.winy.R;
import co.alexdev.winy.WinyApplication;
import co.alexdev.winy.core.util.factory.BaseSettingsFactory;
import co.alexdev.winy.databinding.FragmentSettingsBinding;
import co.alexdev.winy.feature.BaseFragment;
import co.alexdev.winy.feature.ui.account.AccountActivity;
import co.alexdev.winy.feature.ui.login.ActivityLogin;
import co.alexdev.winy.feature.ui.product.settings.uimodel.SettingsFragmentViewModel;

public class SettingsFragment extends BaseFragment {

    private FragmentSettingsBinding binding;
    private SettingsFragmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        BaseSettingsFactory factory = new BaseSettingsFactory(WinyApplication.getDaggerComponent().provideAuthRepository());

        viewModel = ViewModelProviders.of(this, factory).get(SettingsFragmentViewModel.class);
        binding.setViewModel(viewModel);
        binding.containerLogout.setOnClickListener(view -> showAlertDialog());
        binding.containerReport.setOnClickListener(view -> openMailApp());
        binding.containerAccount.setOnClickListener(view -> AccountActivity.startActivity(Objects.requireNonNull(this.getActivity())));

        viewModel.cachedUser.observe(this, cachedUser -> binding.containerAccount.setVisibility(cachedUser == null ? View.GONE : View.VISIBLE));
        return binding.getRoot();
    }

    private void showAlertDialog() {
        alertDialogBuilder.setPositiveButton(R.string.yes, (dialogInterface, i) -> {
            viewModel.signoutUser();
            if (this.getActivity() != null) {
                this.getActivity().finish();
            }
            ActivityLogin.startActivity(Objects.requireNonNull(this.getActivity()));
        }).setNegativeButton(R.string.no, (dialogInterface, i) -> {
        }).setMessage(R.string.logout_message).show();
    }

    private void openMailApp() {
        ShareCompat.IntentBuilder.from(Objects.requireNonNull(this.getActivity()))
                .setType("message/rfc822")
                .addEmailTo(getString(R.string.email_to))
                .setSubject(getString(R.string.os_version) + Build.VERSION.SDK_INT)
                .setText(getString(R.string.describe_problem))
                .startChooser();
    }
}
