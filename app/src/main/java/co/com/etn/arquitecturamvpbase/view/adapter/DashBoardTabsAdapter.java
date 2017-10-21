package co.com.etn.arquitecturamvpbase.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.helper.Constants;
import co.com.etn.arquitecturamvpbase.view.fragments.CustomerFragment;
import co.com.etn.arquitecturamvpbase.view.fragments.ProductFragment;
import co.com.etn.arquitecturamvpbase.view.fragments.ProfileFragment;

/**
 * Created by Lisandro Gómez on 10/14/17.
 */

public class DashBoardTabsAdapter extends FragmentStatePagerAdapter {
    Context context;

    public DashBoardTabsAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        // Donde debe ir
        switch (position) {
            case 0:
                return new ProductFragment();
            case 1:
                return new CustomerFragment();
            case 2:
                return new ProfileFragment();
            default:
                return new ProductFragment();
        }
    }

    @Override
    public int getCount() {
        // Debe estar la cantidad de tabs a trabajar
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getResources().getString(R.string.products_tab1_name);
            case 1:
                return context.getResources().getString(R.string.products_tab2_name);
            case 2:
                return context.getResources().getString(R.string.products_tab3_name);
            default:
                return Constants.EMPTY;
        }
    }
}
