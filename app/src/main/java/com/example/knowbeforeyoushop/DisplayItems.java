package com.example.knowbeforeyoushop;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class DisplayItems extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    FirebaseDatabase firebaseDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=(RecyclerView) findViewById(R.id.recycleView);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Member> options=new FirebaseRecyclerOptions.Builder<Member>().setQuery(FirebaseDatabase.getInstance().getReference().child("Member"),Member.class).build();

        myAdapter=new MyAdapter(options);
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        myAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);

        MenuItem item=menu.findItem(R.id.search);

        SearchView searchView=(SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processSearch(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void processSearch(String s) {
        FirebaseRecyclerOptions<Member> options=new FirebaseRecyclerOptions.Builder<Member>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Member").orderByChild("title")
                        .startAt(s).endAt(s+"\uf8ff"),Member.class).build();

        myAdapter=new MyAdapter(options);
        myAdapter.startListening();
        recyclerView.setAdapter(myAdapter);
    }
}
