package com.example.justorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "GoogleActivity";
    Button loginnwGmail,sign_up;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseFirestore db;
    EditText email,password;
    TextView forgot_pass;
    private users myuser;
    Button login;
    private ProgressDialog pd1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=(EditText)findViewById(R.id.email_txt);
        password=(EditText)findViewById(R.id.pass_txt);
        //forgot_pass=(TextView)findViewById(R.id.forgot);
        login=(Button)findViewById(R.id.log_in);
        loginnwGmail=(Button)findViewById(R.id.gmail_btn);
        myuser= new users(this);
        db= FirebaseFirestore.getInstance();
        pd1 = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        loginnwGmail.setEnabled(false);
        loginnwGmail.setVisibility(View.INVISIBLE);
//        loginnwGmail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                switch (view.getId()) {
//                    case R.id.gmail_btn:
//                        //signIn();
//                        break;
//                }
//            }
//        });
        loginnwGmail.setEnabled(false);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd1.setMessage("Logging You In....Please Wait");
                pd1.show();
                pd1.setCancelable(false);
                normallogin();
            }
        });
        sign_up=findViewById(R.id.signup);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this,SignUp.class);
                startActivity(i);
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_web_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
    /* @Override
     protected void onStart() {
         super.onStart();
         if (mAuth.getCurrentUser() != null) {
             finish();
             startActivity(new Intent(this, Home.class));
         }
     }*/
    /*LogIn With Google*/
    private void signIn()
    {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {

            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if(account!=null){
                Toast.makeText(this, "235", Toast.LENGTH_SHORT).show();
                firebaseAuthWithGoogle(account);
                putdata(account);
                Intent intent= new Intent(MainActivity.this,MainFeed.class);
                startActivity(intent);
                Toast.makeText(this, "successfull", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this,"Login authantication failed",Toast.LENGTH_SHORT).show();
            }
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this,e.getMessage()+" line 138", Toast.LENGTH_SHORT).show();

        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            Toast.makeText(MainActivity.this, "User Signed In", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }
    private void putdata(GoogleSignInAccount acct){
        if (acct != null) {
            String Name = acct.getDisplayName();
            String Email = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            String ImageUrl=personPhoto.toString();
            Map<String, Object> data = new HashMap<>();
            data.put("Name",Name);
            data.put("Email",Email);
            data.put("ImageUrl",ImageUrl);
            data.put("Id",personId);
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
              //  Toast.makeText(this, "Firestore returning Null", Toast.LENGTH_SHORT).show();
            }
            else {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                db.collection("users").document(uid).set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //Toast.makeText(MainActivity.this, "Successful Putted Data", Toast.LENGTH_LONG).show();


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
            }

        }}
    private void normallogin(){
        String e = email.getText().toString();
        String p = password.getText().toString();
        mAuth.signInWithEmailAndPassword(e, p)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(MainActivity.this, "Welcome Back", Toast.LENGTH_SHORT).show();
                            onSuccessfulAuthentication();
                            FirebaseUser user = mAuth.getCurrentUser();
//
                        } else {
                            Toast.makeText(MainActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            pd1.dismiss();
                        }

                        // ...
                    }
                });

    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }




    private void onSuccessfulAuthentication() {

        db.collection("users").document(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Toast.makeText(getApplicationContext(),"Hi "+document.getData().get("name").toString(),Toast.LENGTH_LONG).show();
                        myuser.createSession(document.getString("Name"),
                                document.getString("Hostel"),
                                document.getString("Roll Number"),
                                document.getString("Email"),
                                document.getString("Mobile Number"),
                                document.getString("Image Url"),
                                mAuth.getCurrentUser().getUid());
                        Intent p = new Intent(MainActivity.this, MainFeed.class);
                        startActivity(p);
                        pd1.dismiss();
                        Toast.makeText(MainActivity.this, "Your session created", Toast.LENGTH_SHORT).show();
                    } else {

                    }
                } else {

                    Log.d(TAG, "get failed with ", task.getException());
                    pd1.dismiss();
                }
            }
        });


    }
}
