package co.alexdev.winy.feature.ui.signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import co.alexdev.winy.R;
import co.alexdev.winy.databinding.ActivitySignupBinding;
import co.alexdev.winy.feature.ui.loginsignup.LoginSignupActivity;
import co.alexdev.winy.utils.Constants;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        setAnimation();
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
