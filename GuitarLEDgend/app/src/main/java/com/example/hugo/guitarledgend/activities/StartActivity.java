package com.example.hugo.guitarledgend.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.activities.profiles.ProfilesActivity;

import java.io.File;

public class StartActivity extends AppCompatActivity {
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }

    // Requesting permission to RECORD_AUDIO
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 1;
    private static String [] PERMISSIONS_AUDIO = {Manifest.permission.RECORD_AUDIO};

    public static void verifyAudioPermissions(Activity activity){

        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO);

        if (permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, PERMISSIONS_AUDIO, REQUEST_RECORD_AUDIO_PERMISSION);
        }



    }

    /*
    private boolean permissionToRecordAccepted = false;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();

    }
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        verifyStoragePermissions(this);
        verifyAudioPermissions(this);

        Button start_button = (Button) findViewById(R.id.start_button);
        start_button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, ProfilesActivity.class);
                startActivity(intent);
            }
        });

        TextView title = (TextView) findViewById(R.id.app_name);
        Typeface insomnia = Typeface.createFromAsset(getAssets(), "fonts/Android Insomnia Regular.ttf");
        title.setTypeface(insomnia);

        TextView start_text_view = (TextView) findViewById(R.id.start_button);
        Typeface century = Typeface.createFromAsset(getAssets(), "fonts/Century Gothic Bold.ttf");
        start_text_view.setTypeface(century);

        File sdcard = Environment.getExternalStorageDirectory();

        File wallpaperDirectory = new File(sdcard,"GuitarLEDgend");
        if (!wallpaperDirectory.exists()){
            wallpaperDirectory.mkdirs();
        }
    }
}
