package com.zsc.game.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zsc.game.R;
import com.zsc.game.VR.ImageUrGetter;
import com.zsc.game.ui.adapter.VrPicAdapter;

/**
 * Created by 1 on 2017/12/18.
 */

public class VRpickFragment extends Fragment{

    private XRecyclerView xRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.vrpicturefragment,container,false);
       xRecyclerView = view.findViewById(R.id.vr_pic2);
        return  view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        xRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        xRecyclerView.setAdapter(new VrPicAdapter(getActivity(), ImageUrGetter.getImageItems()));
    }
}
