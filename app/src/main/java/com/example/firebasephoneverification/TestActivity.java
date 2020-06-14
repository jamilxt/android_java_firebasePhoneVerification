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
import com.example.firebasephoneverification.response.CollegeResponse;
import com.example.firebasephoneverification.response.DivisionResponse;
import com.example.firebasephoneverification.util.Constants;
import com.google.gson.Gson;
import com.yuyh.jsonviewer.library.JsonRecyclerView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class TestActivity extends AppCompatActivity {

    private SharedPreferences loginpreferrence;
    private SharedPreferences.Editor editor;
    TextView tv_response;
    TextView tv_token;
    JsonRecyclerView jsonRecyclerView;

    private String token;

    Retrofit retrofit;
    ApiEndpoint API;

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        init();
    }

    private void init() {
        tv_token = findViewById(R.id.token);
        jsonRecyclerView = findViewById(R.id.rv_json);
        jsonRecyclerView.setTextSize(16);


        loginpreferrence = getApplicationContext().getSharedPreferences(Constants.LOGIN_SHARED_PREFERENCE, MODE_PRIVATE);
        editor = loginpreferrence.edit();

        token = loginpreferrence.getString("token", "");
        tv_token.setText(token);

        retrofit = RetrofitInstance.getRetrofitInstance();
        API = retrofit.create(ApiEndpoint.class);
    }


    public void getTokenByPhone(View view) {
        Toast.makeText(this, "Processing...", Toast.LENGTH_SHORT).show();
        API.loginUser("01623573153")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {


                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        Log.e("response", baseResponse.toString());
                        jsonRecyclerView.bindJson(gson.toJson(baseResponse));

                        String phone = baseResponse.getData().getPhone();
                        token = baseResponse.getToken();
                        tv_token.setText(token);
                        boolean isUserExist = baseResponse.getExits();

                        editor.putString("phone", phone);
                        editor.putString("token", token);
                        editor.apply();

                        Toast.makeText(TestActivity.this, "Successful", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getDivisionList(View view) {
        Toast.makeText(this, "Processing...", Toast.LENGTH_SHORT).show();
        API.getDivisionList("Bearer " + token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DivisionResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DivisionResponse divisionResponse) {
                        Log.e("response", divisionResponse.toString());
                        jsonRecyclerView.bindJson(gson.toJson(divisionResponse));
                        Toast.makeText(TestActivity.this, "Successful", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(TestActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getCollegeListByDivision(View view) {
        Toast.makeText(this, "Processing...", Toast.LENGTH_SHORT).show();
        API.getCollegeListByDivisionId("Bearer " + token, 2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CollegeResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CollegeResponse collegeResponse) {
                        Log.e("response", collegeResponse.toString());
                        jsonRecyclerView.bindJson(gson.toJson(collegeResponse));
                        Toast.makeText(TestActivity.this, "Successful", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}