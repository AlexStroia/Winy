package co.alexdev.winy.feature;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import co.alexdev.winy.core.util.Constants;

public class BaseFragment extends Fragment {

    public AlertDialog.Builder alertDialogBuilder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alertDialogBuilder = new AlertDialog.Builder(this.getActivity());
    }

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
