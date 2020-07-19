package com.example.assignment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profile;
    private TextView nameAndAge;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sp = this.getSharedPreferences("assignment", Context.MODE_PRIVATE);
        profile = findViewById(R.id.profile_pic);
        nameAndAge = findViewById(R.id.name_and_age);
        Glide.with(this).load(sp.getString("uri","")).circleCrop()
                .placeholder(R.drawable.ic_account).into(profile);
        if(sp.getString("age","").length()>0) {
            String naa = sp.getString("name", "") + ", " + sp.getString("age", "");
            nameAndAge.setText(naa);
        }else{
            String naa = sp.getString("name","")+", cannot find age";
            nameAndAge.setText(naa);
        }
    }
}
