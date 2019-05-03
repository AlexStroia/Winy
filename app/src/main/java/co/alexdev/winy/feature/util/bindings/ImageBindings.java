package co.alexdev.winy.feature.util.bindings;

import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import co.alexdev.winy.R;


public class ImageBindings {

    @BindingAdapter(value = {"imageUrl", "loadingIndicator"}, requireAll = false)
    public static void setImage(ImageView image, String url, ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(url)) {
            Picasso.get()
                    .load(Uri.parse(url))
                    
                    .placeholder(R.drawable.ic_wine_placeholder)
                    .error(R.drawable.ic_wine_placeholder)
                    .resize(100, 300)
                    .into(image, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
        }
    }
}
