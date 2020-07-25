package com.example.justorder;

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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.example.justorder.SignUp.REQUESCODE;

public class Orders extends AppCompatActivity {

    Spinner Food1,Food2,Food3,Food1nv,Food2nv,Food3nv;
    Spinner Food1n,Food2n,Food3n,Food1nvn,Food2nvn,Food3nvn;
    Button AddItem,Submit;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private final static String KEY_FOOD1 = "ORDER1VEG";
    private final static String KEY_FOOD2 = "ORDER2VEG";
    private final static String KEY_FOOD3 = "ORDER3VEG";
    private final static String KEY_FOOD1NV = "ORDER1NV";
    private final static String KEY_FOOD2NV = "ORDER2NV";
    private final static String KEY_FOOD3NV = "ORDER3NV";

    private final static String KEY_FOOD1N = "ORDER1VEGN";
    private final static String KEY_FOOD2N = "ORDER2VEGN";
    private final static String KEY_FOOD3N = "ORDER3VEGN";
    private final static String KEY_FOOD1NVN = "ORDER1NVN";
    private final static String KEY_FOOD2NVN= "ORDER2NVN";
    private final static String KEY_FOOD3NVN= "ORDER3NVN";

    private final static String KEY_ID = "ID";
    private final static String KEY_NAME = "NAME";
    private final static String KEY_HOSTEL = "HOSTEL";
    private final static String KEY_ROOMNO = "ROOMNO";
    private final static String KEY_MOBNO = "MOBILENO";
    private final static String KEY_PARENTID = "PARENTID";
    private final static String KEY_IMAGEURL = "IMAGEURL";
    private final static String KEY_USERID = "USERID";
    private ProgressDialog progressDialog;
    //private Uri pickedImgUri;
    //private ImageView pic;
    //private Button payment_button;

    private FirebaseStorage  storage = FirebaseStorage.getInstance();
    private StorageReference storageRef= storage.getReference();


    public String s1,s2,s3,s4,Url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        Food1 = (Spinner) findViewById(R.id.food1);
        Food2 = (Spinner) findViewById(R.id.food2);
        Food3 = (Spinner) findViewById(R.id.food3);
        Food1n = (Spinner) findViewById(R.id.food1n);
        Food2n = (Spinner) findViewById(R.id.food2n);
        Food3n = (Spinner) findViewById(R.id.food3n);
        //AddItem = (Button) findViewById(R.id.add_items);
        Submit = (Button) findViewById(R.id.submit_items);
        //pic=(ImageView) findViewById(R.id.imageView_payment);
       // payment_button=(Button)findViewById(R.id.upload_pic);
        Food1nv=(Spinner) findViewById(R.id.food1nv);
        Food2nv=(Spinner) findViewById(R.id.food2nv);
        Food3nv=(Spinner)findViewById(R.id.food3nv);

        Food1nvn=(Spinner) findViewById(R.id.food1nvn);
        Food2nvn=(Spinner) findViewById(R.id.food2nvn);
        Food3nvn=(Spinner)findViewById(R.id.food3nvn);

        progressDialog = new ProgressDialog(this);
        //Submit.setEnabled(false);


        //Food2.setEnabled(false);
       // Food3.setEnabled(false);
        getUserDetails();
      /*  pic.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openGallery();

                    }
                }
        );

        payment_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                      onimageupload();

                    }
                }
        );




      /*  AddItem.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!Food2.isEnabled()) {
                            Food2.setEnabled(true);
                        } else if (Food2.isEnabled() && !(Food3.isEnabled())) {
                            Food3.setEnabled(true);
                        } else {
                            Toast.makeText(Orders.this, "Sorry!!! Maximum 3 orders only!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );*/

        Submit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        db.collection("users").document(mAuth.getUid()).collection("MyOrder").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    int count = 0;
                                    for (DocumentSnapshot document : task.getResult()) {
                                        count++;
                                    }
                                    if (count<=5)
                                        getIDinString();
                                    else{

                                        Toast.makeText(Orders.this, "You already have an order", Toast.LENGTH_SHORT).show();
                                    }



                                } else {
                                   // Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });




                    }
                }

        );

    }



    private void onSubmitClicked( int p)
    {


    final int p1=p+1;
        String ss= Integer.toString(p1);
        String f1 = Food1.getSelectedItem().toString();
        String f2=  Food2.getSelectedItem().toString();
        String f3=  Food3.getSelectedItem().toString();
        String f4=  Food1nv.getSelectedItem().toString();
        String f5=  Food2nv.getSelectedItem().toString();
        String f6=  Food3nv.getSelectedItem().toString();

        String f1n = Food1n.getSelectedItem().toString();
        String f2n=  Food2n.getSelectedItem().toString();
        String f3n=  Food3n.getSelectedItem().toString();
        String f4n=  Food1nvn.getSelectedItem().toString();
        String f5n=  Food2nvn.getSelectedItem().toString();
        String f6n=  Food3nvn.getSelectedItem().toString();

        Map<String,Object> datas = new HashMap<>();
        datas.put(KEY_FOOD1,f1);
        datas.put(KEY_FOOD2,f2);
        datas.put(KEY_FOOD3,f3);
        datas.put(KEY_FOOD1NV,f4);
        datas.put(KEY_FOOD2NV,f5);
        datas.put(KEY_FOOD3NV,f6);

        datas.put(KEY_FOOD1N,f1n);
        datas.put(KEY_FOOD2N,f2n);
        datas.put(KEY_FOOD3N,f3n);
        datas.put(KEY_FOOD1NVN,f4n);
        datas.put(KEY_FOOD2NVN,f5n);
        datas.put(KEY_FOOD3NVN,f6n);



        datas.put(KEY_NAME,s2);
        datas.put(KEY_HOSTEL,s3);
        datas.put(KEY_ROOMNO,s4);
        datas.put(KEY_MOBNO,s1);
        datas.put(KEY_ID,ss);
        datas.put(KEY_USERID,mAuth.getCurrentUser().getUid());
        //datas.put(KEY_IMAGEURL,Url);

       DocumentReference docre= db.collection("Orders").document();
       datas.put(KEY_PARENTID,docre.getId());
                docre.set(datas)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Orders.this,"Successful in mainfeed",Toast.LENGTH_LONG).show();
                        //updateIDinstring(p1+1);



                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Orders.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        db.collection("users").document(mAuth.getUid()).collection("MyOrder").document(mAuth.getUid()).set(datas)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Orders.this,"Successful in Userfeed",Toast.LENGTH_LONG).show();
                        //Intent intent= new Intent(Orders.this,MainFeed.class);
                       // startActivity(intent);


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Orders.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        Map<String,Object> datass = new HashMap<>();
        datass.put(KEY_ID,ss);
        db.collection("id").document("TtoEZIJqtKJftJiIaHH2").set(datass)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Orders.this,"Successful",Toast.LENGTH_LONG).show();
                        Intent intent= new Intent(Orders.this,MainFeed.class);
                        startActivity(intent);


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Orders.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });





    }



                private void getIDinString(){
                    db.collection("id").document("TtoEZIJqtKJftJiIaHH2").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful())

                            { DocumentSnapshot snapshot = task.getResult();
                                if(snapshot.exists())
                                {
                                    final String n = snapshot.getString("ID");
                                    int m=Integer.valueOf(n);
                                    onSubmitClicked(m);

                                }else{
                                    Toast.makeText(Orders.this, "message"+task.getException(), Toast.LENGTH_SHORT).show();
                                }


                            }else{


                                Toast.makeText(Orders.this, "mess"+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

    }


    private void getUserDetails()
    {


        db.collection("users").document(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Toast.makeText(getApplicationContext(),"Hi "+document.getData().get("name").toString(),Toast.LENGTH_LONG).show();
                         s2=   document.getString("Name");
                         s3=   document.getString("Hostel");
                         s4=   document.getString("Room No");
                         s1= document.getString("Mobile Number");
                        Toast.makeText(Orders.this, s4+s2+s3, Toast.LENGTH_SHORT).show();



                    } else {
                        //Toast.makeText(Orders.this, "Your session created", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Log.d("aa", "get failed with ", task.getException());
                }
            }
        });




    }

  /*  private void openGallery() {
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
            //pro_pic.setImageURI(pickedImgUri);
            Toast.makeText(this, "Image Succesfully Choosen", Toast.LENGTH_SHORT).show();
            pic.setImageURI(pickedImgUri);
        }

    }*/

/*
private void onimageupload()
{   progressDialog.setMessage("wait......");
    progressDialog.show();
    if(pickedImgUri!=null){
        final StorageReference ref = storageRef.child("payment/"+ UUID.randomUUID().toString());
        Log.d("prince",ref.toString());
        ref.putFile(pickedImgUri)

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                        Toast.makeText(Orders.this, "Failed " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Orders.this, "Uploaded", Toast.LENGTH_SHORT).show();

                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Url= uri.toString();
                        Toast.makeText(Orders.this, "Url Created succeffully", Toast.LENGTH_SHORT).show();
                        Submit.setEnabled(true);
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(Orders.this, "Error is"+exception.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("pra",exception.toString());

                    }
                });
            }
        });
    }
    else
        Toast.makeText(this,"Image not selected",Toast.LENGTH_LONG).show();



}*/

























}











