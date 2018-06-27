package com.adwait.crud.presentation.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.adwait.crud.presentation.R;
import com.adwait.crud.presentation.utils.Constant;

public class AddSampleUserActivity extends RevealActivity implements View.OnClickListener {


    private RelativeLayout relativeLayout_root;
    private ImageView imageView_close;
    private FloatingActionButton floatingActionButton_add;
    private TextInputEditText textInputEditText_username;
    private TextInputLayout textInputLayout_username;
    private TextInputLayout textInputLayout_email;
    private TextInputEditText textInputEditText_email;


    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_add_user);
        relativeLayout_root = findViewById(R.id.relativeLayout_root);
        imageView_close = findViewById(R.id.imageView_close);
        floatingActionButton_add = findViewById(R.id.floatingActionButton_add);
        textInputLayout_username = findViewById(R.id.textInputLayout_username);
        textInputEditText_username = findViewById(R.id.textInputEditText_username);

        textInputLayout_email = findViewById(R.id.textInputLayout_email);
        textInputEditText_email = findViewById(R.id.textInputEditText_email);
    }

    @Override
    protected void initData(Bundle savedInstanceState) throws Exception {
        super.initData(savedInstanceState);
        showRevealEffect(savedInstanceState,relativeLayout_root);
    }

    @Override
    protected void initEvent() {
        imageView_close.setOnClickListener(this);
        floatingActionButton_add.setOnClickListener(this);
        textInputEditText_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(TextUtils.isEmpty(charSequence)){
                    textInputLayout_username.setError(getString(R.string.empty_field));
                }else{
                    textInputLayout_username.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        textInputEditText_email.addTextChangedListener(new EmailValidationHandler());
    }

    public static void show(Activity invokingActivity, int requestCode, int fabX, int fabY) {
        Intent intent = new Intent(invokingActivity,AddSampleUserActivity.class);
        intent.putExtra(REVEAL_X,fabX);
        intent.putExtra(REVEAL_Y,fabY);
        invokingActivity.startActivityForResult(intent,requestCode);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageView_close:
                onBackPressed();
                break;
            case R.id.floatingActionButton_add:
                validateInputAndPostResult();
                break;
        }
    }

    private void validateInputAndPostResult() {
        String username = textInputEditText_username.getText().toString();
        String email = textInputEditText_email.getText().toString();
        if(!TextUtils.isEmpty(username)&& !TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            setUserDetailResult();
        }
    }

    private void setUserDetailResult() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(Constant.ADD_USERNAME, textInputEditText_username.getText().toString());
        resultIntent.putExtra(Constant.ADD_EMAIL,textInputEditText_email.getText().toString());
        setResult(RESULT_OK,resultIntent);
        finish();
    }

    private class EmailValidationHandler implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (TextUtils.isEmpty(charSequence) || !Patterns.EMAIL_ADDRESS.matcher(charSequence).matches()) {
                textInputLayout_email.setError(getString(R.string.invalid_email));
            } else {
                textInputLayout_email.setError(null);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
