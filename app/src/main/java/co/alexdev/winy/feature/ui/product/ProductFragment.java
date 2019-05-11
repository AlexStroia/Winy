package co.alexdev.winy.feature.ui.product;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.tabs.TabLayout;

import co.alexdev.winy.databinding.FragmentProductBinding;
import co.alexdev.winy.feature.ui.product.uimodel.ProductActivityViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {

    private FragmentProductBinding mBinding;
    private ProductActivityViewModel productActivityViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentProductBinding.inflate(inflater, container, false);
        mBinding.setLifecycleOwner(this);
        productActivityViewModel = ViewModelProviders.of(this).get(ProductActivityViewModel.class);
        mBinding.setViewModel(productActivityViewModel);

        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        mBinding.tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mBinding.viewPager));
        mBinding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mBinding.tabLayout));

        return mBinding.getRoot();
    }
}
