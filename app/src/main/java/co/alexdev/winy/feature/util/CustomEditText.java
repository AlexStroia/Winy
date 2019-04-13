package co.alexdev.winy.feature.util;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;
import co.alexdev.winy.R;

public class CustomEditText extends AppCompatEditText {

    private Drawable mXicondDrable;
    private Drawable mCheckIconDrawable;

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

    private void init() {
        mXicondDrable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_wrong, null);
        mCheckIconDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_check, null);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void showXiconDrawable() {
        setCompoundDrawables(null, null, mXicondDrable, null);
    }

    private void hideDrawable() {
        setCompoundDrawables(null, null, null, null);
    }

    private void showCheckiconDrawable() {
        setCompoundDrawables(null, null, mCheckIconDrawable, null);
    }
}
