package gruppe2.demo.win;

import android.Manifest;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import gruppe2.demo.win.Fragments.navi_hhn;
import gruppe2.demo.win.Fragments.infos_hhn;

public class MainActivity extends AppCompatActivity


        implements NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    //Mit Hilfe dieser können wir später die Position auslesen
    GoogleApiClient mGoogleApiClient;

    //Diese Variable beinhaltet später die Positionsdaten
    Location mLastLocation;

    //Variablen, zur Anzeige der Daten (nicht unbedingt relevant)
    TextView l;
    TextView b;

    // Variablen für Spinner

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Aufruf der Initalisierung
        buildGoogleApiClient();

        //Definieren der Textausgabefelder
        l = (TextView) findViewById(R.id.textLaengenGrad);
        b = (TextView) findViewById(R.id.textBreitenGrad);

        //Starten der eben initalisierten API von Google
        mGoogleApiClient.connect();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new navi_hhn()).commit();


        setContentView(R.layout.activity_main);


        spinner = (Spinner)findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.spinner,android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " selected", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //Initalisiert die API von Google, mit der wir die Position auslesen können
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    //Wird aufgerufen, wenn die Verbindung zur Google API erfolgreich ist
    @Override
    public void onConnected(Bundle connectionHint) {
        //Anhand der erfolgreichen Verbindung die benötigten Daten auslesen
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            //Die ausgelesenen daten verwerten und anzeigen
            l.setText("Längengrad: " + String.valueOf(mLastLocation.getLongitude()));
            b.setText("Breitengrad: " + String.valueOf(mLastLocation.getLatitude()));
        }
    }








                //Wird aufgerufen, wenn die Verbindung zur Google API aussteht
        @Override
        public void onConnectionSuspended ( int i){
        }

        //Wird aufgerufen, wenn die Verbindung zur Google API fehlschlägt
        @Override
        public void onConnectionFailed (ConnectionResult connectionResult){
            l.setText("Längengrad: Da ist ein Fehler aufgetreten.");
            b.setText("Breitengrad: Da ist ein Fehler aufgetreten.");
        }


        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected (MenuItem item){
            FragmentManager fm = getFragmentManager();

            int id = item.getItemId();

            if (id == R.id.infos_hhn) {
                fm.beginTransaction().replace(R.id.content_frame, new infos_hhn()).commit();
            } else if (id == R.id.navi_hhn) {
                fm.beginTransaction().replace(R.id.content_frame, new navi_hhn()).commit();
            }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

    }
