package com.howiv.evento_.model;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Banda {

    public ImageView logo;
    public String nome;
    public List<Artista> integrantes = new ArrayList<>();

    public ImageView getLogo() {
        return logo;
    }

    public void setLogo(ImageView logo) {
        this.logo = logo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Artista> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(List<Artista> integrantes) {
        this.integrantes = integrantes;
    }

    public void addIntegrante(Artista integrante) {
        this.integrantes.add(integrante);
    }

    public void addIntegrantes(List<Artista> integrantes) {
        this.integrantes.addAll(integrantes);
    }

}
