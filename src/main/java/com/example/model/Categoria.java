package com.example.model;

import java.io.Serializable;

public class Categoria implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private long id;
    private String nome;
    private String tamanho;
    private String embalagem;

    public Categoria() {}

    public Categoria(long id, String nome, String tamanho, String embalagem) {
        this.id = id;
        this.nome = nome;
        this.tamanho = tamanho;
        this.embalagem = embalagem;
    }

    public Categoria(String nome, String tamanho, String embalagem) {
        this.nome = nome;
        this.tamanho = tamanho;
        this.embalagem = embalagem;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTamanho() { return tamanho; }
    public void setTamanho(String tamanho) { this.tamanho = tamanho; }
    public String getEmbalagem() { return embalagem; }
    public void setEmbalagem(String embalagem) { this.embalagem = embalagem; }

    @Override
    public String toString() {
        return "Categoria{id=" + id + ", nome='" + nome + "', tamanho='" + tamanho + "', embalagem='" + embalagem + "'}";
    }
}
