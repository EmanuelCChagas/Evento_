package com.howiv.evento_;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Build;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.howiv.evento_.adapter.ArtistaAdapter;
import com.howiv.evento_.model.Artista;
import com.howiv.evento_.utils.ConfirmDialog;

@RequiresApi(api = Build.VERSION_CODES.N)
public class TelaArtistasActivity extends AppCompatActivity {

    Boolean adicionarArtistaBandaActivity = false;
    List<Artista> artistas = new ArrayList<>();
    List<Artista> artistasParaExcluir =  new ArrayList<Artista>();
    List<Artista> artistasParaAdicionarBanda =  new ArrayList<Artista>();
    Menu menuArtista;
    RecyclerView recyclerView;
    ArtistaAdapter artistaAdapter;
    ArtistaAdapter acessoArtistaAdapter;
    View.OnClickListener clickListener;
    View.OnLongClickListener longClickListener;
    View.OnClickListener checkboxClickListener;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_artistas);
        getSupportActionBar().setTitle("Artistas");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF4E71AE));
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            adicionarArtistaBandaActivity = extras.getBoolean("AdicionarBanda");
            if(adicionarArtistaBandaActivity){
                MaterialButton novoArtistaButton = findViewById(R.id.btn_novo_artistas);
                getSupportActionBar().setTitle("Adicionar Artistas");
                LinearLayout layout = (LinearLayout) novoArtistaButton.getParent();
               if(layout != null){
                   layout.removeView(novoArtistaButton);
               }
                setAllCheckboxVisibility(true);
                MaterialButton adicionarArtistaBanda = findViewById(R.id.btn_adicionar_artistas_banda);
                adicionarArtistaBanda.setVisibility(View.VISIBLE);
            }
        }
        recyclerView = findViewById(R.id.recyclerView_artistas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkboxArtista = view.findViewById(R.id.checkBox_artista);
                if(checkboxArtista.getVisibility() == View.INVISIBLE) {
                    //obter index do artista para editar
                    ConstraintLayout layoutArtista = (ConstraintLayout) view.findViewById(R.id.idLayoutArtista);
                    MaterialCardView layoutCardArtista = (MaterialCardView) layoutArtista.getParent();
                    RecyclerView recViewParent = (RecyclerView) layoutCardArtista.getParent();
                    int layoutCardArtistaIndex = recViewParent.getChildAdapterPosition(layoutCardArtista);

                    //caso algum artista for null retirar da lista
                    for (int i = 0; i < artistas.size(); i++) {
                        if (artistas.get(i) == null) {
                            artistas.remove(i);
                        }
                    }
                    //editar artistas tela
                    Artista artistaParaEditar = artistas.get(layoutCardArtistaIndex);
                    Intent i = new Intent(TelaArtistasActivity.this, TelaAdicionarEditarArtistasActivity.class);
                    i.putExtra("EditarArtista", true);
                    Gson gson = new Gson();
                    String artistaEditarJson = gson.toJson(artistaParaEditar);
                    i.putExtra("EditarArtistaDados", artistaEditarJson);
                    startActivity(i);
                }
            }
        };

        longClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                CheckBox checkboxArtista = view.findViewById(R.id.checkBox_artista);
                if (checkboxArtista.getVisibility() == View.INVISIBLE) {
                    boolean isCheckboxMudado = setAllCheckboxVisibility(true);
                    //checkboxArtista.setVisibility(View.VISIBLE);
                    if(isCheckboxMudado){
                        checkboxArtista.setChecked(true);
                    }
                } else {
                    setAllCheckboxVisibility(false);
                    checkboxArtista.setVisibility(View.INVISIBLE);
                }
                return true;
            }
        };

        checkboxClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkboxArtista = view.findViewById(R.id.checkBox_artista);

                //obter index do artista
                ConstraintLayout layoutArtista = (ConstraintLayout) checkboxArtista.getParent();
                MaterialCardView layoutCardArtista = (MaterialCardView) layoutArtista.getParent();
                RecyclerView recViewParent = (RecyclerView) layoutCardArtista.getParent();
                int layoutCardArtistaIndex = recViewParent.getChildAdapterPosition(layoutCardArtista);
                Artista artistaEscolhido = artistas.get(layoutCardArtistaIndex);

                //caso for para adicionar artista numa banda pega o item e poe na lista e adicionar artistabanda
                //do contrario pega o item e coloca na lista de excluir
                if(adicionarArtistaBandaActivity){
                    boolean isChecked =  checkboxArtista.isChecked();
                    if(isChecked){
                        artistasParaAdicionarBanda.add(artistaEscolhido);
                    }else{
                        if(artistasParaAdicionarBanda.size() > 0){
                            artistasParaAdicionarBanda.remove(artistaEscolhido);
                        }
                    }
                }
                else{
                    boolean isChecked =  checkboxArtista.isChecked();
                    if(isChecked){
                        artistasParaExcluir.add(artistaEscolhido);
                    }else{
                        if(artistasParaExcluir.size() > 0){
                            artistasParaExcluir.remove(artistaEscolhido);
                        }
                    }
                }
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
                    artistaAdapter = new ArtistaAdapter(artistas,clickListener,longClickListener,checkboxClickListener);
                    recyclerView.setAdapter(artistaAdapter);
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
       databaseReference.child("Artistas").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DataSnapshot> task) {
               DataSnapshot abc = task.getResult();
               if (!task.isSuccessful()) {
                   Toast.makeText(TelaArtistasActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
               } else {
                   artistas.clear();
                   for (DataSnapshot dn : task.getResult().getChildren()) {
                       Artista artista = dn.getValue(Artista.class);
                       artista.id = dn.getKey();
                       artistas.add(artista);
                       artistaAdapter = new ArtistaAdapter(artistas, clickListener,longClickListener,checkboxClickListener);
                       recyclerView.setAdapter(artistaAdapter);
                   }
               }
           }
       });
       super.onResume();
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuArtista = menu;
        getMenuInflater().inflate(R.menu.menu_artista, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void confirmaExclusaoDeArtistasSelecionados() {
        // excluir os selecionados
        if (artistasParaExcluir.isEmpty()) {
            artistasParaExcluir.add(new Artista(null, "Super-Man", null));
            artistasParaExcluir.add(new Artista(null, "Wonderwoman", null));
        }

        for (int i = 0; i < artistasParaExcluir.size(); i++) {
            if(artistasParaExcluir.get(i).id != null){
                String idExcluir = artistasParaExcluir.get(i).id;
                databaseReference.child("Artistas").child(idExcluir).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(TelaArtistasActivity.this,"Artista Deletado com Sucesso", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(TelaArtistasActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }

        }

        List<String> nomes = artistasParaExcluir.stream()
                .map(a -> a.getNome())
                .collect(Collectors.toList());

        String nomesToast = String.join("\r\n", nomes);


        //Toast.makeText(this, nomesToast, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.itemLixoArtista){
//              if(artistasParaExcluir.size() > 0){
                  ConfirmDialog.confirmDelete(this, this::confirmaExclusaoDeArtistasSelecionados);
//              }
        }
        return super.onOptionsItemSelected(item);
    }


    public void abrirTelaAddArtista(View v) {
        Intent telaAddArtistaIntent = new Intent(this, TelaAdicionarEditarArtistasActivity.class);
        startActivity(telaAddArtistaIntent);
    }

    public boolean setAllCheckboxVisibility(boolean visibilidade) {
        try {
            if (visibilidade) {
                artistaAdapter.mudarVisibilidadeTodosCheckbox(true);
                for (int i = 0; i < artistaAdapter.getItemCount(); i++) {
                    // colocar todos os checkbox visiveis
                    artistaAdapter.notifyItemChanged(i);
                    if(menuArtista != null && adicionarArtistaBandaActivity == false)
                          menuArtista.findItem(R.id.itemLixoArtista).setVisible(true);
                }
                return true;
            } else {
                artistaAdapter.mudarVisibilidadeTodosCheckbox(false);
                for (int i = 0; i < artistaAdapter.getItemCount(); i++) {
                    // colocar todos os checkbox visiveis
                    artistaAdapter.notifyItemChanged(i);
                    if(menuArtista != null && adicionarArtistaBandaActivity == false)
                        menuArtista.findItem(R.id.itemLixoArtista).setVisible(false);
                }
                return true;
            }

        } catch (Exception ex) {
            Log.d("Error",ex.getMessage());
            return false;
        }
    }

    public void passarArtistasParaBandas(View view){
        Intent resultIntent = new Intent();
        Gson gson = new Gson();
        String artistaListaJson = gson.toJson(artistasParaAdicionarBanda);
        resultIntent.putExtra("dadosArtistaLista",artistaListaJson);
        setResult(RESULT_OK,resultIntent);
        finish();
    }
}