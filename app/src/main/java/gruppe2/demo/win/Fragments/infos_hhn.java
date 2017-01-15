package gruppe2.demo.win.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import gruppe2.demo.win.MainActivity;
import gruppe2.demo.win.R;

public class infos_hhn extends Fragment {

    @Nullable

    // Variablen für die Buttonüberwachung

            Button anzeigen1, berechnen1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.info_hhn, container,false);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        berechnen1 = (Button) getView().findViewById(R.id.berechnen1);
        final FragmentManager fm = getFragmentManager();
        berechnen1.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                //Hier variable setzen in Mainactivity
                fm.beginTransaction().replace(R.id.content_frame, new navi_hhn()).commit();
            }
        });



        super.onViewCreated(view, savedInstanceState);



    }

}
