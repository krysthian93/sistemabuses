package tracking.com.trackingandroid.apps.tours.ui;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tracking.com.trackingandroid.R;
import tracking.com.trackingandroid.data.model.Location;
import tracking.com.trackingandroid.data.model.Tour;
import tracking.com.trackingandroid.util.CommonUtils;

public class TourDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.toolbarDetailTour) Toolbar toolbar;
    // @BindView(R.id.map_tour_detail) MapView mapTour;

    private GoogleMap mMap;
    private static final String TAG = TourDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_detail);
        // Tour tour = (Tour) getIntent().getSerializableExtra(CommonUtils.TOURS_MOVIE_DETAIL);

        ButterKnife.bind(this);
        setupToolBar();

        // mapTour.onCreate(savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_tour_detail);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Tour tour = (Tour) getIntent().getSerializableExtra(CommonUtils.TOURS_MOVIE_DETAIL);
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        List<LatLng> latLngs = new ArrayList<>();
        for (Location location: tour.getLocations()) {
            LatLng latLng = new LatLng(Double.parseDouble(location.getLatitud()),
                    Double.parseDouble(location.getLongitud()));
            latLngs.add(latLng);
            builder.include(latLng);
        }

        LatLngBounds bounds = builder.build();
        googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .addAll(latLngs));

        googleMap.setOnMapLoadedCallback(() ->
                googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50)));

    }

    private void setupToolBar() {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Detalle del Recorrido");
        }
    }
}
