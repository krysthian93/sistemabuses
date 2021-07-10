package tracking.com.trackingandroid.apps.record_tour.ui;


import android.Manifest;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;
import tracking.com.trackingandroid.R;
import tracking.com.trackingandroid.apps.record_tour.RecordToursPresenter;
import tracking.com.trackingandroid.data.model.Tour;
import tracking.com.trackingandroid.util.CommonUtils;

import static tracking.com.trackingandroid.util.CommonUtils.COMPLETE_DATE_FORMAT;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordToursFragment extends Fragment implements OnMapReadyCallback, RecordToursView {

    private static final String TAG = RecordToursFragment.class.getSimpleName();
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final int INTERVAL = 10000;
    private String timeStart;
    private String timeFinish;
    private Boolean firstTimeFlag = true;

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location mCurrentLocation;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;
    private List<tracking.com.trackingandroid.data.model.Location> locationList = new ArrayList<>();
    private tracking.com.trackingandroid.data.model.Location location;
    private DateFormat dateFormat = new SimpleDateFormat(COMPLETE_DATE_FORMAT);
    private DialogLoading dialogLoading;

    @Inject
    Context context;
    @Inject
    RecordToursPresenter presenter;

    @BindView(R.id.map_view)
    MapView mapView;

    @BindView(R.id.start_button)
    ToggleButton startButton;

    @BindView(R.id.chronometer)
    Chronometer chronometer;

    @BindView(R.id.timer_container)
    RelativeLayout timerContainer;

    public RecordToursFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dialogLoading = new DialogLoading(getActivity());
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_record_tours, container, false);
        ButterKnife.bind(this, view);

        //map
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        locationConfig();
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            mMap = googleMap;
            mMap.setMyLocationEnabled(true);
        } catch (SecurityException e) {
            Log.e(TAG, "Error Map: " + e.toString());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        if (checkLocationPermissions()) {
            if (checkGooglePlayServices()) {
                startLocationUpdates();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        chronometer.stop();
        stopLocationUpdates();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @OnClick(R.id.start_button)
    public void startRecordTour() {
        if (checkLocationPermissions()) {
            if (checkGooglePlayServices()) {
                if (startButton.isChecked()) {
                    locationList.clear();
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                    timerContainer.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    timeStart = dateFormat.format(new Date());
                } else {
                    chronometer.stop();
                    chronometer.setText(R.string.initial_time);
                    timerContainer.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    timeFinish = dateFormat.format(new Date());
                    // get distance between
                    float [] results = new float[1];
                    Location.distanceBetween(Double.parseDouble(locationList.get(0).getLatitud()),
                            Double.parseDouble(locationList.get(0).getLongitud()),
                            Double.parseDouble(locationList.get(locationList.size() -1).getLatitud()),
                            Double.parseDouble(locationList.get(locationList.size() -1).getLongitud()),
                            results);
                    presenter.createTour(new Tour(timeStart, timeFinish, locationList, results[0]));
                }
            }
        }
    }

    @Override
    public void showProgress() {
        dialogLoading.startLoadingDialog();
    }

    @Override
    public void hideProgress() {
        dialogLoading.dismissLoadingDialog();
    }

    private Boolean checkLocationPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION);
        Log.d(TAG, "checkLocationPermissions: " + permissionState);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Verify the Google Play Services availability, it is necessary for get current user location
     * @return if googlePlayServices is available
     * */
    private Boolean checkGooglePlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            CommonUtils.showSimpleToastMessages(context, getString(R.string.error_play_services));
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(getActivity(), resultCode,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                CommonUtils.showSimpleToastMessages(context, getString(R.string.error_not_available_play_services));
            }
            return false;
        } else {
            return true;
        }
    }

    private void startLocationUpdates() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        //check whether the current location settings are satisfied
        SettingsClient client = LocationServices.getSettingsClient(context);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(getActivity(), locationSettingsResponse -> setCurrentLocation());
        task.addOnFailureListener(e -> {
            int statusCode = ((ApiException) e).getStatusCode();
            switch (statusCode) {
                case CommonStatusCodes.RESOLUTION_REQUIRED:
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(getActivity(),
                                1000);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    // Location settings are not satisfied. However, we have no way
                    // to fix the settings so we won't show the dialog.
                    break;
            }
        });
    }

    /**
     * Implements the callback, receive the current user location.
     * */
    private void locationConfig() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                mCurrentLocation = locationResult.getLastLocation();
                if (firstTimeFlag) {
                    animateCamera(mCurrentLocation);
                    firstTimeFlag = false;
                }
                if (startButton.isChecked()) {
                    // Log.i(TAG, "Location correct: " + mCurrentLocation.getLatitude() + " " + mCurrentLocation.getLongitude());
                    locationList.add(new tracking.com.trackingandroid.data.model.Location( Double.toString(mCurrentLocation.getLatitude()),
                            Double.toString(mCurrentLocation.getLongitude())));
                }
            }
        };
    }


    /**
     * Set the callback the which will receive the location updates.
     * */
    private void setCurrentLocation() {
        try {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                    mLocationCallback, Looper.myLooper());
        } catch (SecurityException e) {
            Log.e("TAG1", "onSuccess Error: ", e);
        }
    }

    private void stopLocationUpdates() {
        if (checkLocationPermissions()) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    private void animateCamera(@NonNull Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
        mMap.animateCamera(cameraUpdate);
    }

}
