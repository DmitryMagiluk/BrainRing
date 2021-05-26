package com.example.brainring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class GameHostActivity extends AppCompatActivity {

    final static int MILLIS = 20999;
    int millisLeft;
    boolean isTimerStart = false;
    boolean isTeamTimerPause = false;
    ImageButton playPauseImageButton;
    TextView timerTextView;
    CountDownTimer countDownTimer;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_host);
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        getSupportActionBar().setTitle("Ведущий");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        playPauseImageButton = findViewById(R.id.playPauseImageButton);
        timerTextView = findViewById(R.id.timerTextView);

        updateTimerTextView(MILLIS);
    }

    public void startTimerButton(View view) {
        if (!isTimerStart && !isTeamTimerPause){
            // Запускаем обратный отсчет
            // Меняем иконку кнопки на паузу
            timerStart(MILLIS);
        } else if (isTimerStart && isTeamTimerPause) {
            timerStart(millisLeft);
            isTeamTimerPause = false;
        }
    }

    void timerStart(int millis){
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.timer_start);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            };
        });
        countDownTimer = new CountDownTimer(millis, 10) {
            @Override
            public void onTick(long l) {
                updateTimerTextView((int)l);
            }

            @Override
            public void onFinish() {
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.timer_finish);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    };
                });
                resetTimer();
            }
        }.start();
        playPauseImageButton.setImageResource(R.drawable.ic_baseline_pause_circle_outline_200);
        isTimerStart = true;
    }

    void resetTimer(){
        countDownTimer.cancel();
        updateTimerTextView(MILLIS);
        playPauseImageButton.setImageResource(R.drawable.ic_baseline_play_circle_outline_200);
        isTimerStart = false;
        isTeamTimerPause = false;
    }

    public void resetTimerButton(View view) {
        // Сбрасываем таймер (устанавливаем 20 секунд)
        // Сбрасываем указатели нажатия кнопок команд
        if (isTeamTimerPause || isTimerStart){
            resetTimer();
        }
    }

    void updateTimerTextView(final int MILLIS){
        millisLeft = MILLIS;
        int seconds = MILLIS/1000;
        int millis = (MILLIS - seconds*1000)/10;

        String secondsString = "";
        String millisString = "";

        if (seconds < 10){
            secondsString = "0" + seconds;
        } else {
            secondsString = seconds + "";
        }

        if (millis < 10){
            millisString = "0" + millis;
        } else {
            millisString = millis + "";
        }

        timerTextView.setText(secondsString + ":" + millisString);
    }

    public void teamPressed(View view) {
        if (isTimerStart && !isTeamTimerPause){
            // Останавливаем таймер
            countDownTimer.cancel();
            // Меняем иконку кнопки на старт
            playPauseImageButton.setImageResource(R.drawable.ic_baseline_play_circle_outline_200);
            isTeamTimerPause = true;
        } else {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.false_start);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                };
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            quitTheGameDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        quitTheGameDialog();
    }

    void quitTheGameDialog (){
        new AlertDialog.Builder(this)
                .setMessage("Вы действительно хотите покинуть игру?")
                .setNegativeButton("Отмена", null)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        GameHostActivity.super.onBackPressed();
                    }
                }).create().show();
    }
}