package com.example.mystore;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.BitmapFactory;

import com.google.android.gms.common.api.ApiException;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.neshan.core.LngLat;
import org.neshan.core.Range;
import org.neshan.layers.VectorElementLayer;

import com.karumi.dexter.BuildConfig;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

import org.neshan.services.NeshanMapStyle;
import org.neshan.services.NeshanServices;
import org.neshan.styles.MarkerStyle;
import org.neshan.styles.MarkerStyleCreator;
import org.neshan.ui.ClickData;
import org.neshan.ui.ClickType;
import org.neshan.ui.MapEventListener;
import org.neshan.ui.MapView;
import org.neshan.utils.BitmapUtils;
import org.neshan.vectorelements.Marker;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

public class NeshanMap extends AppCompatActivity {
    final int REQUEST_CODE = 123;

    final int BASE_MAP_INDEX = 0;

    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 1000;

    private static final String TAG = "NeshanMap";

    private NeshanAddress neshanAddress;
    private MapMatching mapMatching;


    private View reverseBottomSheetView;
    private BottomSheetBehavior reverseBottomSheetBehavior;

    private TextView addressTitle;
    private TextView addressDetails;

    ArrayList<String> listPointMatching;
    Map<Double,Double> hashMapPointStore;

    MapView map;
    NeshanMapStyle mapStyle;

    ImageView themePreview;

    VectorElementLayer userMarkerLayer;
    VectorElementLayer mapLayerPoint;

    private Location userLocation;
    private FusedLocationProviderClient fusedLocationClient;
    private SettingsClient settingsClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;
    private LocationCallback locationCallback;
    private String lastUpdateTime;
    private Boolean mRequestingLocationUpdates;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_neshan_map);

        themePreview = findViewById(R.id.theme_preview);
        listPointMatching =new ArrayList<>();
        hashMapPointStore=new HashMap<>();
    }


    @Override
    protected void onStart() {
        super.onStart();
        initLayoutReferences();
        initLocation();
        startReceivingLocationUpdates();
    }


    private void initViews() {
        map = findViewById(R.id.map);

        addressTitle = findViewById(R.id.title);
        addressDetails = findViewById(R.id.details);

        reverseBottomSheetView = findViewById(R.id.reverse_bottom_sheet_include);
        reverseBottomSheetBehavior = BottomSheetBehavior.from(reverseBottomSheetView);

        reverseBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

    }


    private void initMap() {
        LngLat currentLocation = new LngLat(51.330743, 35.767234);
        userMarkerLayer = NeshanServices.createVectorElementLayer();
        mapLayerPoint = NeshanServices.createVectorElementLayer();
        map.getLayers().add(userMarkerLayer);
        map.getLayers().add(mapLayerPoint);

        map.getOptions().setZoomRange(new Range(4.5f, 18f));
        mapStyle = NeshanMapStyle.STANDARD_DAY;
        map.getLayers().insert(BASE_MAP_INDEX, NeshanServices.createBaseMap(NeshanMapStyle.STANDARD_DAY));

        map.setFocalPointPosition(currentLocation, 0);
        map.setZoom(16, 0);

    }


    private void initLayoutReferences() {
        initViews();
        initMap();
        validateThemePreview();
        neshanMapMatching();
        addMakerPointMap();

        map.setMapEventListener(new MapEventListener() {
            public void onMapClicked(ClickData mapClickInfo) {
                super.onMapClicked(mapClickInfo);
                if (mapClickInfo.getClickType() == ClickType.CLICK_TYPE_LONG) {
                    LngLat clickedLocation = mapClickInfo.getClickPos();
                    Log.i("LogClickedLocation", String.valueOf(clickedLocation));

                    addUserMarker(clickedLocation);

                    neshanReverseAPI(clickedLocation);
                }
            }
        });

    }


    private void neshanReverseAPI(LngLat loc){
        GetDataService retrofitService=RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        String requestURL="https://api.neshan.org/v1/reverse?lat=" + loc.getY() + "&lng=" + loc.getX();
        @SuppressLint("DefaultLocale") final String latLngAddr=String.format("%.6f",loc.getY())+","+String.format("%.6f",loc.getX());

        Call<NeshanAddress> call=retrofitService.getNeshanAddress(requestURL);
        call.enqueue(new Callback<NeshanAddress>() {
            @Override
            public void onResponse(Call<NeshanAddress> call, Response<NeshanAddress> response) {
                if (response.code() == 200) {
                    if (response.body().getCode() == null) {

                        neshanAddress = response.body();
                        addressTitle.setText(neshanAddress.getNeighbourhood());
                        addressDetails.setText(neshanAddress.getAddress());

                    }
                else {
                        addressTitle.setText("آدرس نامشخص");
                        addressDetails.setText(latLngAddr);
                    }
                }
            }

            @Override
            public void onFailure(Call<NeshanAddress> call, Throwable t) {
                Log.d("neshanAddressError", String.valueOf(t));
            }
        });

    }



    private void neshanMapMatching(){
        listPointMatching.add("35.766818,51.331494");
        listPointMatching.add("35.766192,51.329649");
        listPointMatching.add("35.763861,51.334196");

        StringBuilder stringBuilder=new StringBuilder();
        for (String s : listPointMatching) {
            stringBuilder.append(s);
            stringBuilder.append("|");
        }
        String stringBuilder1=stringBuilder.substring(0,stringBuilder.length() - 1);

        GetDataService reverseService=RetrofitClientMapMatching.getRetrofitMatching().create(GetDataService.class);
        String encodePointList= URLEncoder.encode(stringBuilder1);

        String requestUrlMatching="https://api.neshan.org/v1/map-matching?path="+encodePointList;
        Call<MapMatching> call=reverseService.getMapMatching(requestUrlMatching);

        call.enqueue(new Callback<MapMatching>() {
            @Override
            public void onResponse(Call<MapMatching> call, Response<MapMatching> response) {
                Log.i("LogMapPoint", response.body().toString());
            }

            @Override
            public void onFailure(Call<MapMatching> call, Throwable t) {
                Toast.makeText(NeshanMap.this, "ErrorPointMap", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void addMakerPointMap(){

        mapLayerPoint.clear();

        hashMapPointStore.put(35.766818,51.331494);
        hashMapPointStore.put(35.766192,51.329649);
        hashMapPointStore.put(35.763861,51.334196);
        hashMapPointStore.put(35.765782,51.331188);
        hashMapPointStore.put(35.766513,51.332453);
        hashMapPointStore.put(35.768217,51.334405);

        for (Map.Entry<Double,Double> element :hashMapPointStore.entrySet()){
             Double y=element.getKey();
             Double x= element.getValue();

            LngLat loc = new LngLat(x,y);

            MarkerStyleCreator markStCr = new MarkerStyleCreator();
            markStCr.setSize(20f);
            markStCr.setBitmap(BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_marker_store)));
            MarkerStyle markerStyle = markStCr.buildStyle();

            Marker marker=new Marker(loc,markerStyle);

            mapLayerPoint.add(marker);
        }

    }



    private void validateThemePreview() {
        switch (mapStyle) {
            case STANDARD_DAY:
                themePreview.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.map_style_standard_night, getTheme()));
                break;
            case STANDARD_NIGHT:
                themePreview.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.map_style_neshan, getTheme()));
                break;
            case NESHAN:
                themePreview.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.map_style_standard_day, getTheme()));
                break;
        }
    }

    public void changeStyle(View view) {
        NeshanMapStyle previousMapStyle = mapStyle;
        switch (previousMapStyle) {
            case STANDARD_DAY:
                mapStyle = NeshanMapStyle.STANDARD_NIGHT;
                break;
            case STANDARD_NIGHT:
                mapStyle = NeshanMapStyle.NESHAN;
                break;
            case NESHAN:
                mapStyle = NeshanMapStyle.STANDARD_DAY;
                break;
        }

                runOnUiThread(new Runnable() {
            @Override
            public void run() {
                validateThemePreview();
            }
        });

        map.getLayers().remove(map.getLayers().get(BASE_MAP_INDEX));
        map.getLayers().insert(BASE_MAP_INDEX, NeshanServices.createBaseMap(mapStyle));

    }


    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }


    private void initLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        settingsClient = LocationServices.getSettingsClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                userLocation = locationResult.getLastLocation();
                lastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                onLocationChange();
            }
        };

        mRequestingLocationUpdates = false;

        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        locationSettingsRequest = builder.build();

    }


    private void startLocationUpdates() {
        settingsClient
                .checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

                        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

                        onLocationChange();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(NeshanMap.this, REQUEST_CODE);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                Toast.makeText(NeshanMap.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        onLocationChange();
                    }
                });
    }


    public void stopLocationUpdates() {
        fusedLocationClient
                .removeLocationUpdates(locationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Toast.makeText(getApplicationContext(), "Location updates stopped!", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void startReceivingLocationUpdates() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                }).check();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.e(TAG, "User agreed to make required location settings changes.");
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e(TAG, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        break;
                }
                break;
        }
    }


    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void onLocationChange() {
        if (userLocation != null) {
            addUserMarker(new LngLat(userLocation.getLongitude(), userLocation.getLatitude()));
        }
    }


    private void addUserMarker(LngLat loc) {

        userMarkerLayer.clear();

        MarkerStyleCreator markStCr = new MarkerStyleCreator();
        markStCr.setSize(20f);
        markStCr.setBitmap(BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_marker)));
        MarkerStyle markSt = markStCr.buildStyle();

        Marker marker = new Marker(loc, markSt);

        userMarkerLayer.add(marker);


    }

    public void focusOnUserLocation(View view) {
        if (userLocation != null) {
            map.setFocalPointPosition(
                    new LngLat(userLocation.getLongitude(), userLocation.getLatitude()), 0.25f);
            map.setZoom(15, 0.25f);
        }
    }


}