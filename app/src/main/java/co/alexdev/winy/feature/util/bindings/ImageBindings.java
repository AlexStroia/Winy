package co.alexdev.winy.feature.util.bindings;

import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

import co.alexdev.winy.R;


public class ImageBindings {

    @BindingAdapter({"imageUrl"})
    public static void setImage(ImageView image, String url) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.get()
                    .load(Uri.parse(url))
                    .placeholder(R.drawable.ic_wine_placeholder)
                    .error(R.drawable.ic_wine_placeholder)
                    .resize(150, 300)
                    .into(image);
        }
    }
}
