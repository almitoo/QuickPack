package com.example.quickpack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.quickpack.Adapter.Adapter;
import com.example.quickpack.Constants.MyConstants;
import com.example.quickpack.Utils.FirebaseUtils;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<String> titles;
    private List<Integer> images;
    private Adapter adapter;

    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();


        btnLogout=findViewById(R.id.btn_logout);


        if(!FirebaseUtils.checkIfSignedIn(this)) {
            return;
        }
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, Login.class));
                finish();

            }
        });


        getSupportActionBar().hide();
        recyclerView=findViewById(R.id.recyclerView);

        AddAllTitles();
        AddAllImages();


        adapter=new Adapter(this,titles,images,MainActivity.this);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUtils.checkIfSignedIn(this);
    }


    private void AddAllTitles(){
        titles = new ArrayList<>();
        titles.add(MyConstants.EVENTS_SCHEDULER_CAMEL_CASE);
        titles.add(MyConstants.EXPLORE_LOCATION_CAMEL_CASE);
        titles.add(MyConstants.BASIC_NEEDS_CAMEL_CASE);
        titles.add(MyConstants.CLOTHING_CAMEL_CASE);
        titles.add(MyConstants.PERSONAL_CARE_CAMEL_CASE);
        titles.add(MyConstants.BABY_NEEDS_CAMEL_CASE);
        titles.add(MyConstants.HEALTH_CAMEL_CASE);
        titles.add(MyConstants.TECHNOLOGY_CAMEL_CASE);
        titles.add(MyConstants.MY_LIST_CAMEL_CASE);
        titles.add(MyConstants.MY_SELECTIONS_CAMEL_CASE);

    }
    private void  AddAllImages(){
        images= new ArrayList<>();
        images.add(R.drawable.p13);
        images.add(R.drawable.p14);
        images.add(R.drawable.p1);
        images.add(R.drawable.p2);
        images.add(R.drawable.p3);
        images.add(R.drawable.p4);
        images.add(R.drawable.p5);
        images.add(R.drawable.p6);
        images.add(R.drawable.p11);
        images.add(R.drawable.p12);



    }
}