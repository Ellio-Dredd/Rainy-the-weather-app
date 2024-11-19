package com.example.rainy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class df_ReceyclerViewAdapter extends RecyclerView.Adapter<df_ReceyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<dayForcastModel> ForcastModels;


    public df_ReceyclerViewAdapter(Context context, ArrayList<dayForcastModel> ForcastModels){
        this.context = context;
        this.ForcastModels =ForcastModels;
    }


    @NonNull
    @Override
    public df_ReceyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //giving a look to our row

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_veiw_row,parent,false);

        return new df_ReceyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull df_ReceyclerViewAdapter.MyViewHolder holder, int position) {
        // assinge values to the view (bindng)

        holder.tvname.setText(ForcastModels.get(position).getDayName());
        holder.tvforcast.setText(ForcastModels.get(position).getDayForcast());
    }

    @Override
    public int getItemCount() {
        //number of iteams u want to display
        return ForcastModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
            // grabbing the view from our on recycler_view_row layout file


        TextView tvname, tvforcast;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvname = itemView.findViewById(R.id.textView2);
            tvforcast = itemView.findViewById(R.id.textView3);


        }
    }


}
