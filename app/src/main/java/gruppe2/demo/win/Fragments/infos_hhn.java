/*
App erstellt durch Gruppe 2
Alexander Brechlin - 191898
Vitalij Degraf - 191904
Adrian Grünther - 191908
 */

//Fragment zur Dartsellung der HHN-Standorte mitsamt Button für den Pin und Button für Routenberechnung
package gruppe2.demo.win.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import gruppe2.demo.win.R;


public class infos_hhn extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.info_hhn, container,false);


    }
//Hier werden die Buttons erstellt und bei klick darauf eine Varibale an das Fragment navi_hhn übergeben.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        final FragmentManager fm = getFragmentManager();
        final navi_hhn f = new navi_hhn();

        Button markerEuropaplatz = (Button) getView().findViewById(R.id.berechnen1);
        markerEuropaplatz.setOnClickListener(new View.OnClickListener()

        {
            //Bei Klick wird ein Bunlde erstellt und an navi_hhn übergeben
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putInt("schluessel",1);
                f.setArguments(args);

                fm.beginTransaction().replace(R.id.content_frame, f).commit();
            }
        });

        Button routeEuropaplatz = (Button) getView().findViewById(R.id.button14);
        routeEuropaplatz.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putInt("schluessel",2);
                f.setArguments(args);

                fm.beginTransaction().replace(R.id.content_frame, f).commit();
            }
        });
        Button markerSontheim = (Button) getView().findViewById(R.id.anzeigen1);
        markerSontheim.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putInt("schluessel",3);
                f.setArguments(args);

                fm.beginTransaction().replace(R.id.content_frame, f).commit();
            }
        });
        Button routeSontheim = (Button) getView().findViewById(R.id.button6);
        routeSontheim.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putInt("schluessel",4);
                f.setArguments(args);

                fm.beginTransaction().replace(R.id.content_frame, f).commit();
            }
        });

        Button markerkuenzelsau = (Button) getView().findViewById(R.id.button7);
        markerkuenzelsau.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putInt("schluessel",5);
                f.setArguments(args);

                fm.beginTransaction().replace(R.id.content_frame, f).commit();
            }
        });
        Button routekuenzelsau = (Button) getView().findViewById(R.id.button12);
        routekuenzelsau.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putInt("schluessel",6);
                f.setArguments(args);

                fm.beginTransaction().replace(R.id.content_frame, f).commit();
            }
        });
        Button markerschhall = (Button) getView().findViewById(R.id.button11);
        markerschhall.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putInt("schluessel",7);
                f.setArguments(args);

                fm.beginTransaction().replace(R.id.content_frame, f).commit();
            }
        });
        Button routeschhall = (Button) getView().findViewById(R.id.button4);
        routeschhall.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putInt("schluessel",8);
                f.setArguments(args);

                fm.beginTransaction().replace(R.id.content_frame, f).commit();
            }
        });


        super.onViewCreated(view, savedInstanceState);



    }

}
