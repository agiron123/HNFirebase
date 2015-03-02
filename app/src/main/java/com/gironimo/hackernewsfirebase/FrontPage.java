package com.gironimo.hackernewsfirebase;

import android.app.ListActivity;
import android.os.Bundle;
import com.firebase.client.Firebase;
import java.util.ArrayList;

public class FrontPage extends ListActivity {

    final String TAG = "FrontPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topstories_layout);

        Firebase.setAndroidContext(this);

        ArrayList<HNPost> topStories = new ArrayList<>();
        HNPostsArrayAdapter adapter = new HNPostsArrayAdapter(this,R.layout.row_layout,topStories);
        adapter.populateTopStories();
        adapter.addAll(adapter.getTopStories());
        setListAdapter(adapter);
    }

}
