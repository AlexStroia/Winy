package co.alexdev.winy.feature.util.bindings;

import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.BindingAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import co.alexdev.winy.R;

public class FabBindings {
    @BindingAdapter({"imageDrawable"})
    public static void setImageDrawable(FloatingActionButton button, Boolean isAddedToFavorite) {
        Drawable heartFull = ResourcesCompat.getDrawable(button.getResources(), R.drawable.ic_heart_full, null);
        Drawable heartBorder = ResourcesCompat.getDrawable(button.getResources(), R.drawable.ic_heart_border, null);
        button.setImageDrawable(isAddedToFavorite ? heartFull : heartBorder);
    }
}
