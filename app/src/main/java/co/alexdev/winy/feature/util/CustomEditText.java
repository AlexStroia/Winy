package co.alexdev.winy.feature.util;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;
import co.alexdev.winy.R;

public class CustomEditText extends AppCompatEditText {

    private Drawable mXicondDrable;
    private Drawable mCheckIconDrawable;
    private boolean isXbuttonClicked = false;
    private boolean isXButtonShown = false;

    public CustomEditText(Context context) {
        super(context);
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * Location 0: Start of text (set to null).
     * Location 1: Top of text (set to null).
     * Location 2: End of text (set to desired drawable).
     * Location 3: Bottom of text (set to null).
     **/
    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        mXicondDrable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_wrong, null);
        mCheckIconDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_check, null);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    isXButtonShown = false;
                    hideDrawable();
                } else if (charSequence.length() < 3) {
                    isXButtonShown = true;
                    showXiconDrawable();
                } else {
                    isXButtonShown = false;
                    showCheckIconDrawable();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        setOnTouchListener((view, motionEvent) -> {
            if (getCompoundDrawablesRelative()[2] != null) {
                float btnStart;
                float btnEnd;

                // Detect the touch in RTL or LTR layout direction.
                if (getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
                    btnEnd = mXicondDrable.getIntrinsicWidth() + getPaddingStart();

                    if (motionEvent.getX() < btnEnd) {
                        isXbuttonClicked = true;
                    }
                } else {
                    // Layout is LTR.
                    // Get the start of the button on the right side.
                    btnStart = (getWidth() - getPaddingEnd() - mXicondDrable.getIntrinsicWidth());

                    // If the touch occurred after the start of the button,
                    // set isClearButtonClicked to true.
                    if (motionEvent.getX() > btnStart) {
                        isXbuttonClicked = true;
                    }
                }

                if (isXbuttonClicked && isXButtonShown) {
                    // Check for ACTION_DOWN (always occurs before ACTION_UP).
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        // Switch to the black version of clear button.
                        showXiconDrawable();
                    }
                    // Check for ACTION_UP.
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        // Switch to the opaque version of clear button.
                        // Clear the text and hide the clear button.
                        if (getText() != null) {
                            getText().clear();
                        }
                        hideDrawable();
                        return true;
                    }
                } else {
                    return false;
                }
            }
            return false;
        });


    }

    private void showXiconDrawable() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, mXicondDrable, null);
    }

    private void hideDrawable() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
    }

    private void showCheckIconDrawable() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, mCheckIconDrawable, null);
    }
}
