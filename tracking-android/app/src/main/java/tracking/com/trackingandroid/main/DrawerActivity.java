package tracking.com.trackingandroid.main;

import android.Manifest;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import tracking.com.trackingandroid.R;
import tracking.com.trackingandroid.apps.record_tour.ui.RecordToursFragment;
import tracking.com.trackingandroid.apps.tours.ui.ToursFragment;
import tracking.com.trackingandroid.util.CommonUtils;

public class DrawerActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @BindView(R.id.navigationView) NavigationView navigationView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        ButterKnife.bind(this);
        setupNavigationDrawer();
        initDexter();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupNavigationDrawer() {
        //Toolbar Config
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        toggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);

        navigationView.setNavigationItemSelectedListener(
                item -> {
                    selectDrawerItem(item);
                    return true;
                });

        // Set Default Selected Fragment
        selectDrawerItem(navigationView.getMenu().findItem(R.id.item_tours));
        navigationView.setCheckedItem(R.id.item_tours);
    }

    /**
     * Show Fragment from the menu selected in the Navigation Drawer.
     * @param menuItem item to be selected.
     * */
    private void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.item_record_tours:
                fragmentClass = RecordToursFragment.class;
                break;
            default:
                fragmentClass = ToursFragment.class;
                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        } catch (java.lang.InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }

    private void initDexter() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(listenerLocation)
                .check();
    }

    private PermissionListener listenerLocation = new PermissionListener() {

        @Override
        public void onPermissionGranted(PermissionGrantedResponse response) {
            // called when permissions are granted
        }

        @Override
        public void onPermissionDenied(PermissionDeniedResponse response) {
            if (response.isPermanentlyDenied()) {
                CommonUtils.showSimpleToastMessages(getApplicationContext(), getString(R.string.denied_permission_permanently));
            } else {
                CommonUtils.showSimpleToastMessages(getApplicationContext(), getString(R.string.permission_location_error));
            }
        }

        @Override
        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
            token.continuePermissionRequest();
        }
    };

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
