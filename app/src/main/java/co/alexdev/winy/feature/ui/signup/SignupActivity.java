package co.alexdev.winy.feature.ui.signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import co.alexdev.winy.R;
import co.alexdev.winy.core.di.DaggerWinyComponent;
import co.alexdev.winy.core.di.WinyComponent;
import co.alexdev.winy.core.util.ViewModelFactory;
import co.alexdev.winy.databinding.ActivitySignupBinding;
import co.alexdev.winy.feature.ui.product.ProductActivity;
import co.alexdev.winy.feature.ui.signup.uimodel.SignupActivityViewModel;
import co.alexdev.winy.core.util.Constants;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private SignupActivityViewModel signupActivityViewModel;

    @Inject
    ViewModelFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WinyComponent component = DaggerWinyComponent.builder().build();
        component.inject(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        setAnimation();
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

    private void setAnimation() {
        Slide slideAnim = new Slide(Gravity.RIGHT);
        slideAnim.setDuration(Constants.AnimDurations.SLIDE_ANIM);
        slideAnim.setInterpolator(new AccelerateInterpolator());
        getWindow().setEnterTransition(slideAnim);
        getWindow().setExitTransition(slideAnim);
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, SignupActivity.class),
                ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle());
    }
}
