package com.example.justorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MyOrder extends AppCompatActivity {
    private FirebaseFirestore db;
    private ListAdapter adapter;
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        putinrecyclerview();
    }

    public void putinrecyclerview()
    {   db = FirebaseFirestore.getInstance();
        CollectionReference productref = db.collection("users").document(mAuth.getCurrentUser().getUid()).collection("MyOrder");
        Query query = productref;
        FirestoreRecyclerOptions<items_part> options = new FirestoreRecyclerOptions.Builder<items_part>().setQuery(query, items_part.class).build();
        adapter = new ListAdapter(options, this);
        RecyclerView recyclerView = findViewById(R.id.recycle_self);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }


}
