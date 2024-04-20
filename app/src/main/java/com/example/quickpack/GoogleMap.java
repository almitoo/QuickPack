package com.example.quickpack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;


import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;



import com.example.quickpack.Constants.MyConstants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



import java.io.IOException;
import java.util.List;
import java.util.Objects;


public class GoogleMap extends AppCompatActivity implements OnMapReadyCallback {

    private com.google.android.gms.maps.GoogleMap myMap;
    String header;
    SearchView mapSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goole_map);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();
        header=intent.getStringExtra(MyConstants.HEADER_SMALL);
        getSupportActionBar().setTitle(header);

        mapSearchView =findViewById(R.id.searchView);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                String location =mapSearchView.getQuery().toString();
                List<Address>addressList;

                if(location!=null){
                    Geocoder geocoder =new Geocoder(GoogleMap.this);

                    try {
                        addressList=geocoder.getFromLocationName(location,1);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Address address=addressList.get(0);
                    LatLng latlng=new LatLng(address.getLatitude(),address.getLongitude());
                    myMap.addMarker(new MarkerOptions().position(latlng).title(location));
                    myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 13));

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        if (mapFragment != null) {
            mapFragment.getMapAsync(GoogleMap.this);
        }

    }


    @Override
    public void onMapReady(@NonNull com.google.android.gms.maps.GoogleMap googleMap) {
        myMap=googleMap;

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}