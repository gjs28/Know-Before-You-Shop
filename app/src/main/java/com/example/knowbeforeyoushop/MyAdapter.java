package com.example.knowbeforeyoushop;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;



public class MyAdapter extends FirebaseRecyclerAdapter<Member,MyAdapter.MyViewHolder> {

    public MyAdapter(@NonNull FirebaseRecyclerOptions<Member> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Member model) {

        holder.cate.setText(String.valueOf(model.getCategory()));
        holder.no.setText(String.valueOf(model.getNumber()));
        holder.price.setText(String.valueOf(model.getPrice()));
        holder.quant.setText(String.valueOf(model.getQuant()));
        holder.title.setText(model.getTitle());



    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new MyViewHolder(view);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title,cate,price,quant,no;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title=(TextView) itemView.findViewById(R.id.item);
            cate=(TextView) itemView.findViewById(R.id.categoryitem);
            price=(TextView) itemView.findViewById(R.id.priceitem);
            quant=(TextView) itemView.findViewById(R.id.quantityitem);
            no=(TextView) itemView.findViewById(R.id.availitem);


        }
    }
}
