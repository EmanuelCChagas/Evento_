package com.howiv.evento_.model;

import android.widget.ImageView;

public class Artista {
    public ImageView foto = null;
    public String nome;
    public String desc;

    public Artista(){}

    public Artista(ImageView foto, String nome, String desc){
        this.foto = foto;
        this.nome = nome;
        this.desc = desc;
    }

    public ImageView getFoto() {
        return foto;
    }

    public void setFoto(ImageView foto) {
        this.foto = foto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
