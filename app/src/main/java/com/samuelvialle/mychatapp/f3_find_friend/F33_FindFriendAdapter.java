package com.samuelvialle.mychatapp.f3_find_friend;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.samuelvialle.mychatapp.R;
import com.samuelvialle.mychatapp.a0_common.MyOnCLickListener;

public class F33_FindFriendAdapter extends FirestoreRecyclerAdapter<F32_FindFriendModel, F33_FindFriendAdapter.FindFriendViewHolder> {
    /**
     * Variables globales
     **/
    private static final String TAG = "FindFriendAdapter";

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public F33_FindFriendAdapter(@NonNull FirestoreRecyclerOptions<F32_FindFriendModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FindFriendViewHolder holder, int position, @NonNull F32_FindFriendModel model) {
        /** 6 Comme la view est associée à l'adapter, il est maintenant possible d'accéder aux objets via le holder **/
        String fullName = model.getName();
        String userAvatar = model.getAvatar();

        // Le nom depuis les informations de Authenticator
        holder.tvFullName.setText(fullName);

        // L'image de l'avatar depuis le storage
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.a0_ic_user)
                .error(R.drawable.a0_ic_user);

        Context context = holder.ivProfile.getContext();
        Glide.with(context)
                .load(userAvatar)
                .apply(options)
                .fitCenter()
                .override(150, 150)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivProfile);
    }

    @NonNull
    @Override
    public FindFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /** 5 Dans cette méthode on va inflate le layout item_find_friend, de plus on encapsuler cet inflate dans une view pour
         //         * pouvoir l'utiliser par la suite **/
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_f33_find_friends, parent, false);

        // 5.1 On va utiliser le viewHolder de la classe pour afficher la vue créée ci-dessus
        return new FindFriendViewHolder(view);
    }

    @Override
    public void onError(@NonNull FirebaseFirestoreException e) {
        Log.e(TAG, "Error Firebase : " + e.getMessage());
    }

    public class FindFriendViewHolder extends RecyclerView.ViewHolder {
        // Variables lien design code
        private final ImageView ivProfile;
        private final TextView tvFullName;
        private final Button btnSendRequest, btnCancelRequest;
        private final ProgressBar pbRequest;

        public FindFriendViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialiastion des vues
            ivProfile = itemView.findViewById(R.id.ivProfile);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            btnSendRequest = itemView.findViewById(R.id.btnAcceptRequest);
            btnCancelRequest = itemView.findViewById(R.id.btnCancelRequest);
            pbRequest = itemView.findViewById(R.id.pbRequest);

            btnSendRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String state = "send";
                    myOnClickListener.onClick(getSnapshots().getSnapshot(getBindingAdapterPosition()), getBindingAdapterPosition(), state);
                    btnSendRequest.setVisibility(View.GONE);
                    btnCancelRequest.setVisibility(View.VISIBLE);
                }
            });

            btnCancelRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String state = "cancel";
                    myOnClickListener.onClick(getSnapshots().getSnapshot(getBindingAdapterPosition()), getBindingAdapterPosition(), state);
                    btnSendRequest.setVisibility(View.VISIBLE);
                    btnCancelRequest.setVisibility(View.GONE);
                }
            });
        }
    }

    public MyOnCLickListener myOnClickListener;

    public void exploitMyOnCLick(MyOnCLickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }
}

