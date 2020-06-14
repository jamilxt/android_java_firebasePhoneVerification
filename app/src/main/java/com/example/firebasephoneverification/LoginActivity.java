package com.example.firebasephoneverification;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firebasephoneverification.api.ApiEndpoint;
import com.example.firebasephoneverification.api.RetrofitInstance;
import com.example.firebasephoneverification.response.BaseResponse;
import com.example.firebasephoneverification.util.Constants;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences loginpreferrence;
    private SharedPreferences.Editor editor;
    TextView tv_response;
    TextView tv_token;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        tv_response = findViewById(R.id.response);
        tv_token = findViewById(R.id.token);

        loginpreferrence = getApplicationContext().getSharedPreferences(Constants.LOGIN_SHARED_PREFERENCE, MODE_PRIVATE);
        editor = loginpreferrence.edit();

        token = loginpreferrence.getString("token", "");
        tv_token.setText(token);
    }

    private void loginUser() {

        Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();

        Retrofit retrofit = RetrofitInstance.getRetrofitInstance();
        ApiEndpoint API = retrofit.create(ApiEndpoint.class);
        API.loginUser("01623573153")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {


                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            Log.w("++++++++++++", "Code 200 ok");
                            Log.e("response", baseResponse.toString());
                            tv_response.setText(baseResponse.getData().toString());

                            String phone = baseResponse.getData().getPhone();
                            token = baseResponse.getToken();
                            tv_token.setText(token);
                            boolean isUserExist = baseResponse.getExits();

                            editor.putString("phone", phone);
                            editor.putString("token", token);
                            editor.apply();

                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getTokenByPhone(View view) {
        loginUser();
    }
}