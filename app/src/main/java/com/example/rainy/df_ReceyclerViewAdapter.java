package com.example.rainy;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class df_ReceyclerViewAdapter extends RecyclerView.Adapter<df_ReceyclerViewAdapter.MyViewHolder> {
    @NonNull
    @Override
    public df_ReceyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //giving a look to our row
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull df_ReceyclerViewAdapter.MyViewHolder holder, int position) {
        // assinge values to the view (bindng)
    }

    @Override
    public int getItemCount() {
        //number of iteams u want to display
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
            // grabbing the view from our on recycler_view_row layout file


        TextView tvname,tvforcast;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
