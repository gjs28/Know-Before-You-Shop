package com.example.knowbeforeyoushop;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProductAdd extends AppCompatActivity {
    EditText title,price,quantity,nos;
    TextView category;
    Button btnAdd,back;
    DatabaseReference reff;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_add);


        title=findViewById(R.id.titleEt);
        category=findViewById(R.id.categoryTv);
        price=findViewById(R.id.priceEt);
        quantity=findViewById(R.id.quantityEt);
        btnAdd=findViewById(R.id.addProduct);
        nos=findViewById(R.id.nosEt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        reff= FirebaseDatabase.getInstance().getReference().child("Member");
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertProduct();

            }
        });

    }


    private void insertProduct(){
        String tit = title.getText().toString();
        String cat = category.getText().toString();
        String pri = price.getText().toString().trim();
        String quan = quantity.getText().toString();

        String no = nos.getText().toString().trim();


        Member member = new Member(cat,no,pri,quan,tit);
        reff.push().setValue(member);
        Toast.makeText(ProductAdd.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
    }


}
