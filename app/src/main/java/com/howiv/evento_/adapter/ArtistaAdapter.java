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
    //public static List<Artista> listaCheckboxArtistas =new ArrayList<Artista>();
    final private View.OnClickListener clickListener;
    final private View.OnLongClickListener longClickListener;
    final private View.OnClickListener checkboxClickListener;
    private static boolean deixarTodosCheckboxVisiveis = false;


    public ArtistaAdapter(
            List<Artista> artistas,
            View.OnClickListener clickListener,
            View.OnLongClickListener longClickListener,
            View.OnClickListener checkboxClickListener) {
        this.artistas = artistas;
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
        this.checkboxClickListener = checkboxClickListener;
    }

    public List<Artista> getData(){
        return this.artistas;
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
        if(deixarTodosCheckboxVisiveis){
            vhClass.checkBox_artista_selecionado.setVisibility(View.VISIBLE);
        }
        else{
            vhClass.checkBox_artista_selecionado.setVisibility(View.INVISIBLE);
        }
        vhClass.layout_artista.setOnClickListener(clickListener);
        vhClass.layout_artista.setOnLongClickListener(longClickListener);
        vhClass.checkBox_artista_selecionado.setOnClickListener(checkboxClickListener);
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

    public static void mudarVisibilidadeTodosCheckbox(boolean visibilidade){
        deixarTodosCheckboxVisiveis = visibilidade;
    }
}
