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

import static android.R.attr.key;

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
                navi_hhn f =new navi_hhn();


                String file = "a_1";
                String KEY_FILE="file";
                Bundle args=new Bundle();
                args.putString(KEY_FILE,file);
                f.setArguments(args);



                fm.beginTransaction().replace(R.id.content_frame, f).commit();
            }
        });



        super.onViewCreated(view, savedInstanceState);



    }

}
