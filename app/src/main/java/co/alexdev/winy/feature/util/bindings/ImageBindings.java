package co.alexdev.winy.feature.util.bindings;

import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

public class ImageBindings {

    @BindingAdapter({"imageUrl"})
    public static void setImage(ImageView image, String url) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.get().load(Uri.parse(url)).into(image);
        }
    }
}
