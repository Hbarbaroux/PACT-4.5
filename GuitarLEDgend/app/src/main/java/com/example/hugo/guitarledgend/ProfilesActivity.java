package com.example.hugo.guitarledgend;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ProfilesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);

        Button next_button = (Button) findViewById(R.id.next_button);

        TextView comment = (TextView) findViewById(R.id.comment_profile);
        Typeface century = Typeface.createFromAsset(getAssets(), "fonts/Century Gothic.ttf");
        comment.setTypeface(century);
        comment.setText("/* Page des profils non encore implémentée */ ");

        TextView next_text_view = (TextView) findViewById(R.id.next_button);
        Typeface century_bold = Typeface.createFromAsset(getAssets(), "fonts/Century Gothic Bold.ttf");
        next_text_view.setTypeface(century_bold);
    }
}
