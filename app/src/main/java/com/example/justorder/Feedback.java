package com.example.justorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Feedback extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Button btn_feedback;
    EditText res_field;
    private static final String KEY_FEEDBACK="FEEDBACK";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        res_field =(EditText)findViewById(R.id.edt_feedback);
        btn_feedback=(Button)findViewById(R.id.btn_feedback);
       final String feed= res_field.getText().toString();
       btn_feedback.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {





               Map<String,Object> fdb = new HashMap<>();
               fdb.put(KEY_FEEDBACK,feed);
               db.collection("Feedback").document(mAuth.getCurrentUser().getUid()).set(fdb)
                       .addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Toast.makeText(Feedback.this, "Failed with"+e.getMessage(), Toast.LENGTH_SHORT).show();

                           }
                       })
                       .addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void aVoid) {
                               Toast.makeText(Feedback.this, "Successfully submitted feedback!!!", Toast.LENGTH_SHORT).show();
                               Intent itt = new Intent(Feedback.this,MainFeed.class);
                               startActivity(itt);

                           }
                       });







           }
       });




    }
}




