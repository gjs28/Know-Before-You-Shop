package com.example.knowbeforeyoushop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminMainActivity extends AppCompatActivity {

    Button gotoAddProd,gotoUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        gotoAddProd = (Button) findViewById(R.id.gotoAddProd);
        gotoUpdate = (Button) findViewById(R.id.gotoUpdate);

        gotoAddProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProdAdd();
            }
        });

        gotoUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminMainActivity.this,Update.class);
                startActivity(intent);
            }
        });
    }

    public void openProdAdd(){
        Intent intent = new Intent(this, ProductAdd.class);
        startActivity(intent);
    }
}