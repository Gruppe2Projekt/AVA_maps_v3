/*
App erstellt durch Gruppe 2
Alexander Brechlin - 191898
Vitalij Degraf - 191904
Adrian Grünther - 191908
 */
package gruppe2.demo.win.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gruppe2.demo.win.R;

public class MainFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main,container,false);
    }
}
