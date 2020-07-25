package com.example.justorder;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SignUp extends AppCompatActivity {
    EditText fullname, Email, mobile_no, iitg_roll_no,room_no, pass1, pass2;
    ImageView pro_pic;
    Button register;
    Spinner hostel;
    ImageView ImgUserPhoto;
    TextView path;
    static int PReqCode = 1;
    static int REQUESCODE = 1;
    private Uri pickedImgUri;
    private ProgressBar loadingProgress;
    private FirebaseAuth mAuth;
    String Url=null;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef= storage.getReference();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog pd2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        fullname = findViewById(R.id.regName);
        Email = findViewById(R.id.regEmail);
        mobile_no = findViewById(R.id.regMobileNo);
        iitg_roll_no = findViewById(R.id.regRollNo);
        pass1 = findViewById(R.id.regPass);
        pass2 = findViewById(R.id.regPass2);
        hostel=findViewById(R.id.Hostel_list);
        room_no= findViewById(R.id.regRoomNo);
        pd2 = new ProgressDialog(this);

        register = findViewById(R.id.regBtn);
        mAuth = FirebaseAuth.getInstance();
       // pro_pic = findViewById(R.id.regUserPhoto) ;
      /*  pro_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 22)
                    checkAndRequestForPermission();
                else
                    openGallery();
            }
        });*/


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd2.setMessage("Updating Your Info....");
                pd2.setCancelable(false);
                pd2.show();
                register.setVisibility(View.INVISIBLE);
                register();
            }
        });



    }
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),REQUESCODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "line 147", Toast.LENGTH_SHORT).show();

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null&& data.getData() != null ) {
            pickedImgUri = data.getData();
            pro_pic.setImageURI(pickedImgUri);
            Toast.makeText(this, "Image Succesfully Choosen", Toast.LENGTH_SHORT).show();

        }


    }
    private void checkAndRequestForPermission() {


        if (ContextCompat.checkSelfPermission(SignUp.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(SignUp.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(SignUp.this,"Please accept for required permission",Toast.LENGTH_SHORT).show();

            }

            else
            {
                ActivityCompat.requestPermissions(SignUp.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }

        }else

            openGallery();

    }


    private void register(){
        final String email = Email.getText().toString().trim();
        final String password = pass1.getText().toString();
        final String password2 = pass2.getText().toString();
        final String name = fullname.getText().toString().trim();
        final String RollNo = iitg_roll_no.getText().toString().trim();
        final String Hostel = hostel.getSelectedItem().toString();
        final String MobNo=mobile_no.getText().toString().trim();
        final String RoomNo=mobile_no.getText().toString().trim();


         if ( (email.isEmpty())||(RoomNo.isEmpty()) || (name.isEmpty()) || (password.isEmpty()) || !password.equals(password2) || (RollNo.isEmpty()) || (Hostel.isEmpty())|| (MobNo.isEmpty())) {
            Toast.makeText(SignUp.this, "Please Give all Fields & Image", Toast.LENGTH_SHORT).show();
            register.setVisibility(View.VISIBLE);
        }
        else
        CreateUserAccount(email,password);
    }
    private  void CreateUserAccount(final String email,final String password){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                          // Toast.makeText(SignUp.this,"Acoount Created Succefully",Toast.LENGTH_SHORT).show();
                             updateUserInfo(Url);
                            FirebaseUser user = mAuth.getCurrentUser();
                        }
                        else
                        {
                            Toast.makeText(SignUp.this,"Account creation failed because " + task.getException().getMessage().toString(),Toast.LENGTH_SHORT).show();
                            register.setVisibility(View.VISIBLE);
                            pd2.dismiss();
                        }
                    }
                });

    }
    private  void imageUrl()
    {
        if(pickedImgUri!=null){
        final StorageReference ref = storageRef.child("images/"+ UUID.randomUUID().toString());
        Log.d("prince",ref.toString());
        ref.putFile(pickedImgUri)

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                        Toast.makeText(SignUp.this, "Failed " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(SignUp.this, "Uploaded", Toast.LENGTH_SHORT).show();

                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Url= uri.toString();
                        //Toast.makeText(SignUp.this, "Url Created succeffully", Toast.LENGTH_SHORT).show();





                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(SignUp.this, "Error is"+exception.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("pra",exception.toString());

                    }
                });
            }
        });
        }
        else
        Toast.makeText(this,"Image not selected",Toast.LENGTH_LONG).show();

//        Log.d("Str",Url);


    }

    private void updateUserInfo( String Url) {
        final String email = Email.getText().toString().trim();
        final String password = pass1.getText().toString();
        final String name = fullname.getText().toString();
        final String RollNo = iitg_roll_no.getText().toString();
        final String Hostel = hostel.getSelectedItem().toString();
        final String MobNo=mobile_no.getText().toString();
        final String RoomNo=room_no.getText().toString();

        Map<String, Object> data = new HashMap<>();
        data.put("Name",name);
        data.put("Email",email);
        data.put("Password",password);
        data.put("Hostel",Hostel);
        data.put("Mobile Number",MobNo);
        data.put("Roll Number",RollNo);
        data.put("Image Url",Url);
        data.put("Room No",RoomNo);

        db.collection("users").document(mAuth.getUid()).set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SignUp.this,"Successful",Toast.LENGTH_LONG).show();
                        pd2.dismiss();
                        Intent intent= new Intent(SignUp.this,MainActivity.class);
                        startActivity(intent);


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUp.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });






    }



}
