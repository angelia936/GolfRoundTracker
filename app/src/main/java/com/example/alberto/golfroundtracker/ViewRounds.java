package com.example.alberto.golfroundtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewRounds extends AppCompatActivity {

    private Button backBtn;
    private ListView dataList;
    private ArrayList<String> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rounds);

        backBtn = (Button) findViewById(R.id.back_button);
        dataList = (ListView) findViewById(R.id.round_data_list);

        values = ((MyApplication) this.getApplication()).getRoundData();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
        dataList.setAdapter(adapter);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewRounds.this, MainActivity.class));
                finish();
            }
        });
    }
}
