package com.howiv.evento_.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.howiv.evento_.R;
import com.howiv.evento_.model.Artista;

import java.util.ArrayList;
import java.util.List;

public class ArtistaAdapter extends RecyclerView.Adapter {

    List<Artista> artistas;
    public static List<Artista> listaCheckboxArtistas =new ArrayList<Artista>();
    final private View.OnClickListener clickListener;


    public ArtistaAdapter(List<Artista> artistas, View.OnClickListener clickListener) {
        this.artistas = artistas;
        this.clickListener = clickListener;
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
        vhClass.text_desc.setText(artista.getFuncoes());
        vhClass.checkBox_artista_selecionado.setChecked(false);
        //vhClass.image_artista.setImage(conta.getOperation());


       // vhClass.layout_artista.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //    }
       // });
        vhClass.layout_artista.setOnClickListener(clickListener);

        vhClass.layout_artista.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(vhClass.checkBox_artista_selecionado.getVisibility() == View.INVISIBLE){
                    vhClass.checkBox_artista_selecionado.setVisibility(View.VISIBLE);
                    vhClass.checkBox_artista_selecionado.setChecked(true);
                    listaCheckboxArtistas.add(artista);
                }
                else{
                    vhClass.checkBox_artista_selecionado.setVisibility(View.INVISIBLE);
                }
                return false;
            }
        });

        vhClass.checkBox_artista_selecionado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked =  vhClass.checkBox_artista_selecionado.isChecked();
                if(isChecked){
                    listaCheckboxArtistas.add(artista);
                }else{
                    if(listaCheckboxArtistas.size() > 0){
                        listaCheckboxArtistas.remove(artista);
                    }
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return artistas.size();
    }
    public class ViewHolderClass extends RecyclerView.ViewHolder{
        TextView text_nome;
        TextView text_desc;
        CheckBox checkBox_artista_selecionado;
        //ImageView image_artista;
        ConstraintLayout layout_artista;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            text_nome = itemView.findViewById(R.id.nomeArtista);
            text_desc = itemView.findViewById(R.id.descArtista);
            checkBox_artista_selecionado = itemView.findViewById(R.id.checkBox_artista);
            //image_artista = itemView.findViewById(R.id.image_salvar_artista);
            layout_artista = itemView.findViewById(R.id.idLayoutArtista);

        }
    }
}
