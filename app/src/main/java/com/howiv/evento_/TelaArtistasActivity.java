package com.howiv.evento_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.howiv.evento_.adapter.ArtistaAdapter;
import com.howiv.evento_.model.Artista;

public class TelaArtistasActivity extends AppCompatActivity {

    List<Artista> artistas;
    RecyclerView recyclerView;
     ArtistaAdapter artistaAdapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_artistas);

        recyclerView = findViewById(R.id.recyclerView_artistas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        artistas = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Artistas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dn:snapshot.getChildren()){
                    Artista artista = dn.getValue(Artista.class);
                    artistas.add(artista);
                    System.out.print(artista);
                    artistaAdapter = new ArtistaAdapter(artistas);
                    recyclerView.setAdapter(artistaAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("FIREBASE", "erro relacionado ao banco de dados" + error);
            }
        });

    }

    public void abrirTelaAddArtista(View v){
        Intent telaAddArtistaIntent = new Intent(this, TelaAdicionarArtistasActivity.class);
        startActivity(telaAddArtistaIntent);
    }
}