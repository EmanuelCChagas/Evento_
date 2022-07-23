package com.howiv.evento_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.howiv.evento_.adapter.ArtistaAdapter;
import com.howiv.evento_.model.Artista;

public class TelaArtistasActivity extends AppCompatActivity {

    List<Artista> artistas = new ArrayList<>();
    List<Artista> artistasParaExcluir =  new ArrayList<Artista>();
    RecyclerView recyclerView;
    ArtistaAdapter artistaAdapter;
    ArtistaAdapter acessoArtistaAdapter;
    View.OnClickListener clickListener;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_artistas);

        recyclerView = findViewById(R.id.recyclerView_artistas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConstraintLayout layoutArtista = (ConstraintLayout) view.findViewById(R.id.idLayoutArtista);
                RecyclerView recViewParent = (RecyclerView)layoutArtista.getParent();
                int layoutArtistaIndex = recViewParent.getChildAdapterPosition(layoutArtista);
                for (int i = 0; i < artistas.size(); i++) {
                   if(artistas.get(i) == null){
                       artistas.remove(i);
                   }
                }
                //editar artistas tela
                //artistas.get(layoutArtistaIndex);

            }
        };

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Artistas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dn : snapshot.getChildren()) {
                    Artista artista = dn.getValue(Artista.class);
                    artista.id = dn.getKey();
                    artistas.add(artista);
                    artistaAdapter = new ArtistaAdapter(artistas,clickListener);
                    recyclerView.setAdapter(artistaAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("FIREBASE", "erro relacionado ao banco de dados" + error);
            }});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_artista, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.itemLixoArtista){
            obterArtistaListaExcluir();
              if(artistasParaExcluir.size() > 0){
                    //mostrar dialog para excluir
              }
        }
        return super.onOptionsItemSelected(item);
    }


    public void abrirTelaAddArtista(View v) {
        Intent telaAddArtistaIntent = new Intent(this, TelaAdicionarArtistasActivity.class);
        startActivity(telaAddArtistaIntent);
    }

    public void obterArtistaListaExcluir(){
        if(acessoArtistaAdapter.listaCheckboxArtistas != null) {
            if(acessoArtistaAdapter.listaCheckboxArtistas.size() > 0){
                for (int i = 0; i < acessoArtistaAdapter.listaCheckboxArtistas.size(); i++) {
                    artistasParaExcluir.add(acessoArtistaAdapter.listaCheckboxArtistas.get(i));
                }
            }
        }
    }
}