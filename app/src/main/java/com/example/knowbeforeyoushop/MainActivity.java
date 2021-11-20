package com.example.knowbeforeyoushop;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button displayProd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        displayProd=findViewById(R.id.gotoDisplay);

        displayProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProducts();

            }
        });
    }

    private void openProducts() {
        Intent intent= new Intent(this,DisplayItems.class);
        startActivity(intent);
    }


}