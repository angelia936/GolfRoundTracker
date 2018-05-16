package com.example.alberto.golfroundtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startBtn, viewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBtn = (Button) findViewById(R.id.start_button);
        viewBtn = (Button) findViewById(R.id.view_button);

        startBtn.setOnClickListener(this);
        viewBtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.start_button){
            startActivity(new Intent(MainActivity.this, RecordRound.class));
            finish();
        }
        else if(view.getId() == R.id.view_button){
            startActivity(new Intent(MainActivity.this, ViewRounds.class));
            finish();
        }
    }
}
