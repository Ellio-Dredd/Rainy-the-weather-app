package com.example.rainy;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Details extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<dayForcastModel> dayForcastModels = new ArrayList<>() ;
    private static final String API_KEY = "YOUR_API_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firbase();


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


 //for database uses ---------------------------------------------------------

   public void firbase() {
        Map<String, Object> city = new HashMap<>();
        city.put("name", "Los Angeles");
        city.put("state", "CA");
        city.put("country", "USA");

       db.collection("cities").document("LA")
               .set(city)
               .addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {
                       Log.d(TAG, "DocumentSnapshot successfully written!");
                   }
               })
               .addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Log.w(TAG, "Error writing document", e);
                   }
               });

}



}