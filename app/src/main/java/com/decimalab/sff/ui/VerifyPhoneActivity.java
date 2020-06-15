package com.decimalab.sff.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.decimalab.sff.R;
import com.decimalab.sff.api.ApiEndpoint;
import com.decimalab.sff.api.RetrofitInstance;
import com.decimalab.sff.response.BaseResponse;
import com.decimalab.sff.util.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class VerifyPhoneActivity extends AppCompatActivity {

    int resendCounter;
    ProgressBar progressBar;
    TextInputEditText editText;
    AppCompatButton buttonSignIn;
    AppCompatButton buttonResend;
    private String verificationId;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    private String token;
    Retrofit retrofit;
    ApiEndpoint API;

    SharedPreferences loginpreferrence;
    private SharedPreferences.Editor editor;

    private String phoneNumber;
    private boolean isUserExist;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
            mResendToken = forceResendingToken;
            Toast.makeText(VerifyPhoneActivity.this, "Verification code sent!", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            String code = phoneAuthCredential.getSmsCode();

            boolean phoneNumberExist = false;

            if (code != null) {
                //verifying the code like in normal flow
                editText.setText(code);
                verifyCode(code);
                Toast.makeText(VerifyPhoneActivity.this, "Verification success!", Toast.LENGTH_LONG).show();
            } else {
                // you dont get any code, it is instant verification
                Toast.makeText(VerifyPhoneActivity.this, "Doing Instant Verification without sending code!", Toast.LENGTH_LONG).show();
                signInWithCredential(phoneAuthCredential);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifyPhoneActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressbar);
        editText = findViewById(R.id.editTextCode);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonResend = findViewById(R.id.buttonResend);

        retrofit = RetrofitInstance.getRetrofitInstance();
        API = retrofit.create(ApiEndpoint.class);

        loginpreferrence = getApplicationContext().getSharedPreferences(Constants.LOGIN_SHARED_PREFERENCE, MODE_PRIVATE);
        editor = loginpreferrence.edit();
        token = loginpreferrence.getString("token", "");

        phoneNumber = getIntent().getStringExtra("phoneNumber");
        sendVerificationCode(phoneNumber);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = editText.getText().toString().trim();

                if (code.isEmpty() || code.length() < 6) {

                    editText.setError("Enter code...");
                    editText.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });

        buttonResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resendVerificationCode(phoneNumber, mResendToken);

            }
        });

    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(final PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            getTokenByPhone(phoneNumber);


                        } else {
                            Toast.makeText(VerifyPhoneActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void getTokenByPhone(String phoneNo) {
        API.loginUser(phoneNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        Log.e("response", baseResponse.toString());

                        if (baseResponse.getCode() == 200) {
                            String phone = baseResponse.getData().getPhone();
                            token = baseResponse.getToken();
                            isUserExist = baseResponse.getExits();

                            Log.e("VerifyPhoneActivity", String.valueOf(isUserExist));

                            editor.putString("phone", phone);
                            editor.putString("token", token);
                            Constants.isUserExist = baseResponse.getExits();
                            editor.apply();

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(VerifyPhoneActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("VerifyPhoneActivity", e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                        Intent intent = new Intent(VerifyPhoneActivity.this, DashboardActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
    }

    private void sendVerificationCode(String number) {
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );

        progressBar.setVisibility(View.GONE);

        startCountDownTimer();
    }

    private void startCountDownTimer() {

        buttonResend.setEnabled(false);

        //reset counter value on each call
        resendCounter = 60;

        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                buttonResend.setText("Resend OTP in " + String.valueOf(resendCounter) + " Sec");
                resendCounter--;
            }

            public void onFinish() {
                buttonResend.setText("Resend OTP");
                buttonResend.setEnabled(true);
            }
        }.start();
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallBack,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks

        startCountDownTimer();

    }


}
