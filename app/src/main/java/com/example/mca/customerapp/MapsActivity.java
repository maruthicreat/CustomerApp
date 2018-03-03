package com.example.mca.customerapp;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://shopkeeperapp-7d95b.firebaseio.com/");
        myRef = database.getReference("ShopkeeperSignup");
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

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //String value = dataSnapshot.getValue(String.class);
                //System.out.println(value);
                Toast.makeText(MapsActivity.this, "Map entry", Toast.LENGTH_SHORT).show();
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    String Uid = ds.getKey().toString();
                    System.out.println("userid = "+Uid);
                    String shopName = ds.child("shop_name").getValue().toString();
                    double lat = Double.parseDouble(ds.child("latitude").getValue().toString());
                    double log = Double.parseDouble(ds.child("longitude").getValue().toString());
                    System.out.println("latitude  = "+ds.child("latitude").getValue().toString());
                    System.out.println("longitude  = "+ds.child("longitude").getValue().toString());
                    LatLng tamil = new LatLng(lat, log);
                    mMap.addMarker(new MarkerOptions().position(tamil).title(shopName));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(tamil));
                }
                //double latitude = Double.parseDouble(value);
                // Toast.makeText(ViewMap.this, value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                //Log.w(TAG, "Failed to read value.", error.toException());
                System.out.println(error.toException());
            }
        });
    }
}
