package com.example.rainy;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Details extends AppCompatActivity {

    ArrayList<dayForcastModel> dayForcastModels = new ArrayList<>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);

        RecyclerView recyclerView = findViewById(R.id.fRecylerVeiw);

        setUpdayForcastModels();

        df_ReceyclerViewAdapter adapter = new df_ReceyclerViewAdapter(this,dayForcastModels);
        //make sure to make this after Setting up the forcast models
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    private void setUpdayForcastModels(){
        String[] dayNames = getResources().getStringArray(R.array.dayNames);
        String[] dayForcast = getResources().getStringArray(R.array.dayForcast);

        for (int i=0; i< dayNames.length; i++){
            dayForcastModels.add(new dayForcastModel(dayNames[i],dayForcast[i]));
        }


    }
}