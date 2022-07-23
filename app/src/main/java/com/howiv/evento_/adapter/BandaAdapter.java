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
    public static List<Banda> listaCheckboxBandas =new ArrayList<Banda>();
    final private View.OnClickListener clickListener;

    public BandaAdapter(List<Banda> bandas, View.OnClickListener clickListener) {
        this.bandas = bandas;
        this.clickListener = clickListener;
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

        vhClass.layout_banda.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(vhClass.checkBox_banda_selecionado.getVisibility() == View.INVISIBLE){
                    vhClass.checkBox_banda_selecionado.setVisibility(View.VISIBLE);
                    vhClass.checkBox_banda_selecionado.setChecked(true);
                    listaCheckboxBandas.add(banda);
                }
                else{
                    vhClass.checkBox_banda_selecionado.setVisibility(View.INVISIBLE);
                }
                return false;
            }
        });

        vhClass.checkBox_banda_selecionado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked =  vhClass.checkBox_banda_selecionado.isChecked();
                if(isChecked){
                    listaCheckboxBandas.add(banda);
                }else{
                    if(listaCheckboxBandas.size() > 0){
                        listaCheckboxBandas.remove(banda);
                    }
                }
            }
        });
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
}
