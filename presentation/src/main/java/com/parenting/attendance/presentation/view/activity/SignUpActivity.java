package com.parenting.attendance.presentation.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.parenting.attendance.R;
import com.parenting.attendance.presentation.utils.Alert;
import com.parenting.attendance.presentation.utils.Constant;

/**
 * Created by adwait on 20/03/18.
 */

public class SignUpActivity extends BaseActivity implements View.OnClickListener {


    private EditText editText_name;
    private EditText editText_email;
    private EditText editText_company_name;
    private EditText editText_phoneNumber;
    private EditText editText_password;
    private ImageView imageView_show_password;
    private TextView textView_signup;
    private int maxLength = 10;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_signup);
        editText_name = findViewById(R.id.editText_name);
        editText_email = findViewById(R.id.editText_email);
        editText_company_name = findViewById(R.id.editText_company_name);
        editText_phoneNumber = findViewById(R.id.editText_phoneNumber);
        editText_password = findViewById(R.id.editText_password);
        imageView_show_password = findViewById(R.id.imageView_show_password);
        textView_signup = findViewById(R.id.textView_signup);
    }

    @Override
    protected void initData(Bundle savedInstanceState) throws Exception {
        super.initData(savedInstanceState);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        textView_signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textView_signup:
//                TODO:call verify with phone no here
                validate();
                break;
        }
    }

    private void validate() {
        String phoneNumber = editText_phoneNumber.getText().toString().trim();
        if (phoneNumber.length() == 0) {
            Alert.alert(this, getString(R.string.alertsign), getString(R.string.please_enter_phone_number), "", getString(R.string.ok), null, new Runnable() {
                @Override
                public void run() {
                    editText_phoneNumber.requestFocus();
                }
            });
        } else if (phoneNumber.length() < maxLength) {
            Alert.alert(this, getString(R.string.alertsign), getString(R.string.please_enter_valid_phone_number), "", getString(R.string.ok), null, new Runnable() {
                @Override
                public void run() {
                    editText_phoneNumber.setSelection(editText_phoneNumber.getText().length());
                    editText_phoneNumber.requestFocus();
                }
            });
        } else {
//            String phoneNumberOfUser = countryCodePicker.getSelectedCountryCodeWithPlus() + editText_mobile_number.getText().toString().trim();
            String phoneNumberOfUser = Constant.INDIA_COUNTRY_CODE + editText_phoneNumber.getText().toString().trim();
           /**//* Utility.saveUserPhoneNumber(activity, phoneNumberOfUser);
            Utility.saveUserPhoneNumberwithoutPlus(activity, editText_phoneNumber.getText().toString().trim());
            Utility.saveUserCountryCode(activity, countryCodePicker.getSelectedCountryCodeWithPlus());
            Utility.saveUserName(activity, editTextName.getText().toString().trim());*//*
            EatRepeatPref.getInstance().saveString(PhoneVerificationActivity.this, EatRepeatPref.PreferenceKey.phoneNumberOfUser, phoneNumber);

            makeLogin(phoneNumberOfUser);*/
            Intent intent = new Intent();
            intent.putExtra(Constant.phoneNumber, phoneNumberOfUser);
            intent.setClass(this, OTPActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
            startActivity(intent);
            finish();
        }
    }

    public static void signUp(Activity activity) {
        Intent intent = new Intent(activity,SignUpActivity.class);
        activity.startActivity(intent);
    }
}
