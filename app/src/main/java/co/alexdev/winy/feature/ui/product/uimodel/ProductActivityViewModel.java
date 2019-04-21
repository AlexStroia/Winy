package co.alexdev.winy.feature.ui.product.uimodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.alexdev.winy.R;

public class ProductActivityViewModel extends AndroidViewModel {

    public List<String> pagerAdapterTitles = new ArrayList<>();

    public ProductActivityViewModel(@NonNull Application application) {
        super(application);

        pagerAdapterTitles.addAll(Arrays.asList(application.getResources().getStringArray(R.array.pager_titles)));
    }
}
