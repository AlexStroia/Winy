package co.alexdev.winy.feature.ui.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import co.alexdev.winy.R;
import co.alexdev.winy.core.di.DaggerWinyComponent;
import co.alexdev.winy.core.di.WinyComponent;
import co.alexdev.winy.core.di.module.ContextModule;
import co.alexdev.winy.core.repository.AuthenticationRepository;
import co.alexdev.winy.core.util.factory.SettingsViewModelFactory;
import co.alexdev.winy.databinding.ActivityAccountBinding;
import co.alexdev.winy.feature.ui.account.uimodel.AccountActivityViewModel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

public class AccountActivity extends AppCompatActivity {

    private ActivityAccountBinding binding;
    private AccountActivityViewModel viewModel;

    AuthenticationRepository authenticationRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_account);
        binding.setLifecycleOwner(this);

        WinyComponent winyComponent = DaggerWinyComponent.builder().contextModule(new ContextModule(this))
                .build();
        winyComponent.inject(this);
        authenticationRepository = winyComponent.provideAuthRepository();
        Log.d("AccountActivity", "" + authenticationRepository.toString());

        SettingsViewModelFactory factory = new SettingsViewModelFactory(authenticationRepository);
        viewModel = ViewModelProviders.of(this, factory).get(AccountActivityViewModel.class);
        binding.setViewModel(viewModel);
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, AccountActivity.class));
    }
}
