package com.example.brainring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class GameClientActivity extends AppCompatActivity {

    ImageButton answerImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_client);
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        answerImageButton = findViewById(R.id.answerImageButton);

        getSupportActionBar().setTitle("Команда");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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

    public void clientAnswer(View view) {
        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.client_timer_stop);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            };
        });
        answerImageButton.animate().scaleX(0).scaleY(0).setDuration(200);
    }

    void quitTheGameDialog (){
        new AlertDialog.Builder(this)
                .setMessage("Вы действительно хотите покинуть игру?")
                .setNegativeButton("Отмена", null)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        GameClientActivity.super.onBackPressed();
                    }
                }).create().show();

    }
}