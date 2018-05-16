package com.example.alberto.golfroundtracker;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class RecordRound extends AppCompatActivity implements View.OnClickListener {

    private int holeNumber = 1;
    private int totalScore = 0;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    private Button endBtn, confirmBtn, declineBtn;
    private ImageButton recordBtn;
    private TextView speechTxt, infoTxt, holeNumberTxt, scoreTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_round);

        speechTxt = (TextView) findViewById(R.id.txtSpeechInput);
        infoTxt = (TextView) findViewById(R.id.info_txt);
        holeNumberTxt = (TextView) findViewById(R.id.holeNumberTxt);
        scoreTxt = (TextView) findViewById(R.id.score_txt);
        recordBtn = (ImageButton) findViewById(R.id.btnSpeak);
        confirmBtn = (Button) findViewById(R.id.confirm_button);
        declineBtn = (Button) findViewById(R.id.decline_button);
        endBtn = (Button) findViewById(R.id.end_round_button);

        holeNumberTxt.setText(Integer.toString(holeNumber));

        declineBtn.setVisibility(View.GONE);
        confirmBtn.setVisibility(View.GONE);

        declineBtn.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
        recordBtn.setOnClickListener(this);
        endBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnSpeak && view.getVisibility() == View.VISIBLE){
            promptSpeechInput();
        }
        else if(view.getId() == R.id.end_round_button && confirmBtn.getVisibility() == View.GONE){
            updateDatabase();
            startActivity(new Intent(RecordRound.this, ViewRounds.class));
            finish();
        }
        else if(view.getId() == R.id.confirm_button && view.getVisibility() == View.VISIBLE){
            updateScore();
            showRecordButton();
        }
        else if(view.getId() == R.id.decline_button && view.getVisibility() == View.VISIBLE){
            showRecordButton();
        }
    }

    private void promptSpeechInput(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Say something...");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support speech input",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && data != null) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    speechTxt.setText(result.get(0));
                    hideRecordButton();
                }
                break;
            }

        }
    }

    private void hideRecordButton(){
        confirmBtn.setVisibility(View.VISIBLE);
        declineBtn.setVisibility(View.VISIBLE);
        recordBtn.setVisibility(View.GONE);
        infoTxt.setVisibility(View.GONE);
    }

    private void showRecordButton(){
        confirmBtn.setVisibility(View.GONE);
        declineBtn.setVisibility(View.GONE);
        recordBtn.setVisibility(View.VISIBLE);
        infoTxt.setVisibility(View.VISIBLE);
    }

    private void updateDatabase(){
        if(!(totalScore == 0 && holeNumber == 1)) {
            ((MyApplication) this.getApplication()).setRoundData("Played: " + Integer.toString(holeNumber) + ", Score: " + Integer.toString(totalScore));
        }
    }

    private void updateScore(){
        totalScore += Integer.parseInt(speechTxt.getText().toString());
        holeNumber++;

        scoreTxt.setText(Integer.toString(totalScore));
        holeNumberTxt.setText(Integer.toString(holeNumber));
    }
}
