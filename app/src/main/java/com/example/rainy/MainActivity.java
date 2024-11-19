package com.example.rainy;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

    public View date;
    public View time;
    public ImageView animation;
    public Text city;
    public Text temp;
    public Text condition;
    public RecyclerView rv_rainy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        date = findViewById(R.id.txtDate);
        time = findViewById(R.id.time);
        animation = findViewById(R.id.animation);
        city = findViewById(R.id.city);
        temp = findViewById(R.id.temp);
        condition = findViewById(R.id.condition);
        rv_rainy = findViewById(R.id.RVrainy);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}