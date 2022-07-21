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

import java.util.List;

public class ArtistaAdapter extends RecyclerView.Adapter {

    List<Artista> artistas;

    public ArtistaAdapter(List<Artista> artistas) {
        this.artistas = artistas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artista_item,parent,false);
        ViewHolderClass vhClass = new ViewHolderClass(view);
        return vhClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass vhClass = (ViewHolderClass)  holder;
        Artista artista = artistas.get(position);
        vhClass.text_nome.setText(artista.getNome());
        vhClass.text_desc.setText(artista.getDesc());
        //vhClass.image_artista.setImage(conta.getOperation());
    }

    @Override
    public int getItemCount() {
        return artistas.size();
    }
    public class ViewHolderClass extends RecyclerView.ViewHolder{
        TextView text_nome;
        TextView text_desc;
        //ImageView image_artista;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            text_nome = itemView.findViewById(R.id.nomeArtista);
            text_desc = itemView.findViewById(R.id.descArtista);
            //image_artista = itemView.findViewById(R.id.image_salvar_artista);
        }
    }
}
