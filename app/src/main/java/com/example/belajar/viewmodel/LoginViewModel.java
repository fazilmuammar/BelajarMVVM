package com.example.belajar.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableInt;
import android.view.View;
import android.widget.Toast;

import com.example.belajar.MainActivity;
import com.example.belajar.model.ResponseLogin;
import com.example.belajar.network.InitRetrofit;
import com.example.belajar.network.RestApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel {

    public ObservableInt loginView;
    public ObservableInt progressbar;
    Context mContext;


    public LoginViewModel(Context context) {
        this.mContext = context;
        progressbar = new ObservableInt(View.GONE);
        loginView = new ObservableInt(View.VISIBLE);
    }

    public void sendLoginRequest(final String email, final String password) {
        progressbar.set(View.VISIBLE);
        loginView.set(View.GONE);

      RestApi api = InitRetrofit.getInstance();
        Call<ResponseLogin> loginCall =api.loginUser(
                email,
                password
        );
        loginCall.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.isSuccessful()) {
                    progressbar.set(View.GONE);
                    loginView.set(View.VISIBLE);
                    String result = response.body().getResponse();

                    if (result.equals("success")) {
                        mContext.startActivity(new Intent(mContext, MainActivity.class));

                    } else if (result.equals("failed")) {
                        progressbar.set(View.GONE);
                        loginView.set(View.VISIBLE);
                        Toast.makeText(mContext, "Email dan Passewor anda invalid", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Toast.makeText(mContext, "Koneksi Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
