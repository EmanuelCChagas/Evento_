package com.howiv.evento_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.howiv.evento_.model.Banda;

import java.util.ArrayList;
import java.util.List;

public class TelaBandasActivity extends AppCompatActivity {

    private List<Banda> bandas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_bandas);
    }

    public void abrirTelaAddBanda(View view) {
//        startActivity(new Intent(this, TelaAdicionarBandasActivity.class));
    }

}