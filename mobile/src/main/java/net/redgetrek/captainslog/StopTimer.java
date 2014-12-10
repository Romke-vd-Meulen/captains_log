package net.redgetrek.captainslog;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;

import java.util.Date;

public class StopTimer extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_timer);
    }

    @Override
    protected void onResume() {
        super.onResume();

        TimeStoreEntry latest = getTimeStore().getLatestEntry();
        Chronometer chronometer = (Chronometer) findViewById(R.id.stopped_chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime() - (new Date().getTime() - latest.getStarted().getTime()));
    }

    public void storeDescriptionAndNewEntry(View view) {
        storeDescription();

        // Start new entry
        getTimeStore().storeEntry( new TimeStoreEntry(new Date(),null,"") );

        Intent intent = new Intent(this, StartTimer.class);
        startActivity(intent);
    }

    public void storeDescriptionAndFinish(View view) {
        storeDescription();

        Intent intent = new Intent(this, FinishedActivity.class);
        startActivity(intent);
    }

    private void storeDescription() {
        TimeStoreEntry e = getTimeStore().getLatestEntry();
        e.setDescription(((EditText) findViewById(R.id.description)).getText().toString());
        getTimeStore().storeEntry(e);
    }

}
