package com.howiv.evento_.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.howiv.evento_.R;
import com.howiv.evento_.model.Banda;
import com.howiv.evento_.model.Evento;

import java.util.List;

public class EventoAdapter extends RecyclerView.Adapter{
    List<Evento> eventos;

    public EventoAdapter(List<Evento> eventos) {
        this.eventos = eventos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.evento_item,parent,false);
        EventoAdapter.ViewHolderClass vhClass = new EventoAdapter.ViewHolderClass(view);
        return vhClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        EventoAdapter.ViewHolderClass vhClass = (EventoAdapter.ViewHolderClass)  holder;
        Evento evento = eventos.get(position);
        vhClass.text_titulo.setText(evento.getTitulo());
        vhClass.text_dataHora.setText(evento.getData().toString());
        //vhClass.image_banda.setImage(Banda.getOperation());
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }
    public class ViewHolderClass extends RecyclerView.ViewHolder{
        TextView text_titulo;
        TextView text_dataHora;
        //ImageView image_evento;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            text_titulo = itemView.findViewById(R.id.tituloEvento);
            text_dataHora = itemView.findViewById(R.id.dataHoraEvento);
            //image_evento = itemView.findViewById(R.id.fotoEvento);
        }
    }
}
