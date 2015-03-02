package com.gironimo.hackernewsfirebase;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import java.util.ArrayList;

public class FrontPage extends ListActivity {

    final String TAG = "FrontPage";
    private ArrayList<HNPost> topStories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topstories_layout);

        Firebase.setAndroidContext(this);

        topStories = new ArrayList<>();
        HNPostsArrayAdapter adapter = new HNPostsArrayAdapter(this,R.layout.row_layout,topStories);
        adapter.populateTopStories();
        topStories = adapter.getTopStories();
        Log.d("TOP STORIES: ", ""+topStories.size());
        adapter.addAll(topStories);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick (ListView l, View v, int position, long id)
    {
        Toast.makeText(this, topStories.get(position).getUrl(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(topStories.get(position).getUrl()));
        startActivity(intent);
    }

}
