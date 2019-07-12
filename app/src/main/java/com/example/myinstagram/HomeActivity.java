package com.example.myinstagram;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.myinstagram.Fragments.ComposeFragment;
import com.example.myinstagram.Fragments.FeedFragment;
import com.example.myinstagram.Fragments.ProfileFragment;


public class HomeActivity extends AppCompatActivity {



    //initialize Navigation Bar variable
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;


    // image path will be deleted later
    private static String imagePath = "https://pbs.twimg.com/profile_images/1111508562000400385/D1LwKGUB_400x400.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Find the toolbar view inside the activity layout
        toolbar = findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

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
                        // Toast.makeText(HomeActivity.this, "Compose!", Toast.LENGTH_LONG).show();
                         break;
                     case R.id.action_profile :
                         fragment = new ProfileFragment();
                        // Toast.makeText(HomeActivity.this, "Profile !", Toast.LENGTH_LONG).show();
                         break;
                     case R.id.action_home:
                         fragment = new FeedFragment();
                        // Toast.makeText(HomeActivity.this, "Home!", Toast.LENGTH_LONG).show();
                         break;
                 }
                 fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                 return true;
            }
        });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar_navigation, menu);
        return true;
    }
}
