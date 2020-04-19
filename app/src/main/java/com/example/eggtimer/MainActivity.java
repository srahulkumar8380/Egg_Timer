package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.CountedCompleter;

public class MainActivity extends AppCompatActivity {

    Button controlBtn;
    SeekBar timerSeekBar;
    TextView timerTextView;
    Boolean counterIsActive=false;
    CountDownTimer countDownTimer;
    long milliLeft;

    public void resetTimer(){
        timerTextView.setText("00:30");
        timerSeekBar.setProgress(30);
        countDownTimer.cancel();
        controlBtn.setText("Go");
        timerSeekBar.setEnabled(true);
        counterIsActive=false;
    }

    public void timerPause() {
        countDownTimer.cancel();
    }

    private void timerResume() {
        timerStart(milliLeft);
    }

    public void timerStart(long timeLengthMilli){

            countDownTimer = new CountDownTimer(timeLengthMilli, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    milliLeft= millisUntilFinished;
                    updateTimer((int)millisUntilFinished/1000);
                }
                @Override
                public void onFinish() {
                    timerTextView.setText("00:00");
                    MediaPlayer mediaPlayer=MediaPlayer.create(MainActivity.this,R.raw.sound_timer);
                    mediaPlayer.start();
                    resetTimer();
                }
            };
            countDownTimer.start();
    }




    public void Reset(View view) {
        resetTimer();
    }




    public void updateTimer(int secondLeft){
        int min=(int)secondLeft/60;
        int sec=secondLeft-min*60;
        String strMin=Integer.toString(min);
        if(min<10){
            strMin="0"+Integer.toString(min);
        }

        String secString=Integer.toString(sec);
        if(sec<10){
            secString="0"+Integer.toString(sec);
        }
        timerTextView.setText(strMin+":"+secString);

    }

    public void controlTimer(View view) {
        timerSeekBar.setEnabled(false);
        if(controlBtn.getText().equals("Go")){
          //  Log.i("Started", controlBtn.getText().toString());
            controlBtn.setText("Pause");
            timerStart(timerSeekBar.getProgress() * 1000);
        } else if (controlBtn.getText().equals("Pause")){
           // Log.i("Paused", controlBtn.getText().toString());
            controlBtn.setText("Resume");
            timerPause();
        } else if (controlBtn.getText().equals("Resume")){
            controlBtn.setText("Pause");
            timerResume();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerSeekBar=findViewById(R.id.seekBar);
        timerTextView=findViewById(R.id.timer_textView);
        controlBtn=findViewById(R.id.controlButton);
        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


}
