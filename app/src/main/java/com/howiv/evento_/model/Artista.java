package com.howiv.evento_.model;

import android.widget.ImageView;

public class Artista {
    public String id;

    public ImageView foto;
    public String nome;
    public String funcoes;

    public Artista() {
    }

    public Artista(ImageView foto, String nome, String funcoes) {
        this.foto = foto;
        this.nome = nome;
        this.funcoes = funcoes;
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

    public String getFuncoes() {
        return funcoes;
    }

    public void setFuncoes(String funcoes) {
        this.funcoes = funcoes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
