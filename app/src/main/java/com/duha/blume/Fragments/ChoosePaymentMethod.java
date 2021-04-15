package com.duha.blume.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.duha.blume.Extras.Config;
import com.duha.blume.MVP.UserProfileResponse;
import com.duha.blume.Activities.MainActivity;
import com.duha.blume.R;
import com.duha.blume.Retrofit.Api;
import com.duha.blume.Activities.SplashScreen;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ChoosePaymentMethod extends Fragment {

    View view;
    @BindView(R.id.addNewAddressLayout)
    LinearLayout addNewAddressLayout;
    @BindView(R.id.addressCheckBox)
    CheckBox addressCheckBox;
    @BindView(R.id.addNewAddress)
    TextView addNewAddress;
    @BindView(R.id.fillAddress)
    TextView fillAddress;
    @BindView(R.id.paymentMethodsGroup)
    RadioGroup paymentMethodsGroup;
    @BindView(R.id.makePayment)
    Button makePayment;
    String paymentMethod;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.choosePaymentLayout)
    LinearLayout choosePaymentLayout;
    @BindViews({R.id.fullNameEdt, R.id.mobEditText, R.id.cityEditText, R.id.areaEditText, R.id.buildingEditText, R.id.pincodeEditText, R.id.stateEditText, R.id.landmarkEditText,})
    List<EditText> editTexts;
    public static String address, mobileNo,userEmail,profilePinCode;
    Intent intent;
    String s;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int layout = R.layout.fragment_choose_payment_method;
        view = inflater.inflate(layout, container, false);
        ButterKnife.bind(this, view);
        MainActivity.title.setText("Escoja Modo de Pago");
        MainActivity.cart.setVisibility(View.GONE);
        MainActivity.cartCount.setVisibility(View.GONE);
        getUserProfileData();
        addressCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    addNewAddressLayout.setVisibility(View.GONE);
                    addNewAddress.setText("Adicione nueva direccion");

                }
            }
        });
        choosePaymentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);

            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MainActivity.cart.setVisibility(View.VISIBLE);
        MainActivity.cartCount.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.addNewAddress, R.id.makePayment, R.id.fillAddress})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addNewAddress:
                addNewAddressLayout.setVisibility(View.VISIBLE);
                addressCheckBox.setChecked(false);
                addNewAddress.setText("Use esta direccion");
                break;
            case R.id.makePayment:
                if (!addressCheckBox.isChecked()) {
                    if (addNewAddressLayout.getVisibility() == View.VISIBLE) {
                        if (validate(editTexts.get(0))
                                && validate(editTexts.get(1))
                                && validate(editTexts.get(2))
                                && validate(editTexts.get(3))
                                && validate(editTexts.get(4))
                                && validatePinCode(editTexts.get(5))
                                && validate(editTexts.get(6))) {
                            String s=" " ;
                            if (editTexts.get(6).getText().toString().trim().length() > 0) {
                                  s = "Horario de Recepcion: " + editTexts.get(6).getText().toString().trim()+"\n";
                            }
                            address = "Razon Social: "+editTexts.get(0).getText().toString().trim()
                                    + "\n"+" RUC: "
                                    + editTexts.get(4).getText().toString().trim()+"\n"
                                    + s
                                    + " Direccion: " + editTexts.get(3).getText().toString().trim()
                                    + "\n"+" Cuidad: " + editTexts.get(2).getText().toString().trim()+"\n"
                                    + "\n" +" Nombre y Apellido: " + editTexts.get(7).getText().toString().trim()
                                    + "\n"+" Distrito: " + editTexts.get(5).getText().toString().trim()
                                    + "\n" + editTexts.get(1).getText().toString().trim();
                            mobileNo ="Celular: "+editTexts.get(1).getText().toString().trim();
                            moveNext();
                        }
                    } else {
                        Config.showCustomAlertDialog(getActivity(),
                                "Por favor escoja la direccion que registro ",
                                "",
                                SweetAlertDialog.ERROR_TYPE);
                    }
                } else {
                    if (SplashScreen.restaurantDetailResponseData.getDeliverycity().contains(profilePinCode.trim()))
                        moveNext();
                    else {
                        Config.showPincodeCustomAlertDialog1(getActivity(),
                                "No disponible en su area",
                                "No estamos haciendo delivery en su area.",
                                SweetAlertDialog.WARNING_TYPE);

                    }
                }

                break;
            case R.id.fillAddress:
                ((MainActivity) getActivity()).loadFragment(new MyProfile(), true);
                break;
        }

    }
    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    private void moveNext() {
        switch (paymentMethodsGroup.getCheckedRadioButtonId()) {

            case R.id.cod:
                paymentMethod = "Pago en efectivo";
                Config.addOrder(getActivity(),
                        "Pago en Efectivo",
                        "Pago en Efectivo");
                break;
            case R.id.paypal:
                paymentMethod="Pago con POS";
                Config.addOrder(getActivity(),
                        "Pago con POS",
                        "Pago con POS");
                break;
               /* paymentMethod = "paypal";
                intent = new Intent(getActivity(), PayPalActivityPayment.class);
                startActivity(intent);*/
                //break;
            /*case R.id.stripe:
                paymentMethod = "stripe";
                intent = new Intent(getActivity(), StripePaymentIntegration.class);
                startActivity(intent);
                break;*/
            default:
                paymentMethod = "";
                Config.showCustomAlertDialog(getActivity(),
                        "Modo de Pago",
                        "Seleccione su modo de pago",
                        SweetAlertDialog.NORMAL_TYPE);
                break;


        }

        Log.d("paymentMethod", paymentMethod);
    }

    private boolean validate(EditText editText) {
        if (editText.getText().toString().trim().length() > 0) {
            return true;
        }
        editText.setError("Por favor llene aqui");
        editText.requestFocus();
        return false;
    }

    private boolean validatePinCode(EditText editText) {
        if (editText.getText().toString().trim().length() > 0) {
            if (SplashScreen.restaurantDetailResponseData.getDeliverycity().contains(editText.getText().toString().trim()))
                return true;
            else {
                Config.showPincodeCustomAlertDialog(getActivity(),
                        "No disponible en su area",
                        "No estamos haciendo delivery en su area.",
                        SweetAlertDialog.WARNING_TYPE);
                editText.setError("No disponible");
                editText.requestFocus();
                return false;
            }
        }
        editText.setError("Por Favor llene aqui ");
        editText.requestFocus();
        return false;
    }

    public void getUserProfileData() {
        progressBar.setVisibility(View.VISIBLE);
        makePayment.setClickable(false);
        Api.getClient().getUserProfile(
                MainActivity.userId, new Callback<UserProfileResponse>() {
                    @Override
                    public void success(UserProfileResponse userProfileResponse, Response response) {
                        makePayment.setClickable(true);
                        progressBar.setVisibility(View.GONE);
                        userEmail=userProfileResponse.getEmail();
                        String s = "";
                        if (!userProfileResponse.getLandmark().equalsIgnoreCase("")) {
                            s = " Nombre y Apellido: " + userProfileResponse.getLandmark();
                        }
                        if (userProfileResponse.getFlat().equalsIgnoreCase("")) {
                            addressCheckBox.setChecked(false);
                            addressCheckBox.setVisibility(View.GONE);
                            fillAddress.setVisibility(View.VISIBLE);
                        } else {
                            address = userProfileResponse.getName()
                                    + ", "
                                    + userProfileResponse.getFlat()
                                    + s
                                    + ", " + userProfileResponse.getLocality()
                                    + ", " + userProfileResponse.getCity()
                                    + ", " + userProfileResponse.getState()
                                    + ",  " + userProfileResponse.getPincode()
                                    + "\n" + userProfileResponse.getMobile();
                            addressCheckBox.setText(address);
                            mobileNo = userProfileResponse.getMobile();
                            profilePinCode = userProfileResponse.getPincode();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        makePayment.setClickable(true);
                        progressBar.setVisibility(View.GONE);

                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) getActivity()).lockUnlockDrawer(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }
}
