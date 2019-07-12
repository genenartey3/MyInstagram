package com.example.myinstagram;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myinstagram.model.Post;
import com.parse.ParseFile;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    public FeedAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
       final Post post = posts.get(position);
       holder.bind(post);

        // create on click listener on entire item view
        holder.tvDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // checks to ensure position was selected and not empty space
                if (position != RecyclerView.NO_POSITION) {
                    // creates new post and gets the item at selected position
                   Post selectedPost = post;

                    Intent toDetails = new Intent(context, DetailsActivity.class);
                    toDetails.putExtra("items", selectedPost);
                    // gets user
                    toDetails.putExtra("user", selectedPost.getUser());
                    //toDetails.putExtra("image", Parcels.wrap(ivImagePost));
                    context.startActivity(toDetails);


                }





            }
        });



    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvHandle;
        private ImageView ivImagePost;
        private TextView tvDescription;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // create pointers to layout view holders ...
            tvHandle = itemView.findViewById(R.id.tvUser);
            ivImagePost = itemView.findViewById(R.id.ivImagePost);
            tvDescription = itemView.findViewById(R.id.tvDescription);


        }

        public void bind(Post post) {
            // bind view elements to post
            tvHandle.setText(post.getUser().getUsername());

            ParseFile image = post.getImage();
            if (image != null ) {
                Glide.with(context).load(image.getUrl()).into(ivImagePost);
            }
            tvDescription.setText(post.getDescription());
        }
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }
}
