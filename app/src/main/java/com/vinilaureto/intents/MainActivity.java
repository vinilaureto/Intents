package com.vinilaureto.intents;

import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.EXTRA_TITLE;
import static android.content.Intent.ACTION_CHOOSER;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.vinilaureto.intents.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private ActivityResultLauncher<Intent> outraActivityResultLauncher;
    private ActivityResultLauncher<Intent> escolherImagemActivityResultLauncher;
    private ActivityResultLauncher<Intent> escolherAplicativoActivityResultLauncher;
    public static final String PARAMETRO = "PARAMETRO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        getSupportActionBar().setTitle("Tratando Intents");
        getSupportActionBar().setSubtitle("Principais tipos");

        outraActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                activityMainBinding.retornoTv.setText(result.getData().getStringExtra(PARAMETRO));
            }
        });

        escolherImagemActivityResultLauncher = registerForActivityResult( new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK){
                    Intent escolheImagemIntent = new Intent(ACTION_VIEW);
                    escolheImagemIntent.setData(result.getData().getData());
                    startActivity(escolheImagemIntent);
                }
            });

        escolherAplicativoActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK){
                    Intent escolherAppIntent = new Intent(ACTION_VIEW);
                    escolherAppIntent.setData(result.getData().getData());
                    startActivity(escolherAppIntent);
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //return super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.outraActivityMi: {
                Intent outraActivityIntent = new Intent(this, OutraActivity.class);
                outraActivityIntent.putExtra(PARAMETRO, activityMainBinding.parametroEt.getText().toString());
                outraActivityResultLauncher.launch(outraActivityIntent);
                return true;
            }

            case R.id.viewMi: {
                Intent navegadorIntent = new Intent(ACTION_VIEW);
                navegadorIntent.setData(Uri.parse(activityMainBinding.parametroEt.getText().toString()));
                startActivity(navegadorIntent);

                return true;
            }
            case R.id.callMi: {
                verificarPermissaoLigacao();
                return true;
            }
            case R.id.dialMi: {
                Intent discarIntent = new Intent(Intent.ACTION_DIAL);
                discarIntent.setData(Uri.parse("tel: " + activityMainBinding.parametroEt.getText().toString()));
                startActivity(discarIntent);
                return true;
            }
            case R.id.pickMi: {
                escolherImagemActivityResultLauncher.launch(selecionadorDeImagens());
                return true;
            }
            case R.id.chooserMi: {
                Intent escolherActivityIntent = new Intent(ACTION_CHOOSER);
                escolherActivityIntent.putExtra(Intent.EXTRA_INTENT, selecionadorDeImagens());
                escolherActivityIntent.putExtra(EXTRA_TITLE, "Escolha um aplicativo");
                escolherAplicativoActivityResultLauncher.launch(escolherActivityIntent);
            }

        }

        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void verificarPermissaoLigacao() {
        Intent ligarIntent = new Intent(Intent.ACTION_CALL);
        ligarIntent.setData(Uri.parse("tel: " + activityMainBinding.parametroEt.getText().toString()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                startActivity(ligarIntent);
            }   else{
                int CALL_PHONE_PERMISSION_REQUEST_CODE = 1;
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_PERMISSION_REQUEST_CODE);
            }
        }   else{
            startActivity(ligarIntent);
        }
    }

    private Intent selecionadorDeImagens(){
        Intent selecionarImagens = new Intent(Intent.ACTION_PICK);
        String pastaDeImagens = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();
        selecionarImagens.setDataAndType(Uri.parse(pastaDeImagens), "image/*");

        return selecionarImagens;
    }
}