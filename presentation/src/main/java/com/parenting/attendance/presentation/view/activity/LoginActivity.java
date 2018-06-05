package com.parenting.attendance.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parenting.attendance.R;
import com.parenting.attendance.data.net.RestApi;
import com.parenting.attendance.domain.interactor.LoginUseCase;
import com.parenting.attendance.presentation.internal.di.HasComponent;
import com.parenting.attendance.presentation.internal.di.components.DaggerLoginComponent;
import com.parenting.attendance.presentation.internal.di.components.LoginComponent;
import com.parenting.attendance.presentation.internal.di.modules.ActivityModule;
import com.parenting.attendance.presentation.internal.di.modules.LoginModule;
import com.parenting.attendance.presentation.model.Login;
import com.parenting.attendance.presentation.presenter.LoginPresenter;
import com.parenting.attendance.presentation.utils.Constant;
import com.parenting.attendance.presentation.view.LoginView;

import javax.inject.Inject;

/**
 * Created by adwait on 20/03/18.
 */

public class LoginActivity extends BaseActivity implements HasComponent<LoginComponent>,View.OnClickListener,LoginView {

    private EditText editText_phoneNumber;
    private EditText editText_password;
    private TextView textView_Login;
    private TextView textView_signup;

    private LoginComponent loginComponent;

    @Inject
    LoginPresenter presenter;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);
        editText_phoneNumber = findViewById(R.id.editText_phoneNumber);
        editText_password = findViewById(R.id.editText_password);
        textView_Login = findViewById(R.id.textView_Login);
        textView_signup = findViewById(R.id.textView_signup);
    }

    @Override
    protected void initData(Bundle savedInstanceState) throws Exception {
        loginComponent = DaggerLoginComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
        loginComponent.present(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.setLoginView(this);
    }

    @Override
    protected void initEvent() {
        textView_Login.setOnClickListener(this);
        textView_signup.setOnClickListener(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(Constant.REQUEST_CODE_SIGNUP == requestCode){
            if(RESULT_OK == resultCode){
                switchToDashboard();
            }
        }
    }

    private void switchToDashboard() {
        Intent intent = new Intent(this,DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textView_Login:
                String phoneNo = Constant.INDIA_COUNTRY_CODE + editText_phoneNumber.getText().toString().trim();
                String password = editText_password.getText().toString().trim();
                presenter.doLogin(phoneNo, password);
                break;
            case R.id.textView_signup:
                SignUpActivity.signUp(this);
                break;
        }
    }

    @Override
    public void onLoginDone(Login login) {
        switchToDashboard();
    }

    @Override
    public void showLoading() {
        Toast.makeText(this,"Loading",Toast.LENGTH_LONG).show();
    }

    @Override
    public void hideLoading() {
        Toast.makeText(this,"Done!",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showRetry() {
        Toast.makeText(this,"Retry",Toast.LENGTH_LONG).show();
    }

    @Override
    public void hideRetry() {
        Toast.makeText(this,"Hide Retry",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public Context context() {
        return null;
    }

    @Override
    public LoginComponent getComponent() {
        return loginComponent;
    }
}
