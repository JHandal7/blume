package com.duha.blume.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duha.blume.Adapter.PincodeListAdapter;
import com.duha.blume.Extras.Config;
import com.duha.blume.Activities.MainActivity;
import com.duha.blume.R;
import com.duha.blume.Activities.SplashScreen;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PincodeList extends Fragment {

    View view;
    @BindView(R.id.pincodeRecyclerView)
    RecyclerView pincodeRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pincode, container, false);
        ButterKnife.bind(this, view);
        setPicodeListData();
        return view;
    }

    public void setPicodeListData() {
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        flowLayoutManager.setAutoMeasureEnabled(true);
        pincodeRecyclerView.setLayoutManager(flowLayoutManager);
        PincodeListAdapter pincodeListAdapter = new PincodeListAdapter(getActivity(), SplashScreen.restaurantDetailResponseData.getDeliverycity());
        pincodeRecyclerView.setAdapter(pincodeListAdapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) getActivity()).lockUnlockDrawer(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        MainActivity.title.setText("Codigos Postales Disponibles");
        Config.getCartList(getActivity(), true);
    }
}
