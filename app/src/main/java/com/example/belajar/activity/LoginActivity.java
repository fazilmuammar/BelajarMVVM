package com.example.belajar.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.andreabaccega.widget.FormEditText;
import com.example.belajar.MainActivity;
import com.example.belajar.R;
import com.example.belajar.databinding.ActivityLoginBinding;
import com.example.belajar.view.LoginView;
import com.example.belajar.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel mLoginViewModel;
    private ActivityLoginBinding loginActivittyBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginActivittyBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mLoginViewModel = new LoginViewModel(this);
        loginActivittyBinding.setLoginview(mLoginViewModel);

        loginActivittyBinding.setPresenter(new LoginView() {
            @Override
            public void LoginData() {
                FormEditText fdtEmail = (FormEditText) findViewById(R.id.et_email);
                FormEditText fdtPassword = (FormEditText) findViewById(R.id.password);

                final String email = loginActivittyBinding.etEmail.getText().toString();
                final String passowrod = loginActivittyBinding.password.getText().toString();

                FormEditText[] allFields = {fdtEmail, fdtPassword};
                boolean allValid = true;
                for (FormEditText field : allFields) {
                    allValid = field.testValidity() && allValid;
                }
                if (allValid) {
                   mLoginViewModel.sendLoginRequest(email,passowrod);
                }
            }
        });

    }
}
