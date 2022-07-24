package com.howiv.evento_;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TelaAdicionarEditarBandasActivity extends AppCompatActivity {

    private Button btnSalvarBanda;
    private DatabaseReference databaseReferencia;
    ActivityResultLauncher<Intent> abreArtistaActivity;
    List<Artista> artistasSelecionados =  new ArrayList<Artista>();
    ArtistaBandaAdapter artistaAdapter;
    RecyclerView recyclerView;
    Button btnAdicionarIntegrantes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_adicionar_bandas);
        getSupportActionBar().setTitle("Adicionar Bandas");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF4E71AE));
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
                    Banda banda =  new Banda(null,nome,artistasSelecionados);
                databaseReferencia.child("Bandas").push().setValue(banda, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if (error != null) {
                            Toast.makeText(TelaAdicionarEditarBandasActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(TelaAdicionarEditarBandasActivity.this,"Banda Salvo com Sucesso", Toast.LENGTH_LONG).show();
                            limparCampos();
                        }

                    }
                });
             }
        });
        databaseReferencia = FirebaseDatabase.getInstance().getReference();
        databaseReferencia.child("Bandas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot != null){
                    Log.i("FIREBASE", snapshot.getValue().toString());
                    limparCampos();
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
                                         artistasSelecionados = gson.fromJson(data.getStringExtra("dadosArtistaLista"), listType);
                                         artistaAdapter = new ArtistaBandaAdapter(artistasSelecionados);
                                        if(artistasSelecionados != null){
                                            btnAdicionarIntegrantes.setVisibility(View.GONE);
                                            recyclerView.setVisibility(View.VISIBLE);
                                            recyclerView.setAdapter(artistaAdapter);
                                        }

                                    }
                                }
                            });

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void adicionarIntegrantesBanda(View v) {
        //adicionar artistas na banda tela
            Intent telaAddArtistaBandaIntent = new Intent(TelaAdicionarEditarBandasActivity.this, TelaArtistasActivity.class);
            telaAddArtistaBandaIntent.putExtra("AdicionarBanda", true);
            abreArtistaActivity.launch(telaAddArtistaBandaIntent);
    }




    public void limparCampos(){
        TextView nome =  findViewById(R.id.input_nome_banda);
        nome.setText("");
       // funcao.setText("");
    }

}