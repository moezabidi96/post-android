package com.example.mini_projet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AddJob extends AppCompatActivity {
    EditText nameJob,salaryJob,description;
    Button addJob,selectImage;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    public Uri imageUri;
    StorageReference storageRef;
    FirebaseStorage storage;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);
        nameJob=(EditText)findViewById(R.id.jobName) ;
        salaryJob=(EditText)findViewById(R.id.salaryJob) ;
        description=(EditText)findViewById(R.id.description) ;
        addJob=(Button)findViewById(R.id.addJob) ;
        imageView = (ImageView)findViewById(R.id.imageView2) ;
        selectImage=(Button)findViewById(R.id.addImage) ;
        db= FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        addJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                uploadImage();

            }
        });

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choiseImage();
            }
        });


    }
    private void uploadImage()
    {
        if (imageUri != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageRef
                    .child(
                            "images/"
                                    + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(imageUri)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            final Uri downloadUrl = uri;
                                            addJobData(downloadUrl.toString());

                                        }
                                    });

                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(getApplicationContext(),
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(getApplicationContext(),
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
    }
    void addJobData( String imageURL ){
        String jobName = nameJob.getText().toString();
        String salary = salaryJob.getText().toString();
        String desc = description.getText().toString();

        if(!jobName.isEmpty() && !salary.isEmpty() && !desc.isEmpty() && !imageURL.isEmpty()) {

                    List<String> listImageURL = new ArrayList<>();
                    listImageURL.add(imageURL);
            Map<String, Object> job = new HashMap<>();
            job.put("price", salary);
            job.put("name", jobName);
            job.put("description", desc);
            job.put("image", listImageURL);
            job.put("search", jobName.toLowerCase());
            job.put("userId",mAuth.getCurrentUser().getUid());
            try {


                db.collection("Jobs")
                        .add(job)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                //   Log.d("success", "DocumentSnapshot written with ID: " + documentReference.getId());
                                Toast.makeText(getApplicationContext(), "job successfuly added ",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(), Page_Principal.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("error", "Error adding document", e);
                            }
                        });
            } catch (Exception e) {
                Log.w("error", "Error adding document", e);
            }
        }else {
            Toast.makeText(getApplicationContext(), "jobName or salary or description or image is empty ",
                    Toast.LENGTH_SHORT).show();
        }

    }

    private void choiseImage() {
              Intent i=new Intent();
              i.setType("image/*");
              i.setAction(Intent.ACTION_GET_CONTENT);
              startActivityForResult(i,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode== RESULT_OK && data!=null && data.getData()!=null){
           imageUri=data.getData();
            imageView.setImageURI(imageUri);
        }
    }
}