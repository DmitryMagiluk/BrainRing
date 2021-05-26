package com.example.brainring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ClientConnectionSettingsActivity extends AppCompatActivity {

    private boolean isConnected = true;
    ListView serverListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_connection_settings);

        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        filListView();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void testSound(View view) {
        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.false_start);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            };
        });
    }

    public void startGame(View view) {
        if (isConnected){
            Intent intent = new Intent(this, GameClientActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Подключитесь к ведущему игры !", Toast.LENGTH_LONG).show();
        }
    }

    void filListView(){
        serverListView = findViewById(R.id.serverListView);

        final String[] catNames = new String[] {
                "Рыжик", "Барсик", "Мурзик", "Мурка", "Васька",
                "Томасина", "Кристина", "Пушок", "Дымка", "Кузя",
                "Китти", "Масяня", "Симба"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, catNames);

        serverListView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
