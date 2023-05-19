package com.HyperSenGames.izleparakazan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.HyperSenGames.izleparakazan.R;

import java.util.List;

public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.MyViewHolder>
{

List<String> list;
public RecylerViewAdapter(List<String> list)
{
    this.list = list;
}


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textView =(TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(textView);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.serialno.setText(list.get(position));
    holder.requestname.setText(list.get(position));
        holder.requestamount.setText(list.get(position));
        holder.requestbank.setText(list.get(position));
        holder.requestdate.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder

    {
        TextView requestname,requestamount,requestbank,requestdate,serialno;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            serialno = itemView.findViewById(R.id.serialno);
            requestname = itemView.findViewById(R.id.requestname);
            requestamount = itemView.findViewById(R.id.requestamount);
            requestbank = itemView.findViewById(R.id.requestbank);
            requestdate = itemView.findViewById(R.id.requestdate);



        }
    }
}
