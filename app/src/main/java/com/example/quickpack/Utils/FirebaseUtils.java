package com.example.quickpack.Utils;

import android.app.Activity;
import android.content.Intent;

import com.example.quickpack.Login;
import com.example.quickpack.Models.UserItems;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtils {
    public static final String USER_ITEMS = "user_items";
    public static final String USER_CALENDAR = "user_calendar";
    public static final int MIN_PASSWORD_LENGTH = 6;

    // returns true if user is signed in, else returns false
    public static boolean checkIfSignedIn(Activity activity) {
        if(FirebaseAuth.getInstance().getCurrentUser() == null) { // signed out
            activity.startActivity(new Intent(activity, Login.class));
            activity.finish();
            return false;
        } else {
            return true;
        }
    }

    public static void updateUserItems(UserItems userItems, OnCompleteListener<Void> onCompleteListener) {
        FirebaseDatabase.getInstance().getReference()
                .child(FirebaseUtils.USER_ITEMS)
                .child(FirebaseAuth.getInstance().getUid())
                .setValue(userItems)
                .addOnCompleteListener(onCompleteListener);
    }
}
