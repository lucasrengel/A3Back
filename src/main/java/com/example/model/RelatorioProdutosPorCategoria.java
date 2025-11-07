package com.example.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RelatorioProdutosPorCategoria implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<ItemCategoria> itens;

    public RelatorioProdutosPorCategoria() {
        this.itens = new ArrayList<>();
    }

    public RelatorioProdutosPorCategoria(List<ItemCategoria> itens) {
        this.itens = itens;
    }

    public List<ItemCategoria> getItens() { return itens; }
    public void setItens(List<ItemCategoria> itens) { this.itens = itens; }

    public static class ItemCategoria implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private String nomeCategoria;
        private long quantidade;

        public ItemCategoria() {}

        public ItemCategoria(String nomeCategoria, long quantidade) {
            this.nomeCategoria = nomeCategoria;
            this.quantidade = quantidade;
        }

        public String getNomeCategoria() { return nomeCategoria; }
        public void setNomeCategoria(String nomeCategoria) { this.nomeCategoria = nomeCategoria; }
        public long getQuantidade() { return quantidade; }
        public void setQuantidade(long quantidade) { this.quantidade = quantidade; }
    }
}
