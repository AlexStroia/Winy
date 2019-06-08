package co.alexdev.winy;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import co.alexdev.winy.core.util.Constants;

public class BaseActivity extends AppCompatActivity {

    public void animateAlpha(View view, Boolean showOrHide) {
        view.animate().alpha(showOrHide ? Constants.AnimValues.MAX_ALPHA : Constants.AnimValues.MIN_ALPHA).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(showOrHide ? View.VISIBLE : View.GONE);
            }
        });
    }
}
