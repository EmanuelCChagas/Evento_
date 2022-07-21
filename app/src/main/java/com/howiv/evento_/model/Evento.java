package com.howiv.evento_.model;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

public class Evento {
    public String titulo;
    public LocalDateTime Data;
    public String local;
    public DecimalFormat valorIngresso;

    public Evento(){}

    public Evento(String titulo, LocalDateTime data, String local, DecimalFormat valorIngresso, Banda[] bandas, Artista[] artistas) {
        this.titulo = titulo;
        Data = data;
        this.local = local;
        this.valorIngresso = valorIngresso;
        this.bandas = bandas;
        this.artistas = artistas;
    }

    public Banda[] bandas;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDateTime getData() {
        return Data;
    }

    public void setData(LocalDateTime data) {
        Data = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public DecimalFormat getValorIngresso() {
        return valorIngresso;
    }

    public void setValorIngresso(DecimalFormat valorIngresso) {
        this.valorIngresso = valorIngresso;
    }

    public Banda[] getBandas() {
        return bandas;
    }

    public void setBandas(Banda[] bandas) {
        this.bandas = bandas;
    }

    public Artista[] getArtistas() {
        return artistas;
    }

    public void setArtistas(Artista[] artistas) {
        this.artistas = artistas;
    }

    public Artista[] artistas;
}
