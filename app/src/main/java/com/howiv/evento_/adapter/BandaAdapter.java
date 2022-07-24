package com.howiv.evento_.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.howiv.evento_.R;
import com.howiv.evento_.model.Artista;
import com.howiv.evento_.model.Banda;

import java.util.ArrayList;
import java.util.List;

public class BandaAdapter  extends RecyclerView.Adapter {
    List<Banda> bandas;
    final private View.OnClickListener clickListener;
    final private View.OnLongClickListener longClickListener;
    final private View.OnClickListener checkboxClickListener;
    private static boolean deixarTodosCheckboxVisiveis = false;

    public BandaAdapter(List<Banda> bandas,
                        View.OnClickListener clickListener,
                        View.OnLongClickListener longClickListener,
                        View.OnClickListener checkboxClickListener) {
        this.bandas = bandas;
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
        this.checkboxClickListener = checkboxClickListener;
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

        vhClass.checkBox_banda_selecionado.setChecked(false);

        vhClass.layout_banda.setOnClickListener(clickListener);

        if(deixarTodosCheckboxVisiveis){
            vhClass.checkBox_banda_selecionado.setVisibility(View.VISIBLE);
        }
        else{
            vhClass.checkBox_banda_selecionado.setVisibility(View.INVISIBLE);
        }

        vhClass.layout_banda.setOnLongClickListener(longClickListener);
        vhClass.checkBox_banda_selecionado.setOnClickListener(checkboxClickListener);
    }

    @Override
    public int getItemCount() {
        return bandas.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{
       TextView text_nome;
       TextView text_integrantes;
       //ImageView image_banda;
       CheckBox checkBox_banda_selecionado;
        ConstraintLayout layout_banda;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            text_nome = itemView.findViewById(R.id.nomeBanda);
            text_integrantes = itemView.findViewById(R.id.integrantesBanda);
            //image_banda = itemView.findViewById(R.id.fotoBanda);
            checkBox_banda_selecionado = itemView.findViewById(R.id.checkBox_banda);
            layout_banda = itemView.findViewById(R.id.idLayoutBanda);
        }
    }

    public static void mudarVisibilidadeTodosCheckbox(boolean visibilidade){
        deixarTodosCheckboxVisiveis = visibilidade;
    }
}
