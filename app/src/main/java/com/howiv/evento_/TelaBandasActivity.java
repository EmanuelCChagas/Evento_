package com.howiv.evento_;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.howiv.evento_.adapter.ArtistaAdapter;
import com.howiv.evento_.adapter.BandaAdapter;
import com.howiv.evento_.model.Artista;
import com.howiv.evento_.model.Banda;
import com.howiv.evento_.utils.ConfirmDialog;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TelaBandasActivity extends AppCompatActivity {

    Boolean adicionarEventoBandaActivity = false;
    private List<Banda> bandas = new ArrayList<>();
    List<Banda> bandasParaExcluir =  new ArrayList<Banda>();
    Menu menuBanda;
    RecyclerView recyclerView;
    BandaAdapter bandaAdapter;
    BandaAdapter acessoBandaAdapter;
    View.OnClickListener clickListener;
    View.OnLongClickListener longClickListener;
    View.OnClickListener checkboxClickListener;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_bandas);
        getSupportActionBar().setTitle("Bandas");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF4E71AE));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            adicionarEventoBandaActivity = extras.getBoolean("AdicionarEvento");
            if(adicionarEventoBandaActivity){
                Button novoBandaButton = findViewById(R.id.btn_novo_bandas);
                getSupportActionBar().setTitle("Adicionar Bandas");
                ViewGroup layout = (ViewGroup) novoBandaButton.getParent();
                if(null!=layout)
                    layout.removeView(novoBandaButton);

            }
        }
        recyclerView = findViewById(R.id.recyclerView_bandas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkboxBanda = view.findViewById(R.id.checkBox_banda);
                if(checkboxBanda.getVisibility() == View.INVISIBLE) {
                    //obter index da Banda para editar
                    ConstraintLayout layoutBanda = (ConstraintLayout) view.findViewById(R.id.idLayoutBanda);
                    MaterialCardView layoutCardBanda = (MaterialCardView) layoutBanda.getParent();
                    RecyclerView recViewParent = (RecyclerView) layoutCardBanda.getParent();
                    int layoutCardBandaIndex = recViewParent.getChildAdapterPosition(layoutCardBanda);

                    //caso alguma banda for null retirar da lista
                    for (int i = 0; i < bandas.size(); i++) {
                        if (bandas.get(i) == null) {
                            bandas.remove(i);
                        }
                    }
                    //editar bandas tela
                    Banda bandaParaEditar = bandas.get(layoutCardBandaIndex);
                    Intent i = new Intent(TelaBandasActivity.this, TelaAdicionarEditarBandasActivity.class);
                    i.putExtra("EditarBanda", true);
                   Gson gson = new Gson();
                   String bandaEditarJson = gson.toJson(bandaParaEditar);
                   i.putExtra("EditarBandaDados", bandaEditarJson);
                   startActivity(i);
                }
            }
        };

        longClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                CheckBox checkboxBanda = view.findViewById(R.id.checkBox_banda);
                if (checkboxBanda.getVisibility() == View.INVISIBLE) {
                    boolean isCheckboxMudado = setAllCheckboxVisibility(true);
                    if(isCheckboxMudado){
                        checkboxBanda.setChecked(true);
                    }
                } else {
                    setAllCheckboxVisibility(false);
                    checkboxBanda.setVisibility(View.INVISIBLE);
                }
                return true;
            }
        };

        checkboxClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkboxBanda = view.findViewById(R.id.checkBox_banda);

                //obter index da Banda
                ConstraintLayout layoutBanda = (ConstraintLayout) checkboxBanda.getParent();
                MaterialCardView layoutCardBanda = (MaterialCardView) layoutBanda.getParent();
                RecyclerView recViewParent = (RecyclerView) layoutCardBanda.getParent();
                int layoutCardBandaIndex = recViewParent.getChildAdapterPosition(layoutCardBanda);

                Banda bandaEscolhida = bandas.get(layoutCardBandaIndex);

                boolean isChecked =  checkboxBanda.isChecked();
                if(isChecked){
                    bandasParaExcluir.add(bandaEscolhida);
                }else{
                    if(bandasParaExcluir.size() > 0){
                        bandasParaExcluir.remove(bandaEscolhida);
                    }
                }
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
                    bandaAdapter = new BandaAdapter(bandas,clickListener,longClickListener,checkboxClickListener);
                    recyclerView.setAdapter(bandaAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("FIREBASE", "erro relacionado ao banco de dados" + error);
            }});
    }



    @Override
    protected void onResume() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Bandas").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot abc = task.getResult();
                if (!task.isSuccessful()) {
                    Toast.makeText(TelaBandasActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    bandas.clear();
                    for (DataSnapshot dn : task.getResult().getChildren()) {
                        Banda banda = dn.getValue(Banda.class);
                        banda.id = dn.getKey();
                        bandas.add(banda);
                        bandaAdapter = new BandaAdapter(bandas,clickListener,longClickListener,checkboxClickListener);
                        recyclerView.setAdapter(bandaAdapter);
                    }
                }
            }
        });
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuBanda = menu;
        getMenuInflater().inflate(R.menu.menu_banda, menu);
        return super.onCreateOptionsMenu(menu);
    }



    public void confirmaExclusaoDeBandasSelecionadas() {
        // excluir os selecionados
        if (bandasParaExcluir.isEmpty()) {
            //bandasParaExcluir.add(new Artista(null, "Super-Man", null));
            //bandasParaExcluir.add(new Artista(null, "Wonderwoman", null));
        }

        for (int i = 0; i < bandasParaExcluir.size(); i++) {
            if(bandasParaExcluir.get(i).id != null){
                String idExcluir = bandasParaExcluir.get(i).id;
                databaseReference.child("Bandas").child(idExcluir).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(TelaBandasActivity.this,"Banda Deletado com Sucesso", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(TelaBandasActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.itemLixoBanda){
            if(bandasParaExcluir.size() > 0){
                ConfirmDialog.confirmDelete(this, this::confirmaExclusaoDeBandasSelecionadas);
            }
        }
        return super.onOptionsItemSelected(item);
    }


    public void abrirTelaAddBanda(View v) {
        Intent telaAddBandaIntent = new Intent(this, TelaAdicionarEditarBandasActivity.class);
        startActivity(telaAddBandaIntent);
    }


    public boolean setAllCheckboxVisibility(boolean visibilidade) {
        try {
            if (visibilidade) {
                bandaAdapter.mudarVisibilidadeTodosCheckbox(true);
                for (int i = 0; i < bandaAdapter.getItemCount(); i++) {
                    // colocar todos os checkbox visiveis
                    bandaAdapter.notifyItemChanged(i);
                    if(menuBanda != null)
                        menuBanda.findItem(R.id.itemLixoBanda).setVisible(true);
                }
                return true;
            } else {
                bandaAdapter.mudarVisibilidadeTodosCheckbox(false);
                for (int i = 0; i < bandaAdapter.getItemCount(); i++) {
                    // colocar todos os checkbox visiveis
                    bandaAdapter.notifyItemChanged(i);
                    if(menuBanda != null)
                        menuBanda.findItem(R.id.itemLixoBanda).setVisible(false);
                }
                return true;
            }

        } catch (Exception ex) {
            Log.d("Error",ex.getMessage());
            return false;
        }
    }

}