package com.parenting.attendance.presentation.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.parenting.attendance.R;
import com.parenting.attendance.domain.models.VerificationResponse;
import com.parenting.attendance.presentation.presenter.PhoneVerificationPresenter;
import com.parenting.attendance.presentation.utils.Alert;
import com.parenting.attendance.presentation.utils.Constant;
import com.parenting.attendance.presentation.view.VerificationView;
import com.parenting.attendance.presentation.view.customviews.PinEntryView;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

;

public class OTPActivity extends BaseActivity implements View.OnClickListener,VerificationView{

    String phoneNumber = "";
    Activity activity;
    int count = 1;

    private static final String TAG = "VerificationActivity";
    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";
    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private boolean mVerificationInProgress = false;
    private PhoneVerificationPresenter mPresenter;
    private ProgressDialog progressDialog;
    Button button_verify;
    TextView textView_resend;
    PinEntryView editText_pin;

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
//        mPresenter = new VerificationPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(),this,new RestApiImpl(this),this);
        activity = this;
        mAuth = FirebaseAuth.getInstance();

        setToolbar(true, R.drawable.logo,true,getString(R.string.verification));
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");



        button_verify = (Button) findViewById(R.id.button_verify);
        editText_pin = (PinEntryView) findViewById(R.id.editText_pin);
        textView_resend = (TextView) findViewById(R.id.textView_resend);

        button_verify.setOnClickListener(this);
        textView_resend.setOnClickListener(this);

//        ERApp.getInstance().overrideFonts(activity, findViewById(android.R.id.content));
//        FontFacade.getInstance(this).applyTypefaceBold(textView_resend);

        editText_pin.setOnPinEnteredListener(new PinEntryView.OnPinEnteredListener() {
            @Override
            public void onPinEntered(String pin) {
                //Log.e("pin", ":" + pin);
                if (pin.length() == 6){
                    verifyPhoneNumberWithCode(mVerificationId, editText_pin.getText().toString());

                }
            }
        });

        setResendText();

        // Initialize phone auth callbacks
        // [START phone_auth_callbacks]
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                // [START_EXCLUDE silent]
                // Update the UI and attempt sign in with the phone credential
                //updateUI(STATE_VERIFY_SUCCESS, credential);
                // [END_EXCLUDE]
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]
                try {
                    if (progressDialog != null)
                        progressDialog.dismiss();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    showToast("Invalid phone number." + e.getMessage());
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                    // [END_EXCLUDE]
                }

                // Show a message and update the UI
                // [START_EXCLUDE]
                //updateUI(STATE_VERIFY_FAILED);
                // [END_EXCLUDE]
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.e(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                try {
                    if (progressDialog != null)
                        progressDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // [START_EXCLUDE]
                // Update UI
                //updateUI(STATE_CODE_SENT);
                // [END_EXCLUDE]
            }
        };
        // [END phone_auth_callbacks]

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            if (bundle.containsKey(Constant.phoneNumber)) {
                phoneNumber = bundle.getString(Constant.phoneNumber);
                startPhoneNumberVerification(phoneNumber);
                setToolbar(true,R.drawable.logo,true,phoneNumber);
            }

        }

    }


    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]

        Log.e("phoneNumber ",""+phoneNumber +" phone auth:"+ PhoneAuthProvider.getInstance());
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]
        progressDialog.show();

        mVerificationInProgress = true;
    }


    @SuppressLint("HardwareIds")
    private void makeLogin(String phoneNumber) {
        String android_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        //Log.e("android_id", ":" + android_id);
        /*EatRepeatPref.getInstance().saveString(this, EatRepeatPref.PreferenceKey.deviceID, android_id);

        if (NetworkUtil.checkNetworkStatus(this)) {
            Map<String, String> request = RetrofitRequest
                    .getLoginDetails(
                            android_id,
                            "Android",
                            phoneNumber,
                            FirebaseInstanceId.getInstance().getToken(),
                            FirebaseInstanceId.getInstance().getToken());

            doLogin(request);
        } else {
            showAlertNoInternet();
        }*/
    }


    @SuppressWarnings("unchecked")
    private void doLogin(Map<String, String> request) {
        //TODO:convert this to presenter menthod
        Intent resultIntent = getIntent();
//        resultIntent.putExtra(Constant.LOGIN_DATA,new LoginUserDetail());
        resultIntent.putExtra(Constant.PHONE_NUMBER,phoneNumber);
        setResult(RESULT_OK,resultIntent);
        finish();
//        mPresenter.doLogin(request);
        /*try {


            if (progressDialog != null &&
                    !progressDialog.isShowing())
                progressDialog.show();


            Call<LoginUserDetail> call = apiClient.getApi().getLoginDetails(request);

            call.enqueue(new MazkaraRetrofitCallback<LoginUserDetail>(this) {
                @Override
                protected void onResponseMazkara(Call call, Response response) {
                    try {
                        String code = String.valueOf(response.code());
                        if (code.substring(0, 2).contains("50")) {
                            Toast.makeText(activity, "Something went wrong...", Toast.LENGTH_SHORT).show();
                            //onBackPressed();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                protected void onResponseMazkaraObject(Call call, LoginUserDetail response) {
                    if (response != null) {
                        try {
                            if (response.getSuccess().equals("1")) {
                                // setValues(response);
                                String result_json = new Gson().toJson(response);

                                EatRepeatPref.getInstance().saveString(activity, EatRepeatPref.PreferenceKey.login_response, result_json);
                                EatRepeatPref.getInstance().saveString(activity, EatRepeatPref.PreferenceKey.merchantID, response.getData().getUser().getId());
                                EatRepeatPref.getInstance().saveString(activity, EatRepeatPref.PreferenceKey.merchantName, response.getData().getUser().getName());
                                EatRepeatPref.getInstance().saveString(activity, EatRepeatPref.PreferenceKey.branchCurrency, response.getData().getBranchModuleAccess().get(0).getBranch().getCurrency());
                                EatRepeatPref.getInstance().saveInt(activity, EatRepeatPref.PreferenceKey.branchPosition, 0);
                                EatRepeatPref.getInstance().saveInt(activity, EatRepeatPref.PreferenceKey.isUpdateHomeData, 0);
                                EatRepeatPref.getInstance().saveString(activity, EatRepeatPref.PreferenceKey.branchID, response.getData().getBranchModuleAccess().get(0).getBranch().getId());
                                EatRepeatPref.getInstance().saveString(activity, EatRepeatPref.PreferenceKey.branchName, response.getData().getBranchModuleAccess().get(0).getBranch().getLocation());
                                //TODO: instead of switching to dashboard return result OK and finish
                                switchToDashboardActivity();

                            } else {

                                Toast.makeText(activity, response.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }


                @Override
                protected void common() {
                    progressDialog.dismiss();

                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    super.onFailure(call, t);
                    //Log.e("t", ":" + t.getMessage());
                }

            });


        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }


    // [START sign_in_with_phone]
    private void signInWithPhoneAuthCredential(final PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            //Log.e("FirebaseUser", " success :" + new Gson().toJson(user));

                            if (progressDialog != null)
                                progressDialog.dismiss();

                            Log.e("getPhoneNumber ", "" + user.getPhoneNumber());
                            makeLogin(user.getPhoneNumber());
                            // [START_EXCLUDE]
                            //updateUI(STATE_SIGNIN_SUCCESS, user);
                            // [END_EXCLUDE]
                        } else {
                            try {
                                // Sign in failed, display a message and update the UI
                                Log.w(TAG, credential.getSmsCode() + " signInWithCredential:failure", task.getException());
                                showToast(task.getException().getMessage());
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    // The verification code entered was invalid
                                    // [START_EXCLUDE silent]
                                    //mVerificationField.setError("Invalid code.");
                                    // [END_EXCLUDE]
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                if (progressDialog != null)
                                    progressDialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            // [START_EXCLUDE silent]
                            // Update UI
                            //updateUI(STATE_SIGNIN_FAILED);
                            // [END_EXCLUDE]
                        }
                    }
                });
    }

    private void showToast(String message) {
        try {
            Toast.makeText(this,message, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
        }
    }
    // [END sign_in_with_phone]



    private void switchToDashboardActivity() {
        try {
//            EatRepeatPref.getInstance().saveString(this, EatRepeatPref.PreferenceKey.isLoggedIn, "1");
            Intent in = new Intent();
            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            in.setClass(this, DashboardActivity.class);
            startActivity(in);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setResendText() {
        //Log.e("setResendText", " called");
        String resendPreText = getString(R.string.wait_for_fifteen_sec) + " " + "15" + " seconds";
        textView_resend.setText(resendPreText);
        int delay = 0; // delay for 1 sec.
        int period = 1000; // repeat every 10 sec.
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (count >= 15) {
                            textView_resend.setText(Html.fromHtml(getString(R.string.resend_sms)));
                            timer.cancel();
                        } else {
                            int sec = 15 - count;
                            String resendPreText = getString(R.string.wait_for_fifteen_sec) + " " + sec + " seconds";
                            textView_resend.setText(resendPreText);

                        }

                        count++;
                    }
                });

            }
        }, delay, period);

    }



    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        try {
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        signInWithPhoneAuthCredential(credential);
    }


    // [START resend_verification]
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
        try {
            Alert.alert(activity,
                    "",
                    String.format(getString(R.string.code_sent),
                            phoneNumber),
                    null,
                    getString(R.string.ok),
                    null,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // [END resend_verification]


    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.button_verify:

                    verifyPhoneNumberWithCode(mVerificationId,editText_pin.getText().toString());
                    break;
                case R.id.textView_resend:
                    if (count >= 15) {
                        resendVerificationCode(phoneNumber, mResendToken);
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    @Override
//    public void showProgress() {
//        try {
//            progressDialog = new ProgressDialog(this);
//            progressDialog.setIndeterminate(true);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Please Wait...");
//            progressDialog.show();
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//        } catch (Exception e) {
//        }
//    }

//    @Override
//    public void hideProgress() {
//        try {
//            progressDialog.dismiss();
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//        } catch (Exception e) {
//
//        }
//    }

    @Override
    public void showLoading() {
        try {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } catch (Exception e) {
        }
    }

    @Override
    public void hideLoading() {
        try {
            progressDialog.dismiss();
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } catch (Exception e) {

        }
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {
        showToast(message);
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public Context context() {
        return null;
    }

//    @Override
//    public void onLoginDone(LoginUserDetail detail) {
//        String result_json = new Gson().toJson(detail);
//
////        EatRepeatPref.getInstance().saveString(activity, EatRepeatPref.PreferenceKey.login_response, result_json);
////        EatRepeatPref.getInstance().saveString(activity, EatRepeatPref.PreferenceKey.merchantID, detail.getData().getUser().getId());
////        EatRepeatPref.getInstance().saveString(activity, EatRepeatPref.PreferenceKey.merchantName, detail.getData().getUser().getName());
////        EatRepeatPref.getInstance().saveString(activity, EatRepeatPref.PreferenceKey.branchCurrency, detail.getData().getBranchModuleAccess().get(0).getBranch().getCurrency());
////        EatRepeatPref.getInstance().saveInt(activity, EatRepeatPref.PreferenceKey.branchPosition, 0);
////        EatRepeatPref.getInstance().saveInt(activity, EatRepeatPref.PreferenceKey.isUpdateHomeData, 0);
////        EatRepeatPref.getInstance().saveString(activity, EatRepeatPref.PreferenceKey.branchID, detail.getData().getBranchModuleAccess().get(0).getBranch().getId());
////        EatRepeatPref.getInstance().saveString(activity, EatRepeatPref.PreferenceKey.branchName, detail.getData().getBranchModuleAccess().get(0).getBranch().getLocation());
//        //TODO: instead of switching to dashboard return result OK and finish
////        switchToDashboardActivity();
//        Intent resultIntent = new Intent();
//        resultIntent.putExtra(Constant.LOGIN_DATA,detail);
//        resultIntent.putExtra(Constant.PHONE_NUMBER,phoneNumber);
//        setResult(RESULT_OK);
//        finish();
//    }

//    @Override
//    public void onLoginFailed(Exception e) {
//        if(e instanceof EatRepeatException){
//            showError(e.getMessage());
//        }else{
//            firebaseCrashLog("MenuActivity onMenuDataFailed", e);
//        }
//        setResult(RESULT_CANCELED);
//        finish();
//    }

    @Override
    public void onVerificationSuccess(VerificationResponse response) {
//        Intent resultIntent = new Intent();
//        resultIntent.putExtra(Constant.LOGIN_DATA,response);
        setResult(RESULT_OK);
        finish();
    }
}
