package gruppe2.demo.win.Fragments;

import android.Manifest;
import android.content.Context;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;

import gruppe2.demo.win.R;


public class navi_hhn extends Fragment implements OnMapReadyCallback, DirectionCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    //Variablen
    private GoogleMap mMap;
    //Positonsbestimmung
    //Mit Hilfe dieser können wir später die Position auslesen
    GoogleApiClient mGoogleApiClient;
    //Diese Variable beinhaltet später die Positionsdaten
    Location mLastLocation;

    double laengengrad;
    double breitengrad;

    //Navigation
    String serverKey = "AIzaSyDytcF7j1kQPtIKDhRYPjcssNwoyp7yVzE";
    LatLng destination;
    Marker Standort;
    Marker HHNStandort;
    Polyline routenavi;


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

        Spinner spinner = (Spinner) getView().findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.spinner, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String ziel =  parent.getItemAtPosition(position).toString();

                if (ziel.equals("Bitte HHN-Standort auswählen")) {
                    mMap.clear();
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(49.148356, 9.216501), 10));
                }
                else if (ziel.equals("Campus Sontheim")) {
                    destination = new LatLng(49.122635, 9.206136);
                    mMap.clear();
                    requestDirection();
                }
                else if (ziel.equals("Campus Europaplatz")){
                    destination = new LatLng(49.148356, 9.216501);
                    mMap.clear();
                    requestDirection();
                }
                else if (ziel.equals("Campus Schwäbisch-Hall")){
                    destination = new LatLng(49.112501, 9.743649);
                    mMap.clear();
                    requestDirection();
                }
                else if (ziel.equals("Campus Künzelsau")){
                    destination = new LatLng(49.275475, 9.712272);
                    mMap.clear();
                    requestDirection();
                }
                else {
                    System.out.println("Fehler");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //LatLng marker = new LatLng(49.122102, 9.210772);
        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 13));
        //LatLng SONTHEIM = new LatLng(49.122102, 9.210772);
        //googleMap.addMarker(new MarkerOptions().title("HS Heilbronn").position(SONTHEIM));

        mMap.setMyLocationEnabled(true);
        standortbestimmung();
    }



    public void standortbestimmung() {
        //Aufruf der Initalisierung
        buildGoogleApiClient();

        //Starten der eben initalisierten API von Google
        mGoogleApiClient.connect();

    }

    //Initalisiert die API von Google, mit der wir die Position auslesen können
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    //Wird aufgerufen, wenn die Verbindung zur Google API erfolgreich ist
    @Override
    public void onConnected(Bundle connectionHint) {
        //Anhand der erfolgreichen Verbindung die benötigten Daten auslesen
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        String stringlaengengrad = String.valueOf(mLastLocation.getLongitude());
        String stringbreitengrad = String.valueOf(mLastLocation.getLatitude());
        laengengrad = Double.parseDouble(stringlaengengrad);
        breitengrad = Double.parseDouble(stringbreitengrad);

    }



    //Wird aufgerufen, wenn die Verbindung zur Google API aussteht
    @Override
    public void onConnectionSuspended(int i) {}

    //Wird aufgerufen, wenn die Verbindung zur Google API fehlschlägt
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //l.setText("Längengrad: Da ist ein Fehler aufgetreten.");
        //b.setText("Breitengrad: Da ist ein Fehler aufgetreten.");
    }



    public void requestDirection() {
        LatLng origin = new LatLng(breitengrad, laengengrad);
        GoogleDirection.withServerKey(serverKey)
                .from(origin)
                .to(destination)
                .transportMode(TransportMode.DRIVING)
                .execute(this);

    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
    if (direction.isOK()) {
        LatLng origin = new LatLng(breitengrad, laengengrad);

        Standort = mMap.addMarker(new MarkerOptions().position(origin).title("Dein Standort"));
        //Standort.showInfoWindow();
        HHNStandort = mMap.addMarker(new MarkerOptions().position(destination).title("HHN-Standort"));
        HHNStandort.showInfoWindow();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destination, 10));

        ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
        routenavi = mMap.addPolyline(DirectionConverter.createPolyline(this.getContext(), directionPositionList, 5, Color.rgb(0,118,188)));

    }
    }

    @Override
    public void onDirectionFailure(Throwable t) {
        System.out.println("Fehler bei der Routenberechnung");
    }
}
