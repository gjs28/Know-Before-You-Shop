package com.example.knowbeforeyoushop;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.eventbus.EventBus;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class MyAdapterUpdate extends FirebaseRecyclerAdapter<Member, MyAdapterUpdate.MyViewHolder> {

    public MyAdapterUpdate(@NonNull FirebaseRecyclerOptions<Member> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, final int position, @NonNull Member model) {

        holder.cate.setText(String.valueOf(model.getCategory()));
        holder.no.setText(String.valueOf(model.getNumber()));
        holder.price.setText(String.valueOf(model.getPrice()));
        holder.quant.setText(String.valueOf(model.getQuant()));
        holder.title.setText(String.valueOf(model.getTitle()));

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogPlus dialogPlus=DialogPlus.newDialog(view.getContext()).setContentHolder(new ViewHolder(R.layout.dialog_content))
                        .setExpanded(true,1300).create();

                View myview=dialogPlus.getHolderView();
                EditText noDc=myview.findViewById(R.id.availDc);
                EditText priceDc=myview.findViewById(R.id.priceDc);
                EditText quantDc=myview.findViewById(R.id.quantityDc);
                EditText titleDc=myview.findViewById(R.id.titleDc);
                Button update=myview.findViewById(R.id.upBtn);

                titleDc.setText(model.getTitle());
                priceDc.setText(String.valueOf(model.getPrice()));
                quantDc.setText(model.getQuant());
                noDc.setText(String.valueOf(model.getNumber()));

                dialogPlus.show();

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("title",titleDc.getText().toString());
                        map.put("quant",quantDc.getText().toString());
                        map.put("price",priceDc.getText().toString());
                        map.put("number",noDc.getText().toString());


                        FirebaseDatabase.getInstance().getReference().child("Member").child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.title.getContext(), "Data updated successfully",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(holder.title.getContext(), "Error!",Toast.LENGTH_SHORT).show();
                                dialogPlus.dismiss();
                            }
                        });


                    }
                });

            }
        });


    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.itemupdate,parent,false);
        return new MyViewHolder(view);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title,cate,price,quant,no;
        ImageView edit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title=(TextView) itemView.findViewById(R.id.itemup);
            cate=(TextView) itemView.findViewById(R.id.categoryup);
            price=(TextView) itemView.findViewById(R.id.priceup);
            quant=(TextView) itemView.findViewById(R.id.quantityup);
            no=(TextView) itemView.findViewById(R.id.availup);

            edit=(ImageView) itemView.findViewById(R.id.editBtn);


        }
    }
}
