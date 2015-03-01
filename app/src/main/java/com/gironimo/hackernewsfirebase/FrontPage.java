package com.gironimo.hackernewsfirebase;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.Firebase;


public class FrontPage extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

        Firebase.setAndroidContext(this);
        //Firebase reference to the data store
        //Note that data is not retrieved until we tell Firebase to read the data.
        Firebase rootRef = new Firebase("https://docs-examples.firebaseio.com/web/data");

        //TODO: Query top 25 posts from HackerNews

        //TODO: Create a UI to display the top 25 HN Posts

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_front_page, menu);
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
