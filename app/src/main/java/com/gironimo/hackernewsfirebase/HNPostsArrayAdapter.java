package com.gironimo.hackernewsfirebase;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//ArrayAdapter to place posts into the listview
public class HNPostsArrayAdapter extends ArrayAdapter<HNPost> {

    private Context context;
    private ArrayList<HNPost> topStories;

    public HNPostsArrayAdapter(Context context, int resource, ArrayList<HNPost> topStories) {
        super(context, resource, topStories);
        this.context = context;
        this.topStories = topStories;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_layout, parent, false);
        TextView titleTextView = (TextView) row.findViewById(R.id.titleTextView);
        TextView authorTextView = (TextView) row.findViewById(R.id.authorTextView);
        TextView scoreTextView = (TextView) row.findViewById(R.id.scoreTextView);

        //Set the text for the textViews
        titleTextView.setText(topStories.get(position).getTitle());
        authorTextView.setText(topStories.get(position).getAuthor());
        scoreTextView.setText(String.valueOf(topStories.get(position).getScore()));

        return row;
    }

    public void populateTopStories() {
        final Firebase ref = new Firebase("https://hacker-news.firebaseio.com/v0/");
        final Map<Long, ValueEventListener> valueEventListenerMap = new HashMap<Long, ValueEventListener>();

        ref.child("topstories").limitToFirst(25).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!valueEventListenerMap.isEmpty()) {
                    for (Long id : valueEventListenerMap.keySet()) {
                        ref.child("item").child(String.valueOf(id)).removeEventListener(valueEventListenerMap.get(id));
                    }
                    valueEventListenerMap.clear();
                }

                ArrayList<Long> postIds = (ArrayList<Long>)snapshot.getValue();

                for(int i = 0; i < 25; i++) {
                    Long id = postIds.get(i);
                    ValueEventListener articleListener = ref.child("item").child(String.valueOf(id)).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            Map<String, Object> itemMap = (Map<String, Object>) snapshot.getValue();

                            if(itemMap != null)
                            {
                                HNPost post = new HNPost();
                                if(itemMap.get("by")!= null)
                                    post.setAuthor((String) itemMap.get("by"));
                                if(itemMap.get("id")!= null)
                                    post.setId((Long) itemMap.get("id"));
                                if(itemMap.get("score")!= null)
                                    post.setScore((Long) itemMap.get("score"));
                                if(itemMap.get("text")!= null)
                                    post.setText((String) itemMap.get("text"));
                                if(itemMap.get("title") != null)
                                    post.setTitle((String) itemMap.get("title"));
                                if(itemMap.get("type")!= null)
                                    post.setType((String) itemMap.get("type"));
                                if(itemMap.get("url")!= null)
                                    post.setUrl((String) itemMap.get("url"));

                                topStories.add(post);
                                notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                        }
                    });

                    valueEventListenerMap.put(id, articleListener);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

    }

    public ArrayList<HNPost> getTopStories() {
        return topStories;
    }
}
