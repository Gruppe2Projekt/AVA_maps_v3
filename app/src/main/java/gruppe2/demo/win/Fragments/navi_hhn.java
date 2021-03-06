/*
App erstellt durch Gruppe 2
Alexander Brechlin - 191898
Vitalij Degraf - 191904
Adrian Grünther - 191908
 */
package gruppe2.demo.win.Fragments;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;

import gruppe2.demo.win.R;

import static android.content.Context.LOCATION_SERVICE;


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
    String markertitle;
    String beschreibung;

    //Variable zur Abfrage ob das GPS an ist
    Boolean obgpsan;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Es wird geprüft ob das GPS an ist. Wenn GPS an ist wird "opgpsan" auf true gesetzt
        LocationManager locationManager = (LocationManager) this.getContext().getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

            obgpsan = true;

        }else{
            showGPSDisabledAlertToUser();
            obgpsan = false;
        }

        return inflater.inflate(R.layout.fragment_gmaps, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapFragment fragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);

        //Spinner wird erstellt für die Auswahl der Navi-Standorte
        Spinner spinner = (Spinner) getView().findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.spinner, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String ziel =  parent.getItemAtPosition(position).toString();
                LatLng standort = new LatLng(breitengrad, laengengrad);

                //Je nach Auswahl im Spinner wird eine andere Route navigiert

                if (ziel.equals("Bitte HHN-Standort auswählen")) {
                    mMap.clear();
                    Marker aktuellerstandort = mMap.addMarker(new MarkerOptions().position(standort).title("Dein Standort"));
                    aktuellerstandort.showInfoWindow();
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(standort, 14));
                }
                else if (ziel.equals("Campus Sontheim")) {
                    destination = new LatLng(49.122235, 9.211491);
                    mMap.clear();
                    markertitle = "Campus Sontheim";
                    beschreibung = "Max-Planck-Straße 39, 74081 Heilbronn";
                    requestDirection();
                }
                else if (ziel.equals("Campus Europaplatz")){
                    destination = new LatLng(49.148356, 9.216501);
                    mMap.clear();
                    markertitle = "Campus Europaplatz";
                    beschreibung = "Am Europaplatz 11,74076 Heilbronn";
                    requestDirection();
                }
                else if (ziel.equals("Campus Schwäbisch-Hall")){
                    destination = new LatLng(49.112501, 9.743649);
                    mMap.clear();
                    markertitle = "Campus Schwäbisch-Hall";
                    beschreibung = "Ziegeleiweg 4, 74523 Schwäbisch Hall";
                    requestDirection();
                }
                else if (ziel.equals("Campus Künzelsau")){
                    destination = new LatLng(49.275475, 9.712272);
                    mMap.clear();
                    markertitle = "Campus Künzelsau";
                    beschreibung = "Daimlerstraße 35, 74653 Künzelsau";
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

        //Aktiviert Maps eigene Standortbestimmung
        mMap.setMyLocationEnabled(true);

        //Ruft die eigene Standortbestimmung auf für die Routennavigation
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

    //Falls GPS nicht an ist poppt Fenster auf und verweist auf die Einstellungen
    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("Zur Nutzung dieser App ist die Standortbestimmung notwendig. Du kannst diese in den Einstellungen einschalten.")
                .setCancelable(false)
                .setPositiveButton("Zu den Standorteinstellungen",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                                System.exit(0);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Abbrechen und App beenden",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                        System.exit(0);
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    //Wird aufgerufen, wenn die Verbindung zur Google API erfolgreich ist
    @Override
    public void onConnected(Bundle connectionHint) {
        //Anhand der erfolgreichen Verbindung die benötigten Daten auslesen

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (obgpsan.equals(true)) {

                String stringlaengengrad = String.valueOf(mLastLocation.getLongitude());
                String stringbreitengrad = String.valueOf(mLastLocation.getLatitude());
                laengengrad = Double.parseDouble(stringlaengengrad);
                breitengrad = Double.parseDouble(stringbreitengrad);

            }else{
                Toast.makeText(getContext(), "Standortbestimmung ist notwendig, bitte anmachen", Toast.LENGTH_LONG).show();


            }


        //Pin für den aktuellen Standort beim erstmaligen Starten
        LatLng standort = new LatLng(breitengrad, laengengrad);
        mMap.clear();
        Marker aktuellerstandort = mMap.addMarker(new MarkerOptions().position(standort).title("Dein Standort"));
        aktuellerstandort.showInfoWindow();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(standort, 10));

        //Wenn Buttons in Info_hhn gedrückt werden, wird dies ausgeführt je nach Button
        Bundle args = this.getArguments();
        if (args != null) {
            Integer nachricht = args.getInt("schluessel");

            if (nachricht.equals(1)) {
                mMap.clear();
                Marker europaplatz = mMap.addMarker(new MarkerOptions().position(new LatLng(49.148356, 9.216501)).title("Campus Europaplatz").snippet("Am Europaplatz 11,74076 Heilbronn"));
                europaplatz.showInfoWindow();
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(49.148356, 9.216501), 14));
            }
            else if (nachricht.equals(2)) {
                destination = new LatLng(49.148356, 9.216501);
                mMap.clear();
                markertitle = "Campus Europaplatz";
                beschreibung = "Am Europaplatz 11,74076 Heilbronn";
                requestDirection();
            }
            else if (nachricht.equals(3)) {
                mMap.clear();
                Marker Sontheim = mMap.addMarker(new MarkerOptions().position(new LatLng(49.122235, 9.211491)).title("Campus Sontheim").snippet("Max-Planck-Straße 39, 74081 Heilbronn"));
                Sontheim.showInfoWindow();
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(49.122235, 9.211491), 14));
            }
            else if (nachricht.equals(4)) {
                destination = new LatLng(49.122235, 9.211491);
                mMap.clear();
                markertitle = "Campus Sontheim";
                beschreibung = "Max-Planck-Straße 39, 74081 Heilbronn";
                requestDirection();
            }
            else if (nachricht.equals(5)) {
                mMap.clear();
                Marker kuenzelsau = mMap.addMarker(new MarkerOptions().position(new LatLng(49.275475, 9.712272)).title("Campus Künzelsau").snippet("Daimlerstraße 35, 74653 Künzelsau"));
                kuenzelsau.showInfoWindow();
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(49.275475, 9.712272), 14));
            }
            else if (nachricht.equals(6)) {
                destination = new LatLng(49.275475, 9.712272);
                mMap.clear();
                markertitle = "Campus Künzelsau";
                beschreibung = "Daimlerstraße 35, 74653 Künzelsau";
                requestDirection();

            }
            else if (nachricht.equals(7)) {
                mMap.clear();
                Marker schwaebischhall = mMap.addMarker(new MarkerOptions().position(new LatLng(49.112501, 9.743649)).title("Campus Schwäbisch-Hall").snippet("Ziegeleiweg 4, 74523 Schwäbisch Hall"));
                schwaebischhall.showInfoWindow();
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(49.112501, 9.743649), 14));
            }
            else if (nachricht.equals(8)) {
                destination = new LatLng(49.112501, 9.743649);
                mMap.clear();
                markertitle = "Campus Schwäbisch-Hall";
                requestDirection();
                beschreibung = "Ziegeleiweg 4, 74523 Schwäbisch Hall";
            }
            else {
                System.out.println("Fehler bei der Datenübergabe");
            }

        } else {
            System.out.println("Keine daten vorhanden");
        }

    }


    //Wird aufgerufen, wenn die Verbindung zur Google API aussteht
    @Override
    public void onConnectionSuspended(int i) {}

    //Wird aufgerufen, wenn die Verbindung zur Google API fehlschlägt
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    //Funktion zur Routenberechnung
    public void requestDirection() {
        LatLng origin = new LatLng(breitengrad, laengengrad);
        GoogleDirection.withServerKey(serverKey)
                .from(origin)
                .to(destination)
                .transportMode(TransportMode.DRIVING)
                .execute(this);

    }
    //Falls Routenberechnung geglückt ist wird dies aufgerufen
    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
    if (direction.isOK()) {
        LatLng origin = new LatLng(breitengrad, laengengrad);

        //Setzt Marker am Standort und am Ziel
        Standort = mMap.addMarker(new MarkerOptions().position(origin).title("Dein Standort"));
        //Standort.showInfoWindow();
        HHNStandort = mMap.addMarker(new MarkerOptions().position(destination).title(markertitle).snippet(beschreibung).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        HHNStandort.showInfoWindow();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destination, 13));

        //Zeichnet die Route
        ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
        routenavi = mMap.addPolyline(DirectionConverter.createPolyline(this.getContext(), directionPositionList, 5, Color.rgb(0,118,188)));

    }
    }

    //Wird aufgerufen falls Routenberechnung nicht funktioniert hat
    @Override
    public void onDirectionFailure(Throwable t) {
        System.out.println("Fehler bei der Routenberechnung");
    }
}
