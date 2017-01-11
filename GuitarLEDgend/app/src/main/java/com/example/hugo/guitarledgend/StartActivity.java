package com.example.hugo.guitarledgend;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        TextView title = (TextView) findViewById(R.id.app_name);
        Typeface insomnia = Typeface.createFromAsset(getAssets(), "fonts/Android Insomnia Regular.ttf");
        title.setTypeface(insomnia);

    }
}
