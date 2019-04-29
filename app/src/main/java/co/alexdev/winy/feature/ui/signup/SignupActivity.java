package co.alexdev.winy.feature.ui.signup;

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
import co.alexdev.winy.core.util.Constants;
import co.alexdev.winy.core.util.factory.SignupLoginViewModelFActory;
import co.alexdev.winy.databinding.ActivitySignupBinding;
import co.alexdev.winy.feature.ui.product.ProductActivity;
import co.alexdev.winy.feature.ui.signup.uimodel.SignupActivityViewModel;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private SignupActivityViewModel signupActivityViewModel;

    @Inject
    SignupLoginViewModelFActory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WinyComponent component = DaggerWinyComponent.builder().build();
        component.inject(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);

        binding.setLifecycleOwner(this);
        signupActivityViewModel = ViewModelProviders.of(this, factory).get(SignupActivityViewModel.class);
        getLifecycle().addObserver(signupActivityViewModel);
        binding.setViewModel(signupActivityViewModel);

        signupActivityViewModel.signupStateEnumLiveData.observe(this, signupStateEnum -> {

            if (Constants.FIREBASE_DATABASE.SIGNUP_STATE.SUCCES.equals(signupStateEnum)) {
                ProductActivity.startActivity(this);
                finish();
            } else if (Constants.FIREBASE_DATABASE.SIGNUP_STATE.FAILURE.equals(signupStateEnum)) {
                binding.progressBar.setVisibility(View.GONE);
                Snackbar.make(binding.coordinator, signupActivityViewModel.userMessage, Snackbar.LENGTH_LONG).show();
            } else if (Constants.FIREBASE_DATABASE.SIGNUP_STATE.STARTED.equals(signupStateEnum)) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, SignupActivity.class));
    }
}
