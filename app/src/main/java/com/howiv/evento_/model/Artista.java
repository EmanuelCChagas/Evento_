package com.howiv.evento_.model;

import android.widget.ImageView;

public class Artista {

    public ImageView foto;
    public String nome;
    public String funcoes;

    public boolean isSelecionado;

    public Artista() {
    }

    public Artista(ImageView foto, String nome, String funcoes) {
        this.foto = foto;
        this.nome = nome;
        this.funcoes = funcoes;
    }

    public boolean isSelecionado() {
        return isSelecionado;
    }

    public void setSelecionado(boolean selecionado) {
        isSelecionado = selecionado;
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

}
