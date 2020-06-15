package com.decimalab.sff.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SpinnerListener;
import com.decimalab.sff.R;
import com.decimalab.sff.api.ApiEndpoint;
import com.decimalab.sff.api.RetrofitInstance;
import com.decimalab.sff.model.College;
import com.decimalab.sff.model.Division;
import com.decimalab.sff.model.StudentCollege;
import com.decimalab.sff.request.StudentRequest;
import com.decimalab.sff.response.DivisionResponse;
import com.decimalab.sff.response.StudentResponse;
import com.decimalab.sff.util.Constants;
import com.decimalab.sff.util.SharedPrefUtils;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class EditInfoFragment extends Fragment {

    private FragmentActivity fragmentActivity;

    View view;

    private String token;
    Retrofit retrofit;
    ApiEndpoint API;

    EditText et_name;
    EditText et_phone;
    EditText et_picture;
    EditText et_roll;
    EditText et_reg;
    EditText et_board;
    EditText et_passing_year;
    EditText et_group;
    EditText et_quotes;
    EditText et_college_ids;
    EditText et_payment_type;
    EditText et_txr_id;
    EditText et_amount;

    ImageView iv_admit_card;

    FloatingActionButton fab_save_info;
    LinearLayout ll_uploadAdmit;

    ProgressBar pb_loading_info;
    LinearLayout ll_user_form;

    List<Long> selectedCollegeIdList = new ArrayList<>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_info, container, false);

        init();
        getLoggedInUserInfo();

        et_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBoardDialog(view);
            }
        });

        et_passing_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPassYearDialog(view);
            }
        });

        et_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGroupDialog(view);
            }
        });

        ll_uploadAdmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadAdmit();
            }
        });


        fab_save_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateLoggedInUserInfo();
            }
        });

        return view;
    }

    private void init() {
        token = SharedPrefUtils.getAuthToken(view.getContext());
        retrofit = RetrofitInstance.getRetrofitInstance();
        API = retrofit.create(ApiEndpoint.class);

        et_name = view.findViewById(R.id.name);
        et_phone = view.findViewById(R.id.phone);
        et_picture = view.findViewById(R.id.picture);
        et_roll = view.findViewById(R.id.roll);
        et_reg = view.findViewById(R.id.reg);
        et_board = view.findViewById(R.id.board);
        et_passing_year = view.findViewById(R.id.passing_year);
        et_group = view.findViewById(R.id.group);
        et_quotes = view.findViewById(R.id.quotes);
        et_college_ids = view.findViewById(R.id.college_ids);
        et_payment_type = view.findViewById(R.id.payment_type);
        et_txr_id = view.findViewById(R.id.txr_id);
        et_amount = view.findViewById(R.id.amount);

        iv_admit_card = view.findViewById(R.id.selected_admit_card);

        fab_save_info = view.findViewById(R.id.save_info);
        ll_uploadAdmit = view.findViewById(R.id.admit_image_upload);

        pb_loading_info = view.findViewById(R.id.loadingInfo);
        ll_user_form = view.findViewById(R.id.userForm);
    }

    public void getLoggedInUserInfo() {
        API.getLoggedInUserInfo("Bearer " + token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StudentResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(StudentResponse studentResponse) {
                        Log.e("response", studentResponse.toString());

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

                            for (StudentCollege studentCollege :
                                    studentResponse.getData().getStudentColleges()) {
                                selectedCollegeIdList.add(studentCollege.getCollegeId());
                            }

                            et_payment_type.setText(String.valueOf(studentResponse.getData().getPaymentType()));
                            et_txr_id.setText(String.valueOf(studentResponse.getData().getTxrId()));
                            et_amount.setText(String.valueOf(studentResponse.getData().getAmount()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("EditInfoFragment", e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        getDivisionWithCollegeList();
                        if (et_picture.getText() != null) {
                            String imageUrl = Constants.BASE_URL_PICASSO + et_picture.getText().toString();
//                            Toast.makeText(getContext(), imageUrl, Toast.LENGTH_LONG).show();
                            Picasso.get().load(imageUrl).into(iv_admit_card);
                        }

                    }
                });
    }

    public void getDivisionWithCollegeList() {
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

                        if (divisionResponse.getCode() == 200) {
                            MultiSpinnerSearch searchSpinner = view.findViewById(R.id.colleges);

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

                                Long collegeId = collegeList.get(i).getId();
                                String collegeName = collegeList.get(i).getName();

                                h.setId(collegeId);
                                h.setName(collegeName);

                                for (long selectedCollegeId :
                                        selectedCollegeIdList) {
                                    if (selectedCollegeId == collegeId) {
                                        h.setSelected(true);
                                    }
                                }

                                keyPairBoolDataList.add(h);
                            }

                            searchSpinner.setItems(keyPairBoolDataList, -1, new SpinnerListener() {
                                @Override
                                public void onItemsSelected(List<KeyPairBoolData> items) {

                                    selectedCollegeIdList.clear();

                                    for (int i = 0; i < items.size(); i++) {
                                        if (items.get(i).isSelected()) {
                                            Log.i("TAG", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                                            selectedCollegeIdList.add(items.get(i).getId());
                                        }
                                    }


//                                    Toast.makeText(getContext(), selectedCollegeIdList.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("EditInfoFragment", e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        pb_loading_info.setVisibility(View.GONE);
                        ll_user_form.setVisibility(View.VISIBLE);
                    }
                });
    }

    public void updateLoggedInUserInfo() {

        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setName(et_name.getText().toString());
        studentRequest.setPhone(et_phone.getText().toString());
        studentRequest.setPicture(et_picture.getText().toString());
        studentRequest.setRoll(et_roll.getText().toString());
        studentRequest.setReg(et_reg.getText().toString());
        studentRequest.setBoard(et_board.getText().toString());
        studentRequest.setPassingYear(Long.valueOf(et_passing_year.getText().toString()));
//        long passing_year = 2019;
//        studentRequest.setPassingYear(passing_year);
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
                        Toast.makeText(getContext(), "Updating...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(StudentResponse studentResponse) {
                        Log.e("response", studentResponse.toString());


                        if (studentResponse.getCode() == 200) {
                            Toast.makeText(getContext(), "Profile Info Updated!", Toast.LENGTH_SHORT).show();

                            // Goto InfoFragment
                            FragmentManager manager = fragmentActivity.getSupportFragmentManager();
                            manager.beginTransaction().replace(R.id.fragment_container, new InfoFragment()).commit();

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("EditInfoFragment", e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void uploadAdmit() {

        ImagePicker imagePicker = null;

        imagePicker.create(this)
                .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
                .folderMode(true) // folder mode (false by default)
                .toolbarFolderTitle("Folder") // folder selection title
                .toolbarImageTitle("Tap to select") // image selection title
                .toolbarArrowColor(Color.BLACK) // Toolbar 'up' arrow color
                .includeVideo(false) // Show video on image picker
                .single() // single mode
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                .start(); // start image picker activity with request code


    }


    private void showBoardDialog(final View v) {
        final String[] array = new String[]{
                "Barisal", "Chittagong", "Comilla", "Dhaka", "Jessore", "Mymensingh", "Rajshahi", "Sylhet", "Dinajpur", "Technical Education Board", "Madrasah Education Board"
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Board");
        builder.setSingleChoiceItems(array, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((EditText) v).setText(array[i]);
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void showPassYearDialog(final View v) {
        final String[] array = new String[]{
                "2025", "2024", "2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015"
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Pass Year");
        builder.setSingleChoiceItems(array, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((EditText) v).setText(array[i]);
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void showGroupDialog(final View v) {
        final String[] array = new String[]{
                "Science", "Bussiness Studies", "Humanities",
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Group");
        builder.setSingleChoiceItems(array, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((EditText) v).setText(array[i]);
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            // Get a list of picked images
            List<Image> images = ImagePicker.getImages(data);
            // or get a single image only
            Image image = ImagePicker.getFirstImageOrNull(data);

            //Log.d("image_check", image.getPath());

            try {
                convertToBase64(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void convertToBase64(Image image) throws FileNotFoundException {
        if (image == null) return;

        Bitmap bm = BitmapFactory.decodeFile(image.getPath());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
        byte[] b = baos.toByteArray();

        String encodedString;

        encodedString = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("image_check", image.getPath());
        Log.d("image_check", encodedString);

        //decode base64 string to image
        byte[] imageBytes;
        imageBytes = Base64.decode(encodedString, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        iv_admit_card.setImageBitmap(decodedImage);

        encodedString = "data:image/jpeg;base64," + encodedString;
        et_picture.setText(encodedString);
    }
}