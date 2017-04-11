package com.example.hugo.guitarledgend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hugo.guitarledgend.R;

public class ChooseSpeedActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_speed);


        Intent intent =getIntent();
        final long partition_id=intent.getLongExtra("partition_id",1L);

        final int X1=intent.getIntExtra("X1",0);
        final int X2=intent.getIntExtra("X2",0);
        final int replay=intent.getIntExtra("replay",0);

        final EditText etSpeed = (EditText) findViewById(R.id.speed_text);

        Button ok = (Button) findViewById(R.id.ok_button_ChooseSpeedActivity);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String vitesse = etSpeed.getText().toString();

                if(TextUtils.isEmpty(vitesse)) {
                    etSpeed.setError("Veuillez rentrer une vitesse");
                }
                else if(!TextUtils.isDigitsOnly(vitesse)){
                    etSpeed.setError("Veuillez rentrer une vitesse coherente");
                }
                else if(Integer.parseInt(vitesse) <= 0){
                    etSpeed.setError("Veuillez rentrer une vitesse positive");

                }
                else{
                    Intent intent = new Intent(ChooseSpeedActivity.this, PartitionPlayingActivity.class);
                    intent.putExtra("partition_id", partition_id);
                    intent.putExtra("vitesse", Integer.parseInt(vitesse));
                    if (replay==1){
                        intent.putExtra("X1", X1);
                        intent.putExtra("X2", X2);
                        intent.putExtra("replay",1);

                    }
                    startActivity(intent);
                    finish();
                }

            }
        });


    }
}
