package com.help.womensaftey;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class activity_map extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    Marker marker;
    LocationListener locationlistener;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        //if check the network provider is enable
        if (locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //get check the latitude
                    double latitude = location.getLatitude();
                    //get check the longitude
                    double longitude = location.getLongitude();

                    Log.d("lat 1: ", String.valueOf(latitude));

                    Log.d("lon 1: ", String.valueOf(longitude));

                    //initiat the class,LatLng
                    LatLng latLng = new LatLng(latitude, longitude);
                    //initiat the class,Geocoder
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressesList = geocoder.getFromLocation(latitude, longitude, 1);

                        String result = addressesList.get(0).getLocality() + ":";
                        result += addressesList.get(0).getCountryName();
                        mMap.addMarker(new MarkerOptions().position(latLng).title(result));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
        }

        else if (locationManager.isProviderEnabled(locationManager.GPS_PROVIDER)) {
            }
                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        //get check the latitude
                        double latitude = location.getLatitude();
                        //get check the longitude
                        double longitude = location.getLongitude();
                        //initiat the class,LatLng

                        Log.d("lo: ", String.valueOf(latitude));

                        Log.d("la: ", String.valueOf(longitude));


                        LatLng latLng = new LatLng(latitude, longitude);
                        //initiat the class,Geocoder
                        Geocoder geocoder = new Geocoder(getApplicationContext());
                        try {
                            List<Address> addressesList = geocoder.getFromLocation(latitude, longitude, 1);

                            String result = addressesList.get(0).getLocality() + ":";
                            result += addressesList.get(0).getCountryName();
                            mMap.addMarker(new MarkerOptions().position(latLng).title(result));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                });
            }

        @Override
        public void onMapReady (GoogleMap googleMap){
            mMap = googleMap;

        }
}
