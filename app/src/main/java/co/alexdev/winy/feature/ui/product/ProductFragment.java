package co.alexdev.winy.feature.ui.product;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;

import com.google.android.material.tabs.TabLayout;

import co.alexdev.winy.databinding.FragmentProductBinding;
import co.alexdev.winy.feature.BaseFragment;
import co.alexdev.winy.feature.ui.product.uimodel.ProductActivityViewModel;

public class ProductFragment extends BaseFragment {

    private FragmentProductBinding mBinding;
    private ProductActivityViewModel productActivityViewModel;
    public static final String TAG = "ProductFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentProductBinding.inflate(inflater, container, false);
        mBinding.setLifecycleOwner(this);
        productActivityViewModel = ViewModelProviders.of(this).get(ProductActivityViewModel.class);
        mBinding.setViewModel(productActivityViewModel);

        PagerAdapter adapter = new PagerStateAdapter(getChildFragmentManager(), productActivityViewModel.pagerAdapterTitles);
        mBinding.viewPager.setAdapter(adapter);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        mBinding.tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mBinding.viewPager));
        mBinding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mBinding.tabLayout));

        return mBinding.getRoot();
    }
}
