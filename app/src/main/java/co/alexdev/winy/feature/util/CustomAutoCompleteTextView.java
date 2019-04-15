package co.alexdev.winy.feature.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.core.content.ContextCompat;
import co.alexdev.winy.R;

public class CustomAutoCompleteTextView extends AppCompatAutoCompleteTextView {

    private Drawable mFocusedDrawable;
    private Drawable mUnfocusedDrawable;

    public CustomAutoCompleteTextView(Context context) {
        super(context);
        initView();

    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mFocusedDrawable = ContextCompat.getDrawable(this.getContext(), R.drawable.autocomplete_background_focused);
        mUnfocusedDrawable = ContextCompat.getDrawable(this.getContext(), R.drawable.autocomplete_background_unfocused);

        setOnFocusChangeListener((view, hasFocus) -> setBackgroundDrawable(hasFocus ? mFocusedDrawable : mUnfocusedDrawable));
    }
}
