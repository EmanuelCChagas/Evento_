package com.howiv.evento_;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.howiv.evento_.model.Artista;

import java.util.HashMap;
import java.util.Map;

public class TelaAdicionarEditarArtistasActivity extends AppCompatActivity {

    private Button btnSalvarArtista;
    Boolean editarArtistaActivity = false;
    private DatabaseReference databaseReferencia;
    private Artista artistaParaEditar = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_adicionar_artistas);
        getSupportActionBar().setTitle("Adicionar Artistas");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF4E71AE));
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            editarArtistaActivity = extras.getBoolean("EditarArtista");
            if(editarArtistaActivity){
                getSupportActionBar().setTitle("Editar Artistas");
                Gson gson = new Gson();
                 artistaParaEditar = gson.fromJson(getIntent().getStringExtra("EditarArtistaDados"), Artista.class);
                if(artistaParaEditar != null){
                    popularDadosArtistaEditar(artistaParaEditar);
                }

            }
        }
        btnSalvarArtista = findViewById(R.id.btn_salvar_artista);
        btnSalvarArtista.setOnClickListener(view ->  {
            //val foto = binding.imageSalvarArtista
            EditText nomeView =  findViewById(R.id.input_nome_artista);
            EditText funcaoView = findViewById(R.id.input_func_artista);

            String nome = nomeView.getText().toString();
            String funcao = funcaoView.getText().toString();

            if (nome.isEmpty() || funcao.isEmpty()) {
                Toast.makeText(this, "Há campos não preenchidos!", Toast.LENGTH_SHORT).show();
            } else {
                salvarArtistas(nome, funcao);
            }
        });
        databaseReferencia = FirebaseDatabase.getInstance().getReference();

    }

    public void popularDadosArtistaEditar(Artista artistaDados){
        EditText nomeView =  (EditText)findViewById(R.id.input_nome_artista);
        EditText funcaoView = (EditText)findViewById(R.id.input_func_artista);
        nomeView.setText((String)artistaDados.nome);
        funcaoView.setText((String)artistaDados.funcoes);

    }

    public void salvarArtistas(String nome, String funcao){
        Artista artista = new Artista(null, nome, funcao);
        if(editarArtistaActivity){
            if(artistaParaEditar != null){
                    Map<String, Object> artistaUpdate = new HashMap<>();
                    artistaUpdate.put("/Artistas/" + artistaParaEditar.id,artista);

                    databaseReferencia.updateChildren(artistaUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                               Toast.makeText(TelaAdicionarEditarArtistasActivity.this,"Artista Salvo com Sucesso", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(TelaAdicionarEditarArtistasActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        }
        else{
              databaseReferencia.child("Artistas").push().setValue(artista, new DatabaseReference.CompletionListener() {
                  @Override
                  public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                      if (error != null) {
                          Toast.makeText(TelaAdicionarEditarArtistasActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                      } else {
                          Toast.makeText(TelaAdicionarEditarArtistasActivity.this,"Artista Salvo com Sucesso", Toast.LENGTH_LONG).show();
                          limparCampos();
                      }

                  }
              });
        }
    }

    public void limparCampos(){
        TextView nome =  findViewById(R.id.input_nome_artista);
        TextView funcao = findViewById(R.id.input_func_artista);
        nome.setText("");
        funcao.setText("");
    }

}