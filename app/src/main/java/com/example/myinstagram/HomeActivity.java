package com.example.myinstagram;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myinstagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;


public class HomeActivity extends AppCompatActivity {

    // initialize layout variables
    private EditText descriptionInput;
    private Button btnCreate;
    private Button btnRefresh;
    private Button btnGetPic;


    // image path will be deleted later
    private static String imagePath = "https://pbs.twimg.com/profile_images/1111508562000400385/D1LwKGUB_400x400.jpg";


    //variables needed for camera
    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // establish references to layout elements
        descriptionInput = findViewById(R.id.etDescription);
        btnCreate = findViewById(R.id.btnCreate);
        btnRefresh = findViewById(R.id.btnRefresh);
        btnGetPic = findViewById(R.id.btnGetPic);

        // register Post class as subclass
        ParseObject.registerSubclass(Post.class);

        // setting on click listener to fire an intent to open camera
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get description from edit text file, get current user as well
                final String description = descriptionInput.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();

                // uncomment when finished with image capture
                //final File file = new File(imagePath);
                //ParseFile parseFile = new ParseFile(file);

                createPost(description, user);
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadTopPosts();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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

    private void createPost(String description, ParseUser user) {
        // create new Post object
        final Post newPost = new Post();
        Log.d("CreatePost", "YEeerrrrRRR");

        // insert post data into Post object
        newPost.setDescription(description);
        // newPost.setImage(imageFile);
        newPost.setUser(user);

        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null ) {
                     Log.d("HomeActivity", "Create Post Success!!");
                     descriptionInput.setText("");
                } else {
                    Log.d("HomeActivity", "Failed to create post");
                    e.printStackTrace();
                }
            }
        });
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }




    public void onLaunchCamera(View view) {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(HomeActivity.this, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }
}
