package com.xcm91.relation.statusbarutil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xcm91.relation.statusbar.R;


/**
 * Created by Jaeger on 16/8/11.
 *
 * Email: chjie.jaeger@gmail.com
 * GitHub: https://github.com/laobie
 */
public class ImageFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragement_image, container, false);
    }
}
