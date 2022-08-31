package com.example.changelightdarkthemes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
        import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private Button switchs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    switchs=findViewById(R.id.switchs);


        SharedPreferences sharedPreferenelegate =getSharedPreferences("Sharedprefs", MODE_PRIVATE);

        final SharedPreferences.Editor editor
                = sharedPreferenelegate.edit();
        final boolean isDarkModeOn
                = sharedPreferenelegate
                .getBoolean(
                        "isDarkModeOn", false);


        if (isDarkModeOn) {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES);

        }
        else {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_NO);

        }




        switchs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isDarkModeOn){
                    AppCompatDelegate
                            .setDefaultNightMode(AppCompatDelegate
                                    .MODE_NIGHT_NO);


                    editor.putBoolean(
                            "isDarkModeOn", false);
                    editor.apply();
                }else{

                    AppCompatDelegate
                            .setDefaultNightMode(AppCompatDelegate
                                    .MODE_NIGHT_YES);

                    editor.putBoolean(
                            "isDarkModeOn", true);
                    editor.apply();
                }


            }
        });





    }
}