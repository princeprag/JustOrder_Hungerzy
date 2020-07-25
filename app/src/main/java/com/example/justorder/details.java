package com.example.justorder;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class details extends AppCompatActivity {
    Button callcust,delcust;
    TextView Name,o1,o2,o3,roomno,on1,on2,on3,mob;
    TextView  o1n,o2n,o3n,on1n,on2n,on3n;

    private static final int Request_Call=2;
    FirebaseAuth mAuth= FirebaseAuth.getInstance();
    String parentid,userid;
    FirebaseFirestore db= FirebaseFirestore.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        callcust=findViewById(R.id.call_customer);
        delcust=findViewById(R.id.delete_customer);
        Name=findViewById(R.id.text_name);
        o1=findViewById(R.id.od1);
        o2=findViewById(R.id.od2);
        o3=findViewById(R.id.od3);
        on1=findViewById(R.id.od1nv);
        on2=findViewById(R.id.od2nv);
        on3=findViewById(R.id.od3nv);

        o1n=findViewById(R.id.od1n);
        o2n=findViewById(R.id.od2n);
        o3n=findViewById(R.id.od3n);
        on1n=findViewById(R.id.od1nvn);
        on2n=findViewById(R.id.od2nvn);
        on3n=findViewById(R.id.od3nvn);



        roomno=findViewById(R.id.roomno);
        mob=findViewById(R.id.text_mob);
        //Toast.makeText(this, mAuth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();



        if( ! ( (mAuth.getCurrentUser().getUid()).equals("FlKUSNXSetMwfVQp05tnywXRXSW2") || (mAuth.getCurrentUser().getUid()).equals("MZNThYp5ZfZrT88FAzpfdNN0fMi2") )){

            callcust.setEnabled(false);
            delcust.setEnabled(false);
            callcust.setVisibility(View.INVISIBLE);
            delcust.setVisibility(View.INVISIBLE);
            roomno.setVisibility(View.INVISIBLE);
            mob.setVisibility(View.INVISIBLE);



        }
        Intent i = getIntent();
        String name = i.getStringExtra("Name");
        String room = i.getStringExtra("RoomNo");
        String order1 = i.getStringExtra("Order1");
        String order2 = i.getStringExtra("Order2");
        String order3 = i.getStringExtra("Order3");
        String order1nv = i.getStringExtra("Order1nv");
        String order2nv = i.getStringExtra("Order2nv");
        String order3nv = i.getStringExtra("Order3nv");

        String order1n = i.getStringExtra("Order1n");
        String order2n = i.getStringExtra("Order2n");
        String order3n = i.getStringExtra("Order3n");
        String order1nvn = i.getStringExtra("Order1nvn");
        String order2nvn = i.getStringExtra("Order2nvn");
        String order3nvn = i.getStringExtra("Order3nvn");

        String m= i.getStringExtra("Mobile_no");
        parentid= i.getStringExtra("ParentId");
        userid=i.getStringExtra("UserId");





        //pic=findViewById(R.id.image_pic);
        Name.setText(name);
        o1.setText(order1);
        o2.setText(order2);
        o3.setText(order3);
        on1.setText(order1nv);
        on2.setText(order2nv);
        on3.setText(order3nv);

        o1n.setText(order1n);
        o2n.setText(order2n);
        o3n.setText(order3n);
        on1n.setText(order1nvn);
        on2n.setText(order2nvn);
        on3n.setText(order3nvn);


        roomno.setText(room);
        mob.setText(m);


        callcust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Mobile_No=getIntent().getStringExtra("Mobile_no");
                //Toast.makeText(details.this, Mobile_No, Toast.LENGTH_SHORT).show();
                makephonecall(Mobile_No);
            }
        });
        //getdata();

        delcust.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteitemfromfeed();
                    }
                }
        );


    }


    @Override
    protected void onStart() {
        super.onStart();

    }

  /*  private void getdata()
    {if(getIntent().hasExtra("Name") &&getIntent().hasExtra("Order1nv") &&getIntent().hasExtra("Order2nv") &&getIntent().hasExtra("Order3nv") &&getIntent().hasExtra("ParentId") &&getIntent().hasExtra("Order1") &&getIntent().hasExtra("Order2") &&getIntent().hasExtra("Order3") &&getIntent().hasExtra("RoomNo")  &&getIntent().hasExtra("Mobile_no"))
    {

        //Toast.makeText(this,m , Toast.LENGTH_SHORT).show();



        //String imageurl=i.getStringExtra("ImageURL");


       // Toast.makeText(this,cat, Toast.LENGTH_SHORT).show();
        addData(name,order1,order2,order3,order1nv,order2nv,order3nv,room,m);
    }*/


    private void addData(String name,String od1,String od2,String od3,String od1nv,String od2nv,String od3nv,String room,String m){

        ImageView pic;

        //Picasso.get().load(imageurl).fit().into(pic);



    }
    private void makephonecall(String mobile_No){
        if(mobile_No.trim().length()>0) {
            if(ContextCompat.checkSelfPermission(details.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(details.this,new String[]{Manifest.permission.CALL_PHONE},Request_Call);
            }
            else
            {
                String dial="tel:"+mobile_No;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }


        }
        else
            Toast.makeText(this,"Mobile Number is Not Valid",Toast.LENGTH_SHORT).show();
    }

    private void deleteitemfromfeed()
    {
        //Toast.makeText(this, parentid, Toast.LENGTH_SHORT).show();

 db.collection("Orders").document(parentid).delete()
         .addOnSuccessListener(
         new OnSuccessListener<Void>() {
             @Override
             public void onSuccess(Void aVoid) {
                 Toast.makeText(details.this, "Deleted", Toast.LENGTH_SHORT).show();
                 Intent i3= new Intent(details.this,MainFeed.class);
                 startActivity(i3);

             }
         }
 )
         .addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 Toast.makeText(details.this, "Failed because"+e.toString(), Toast.LENGTH_SHORT).show();
             }
         });
 db.collection("users").document(userid).collection("MyOrder").document(userid).delete();



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==Request_Call)
        {    String Mobile_No=getIntent().getStringExtra("Mobile_no");

            if(grantResults.length >0 &&  grantResults[0]==PackageManager.PERMISSION_GRANTED)
                makephonecall(Mobile_No);
            else
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
        }

    }
}

