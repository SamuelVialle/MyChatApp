package com.samuelvialle.mychatapp.f2_requests;

import static com.samuelvialle.mychatapp.a0_common.Constants.CURRENT_USER;
import static com.samuelvialle.mychatapp.a0_common.Constants.FIRESTORE_INSTANCE;
import static com.samuelvialle.mychatapp.a0_common.Constants.FRIEND_REQUESTS;
import static com.samuelvialle.mychatapp.a0_common.Constants.USERS;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.Query;
import com.samuelvialle.mychatapp.R;
import com.samuelvialle.mychatapp.a1_login.A12_SignUpActivity;
import com.samuelvialle.mychatapp.f3_find_friend.F32_FindFriendModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class F21_RequestFragment extends Fragment {

    private View view;
    private RecyclerView rvRequests;
    private F23_RequestAdapter requestAdapter;
    private List<F32_FindFriendModel> requestModel;
    private TextView tvEmptyRequestList;
    private CollectionReference usersCollectionReference; // La base Users pour récupérer les informations
    private CollectionReference friendRequestCollectionReference; // La base friendRequest
    private FirebaseUser currentUser;
    private String currentUserId;
    private View progressBar;
    private Query queryFriendRequestsCollection;
    private String avatar;
    private List<String> requestFrom;

    public F21_RequestFragment() {
        // Required empty public constructor
    }

    private void initUI() {
        rvRequests = view.findViewById(R.id.rvRequests);
        progressBar = view.findViewById(R.id.progressBar);
        tvEmptyRequestList = view.findViewById(R.id.tvEmptyRequestsList);
        rvRequests.setLayoutManager(new LinearLayoutManager(getContext()));
        requestFrom = new ArrayList<>();

    }

    private void initFB() {
        // Gestion de l'utilisateur courant
        currentUser = CURRENT_USER;
        currentUserId = currentUser.getUid();
        // Initialisation des bases
        usersCollectionReference = FIRESTORE_INSTANCE.collection(USERS);
        friendRequestCollectionReference = FIRESTORE_INSTANCE.collection(FRIEND_REQUESTS);
    }

    private void initUserID(){
        Intent intent = getActivity().getIntent();
        currentUserId = intent.getStringExtra("bundleCurrentUserId");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_f21_request, container, false);
        initUI();
        initUserID();
        initFB();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialisation du textView en cas de liste vide
//        tvEmptyRequestList.setVisibility(View.VISIBLE);

        // Initialisation de la progressBar pour la rendre visible le temps de la recherche dans la base
//        progressBar.setVisibility(View.VISIBLE);

        // Appel de la méthode pour afficher les données des users dans le recyclerView
        getFriendsRequest();
    }

    private void getFriendsRequest() { // On récupère le contenu du tableau dans lequel sont stockés les id des Users ayant fait la demande d'ami
        friendRequestCollectionReference.document(currentUserId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()) {
                                Map<String, Object> map = documentSnapshot.getData();
                                for (Map.Entry<String, Object> entry : map.entrySet()) {
                                    requestFrom.add(entry.getValue().toString());
                                }
                                Log.i("TAG", requestFrom.toString());
                                getFriendsRequestInformation();
                            }
                        }
                    }
                });
    }

    private void getFriendsRequestInformation() { // Récupération des informations des usrs ayant fait la demande pour les afficher dans le recyclerView
        String[] items = requestFrom.get(0).split(",");
        for (String friendWhoRequest : items) {
            Log.i("TAG", "getFriendsRequestInformation: " + friendWhoRequest);
//            Query queryRequestFrom = usersCollectionReference
//                    .whereEqualTo(FieldPath.documentId(), friendWhoRequest);
//            FirestoreRecyclerOptions<F32_FindFriendModel> requestsFriendsModelRecyclerOptions =
//                    new FirestoreRecyclerOptions.Builder<F32_FindFriendModel>()
//                            .setQuery(queryRequestFrom, F32_FindFriendModel.class)
//                            .build();
//
//            requestAdapter = new F23_RequestAdapter(requestsFriendsModelRecyclerOptions);
//            rvRequests.setAdapter(requestAdapter);
//            requestAdapter.startListening();
        }
//        queryFriendRequestsCollection = friendRequestCollectionReference
//                .orderBy();
//
//        Query queryRequestFrom = usersCollectionReference
//                .whereEqualTo(FieldPath.documentId(), friendWhoRequest);
//        FirestoreRecyclerOptions<F32_FindFriendModel> requestsFriendsModelRecyclerOptions =
//                new FirestoreRecyclerOptions.Builder<F32_FindFriendModel>()
//                        .setQuery(queryRequestFrom, F32_FindFriendModel.class)
//                        .build();
//
//        requestAdapter = new F23_RequestAdapter(requestsFriendsModelRecyclerOptions);
//        rvRequests.setAdapter(requestAdapter);
//        requestAdapter.startListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (CURRENT_USER == null) {
            startActivity(new Intent(getContext(), A12_SignUpActivity.class));
        } else {
//            requestAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
//        requestAdapter.stopListening();
    }
}