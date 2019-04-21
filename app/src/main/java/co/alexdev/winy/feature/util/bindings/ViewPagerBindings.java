package co.alexdev.winy.feature.util.bindings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

import co.alexdev.winy.feature.ui.product.PagerStateAdapter;

public class ViewPagerBindings {

    @BindingAdapter({"items"})
    public static void setItems(ViewPager pager, List<String> titles) {
        PagerAdapter adapter = null;
        if (pager.getAdapter() == null) {
            adapter = new PagerStateAdapter(((AppCompatActivity) pager.getContext()).getSupportFragmentManager(), titles);
        }
        pager.setAdapter(adapter);
    }
}
