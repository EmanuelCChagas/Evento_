package com.howiv.evento_;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.gson.reflect.TypeToken;
import com.howiv.evento_.adapter.ArtistaAdapter;
import com.howiv.evento_.adapter.ArtistaBandaAdapter;
import com.howiv.evento_.model.Artista;
import com.howiv.evento_.model.Banda;
import com.howiv.evento_.utils.ConfirmDialog;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TelaAdicionarEditarBandasActivity extends AppCompatActivity {

    private Button btnSalvarBanda;
    private DatabaseReference databaseReferencia;
    ActivityResultLauncher<Intent> abreArtistaActivity;
    List<Artista> artistasSelecionados =  new ArrayList<Artista>();
    ArtistaBandaAdapter artistaAdapter;
    RecyclerView recyclerView;
    Button btnAdicionarIntegrantes;
    Boolean editarBandaActivity = false;
    private Banda bandaParaEditar = null;
    List<Artista> artistasParaRetirar =  new ArrayList<Artista>();
    List<Artista> artistasEdicaoBanda =  new ArrayList<Artista>();
    Menu menuBanda;
    View.OnClickListener checkboxClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_adicionar_bandas);
        getSupportActionBar().setTitle("Adicionar Bandas");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF4E71AE));

        checkboxClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkboxArtista = view.findViewById(R.id.checkBox_artista);

                //obter index do artista
                ConstraintLayout layoutArtista = (ConstraintLayout) checkboxArtista.getParent();
                MaterialCardView layoutCardArtista = (MaterialCardView) layoutArtista.getParent();
                RecyclerView recViewParent = (RecyclerView) layoutCardArtista.getParent();
                int layoutCardArtistaIndex = recViewParent.getChildAdapterPosition(layoutCardArtista);
                if(editarBandaActivity){
                    Artista artistaEscolhido = artistasEdicaoBanda.get(layoutCardArtistaIndex);
                    boolean isChecked =  checkboxArtista.isChecked();
                    if(isChecked){
                        artistasParaRetirar.add(artistaEscolhido);
                        if(menuBanda != null)
                            menuBanda.findItem(R.id.itemLixoBanda).setVisible(true);
                    }else{
                        if(artistasParaRetirar.size() > 0){
                            artistasParaRetirar.remove(artistaEscolhido);
                        }
                        else{
                            if(menuBanda != null)
                                menuBanda.findItem(R.id.itemLixoBanda).setVisible(false);
                        }
                    }
                }
                else{
                    Artista artistaEscolhido = artistasSelecionados.get(layoutCardArtistaIndex);
                    boolean isChecked =  checkboxArtista.isChecked();
                    if(isChecked){
                        artistasParaRetirar.add(artistaEscolhido);
                    }else{
                        if(artistasParaRetirar.size() > 0){
                            artistasParaRetirar.remove(artistaEscolhido);
                        }
                    }
                }
            }
        };

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            editarBandaActivity = extras.getBoolean("EditarBanda");
            if(editarBandaActivity){
                getSupportActionBar().setTitle("Editar Banda");
                Gson gson = new Gson();
                bandaParaEditar = gson.fromJson(getIntent().getStringExtra("EditarBandaDados"), Banda.class);
                if(bandaParaEditar != null){
                    popularDadosBandaEditar(bandaParaEditar);
                }

            }
        }
        recyclerView = findViewById(R.id.recyclerView_artistas_bandas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnSalvarBanda = findViewById(R.id.btn_salvar_banda);
        btnAdicionarIntegrantes = findViewById(R.id.btn_adicionar_integrante_banda);
        btnSalvarBanda.setOnClickListener(view ->  {
            EditText nomeView =  findViewById(R.id.input_nome_banda);

            String nome = nomeView.getText().toString();
                if (nome.isEmpty() || artistasSelecionados.size() == 0) {
                    Toast.makeText(this, "Há campos não preenchidos!", Toast.LENGTH_SHORT).show();
                } else {
                    if(!editarBandaActivity) {
                        Banda banda = new Banda(null, nome, artistasSelecionados);
                    databaseReferencia.child("Bandas").push().setValue(banda, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if (error != null) {
                                Toast.makeText(TelaAdicionarEditarBandasActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(TelaAdicionarEditarBandasActivity.this, "Banda Salvo com Sucesso", Toast.LENGTH_LONG).show();
                                limparCampos();
                            }

                        }
                    });
                    }
                    else{
                        Banda banda = new Banda(null, nome, artistasEdicaoBanda);
                        Map<String, Object> bandaUpdate = new HashMap<>();
                        bandaUpdate.put("/Bandas/" + bandaParaEditar.id,banda);
                        databaseReferencia.updateChildren(bandaUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(TelaAdicionarEditarBandasActivity.this,"Banda Salvo com Sucesso", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(TelaAdicionarEditarBandasActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                    }
                }
        });
        databaseReferencia = FirebaseDatabase.getInstance().getReference();
        databaseReferencia.child("Bandas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot != null){
                    Log.i("FIREBASE", snapshot.getValue().toString());
                    if(!editarBandaActivity){
                        limparCampos();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if(error !=null){
                    Log.i("FIREBASE", "erro relacionado ao banco de dados" + error);
                }
            }
        });
        abreArtistaActivity =
                    registerForActivityResult(
                            new ActivityResultContracts.StartActivityForResult(),
                            new ActivityResultCallback<ActivityResult>() {
                                @Override
                                public void onActivityResult(ActivityResult result) {
                                    if (result.getResultCode() == Activity.RESULT_OK) {
                                        // There are no request codes
                                        Intent data = result.getData();
                                        Gson gson = new Gson();
                                        Type listType = new TypeToken<ArrayList<Artista>>(){}.getType();
                                       // adicionar artistas caso ja tenha artistas
                                        if (artistasSelecionados.size() > 0) {
                                            List<Artista> artistasSelecionadosJson =  new ArrayList<Artista>();
                                            artistasSelecionadosJson = gson.fromJson(data.getStringExtra("dadosArtistaLista"), listType);
                                            for (int i = 0; i < artistasSelecionadosJson.size(); i++) {
                                                artistasSelecionados.add(artistasSelecionadosJson.get(i));
                                            }

                                        }
                                        else{
                                            artistasSelecionados = gson.fromJson(data.getStringExtra("dadosArtistaLista"), listType);
                                        }

                                         if(!editarBandaActivity){
                                             artistaAdapter = new ArtistaBandaAdapter(artistasSelecionados,checkboxClickListener);
                                             if(artistasSelecionados != null){
                                                // btnAdicionarIntegrantes.setVisibility(View.GONE);
                                                 recyclerView.setVisibility(View.VISIBLE);
                                                 recyclerView.setAdapter(artistaAdapter);
                                             }
                                         }
                                         else{
                                             // adicionar artistas na lista de edição bandas
                                             for (int i = 0; i < artistasSelecionados.size(); i++) {
                                                 artistasEdicaoBanda.add(artistasSelecionados.get(i));
                                             }
                                             artistaAdapter = new ArtistaBandaAdapter(artistasEdicaoBanda,checkboxClickListener);
                                             if(artistasSelecionados != null){
                                                // btnAdicionarIntegrantes.setVisibility(View.GONE);
                                                 recyclerView.setVisibility(View.VISIBLE);
                                                 recyclerView.setAdapter(artistaAdapter);
                                             }
                                         }

                                    }
                                }
                            });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuBanda = menu;
        getMenuInflater().inflate(R.menu.menu_banda, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void confirmaExclusaoDeArtistaSelecionados() {
        if(editarBandaActivity){
            for (int i = 0; i < artistasParaRetirar.size(); i++) {
                if (artistasParaRetirar.get(i).id != null) {
                    artistasEdicaoBanda.remove(artistasParaRetirar.get(i));
                }
            }

            if(artistasEdicaoBanda.size() <= 0){
                btnAdicionarIntegrantes.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        }
        else{
            for (int i = 0; i < artistasParaRetirar.size(); i++) {
                if (artistasParaRetirar.get(i).id != null) {
                    artistasSelecionados.remove(artistasParaRetirar.get(i));
                }
            }
            if(artistasSelecionados.size() <= 0){
                btnAdicionarIntegrantes.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        }
        recyclerView.setAdapter(artistaAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.itemLixoBanda){
//              if(artistasParaExcluir.size() > 0){
            confirmaExclusaoDeArtistaSelecionados();
//              }
        }
        return super.onOptionsItemSelected(item);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void adicionarIntegrantesBanda(View v) {
        //adicionar artistas na banda tela
            Intent telaAddArtistaBandaIntent = new Intent(TelaAdicionarEditarBandasActivity.this, TelaArtistasActivity.class);
            telaAddArtistaBandaIntent.putExtra("AdicionarBanda", true);
            abreArtistaActivity.launch(telaAddArtistaBandaIntent);
    }


    public void popularDadosBandaEditar(Banda bandaDados){
        EditText nomeView =  (EditText) findViewById(R.id.input_nome_banda);
        RecyclerView recyclerViewIntegrantes = (RecyclerView)findViewById(R.id.recyclerView_artistas_bandas);
        nomeView.setText((String)bandaDados.nome);
        for (int i = 0; i < bandaDados.integrantes.size(); i++) {
            artistasEdicaoBanda.add(bandaDados.integrantes.get(i));
        }

        if(artistasEdicaoBanda.size() > 0){
            artistaAdapter = new ArtistaBandaAdapter(artistasEdicaoBanda,checkboxClickListener);
            //btnAdicionarIntegrantes = findViewById(R.id.btn_adicionar_integrante_banda);
            //btnAdicionarIntegrantes.setVisibility(View.GONE);
            recyclerViewIntegrantes.setVisibility(View.VISIBLE);
            recyclerViewIntegrantes.setAdapter(artistaAdapter);
        }
    }


    public void limparCampos(){
        TextView nome =  findViewById(R.id.input_nome_banda);
        nome.setText("");
       // funcao.setText("");
    }

}