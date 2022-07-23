package com.howiv.evento_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.howiv.evento_.model.Artista;
import com.howiv.evento_.model.Banda;

public class TelaAdicionarEditarBandasActivity extends AppCompatActivity {

    private Button btnSalvarBanda;
    private DatabaseReference databaseReferencia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_adicionar_bandas);
        getSupportActionBar().setTitle("Adicionar Bandas");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF4E71AE));

        btnSalvarBanda = findViewById(R.id.btn_salvar_banda);
        btnSalvarBanda.setOnClickListener(view ->  {
            EditText nomeView =  findViewById(R.id.input_nome_banda);

            String nome = nomeView.getText().toString();

            //if (nome.isEmpty() || funcao.isEmpty()) {
            //    Toast.makeText(this, "Há campos não preenchidos!", Toast.LENGTH_SHORT).show();
            //} else {
            //    salvarBanda(nome, funcao);
            //    limparCampos();
           // }
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

    }


    public void adicionarIntegrantesBanda(View v) {
        Intent i = new Intent(this, TelaArtistasActivity.class);
        i.putExtra("AdicionarBanda",true);
        startActivity(i);
    }


    public void salvarBanda(String nome, String funcao){
        Banda banda = new Banda(null, nome, null);
        databaseReferencia.child("Bandas").push().setValue(banda);

    }

    public void limparCampos(){
        TextView nome =  findViewById(R.id.input_nome_banda);
        nome.setText("");
       // funcao.setText("");
    }

}