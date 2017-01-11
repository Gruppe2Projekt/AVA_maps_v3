package gruppe2.demo.win.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import gruppe2.demo.win.R;

public class navi_hhn extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gmaps, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapFragment fragment = (MapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //LatLng marker = new LatLng(49.122102, 9.210772);

        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 13));
        LatLng SONTHEIM = new LatLng(49.122102, 9.210772);

        googleMap.addMarker(new MarkerOptions().title("HS Heilbronn").position(SONTHEIM));
        mMap.setMyLocationEnabled(true);
    }
}
