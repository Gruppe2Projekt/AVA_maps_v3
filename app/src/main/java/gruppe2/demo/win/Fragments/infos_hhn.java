package gruppe2.demo.win.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import gruppe2.demo.win.MainActivity;
import gruppe2.demo.win.R;


public class infos_hhn extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.info_hhn, container,false);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        final FragmentManager fm = getFragmentManager();
        final navi_hhn f = new navi_hhn();

        Button markerEuropaplatz = (Button) getView().findViewById(R.id.berechnen1);
        markerEuropaplatz.setOnClickListener(new View.OnClickListener()

        {
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
