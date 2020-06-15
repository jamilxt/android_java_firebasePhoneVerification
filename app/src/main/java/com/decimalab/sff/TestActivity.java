package com.decimalab.sff;

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
import com.decimalab.sff.api.ApiEndpoint;
import com.decimalab.sff.api.RetrofitInstance;
import com.decimalab.sff.model.College;
import com.decimalab.sff.model.Division;
import com.decimalab.sff.request.StudentRequest;
import com.decimalab.sff.response.BaseResponse;
import com.decimalab.sff.response.DivisionResponse;
import com.decimalab.sff.response.StudentResponse;
import com.decimalab.sff.util.Constants;
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
                .subscribe(new Observer<StudentResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Toast.makeText(TestActivity.this, "Processing...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(StudentResponse studentResponse) {
                        Log.e("response", studentResponse.toString());
                        jsonRecyclerView.bindJson(gson.toJson(studentResponse));

                        if (studentResponse.getCode() == 200) {
                            et_name.setText(studentResponse.getData().getName());
                            et_phone.setText(studentResponse.getData().getPhone());
                            et_picture.setText(studentResponse.getData().getPicture());
                            et_roll.setText(studentResponse.getData().getRoll());
                            et_reg.setText(studentResponse.getData().getReg());
                            et_board.setText(studentResponse.getData().getBoard());
                            et_passing_year.setText(String.valueOf(studentResponse.getData().getPassingYear()));
                            et_group.setText(String.valueOf(studentResponse.getData().getGroup()));
                            et_quotes.setText(String.valueOf(studentResponse.getData().getQuotes()));
                            et_payment_type.setText(String.valueOf(studentResponse.getData().getPaymentType()));
                            et_txr_id.setText(String.valueOf(studentResponse.getData().getTxrId()));
                            et_amount.setText(String.valueOf(studentResponse.getData().getAmount()));
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

    public void updateLoggedInUserInfo(View view) {

        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setName(et_name.getText().toString());
        studentRequest.setPhone(et_phone.getText().toString());
        studentRequest.setPicture(et_picture.getText().toString());
        studentRequest.setRoll(et_roll.getText().toString());
        studentRequest.setReg(et_reg.getText().toString());
        studentRequest.setBoard(et_board.getText().toString());
        studentRequest.setPassingYear(Long.valueOf(et_passing_year.getText().toString()));
        studentRequest.setGroup(et_group.getText().toString());
        studentRequest.setQuotes(et_quotes.getText().toString());
        studentRequest.setPaymentType(et_payment_type.getText().toString());
        studentRequest.setTxrId(et_txr_id.getText().toString());
        studentRequest.setAmount(et_amount.getText().toString());
        studentRequest.setStudentColleges(selectedCollegeIdList);

        API.updateLoggedInUserInfo("Bearer " + token, studentRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StudentResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Toast.makeText(TestActivity.this, "Processing...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(StudentResponse studentResponse) {
                        Log.e("response", studentResponse.toString());
                        jsonRecyclerView.bindJson(gson.toJson(studentResponse));


                        if (studentResponse.getCode() == 200) {
                            Toast.makeText(TestActivity.this, "Profile Info Updated!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(TestActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(TestActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}