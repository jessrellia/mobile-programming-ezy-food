package com.example.ezyfood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    FusedLocationProviderClient fusedLocationClient;
    Vector<com.example.ezyfood.Location> locationVector = new Vector<>();
    private MarkerOptions options = new MarkerOptions();
    private ArrayList<LatLng> latLngs = new ArrayList<>();
    private SQLiteDatabase db;
    private Cursor cursor;
    int balance;
    Vector<Double> distanceVector = new Vector<>();
    Vector<Double> curr_location = new Vector<Double>();
    Vector<Double> mind_vector = new Vector<>();
    Vector<String> closest_vector = new Vector<>();
    Vector<String> chosen_branch_vector = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        SQLiteOpenHelper efDatabaseHelper = new EzyFoodyDatabaseHelper(this);
        try {
            db = efDatabaseHelper.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM locations", null);
            if(cursor.moveToFirst()){
                do{
                    locationVector.add(new com.example.ezyfood.Location(
                            cursor.getString(1),
                            cursor.getDouble(2),
                            cursor.getDouble(3)
                    ));
                } while(cursor.moveToNext());
                cursor.close();
            }
        } catch (SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        if(getIntent().getIntExtra("balance", 0) == 0){
            balance = 0;
        } else {
            balance = getIntent().getIntExtra("balance", 0);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        Log.d("tag_2", "ayye");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
//            return;

            if(ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(MapsActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }else{
                ActivityCompat.requestPermissions(MapsActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            Log.d("tag_3", "=");
        }

        Log.d("is_it_null", ""+(mMap==null));
        if(mMap != null){
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.setMyLocationEnabled(true);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            Log.d("tag_masuk", "aye");
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                buildGoogleApiClient();
                if(mMap != null){
                    mMap.setMyLocationEnabled(true);
                }

                Log.d("tag_4", "=");
            }
        } else {
            buildGoogleApiClient();
            if(mMap != null){
                mMap.setMyLocationEnabled(true);
            }
            Log.d("tag_5", "=");
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null
                        Log.d("tag_6", "=");
                        if (location != null) {
                            // Logic to handle location object
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            curr_location.add(location.getLatitude());
                            curr_location.add(location.getLongitude());
                            Log.d("haha", curr_location.size()+"");
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(latLng);
                            markerOptions.title("Current Position");
                            markerOptions.draggable(true);
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                            mCurrLocationMarker = mMap.addMarker(markerOptions);
                            mCurrLocationMarker.setTag(latLng);
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                            Log.d("tag_7", "=");
                            loadBranches(curr_location);
                        } else {
                            LatLng binus = new LatLng(-6.242720, 106.655630);
                            mMap.addMarker(new MarkerOptions().position(binus).title("Binus Alam Sutera"));
                            curr_location.add(location.getLatitude());
                            curr_location.add(location.getLongitude());
                            Log.d("haha_333", curr_location.size() + "");
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(binus));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                            Log.d("tag_8", "=");
                        }
                    }
                });

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {

                    if(marker!=null && !marker.getTitle().equals("Current Position")){
                        Context context = getApplicationContext();
                        Toast toast = Toast.makeText(context, "Order from"+marker.getTitle(), Toast.LENGTH_SHORT);
                        toast.show();
                        TextView chosen_branch = (TextView) findViewById(R.id.chosen_branch);
                        chosen_branch.setText(marker.getTitle());
                        chosen_branch_vector.add(marker.getTitle());
                    } else if (marker != null && marker.getTitle().equals("Current Position")) {
                            Context context = getApplicationContext();
                            Log.d("marker_id", marker.getId() + "");
                            Toast toast = Toast.makeText(context, "Order from the closest EzyFoody branch, " + closest_vector.get(0), Toast.LENGTH_SHORT);
                            toast.show();
                        TextView chosen_branch = (TextView) findViewById(R.id.chosen_branch);
                        chosen_branch.setText(closest_vector.get(0));
                        chosen_branch_vector.add(closest_vector.get(0));
                        }

                }
            });
    }



    public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit.equals("K")) {
                dist = dist * 1.609344;
            } else if (unit.equals("N")) {
                dist = dist * 0.8684;
            }
            dist = (double) Math.round(dist * 100) / 100;
            return (dist);
        }
    }

    public void loadBranches(Vector<Double> curr_location) {
        int i = 0;
        double j=0.0;
        double min_distance = 99999999.9;
        String closest = null;
        for (com.example.ezyfood.Location loc: locationVector) {
            latLngs.add(new LatLng(loc.getLatitude(), loc.getLongitude()));
            double distance = distance(curr_location.get(0), curr_location.get(1), loc.getLatitude(), loc.getLongitude(), "K");
            distanceVector.add(distance);
//            Log.d("tag_curr_lat", curr_loc.get(0)+"");
//            Log.d("tag_curr_lon", mCurrLocationMarker+"");
//            Log.d("tag_last_loc", mLastLocation+"");
            Log.d("Tag", loc.getLatitude()+"");
            Log.d("Tag_2", loc.getLongitude()+"");
            if(distance < min_distance){
                min_distance = distance;
                closest = loc.getBranch_name();
            }
        }
        Log.d("tag_min_distance", min_distance+"");
        Log.d("tag_min_closest", closest);
        mind_vector.add(min_distance);
        closest_vector.add(closest);

        for (LatLng point : latLngs) {
            Log.d("tag+pts", point+"");
            options.position(point);
            options.title(locationVector.get(i).getBranch_name());
            options.snippet(distanceVector.get(i)+"KM");
            mMap.addMarker(options);
            i++;
        }
    }

    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this,
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults){
        switch (requestCode){
            case 1: {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(MapsActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
        Log.d("tag_9", "=");
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
            Log.d("tag_10", "=");
        }
        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mCurrLocationMarker = mMap.addMarker(markerOptions);
        Log.d("tag_11", "=");

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        Log.d("tag_12", "=");
        //stop location updates
//        if (mGoogleApiClient != null) {
////            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
//        }
        Log.d("tag_13", "=");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        Log.d("tag_14", "=");
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
////            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
//            Log.d("tag_15", "=");
//        }
//        loadBranches();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    public void chooseBranch(View view) {
        if(!chosen_branch_vector.isEmpty()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("chosen_branch", chosen_branch_vector.get(chosen_branch_vector.size()-1));
            Log.d("chosen_branch_awal", chosen_branch_vector.get(chosen_branch_vector.size()-1));
            intent.putExtra("balance", balance);
            chosen_branch_vector.clear();
            startActivity(intent);
        } else {
           Toast toast =  Toast.makeText(this, "You must select a location first!", Toast.LENGTH_SHORT);
           toast.show();
        }

    }
}