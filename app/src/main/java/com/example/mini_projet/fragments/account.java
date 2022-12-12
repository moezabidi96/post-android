package com.example.mini_projet.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mini_projet.MainActivity;
import com.example.mini_projet.R;
import com.example.mini_projet.SimpleUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;


public class account extends Fragment {


    private FirebaseAuth firebaseAuth;
    TextView name,email,password,phone;
    String userID;

  ImageView eye;

    FirebaseFirestore fStore;
    FirebaseUser user;
    FirebaseAuth mAuth;
    SimpleUser user1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_account, container, false);
        mAuth = FirebaseAuth.getInstance();

        name=(TextView)view.findViewById(R.id.User);
        email=(TextView)view.findViewById(R.id.Email);
        password=(TextView)view.findViewById(R.id.password);
        phone=(TextView)view.findViewById(R.id.Phone);
        eye=view.findViewById(R.id.eye);
       // password.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );

            }
        });



        fStore = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();
        loadUserInformation();

       view.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(getActivity(), MainActivity.class));

            }
        });







        return view ;
    }
    private void loadUserInformation() {



        if (user != null) {
            email.setText(user.getEmail());

            userID = mAuth.getCurrentUser().getUid();
            DocumentReference documentReference = fStore.collection("user").document(userID);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                  user1=documentSnapshot.toObject(SimpleUser.class);
                    phone.setText(user1.getPhone());
                    password.setText(user1.getPassword());
                    name.setText(user1.getName());





                }
            });




        /*    if (user.isEmailVerified()) {

               // textView.setText("Email verified");
            } else {
                textView.setText("Email not verified (Click to verify)");
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getActivity(), "Verification email sent", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }*/
        }
    }

}