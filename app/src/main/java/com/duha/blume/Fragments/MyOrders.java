package com.duha.blume.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.duha.blume.Activities.AccountVerification;
import com.duha.blume.Adapter.MyOrdersAdapter;
import com.duha.blume.Activities.Login;
import com.duha.blume.Activities.MainActivity;
import com.duha.blume.Activities.SignUp;
import com.duha.blume.Extras.Config;
import com.duha.blume.MVP.MyOrdersResponse;
import com.duha.blume.R;
import com.duha.blume.Retrofit.Api;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MyOrders extends Fragment {

    View view;
    @BindView(R.id.myOrdersRecyclerView)
    RecyclerView myOrdersRecyclerView;
    public static MyOrdersResponse myOrdersResponseData;

    @BindView(R.id.emptyOrdersLayout)
    LinearLayout emptyOrdersLayout;
    @BindView(R.id.loginLayout)
    LinearLayout loginLayout;
    @BindView(R.id.continueShopping)
    Button continueShopping;

    @BindView(R.id.verifyEmailLayout)
    LinearLayout verifyEmailLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_orders, container, false);
        ButterKnife.bind(this, view);
        MainActivity.title.setText("My Orders");
        if (!MainActivity.userId.equalsIgnoreCase("")) {
            getMyOrders();
        } else {
            loginLayout.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @OnClick({R.id.continueShopping, R.id.loginNow, R.id.txtSignUp, R.id.verfiyNow})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.continueShopping:
                ((MainActivity)getActivity()).removeCurrentFragmentAndMoveBack();
                break;
            case R.id.loginNow:
                Config.moveTo(getActivity(), Login.class);
                break;
            case R.id.txtSignUp:
                Config.moveTo(getActivity(), SignUp.class);
                break;

            case R.id.verfiyNow:
                Config.moveTo(getActivity(), AccountVerification.class);
                break;
        }
    }
    public void getMyOrders() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        Api.getClient().getMyOrders(MainActivity.userId, new Callback<MyOrdersResponse>() {
            @Override
            public void success(MyOrdersResponse myOrdersResponse, Response response) {
                pDialog.dismiss();
                if (myOrdersResponse.getSuccess().equalsIgnoreCase("true")) {
                    try {
                        Log.d("size", myOrdersResponse.getOrderdata().size() + "");
                        myOrdersResponseData = myOrdersResponse;
                        setProductsData();
                    } catch (Exception e) {
                        emptyOrdersLayout.setVisibility(View.VISIBLE);
                    }
                }else {
                    verifyEmailLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                emptyOrdersLayout.setVisibility(View.VISIBLE);
                pDialog.dismiss();

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) getActivity()).lockUnlockDrawer(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        Config.getCartList(getActivity(), true);
    }

    private void setProductsData() {
        GridLayoutManager gridLayoutManager;
        gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        myOrdersRecyclerView.setLayoutManager(gridLayoutManager);
        MyOrdersAdapter myOrdersAdapter = new MyOrdersAdapter(getActivity(), myOrdersResponseData.getOrderdata());
        myOrdersRecyclerView.setAdapter(myOrdersAdapter);

    }
}
