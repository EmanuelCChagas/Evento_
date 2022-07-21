package com.howiv.evento_.model;

public class Banda {
    public String nome;
    public Artista[] integrantes;

    Banda(){}

    public Banda(String nome, Artista[] integrantes) {
        this.nome = nome;
        this.integrantes = integrantes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Artista[] getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(Artista[] integrantes) {
        this.integrantes = integrantes;
    }
}
