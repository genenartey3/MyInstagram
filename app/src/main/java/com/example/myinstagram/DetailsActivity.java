package com.example.myinstagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myinstagram.model.Post;
import com.parse.ParseUser;

public class DetailsActivity extends AppCompatActivity {

    //ImageView displayImage = Parcels.unwrap(getIntent().getParcelableExtra("image"));

    public ImageView ivImage;
    public TextView  tvDescription;
    public TextView  tvUsername;
    public TextView  tvTimestamp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ivImage = findViewById(R.id.ivSelectedImage);
        tvDescription = findViewById(R.id.tvDescription);
        tvUsername = findViewById(R.id.tvUsername);
        tvTimestamp = findViewById(R.id.tvTimeStamp);

        Post activePost = getIntent().getParcelableExtra("items");
        ParseUser activeUser = getIntent().getParcelableExtra("user");

        tvDescription.setText(activePost.getDescription());

        tvTimestamp.setText(activePost.getCreatedAt().toString());
        tvUsername.setText(activeUser.getUsername());

        Glide.with(DetailsActivity.this)
                .load(activePost.getImage().getUrl())
                .into(ivImage);


    }




}
