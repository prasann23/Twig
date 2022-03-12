package com.example.twig;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Post {

    public String uid;
    public String imageUuid;
    public String creatorName;
    public int likesCount;

    public Post() {
        // Needed for Firebase
    }

    public Post(String uid, String imageUuid, String creatorName, int likesCount) {
        this.uid = uid;
        this.imageUuid = imageUuid;
        this.creatorName = creatorName;
        this.likesCount = likesCount;
    }
}
