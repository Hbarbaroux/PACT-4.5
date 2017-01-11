package com.example.hugo.guitarledgend;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

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
    }
}
