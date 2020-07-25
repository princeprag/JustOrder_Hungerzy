package com.example.justorder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainFeed extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentuser = mAuth.getCurrentUser();
    private GoogleSignInClient mGoogleSignInClient;
    ImageView pro_pic;
    TextView name, Email;
    public  users myuser ;

    ListAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_feed);
        toolbar = findViewById(R.id.toolbar);
        //putnewsfeed();

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        myuser= new users(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        putinrecyclerview();

        if (savedInstanceState == null) {

        }




    }

    private CollectionReference userref = db.collection("users");
/////////////////


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
             case R.id.nav_order:
              Intent s=new Intent(MainFeed.this,Orders.class);
              startActivity(s);
              break;

              case R.id.nav_my_order:
                Intent s1 = new Intent(MainFeed.this, MyOrder.class);
                startActivity(s1);
                break;


            case R.id.nav_my_menu:
                Intent s2 = new Intent(MainFeed.this, MessMenu.class);
                startActivity(s2);
                break;

                case R.id.nav_aboutUs:
                    Intent s9 = new Intent(MainFeed.this, aboutUs.class);
                    startActivity(s9);
                    break;

            case R.id.nav_campusmenu:
                Intent s6 = new Intent(MainFeed.this, Khoka.class);
                startActivity(s6);
                break;


            case R.id.nav_book:

                String dial="tel:"+"6204774832";
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                break;



            case R.id.nav_send_feedback:
                Intent s7=new Intent(MainFeed.this,Feedback.class);
                startActivity(s7);
                break;
            case R.id.signOut:
                signout();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            moveTaskToBack(true);
        }
    }


   public void putinrecyclerview()
    {   db = FirebaseFirestore.getInstance();

        CollectionReference productref = db.collection("Orders");
        Query query = productref.orderBy("ID", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<items_part> options = new FirestoreRecyclerOptions.Builder<items_part>().setQuery(query, items_part.class).build();
        adapter = new ListAdapter(options, this);
        RecyclerView recyclerView = findViewById(R.id.drawer_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }



    @Override
    protected void onStart() {
        super.onStart();
        get_put_userDetails();

    }
//
//
//     @Override
//     protected void onStop() {
//         super.onStop();
//       //  adapter.stopListening();
//     }
    private void signout() {
       /* mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(Drawer.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(Drawer.this, "SignOut Succesfully", Toast.LENGTH_LONG).show();

                    }
                });*/
        myuser.logout();
        Intent it= new Intent(MainFeed.this,MainActivity.class);
        startActivity(it);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

    }

//    private void put_userdata_header(GoogleSignInAccount acct) {
//        navigationView = findViewById(R.id.nav_view);
//        View headerview = navigationView.getHeaderView(0);
//        pro_pic = headerview.findViewById(R.id.pic);
//        name = headerview.findViewById(R.id.name_user);
//        Email = headerview.findViewById(R.id.email_user);
//
//        if (acct != null) {
//            String personName = acct.getDisplayName();
//            String personEmail = acct.getEmail();
//            Uri personPhoto = acct.getPhotoUrl();
//            // String Url=personPhoto.toString();
//            name.setText(personName);
//            Email.setText(personEmail);
//            // Picasso.get().load(String.valueOf(personPhoto)).fit().into(pro_pic);
//            Glide.with(this).load(String.valueOf(personPhoto)).into(pro_pic);
//        }
////       name.setText(currentuser.getDisplayName());
////       Email.setText(currentuser.getEmail());
////       Glide.with(this).load(currentuser.getPhotoUrl()).into(pro_pic);
//
//    }


    private void get_put_userDetails(){
        final String uid= mAuth.getCurrentUser().getUid();
        DocumentReference docref= db.collection("users").document(uid);
        docref.get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();

                            if (documentSnapshot != null) {

                                navigationView = findViewById(R.id.nav_view);
                                View headerview = navigationView.getHeaderView(0);
                                pro_pic = headerview.findViewById(R.id.pic);
                                name = headerview.findViewById(R.id.name_user);
                                Email = headerview.findViewById(R.id.email_user);
                                name.setText(documentSnapshot.getString("Name"));
                                Email.setText(documentSnapshot.getString("Email"));
                                String url_string = documentSnapshot.getString("Image Url");
                                Glide.with(MainFeed.this).load(url_string).into(pro_pic);
                            } else {
                                Toast.makeText(MainFeed.this, "Document snapshot null", Toast.LENGTH_SHORT).show();
                            }
                        }else{

                            Toast.makeText(MainFeed.this, "Task is unsuccessfull because"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }







}













