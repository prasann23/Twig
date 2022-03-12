package com.example.twig;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class StorageUtil {

    private static StorageReference storageRef;
    public static final String IMAGES_DB = "images";

    public static void init() {
        if (storageRef == null)
            storageRef = FirebaseStorage.getInstance().getReference();
    }

    public static StorageReference getInstance() {
        return storageRef;
    }

//    public void addImage()
}
