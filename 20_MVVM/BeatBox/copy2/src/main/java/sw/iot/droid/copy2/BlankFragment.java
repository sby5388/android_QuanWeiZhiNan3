package sw.iot.droid.copy2;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sw.iot.droid.copy2.databinding.FragmentBlankBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final FragmentBlankBinding fragmentBlankBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_blank, null, false);
        fragmentBlankBinding.textViewShow.setText("Mvvm");
        return fragmentBlankBinding.getRoot();
    }

}
