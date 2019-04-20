package co.alexdev.winy.feature.ui.signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import co.alexdev.winy.R;
import co.alexdev.winy.databinding.ActivitySignupBinding;
import co.alexdev.winy.feature.ui.signup.uimodel.SignupActivityViewModel;
import co.alexdev.winy.utils.Constants;
import co.alexdev.winy.utils.Constants.SIGNUP_STATE;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.animation.AccelerateInterpolator;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private SignupActivityViewModel signupActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        setAnimation();
        binding.setLifecycleOwner(this);
        signupActivityViewModel = ViewModelProviders.of(this).get(SignupActivityViewModel.class);
        binding.setViewModel(signupActivityViewModel);

        signupActivityViewModel.signupStateLiveData.observe(this, signupState -> {
            if (signupState == SIGNUP_STATE.FAILED) {

            } else if (signupState == SIGNUP_STATE.STARTED) {

            } else if (signupState == SIGNUP_STATE.SUCCES) {

            } else {

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
