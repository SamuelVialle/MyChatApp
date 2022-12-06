package com.samuelvialle.mychatapp.a0_common;

import android.view.View;

import com.google.firebase.firestore.DocumentSnapshot;

public interface MyOnCLickListener {
    void onClick(DocumentSnapshot documentSnapshot, int position, String s);
}

