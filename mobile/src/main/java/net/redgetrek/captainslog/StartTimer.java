package net.redgetrek.captainslog;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import java.util.Date;

public class StartTimer extends ActivityBase {

    public static final String START_DATE = "net.redgetrek.captainslog.START_DATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_timer);
    }

    @Override
    protected void onResume() {
        super.onResume();

        TimeStoreEntry latest = getTimeStore().getLatestEntry();
        Chronometer chronometer = (Chronometer) findViewById(R.id.started_chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime() - (new Date().getTime() - latest.getStarted().getTime()));
        chronometer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ((Chronometer) findViewById(R.id.started_chronometer)).stop();
    }

    /* We're done with this block of time */
    public void stopTimerNormal(View view) {
        TimeStoreEntry e = getTimeStore().getLatestEntry();
        if (e.getStopped() == null) {
            e.setStopped(new Date());
            getTimeStore().storeEntry(e);
        }

        Intent intent = new Intent(this, StopTimer.class);
        startActivity(intent);
    }

}
