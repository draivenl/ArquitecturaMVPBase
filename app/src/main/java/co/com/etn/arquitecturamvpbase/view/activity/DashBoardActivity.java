package co.com.etn.arquitecturamvpbase.view.activity;

import android.content.res.ColorStateList;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.helper.Constants;
import co.com.etn.arquitecturamvpbase.helper.CustomSharedPreferences;
import co.com.etn.arquitecturamvpbase.model.User;
import co.com.etn.arquitecturamvpbase.view.BaseActivity;
import co.com.etn.arquitecturamvpbase.view.adapter.DashBoardTabsAdapter;

public class DashBoardActivity extends BaseActivity {

    private TabLayout tlTabs;
    private ViewPager containerPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        initViews();
        loadToolbar();
        loadTabsAdapter();
    }

    private void loadToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Constants.EMPTY);
        setSupportActionBar(toolbar);
    }

    private void initViews() {
        tlTabs = (TabLayout) findViewById(R.id.dash_board_tlTabs);
        containerPager = (ViewPager) findViewById(R.id.dash_board_container);
    }
    private void loadTabsAdapter() {
        DashBoardTabsAdapter dashBoardTabsAdapter = new DashBoardTabsAdapter(getSupportFragmentManager(), getApplicationContext());
        containerPager.setAdapter(dashBoardTabsAdapter);
        tlTabs.setupWithViewPager(containerPager);
        tlTabs.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        tlTabs.setTabTextColors(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorPrimaryDark)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_close) {
            CustomSharedPreferences preferences = new CustomSharedPreferences(this);
            preferences.deleteValue(User.class.getName());
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }
}
