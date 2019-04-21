package co.alexdev.winy.feature.ui.product;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

import co.alexdev.winy.feature.ui.product.dish.DishFragment;
import co.alexdev.winy.feature.ui.product.random.RandomFragment;
import co.alexdev.winy.feature.ui.product.wine.WineFragment;

public class PagerStateAdapter extends FragmentStatePagerAdapter {


    private List<String> titles;

    public PagerStateAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new WineFragment();
            case 1:
                return new DishFragment();
            case 2:
                return new RandomFragment();
        }
        return null;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }
}
