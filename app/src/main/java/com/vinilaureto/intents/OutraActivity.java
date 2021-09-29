package com.vinilaureto.intents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.vinilaureto.intents.databinding.ActivityOutraBinding;

import static com.vinilaureto.intents.MainActivity.PARAMETRO;

public class OutraActivity extends AppCompatActivity {
    private ActivityOutraBinding activityOutraBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityOutraBinding = ActivityOutraBinding.inflate(getLayoutInflater());
        setContentView(activityOutraBinding.getRoot());

        getSupportActionBar().setTitle("Outra Activity");
        getSupportActionBar().setSubtitle("Recebe e retorna um valor");

        activityOutraBinding.recebidoTv.setText(getIntent().getStringExtra(PARAMETRO));

        activityOutraBinding.retornarBt.setOnClickListener(view -> {
            String result = activityOutraBinding.retornoEt.getText().toString();

            Intent retornoIntent = new Intent();
            retornoIntent.putExtra(PARAMETRO, result);
            setResult(RESULT_OK, retornoIntent);
            finish();
        });

    }
}