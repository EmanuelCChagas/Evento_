package com.howiv.evento_.model;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Evento {

    public String titulo;
    public LocalDateTime data;
    public String local;
    public DecimalFormat valorIngresso;
    public List<Banda> bandas = new ArrayList<>();
    public List<Artista> artistas = new ArrayList<>();

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
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

    public List<Banda> getBandas() {
        return bandas;
    }

    public void setBandas(List<Banda> bandas) {
        this.bandas = bandas;
    }

    public List<Artista> getArtistas() {
        return artistas;
    }

    public void setArtistas(List<Artista> artistas) {
        this.artistas = artistas;
    }

    public void addBanda(Banda banda) {
        this.bandas.add(banda);
    }

    public void addBandas(List<Banda> bandas) {
        this.bandas.addAll(bandas);
    }

    public void addArtista(Artista artista) {
        this.artistas.add(artista);
    }

    public void addArtistas(List<Artista> artistas) {
        this.artistas.addAll(artistas);
    }

    public Evento(){}

    public Evento(String titulo, LocalDateTime data, String local, DecimalFormat valorIngresso, List<Banda> bandas, List<Artista> artistas) {
        this.titulo = titulo;
        this.data = data;
        this.local = local;
        this.valorIngresso = valorIngresso;
        this.bandas = bandas;
        this.artistas = artistas;
    }
}
