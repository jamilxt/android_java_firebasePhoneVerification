package com.example.firebasephoneverification;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SpinnerListener;
import com.example.firebasephoneverification.api.ApiEndpoint;
import com.example.firebasephoneverification.api.RetrofitInstance;
import com.example.firebasephoneverification.model.College;
import com.example.firebasephoneverification.model.Division;
import com.example.firebasephoneverification.response.BaseResponse;
import com.example.firebasephoneverification.response.DivisionResponse;
import com.example.firebasephoneverification.response.UserResponse;
import com.example.firebasephoneverification.util.Constants;
import com.google.gson.Gson;
import com.yuyh.jsonviewer.library.JsonRecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class TestActivity extends AppCompatActivity {

    private SharedPreferences loginpreferrence;
    private SharedPreferences.Editor editor;
    TextView tv_token;
    EditText et_name;
    EditText et_phone;
    EditText et_picture;
    EditText et_roll;
    EditText et_reg;
    EditText et_board;
    EditText et_passing_year;
    EditText et_group;
    EditText et_quotes;
    Spinner sp_divisions;
    EditText et_college_ids;
    EditText et_payment_type;
    EditText et_txr_id;
    EditText et_amount;

    JsonRecyclerView jsonRecyclerView;

    private String token;

    Retrofit retrofit;
    ApiEndpoint API;

    Gson gson = new Gson();

    List<Long> selectedCollegeIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        init();
    }

    private void init() {
        tv_token = findViewById(R.id.token);

        et_name = findViewById(R.id.name);
        et_phone = findViewById(R.id.phone);
        et_picture = findViewById(R.id.picture);
        et_roll = findViewById(R.id.roll);
        et_reg = findViewById(R.id.reg);
        et_board = findViewById(R.id.board);
        et_passing_year = findViewById(R.id.passing_year);
        et_group = findViewById(R.id.group);
        et_quotes = findViewById(R.id.quotes);
        sp_divisions = findViewById(R.id.divisions);
        et_college_ids = findViewById(R.id.college_ids);
        et_payment_type = findViewById(R.id.payment_type);
        et_txr_id = findViewById(R.id.txr_id);
        et_amount = findViewById(R.id.amount);

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
        API.loginUser("01623573153")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Toast.makeText(TestActivity.this, "Processing...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        Log.e("response", baseResponse.toString());
                        jsonRecyclerView.bindJson(gson.toJson(baseResponse));

                        if (baseResponse.getCode() == 200) {
                            String phone = baseResponse.getData().getPhone();
                            token = baseResponse.getToken();
                            tv_token.setText(token);
                            boolean isUserExist = baseResponse.getExits();

                            editor.putString("phone", phone);
                            editor.putString("token", token);
                            editor.apply();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(TestActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(TestActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getDivisionWithCollegeList(View view) {
        API.getDivisionList("Bearer " + token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DivisionResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Toast.makeText(TestActivity.this, "Processing...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(DivisionResponse divisionResponse) {
                        Log.e("response", divisionResponse.toString());
                        jsonRecyclerView.bindJson(gson.toJson(divisionResponse));

                        if (divisionResponse.getCode() == 200) {
                            MultiSpinnerSearch searchSpinner = findViewById(R.id.colleges);

                            List<College> collegeList = new ArrayList<>();
                            for (Division division :
                                    divisionResponse.getData()) {
                                for (College college :
                                        division.getColleges()) {
                                    collegeList.add(college);
                                }
                            }
                            List<KeyPairBoolData> keyPairBoolDataList = new ArrayList<>();

                            for (int i = 0; i < collegeList.size(); i++) {
                                KeyPairBoolData h = new KeyPairBoolData();
                                h.setId(collegeList.get(i).getId());
                                h.setName(collegeList.get(i).getName());
                                h.setSelected(false); // jamilxt
                                keyPairBoolDataList.add(h);
                            }

                            searchSpinner.setItems(keyPairBoolDataList, -1, new SpinnerListener() {
                                @Override
                                public void onItemsSelected(List<KeyPairBoolData> items) {

                                    selectedCollegeIdList = new ArrayList<>();

                                    for (int i = 0; i < items.size(); i++) {
                                        if (items.get(i).isSelected()) {
                                            Log.i("TAG", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                                            selectedCollegeIdList.add(items.get(i).getId());
                                        }
                                    }


                                    Toast.makeText(TestActivity.this, selectedCollegeIdList.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(TestActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(TestActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    }
                });
    }

//    public void getCollegeListByDivision(View view) {
//        getCollegeListByDivisionId(2);
//    }

//    private void getCollegeListByDivisionId(long division_id) {
//        API.getCollegeListByDivisionId("Bearer " + token, division_id)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<CollegeResponse>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Toast.makeText(TestActivity.this, "Processing...", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onNext(CollegeResponse collegeResponse) {
//                        Log.e("response", collegeResponse.toString());
//                        jsonRecyclerView.bindJson(gson.toJson(collegeResponse));
//
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Toast.makeText(TestActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Toast.makeText(TestActivity.this, "Successful", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

    public void getLoggedInUserInfo(View view) {
        API.getLoggedInUserInfo("Bearer " + token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Toast.makeText(TestActivity.this, "Processing...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(UserResponse userResponse) {
                        Log.e("response", userResponse.toString());
                        jsonRecyclerView.bindJson(gson.toJson(userResponse));

                        if (userResponse.getCode() == 200) {
                            et_name.setText(userResponse.getData().getName());
                            et_phone.setText(userResponse.getData().getPhone());
                            et_picture.setText(userResponse.getData().getPicture());
                            et_roll.setText(userResponse.getData().getRoll());
                            et_reg.setText(userResponse.getData().getReg());
                            et_board.setText(userResponse.getData().getBoard());
                            et_passing_year.setText(String.valueOf(userResponse.getData().getPassingYear()));
                            et_group.setText(String.valueOf(userResponse.getData().getGroup()));
                            et_quotes.setText(String.valueOf(userResponse.getData().getQuotes()));
                            et_payment_type.setText(String.valueOf(userResponse.getData().getPaymentType()));

                            et_txr_id.setText(String.valueOf(userResponse.getData().getTxrId()));
                            et_amount.setText(String.valueOf(userResponse.getData().getAmount()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(TestActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(TestActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}