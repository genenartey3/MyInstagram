package com.example.myinstagram;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myinstagram.Fragments.ComposeFragment;
import com.example.myinstagram.Fragments.FeedFragment;
import com.example.myinstagram.Fragments.ProfileFragment;
import com.example.myinstagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.List;


public class HomeActivity extends AppCompatActivity {



    //initialize Navigation Bar variable
    private BottomNavigationView bottomNavigationView;


    // image path will be deleted later
    private static String imagePath = "https://pbs.twimg.com/profile_images/1111508562000400385/D1LwKGUB_400x400.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //create fragment manager
        final FragmentManager fragmentManager = getSupportFragmentManager();

        //reference to bottom nav bar layout
        bottomNavigationView = findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                 Fragment fragment = null;
                 switch  (menuItem.getItemId()) {
                     case R.id.action_compose :
                         fragment = new ComposeFragment();
                         Toast.makeText(HomeActivity.this, "Compose!", Toast.LENGTH_LONG).show();
                         break;
                     case R.id.action_profile :
                         fragment = new ProfileFragment();
                         Toast.makeText(HomeActivity.this, "Profile !", Toast.LENGTH_LONG).show();
                         break;
                     case R.id.action_home:
                         fragment = new FeedFragment();
                         Toast.makeText(HomeActivity.this, "Home!", Toast.LENGTH_LONG).show();
                         break;
                 }
                 fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                 return true;
            }
        });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }

    private void loadTopPosts() {
        // creating new post query class
        final Post.Query postsQuery = new Post.Query();
        // get top 20 posts with user info
        postsQuery.getTop().withUser();



        // registers sub class
        ParseObject.registerSubclass(Post.class);

        // make query to grab current posts
        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < objects.size(); i++) {
                        Log.d("HomeActivity", "Post[" + i + "] = " + objects.get(i).getDescription()
                                + " username =" + objects.get(i).getUser().getUsername());
                    }
                } else {
                    Log.d("HomeActivity", "Querying of Posts failed");
                    e.printStackTrace();
                }
            }
        });

    }


}
