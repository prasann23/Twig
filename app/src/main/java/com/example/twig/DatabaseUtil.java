package com.example.twig;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DatabaseUtil {

    private static DatabaseReference databaseRef;
    public static final String POSTS_DATABASE = "posts";

//    Should be called only once, by the MainActivity
    public static void init() {
        if (databaseRef == null)
            databaseRef = FirebaseDatabase.getInstance().getReference();
//        databaseRef.addValueEventListener()
    }

    public static void addNewPost(String postUuid, String uid, String imageUUID, String creatorName, int likes) {
        Post newPost = new Post(uid, imageUUID, creatorName, likes);

        databaseRef.child(POSTS_DATABASE).child(postUuid).setValue(newPost).addOnCompleteListener(task -> {
            if (task.isSuccessful())
                Log.d("yyaasshh", "post uploaded to rtdb");
            if (task.isCanceled())
                Log.d("yyaasshh", "post upload failed to rtdb");

        });
    }

    public static String getKey() {
        return databaseRef.child(POSTS_DATABASE).push().getKey();
    }

    public static void getAllPost(Context context, HomeFragment.loadPostsCallback callback) {
        ArrayList<Post> posts= new ArrayList<>();

        databaseRef.child(POSTS_DATABASE).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                task.getResult().getChildren().forEach(action -> {
                    posts.add(action.getValue(Post.class));
                });

                callback.loadPosts(posts);
            }
        });
    }
}
