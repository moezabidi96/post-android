package com.example.mini_projet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText Email,Password,name,phone;
    Button SignIn,SignUp;
    Intent i;
    FirebaseFirestore db;
    String userName,userPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Email=(EditText)findViewById(R.id.email) ;
        Password=(EditText)findViewById(R.id.password) ;
        name=(EditText)findViewById(R.id.name) ;
        phone=(EditText)findViewById(R.id.phone) ;

        SignIn=(Button)findViewById(R.id.signin);
        SignUp=(Button)findViewById(R.id.btnSignup);
        db= FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
         i =new Intent(getApplicationContext(),MainActivity.class);


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=Email.getText().toString();
                String password=Password.getText().toString();
                 userName=name.getText().toString();
                userPhone=phone.getText().toString();
                if(!email.isEmpty() && !password.isEmpty()){
                CreateUser(email,password);
            }else {
                    Toast.makeText(getApplicationContext(), "email or password is empty ",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(i);
               finish();
            }
        });
    }
void CreateUser(String email,String password){
    mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information

                        FirebaseUser CurrentUser = mAuth.getCurrentUser();
                        Map<String, Object> userInfo = new HashMap<>();
                        userInfo.put("email", CurrentUser.getEmail());
                        userInfo.put("name", userName);
                        userInfo.put("phone", userPhone);
                        userInfo.put("password", password);
                        db.collection("user").document(CurrentUser.getUid())
                                .set(userInfo)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Intent intent=new Intent(getApplicationContext(), Page_Principal.class);
                                        startActivity(intent);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        //Log.w(TAG, "Error writing document", e);
                                    }
                                });



                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(getApplicationContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        Log.d("error", task.getException().toString());
                    }

                    // ...
                }
            });
}

    public void arrocBack(View view) {

        startActivity(i);
        finish();
    }
}