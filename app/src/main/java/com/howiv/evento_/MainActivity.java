package com.howiv.evento_;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    //private DatabaseReference databaseReferencia = FirebaseDatabase.getInstance().getReference();
    private Button btnDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF4E71AE));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemEventos:
                this.abrirEventos();
                break;
            case R.id.itemArtistas:
                this.abrirArtistas();
                break;
            case R.id.itemBandas:
                this.abrirBandas();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public final void abrirEventos() {
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public final void abrirArtistas() {
        Intent telaArtistaIntent = new Intent(this, TelaArtistasActivity.class);
        startActivity(telaArtistaIntent);
    }

    public final void abrirBandas() {
        Intent telaBandasIntent = new Intent(this, TelaBandasActivity.class);
        startActivity(telaBandasIntent);
    }

}