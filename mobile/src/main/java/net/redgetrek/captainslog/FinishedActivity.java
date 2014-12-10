package net.redgetrek.captainslog;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class FinishedActivity extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<String> entry_descriptions = new ArrayList<String>();
        for(TimeStoreEntry e: getTimeStore().getTodayEntries()) {
            entry_descriptions.add(
                new SimpleDateFormat("H:m:s").format(e.getStarted()) + " - " +
                new SimpleDateFormat("H:m:s").format(e.getStopped()) + ": " +
                e.getDescription()
            );
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, entry_descriptions);
        ((ListView) findViewById(R.id.entries)).setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_finished, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
