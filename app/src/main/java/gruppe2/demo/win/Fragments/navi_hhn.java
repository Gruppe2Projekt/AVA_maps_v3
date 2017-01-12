package gruppe2.demo.win.Fragments;

import android.Manifest;
import android.content.Context;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;

import java.util.ArrayList;

import gruppe2.demo.win.R;


public class navi_hhn extends Fragment implements OnMapReadyCallback, DirectionCallback {

    private GoogleMap mMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gmaps, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapFragment fragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //LatLng marker = new LatLng(49.122102, 9.210772);

        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 13));
        //LatLng SONTHEIM = new LatLng(49.122102, 9.210772);
        //googleMap.addMarker(new MarkerOptions().title("HS Heilbronn").position(SONTHEIM));

        mMap.setMyLocationEnabled(true);
        requestDirection();

    }
    String serverKey = "AIzaSyDytcF7j1kQPtIKDhRYPjcssNwoyp7yVzE";
    LatLng origin = new LatLng(48.9190624, 9.3068855);
    LatLng destination = new LatLng(49.282437, 9.3281893);

    public void requestDirection() {
        GoogleDirection.withServerKey(serverKey)
                .from(origin)
                .to(destination)
                .transportMode(TransportMode.DRIVING)
                .execute(this);

    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
    if (direction.isOK()) {
        mMap.addMarker(new MarkerOptions().position(origin));
        mMap.addMarker(new MarkerOptions().position(destination));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(origin, 11));

        ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
        mMap.addPolyline(DirectionConverter.createPolyline(this.getContext(), directionPositionList, 5, Color.RED));
        }
    }

    @Override
    public void onDirectionFailure(Throwable t) {

    }
}
