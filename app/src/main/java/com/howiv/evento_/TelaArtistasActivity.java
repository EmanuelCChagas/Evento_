package com.howiv.evento_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import com.howiv.evento_.model.Artista;

public class TelaArtistasActivity extends AppCompatActivity {

    List<Artista> artistas;
    RecyclerView recyclerView;
    // contaAdapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_artistas);
    }
}