package co.alexdev.winy.feature.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import co.alexdev.winy.R;
import co.alexdev.winy.core.di.DaggerWinyComponent;
import co.alexdev.winy.core.di.WinyComponent;
import co.alexdev.winy.core.di.module.ContextModule;
import co.alexdev.winy.core.util.Constants;
import co.alexdev.winy.core.util.factory.SignupLoginViewModelFActory;
import co.alexdev.winy.databinding.ActivityLoginBinding;
import co.alexdev.winy.feature.ui.login.uimodel.ActivityLoginViewModel;
import co.alexdev.winy.feature.ui.product.ProductActivity;

public class ActivityLogin extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private ActivityLoginViewModel activityLoginViewModel;

    @Inject
    SignupLoginViewModelFActory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        WinyComponent component = DaggerWinyComponent.builder().contextModule(new ContextModule(this)).build();
        component.inject(this);

        binding.setLifecycleOwner(this);
        activityLoginViewModel = ViewModelProviders.of(this, factory).get(ActivityLoginViewModel.class);
        binding.setViewModel(activityLoginViewModel);

        activityLoginViewModel.loginStateEnumLiveData.observe(this, loggedState -> {
            if (Constants.FIREBASE_DATABASE.LOGIN_STATE.SUCCESS.equals(loggedState)) {
                ProductActivity.startActivity(this);
            } else if (Constants.FIREBASE_DATABASE.LOGIN_STATE.LOADING.equals(loggedState)) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.progressBar.setVisibility(View.GONE);
                Snackbar.make(binding.coordinator, activityLoginViewModel.loginMessage, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, ActivityLogin.class));
    }
}
