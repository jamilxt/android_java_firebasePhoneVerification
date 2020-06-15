package com.decimalab.sff.fragment;

import android.app.Activity;
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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.decimalab.sff.R;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.List;


public class EditInfoFragment extends Fragment {

    private FragmentActivity myContext;

    Image admitImage;
    ImageView images;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_edit_info, container, false);

        images = view.findViewById(R.id.image);

        (view.findViewById(R.id.et_board)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBoardDialog(v);
            }
        });

        (view.findViewById(R.id.et_pass_year)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPassYearDialog(v);
            }
        });

        (view.findViewById(R.id.et_group)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGroupDialog(v);
            }
        });

        (view.findViewById(R.id.admit_image_upload)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadAdmit();

                Toast.makeText(myContext, "clicked", Toast.LENGTH_SHORT).show();
            }
        });

        (view.findViewById(R.id.fab_info_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FragmentManager manager = myContext.getSupportFragmentManager();

                manager.beginTransaction().replace(R.id.fragment_container, new InfoFragment()).commit();

            }
        });


        return view;


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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
    public void onAttach(Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
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

        //encodedString = Base64.encodeToString(b, Base64.DEFAULT);
        encodedString = Base64.encodeToString(b, Base64.DEFAULT);


        Log.d("image_check", image.getPath());
        Log.d("image_check", encodedString);

        //decode base64 string to image
        byte[] imageBytes;
        imageBytes = Base64.decode(encodedString, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        images.setImageBitmap(decodedImage);


    }
}