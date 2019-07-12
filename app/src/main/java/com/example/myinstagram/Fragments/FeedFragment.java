package com.example.myinstagram.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myinstagram.FeedAdapter;
import com.example.myinstagram.R;
import com.example.myinstagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;


public class FeedFragment extends Fragment {

    //initialize recycler view var and adapter var
    public RecyclerView rvFeed;
    public FeedAdapter feedAdapter;

    //initialize list of posts variable
    public List<Post> mPosts;

    //initialize swipe to refresh container
    SwipeRefreshLayout swipeContainer;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //initialize array list
        mPosts = new ArrayList<>();
        // reference recycler view in layout
        rvFeed = view.findViewById(R.id.rvFeed);
        // create adapter
        feedAdapter = new FeedAdapter(getContext(), mPosts);
        // set adapter on recycler view
        rvFeed.setAdapter(feedAdapter);
        //set layout manager on recycler view
        rvFeed.setLayoutManager(new LinearLayoutManager(getContext()));
        // crate data source ie. list of posts

        // Lookup the swipe container view
        swipeContainer = view.findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPosts.clear();
                loadTopPosts();
                swipeContainer.setRefreshing(false);
            }
        });


        loadTopPosts();
    }

    private void loadTopPosts() {
        // creating new post query class
        final Post.Query postsQuery = new Post.Query();

        //ParseQuery<Post> postQuery = new ParseQuery<>(Post.class);
        //postQuery.include(Post.KEY_USER)
        // get top 20 posts with user info
        postsQuery.getTop().withUser();




        // registers sub class
        ParseObject.registerSubclass(Post.class);


        // make query to grab current posts
        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {
                    // occupy Recycler View w posts...
                    mPosts.addAll(objects);
                    feedAdapter.notifyDataSetChanged();

                    for (int i = 0; i < objects.size(); i++) {
                        Log.d("FeedFragment", "Post[" + i + "] = " + objects.get(i).getDescription()
                                + " username =" + objects.get(i).getUser().getUsername());

                    }
                } else {
                    Log.d("FeedFragment", "Querying of Posts failed");
                    e.printStackTrace();
                }
            }
        });

    }

}
