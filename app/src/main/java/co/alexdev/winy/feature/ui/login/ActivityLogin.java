package co.alexdev.winy.feature.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import co.alexdev.winy.R;
import co.alexdev.winy.core.util.Constants;
import co.alexdev.winy.databinding.ActivityLoginBinding;
import co.alexdev.winy.feature.ui.login.uimodel.ActivityLoginViewModel;
import co.alexdev.winy.feature.ui.product.ProductActivity;
import co.alexdev.winy.feature.ui.signup.SignupActivity;

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

public class ActivityLogin extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private ActivityLoginViewModel activityLoginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        setAnimation();
        binding.setLifecycleOwner(this);
        activityLoginViewModel = ViewModelProviders.of(this).get(ActivityLoginViewModel.class);
        binding.setViewModel(activityLoginViewModel);

        activityLoginViewModel.loginStateEnumLiveData.observe(this, loggedState -> {
            if (Constants.FIREBASE_DATABASE.LOGIN_STATE.SUCCES.equals(loggedState)) {
                ProductActivity.startActivity(this);
            } else if (Constants.FIREBASE_DATABASE.LOGIN_STATE.LOADING.equals(loggedState)) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.progressBar.setVisibility(View.GONE);
                Snackbar.make(binding.coordinator, activityLoginViewModel.loginMessage, Snackbar.LENGTH_LONG).show();
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
        context.startActivity(new Intent(context, ActivityLogin.class),
                ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle());
    }
}
