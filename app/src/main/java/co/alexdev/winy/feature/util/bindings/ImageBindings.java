package co.alexdev.winy.feature.util.bindings;

import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class ImageBindings {

    @BindingAdapter(value = {"imageUrl", "loadingIndicator"}, requireAll = false)
    public static void setImage(ImageView image, String url, ProgressBar progressBar) {
        if (!TextUtils.isEmpty(url)) {
            if (progressBar != null) {
                progressBar.setVisibility(View.VISIBLE);
                Picasso.get()
                        .load(Uri.parse(url))
                        .resize(100, 300)
                        .into(image, new Callback() {
                            @Override
                            public void onSuccess() {
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(Exception e) {
                                e.printStackTrace();
                            }
                        });

            } else {
                Picasso.get()
                        .load(url)
                        .resize(100, 300)
                        .into(image);
            }
        }
    }
}
