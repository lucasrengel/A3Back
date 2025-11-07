package com.example.model;

import java.io.Serializable;
import java.util.List;

public class RelatorioBalanco implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<ItemBalanco> itens;
    private double valorTotalEstoque;

    public RelatorioBalanco() {}

    public RelatorioBalanco(List<ItemBalanco> itens, double valorTotalEstoque) {
        this.itens = itens;
        this.valorTotalEstoque = valorTotalEstoque;
    }

    public List<ItemBalanco> getItens() { return itens; }
    public void setItens(List<ItemBalanco> itens) { this.itens = itens; }
    public double getValorTotalEstoque() { return valorTotalEstoque; }
    public void setValorTotalEstoque(double valorTotalEstoque) { this.valorTotalEstoque = valorTotalEstoque; }

    public static class ItemBalanco implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private String nome;
        private int quantidade;
        private double valorTotal;

        public ItemBalanco() {}

        public ItemBalanco(String nome, int quantidade, double valorTotal) {
            this.nome = nome;
            this.quantidade = quantidade;
            this.valorTotal = valorTotal;
        }

        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
        public int getQuantidade() { return quantidade; }
        public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
        public double getValorTotal() { return valorTotal; }
        public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }
    }
}
