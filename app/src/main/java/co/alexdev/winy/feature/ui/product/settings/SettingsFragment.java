package co.alexdev.winy.feature.ui.product.settings;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProviders;

import java.util.Objects;

import co.alexdev.winy.R;
import co.alexdev.winy.databinding.FragmentSettingsBinding;
import co.alexdev.winy.feature.BaseFragment;
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
        viewModel = ViewModelProviders.of(this).get(SettingsFragmentViewModel.class);
        binding.setViewModel(viewModel);
        binding.containerLogout.setOnClickListener(view -> showAlertDialog());
        return binding.getRoot();
    }


    public void showAlertDialog() {
        alertDialogBuilder.setPositiveButton(R.string.yes, (dialogInterface, i) -> {
            viewModel.signoutUser();
            ActivityLogin.startActivity(Objects.requireNonNull(this.getActivity()));
        }).setNegativeButton(R.string.no, (dialogInterface, i) -> {
        }).setMessage(R.string.logout_message).show();
    }
}
