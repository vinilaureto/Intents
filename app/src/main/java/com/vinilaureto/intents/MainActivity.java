package com.vinilaureto.intents;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.vinilaureto.intents.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private ActivityResultLauncher<Intent> outraActivityResultLauncher;
    public static String parametro = "PARAMETRO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        getSupportActionBar().setTitle("Tratando Intents");
        getSupportActionBar().setSubtitle("Principais tipos");

        outraActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                activityMainBinding.retornoTv.setText(result.getData().getStringExtra(parametro));
            }
        });

        Log.v("Tag" , "onCreate: Início CC");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("Tag" , "onStart: Início CV");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("Tag" , "onResume: Início CPP");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("Tag" , "onPause: Fim CPP");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("Tag" , "onStop: Fim CV");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("Tag" , "onDestroy: Fim CC");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //return super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.outraActivityMi) {
            Intent outraActivityIntent = new Intent(this, OutraActivity.class);
            outraActivityIntent.putExtra(parametro, activityMainBinding.parametroEt.getText().toString());
            outraActivityResultLauncher.launch(outraActivityIntent);
            return true;
        }

        return false;
    }
}