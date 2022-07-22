package com.howiv.evento_.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.howiv.evento_.R;
import com.howiv.evento_.model.Artista;
import com.howiv.evento_.model.Banda;

import java.util.List;

public class BandaAdapter  extends RecyclerView.Adapter {
    List<Banda> bandas;

    public BandaAdapter(List<Banda> bandas) {
        this.bandas = bandas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.banda_item,parent,false);
        BandaAdapter.ViewHolderClass vhClass = new BandaAdapter.ViewHolderClass(view);
        return vhClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BandaAdapter.ViewHolderClass vhClass = (BandaAdapter.ViewHolderClass)  holder;
        Banda banda = bandas.get(position);
        vhClass.text_nome.setText(banda.getNome());
        vhClass.text_integrantes.setText(banda.getIntegrantes().size());
        //vhClass.image_banda.setImage(Banda.getOperation());
    }

    @Override
    public int getItemCount() {
        return bandas.size();
    }
    public class ViewHolderClass extends RecyclerView.ViewHolder{
       TextView text_nome;
       TextView text_integrantes;
       //ImageView image_banda;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            text_nome = itemView.findViewById(R.id.nomeBanda);
            text_integrantes = itemView.findViewById(R.id.integrantesBanda);
            //image_banda = itemView.findViewById(R.id.fotoBanda);
        }
    }
}
