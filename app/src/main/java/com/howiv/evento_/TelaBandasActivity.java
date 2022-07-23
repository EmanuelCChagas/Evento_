package com.howiv.evento_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.howiv.evento_.adapter.BandaAdapter;
import com.howiv.evento_.model.Banda;

import java.util.ArrayList;
import java.util.List;

public class TelaBandasActivity extends AppCompatActivity {

    private List<Banda> bandas = new ArrayList<>();
    List<Banda> bandasParaExcluir =  new ArrayList<Banda>();
    RecyclerView recyclerView;
    BandaAdapter bandaAdapter;
    BandaAdapter acessoBandaAdapter;
    View.OnClickListener clickListener;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_bandas);
        getSupportActionBar().setTitle("Bandas");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF4E71AE));

        recyclerView = findViewById(R.id.recyclerView_bandas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConstraintLayout layoutBanda = (ConstraintLayout) view.findViewById(R.id.idLayoutBanda);
                RecyclerView recViewParent = (RecyclerView)layoutBanda.getParent();
                int layoutBandaIndex = recViewParent.getChildAdapterPosition(layoutBanda);
                for (int i = 0; i < bandas.size(); i++) {
                    if(bandas.get(i) == null){
                        bandas.remove(i);
                    }
                }
                //editar artistas tela
                //artistas.get(layoutArtistaIndex);

            }
        };

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Bandas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dn : snapshot.getChildren()) {
                    Banda banda = dn.getValue(Banda.class);
                    banda.id = dn.getKey();
                    bandas.add(banda);
                    bandaAdapter = new BandaAdapter(bandas,clickListener);
                    recyclerView.setAdapter(bandaAdapter);
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
        if(item.getItemId() == R.id.itemLixoBanda){
            obterBandaListaExcluir();
            if(bandasParaExcluir.size() > 0){
                //mostrar dialog para excluir
            }
        }
        return super.onOptionsItemSelected(item);
    }


    public void abrirTelaAddBanda(View v) {
        Intent telaAddBandaIntent = new Intent(this, TelaAdicionarEditarBandasActivity.class);
        startActivity(telaAddBandaIntent);
    }

    public void obterBandaListaExcluir(){
        if(acessoBandaAdapter.listaCheckboxBandas != null) {
            if(acessoBandaAdapter.listaCheckboxBandas.size() > 0){
                for (int i = 0; i < acessoBandaAdapter.listaCheckboxBandas.size(); i++) {
                    bandasParaExcluir.add(acessoBandaAdapter.listaCheckboxBandas.get(i));
                }
            }
        }
    }
}