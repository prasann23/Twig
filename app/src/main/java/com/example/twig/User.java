package com.example.twig;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class User {

    private static FirebaseUser currentUser = null;
    private static final String TAG = "__User__";
    private static FirebaseAuth fAuthInstance = FirebaseAuth.getInstance();

//    Should be called only once, when the user logs in or register!!
    public static void loginUser(String email, String pass, Context context) {
//        Toast.makeText(context, "Trying to login", Toast.LENGTH_SHORT).show();
        fAuthInstance.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
            Intent intent = new Intent();
            intent.setAction(TwigConstants.LOGIN_RECEIVER);
            if (task.isSuccessful()) {
                currentUser = fAuthInstance.getCurrentUser();
                intent.putExtra("success", true);
            } else {
                intent.putExtra("success", false);
                intent.putExtra("error", task.getException().getMessage());
            }
            context.sendBroadcast(intent);
        });
    }

    public static void registerUser(String email, String pass, String userName, Context context) {
        fAuthInstance.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
            Intent intent = new Intent();
            intent.setAction(TwigConstants.REGISTER_RECEIVER);
            if (task.isSuccessful()) {
                currentUser = fAuthInstance.getCurrentUser();
                updateDisplayName(userName);
                intent.putExtra("success", true);
            } else {
                intent.putExtra("success", false);
                intent.putExtra("error", task.getException().getMessage());
            }
            context.sendBroadcast(intent);
        });
    }

    public static void logout() {
        fAuthInstance.signOut();
        currentUser = null;
    }

    public static boolean isUserLoggedIn() {
        return currentUser != null;
    }

    public static FirebaseUser getCurrentUser() {
        if (currentUser == null)
            currentUser = fAuthInstance.getCurrentUser();
        return currentUser;
    }


    public static void updateDisplayName(String name) {
        UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();
        currentUser.updateProfile(changeRequest).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
//                Toast.makeText(, "", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Updated username successfully");
            }
        });
    }

    public static String getDisplayName() {
        return currentUser.getDisplayName();
    }
    public static String getUserEmail() {
        return currentUser.getEmail();
    }
}
