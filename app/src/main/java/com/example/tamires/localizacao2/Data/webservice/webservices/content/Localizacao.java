package com.example.tamires.localizacao2.Data.webservice.webservices.content;

import java.io.Serializable;

public class Localizacao implements Serializable
{
    private StringValue nomLocal;
    private StringValue enderecoLocal;
    private StringValue telLocal;
    private transient LocationValue posicao;
    private StringValue tipoLocal;
    private StringValue descLocal;

    public Localizacao() {
        this.nomLocal = nomLocal;
        this.enderecoLocal = enderecoLocal;
        this.telLocal = telLocal;
        this.posicao = posicao;
        this.tipoLocal = tipoLocal;
        this.descLocal = descLocal;
    }

    public StringValue getNomLocal() {
        return nomLocal;
    }

    public void setNomLocal(StringValue nomLocal) {
        this.nomLocal = nomLocal;
    }

    public StringValue getEnderecoLocal() {
        return enderecoLocal;
    }

    public void setEnderecoLocal(StringValue enderecoLocal) {
        this.enderecoLocal = enderecoLocal;
    }

    public StringValue getTelLocal() {
        return telLocal;
    }

    public void setTelLocal(StringValue telLocal) {
        this.telLocal = telLocal;
    }

    public LocationValue getPosicao() {
        return posicao;
    }

    public void setPosicao(LocationValue posicao) {
        this.posicao = posicao;
    }

    public StringValue getTipoLocal() {
        return tipoLocal;
    }

    public void setTipoLocal(StringValue tipoLocal) {
        this.tipoLocal = tipoLocal;
    }

    public StringValue getDescLocal() {
        return descLocal;
    }

    public void setDescLocal(StringValue descLocal) {
        this.descLocal = descLocal;
    }
}
