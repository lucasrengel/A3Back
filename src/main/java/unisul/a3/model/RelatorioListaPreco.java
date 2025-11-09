package unisul.a3.model;

import java.io.Serializable;
import java.util.List;

public class RelatorioListaPreco implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<ItemListaPreco> itens;

    public RelatorioListaPreco() {}

    public RelatorioListaPreco(List<ItemListaPreco> itens) {
        this.itens = itens;
    }

    public List<ItemListaPreco> getItens() { return itens; }
    public void setItens(List<ItemListaPreco> itens) { this.itens = itens; }

    public static class ItemListaPreco implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private String nome;
        private double preco;
        private String unidade;
        private String nomeCategoria;

        public ItemListaPreco() {}

        public ItemListaPreco(String nome, double preco, String unidade, String nomeCategoria) {
            this.nome = nome;
            this.preco = preco;
            this.unidade = unidade;
            this.nomeCategoria = nomeCategoria;
        }

        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
        public double getPreco() { return preco; }
        public void setPreco(double preco) { this.preco = preco; }
        public String getUnidade() { return unidade; }
        public void setUnidade(String unidade) { this.unidade = unidade; }
        public String getNomeCategoria() { return nomeCategoria; }
        public void setNomeCategoria(String nomeCategoria) { this.nomeCategoria = nomeCategoria; }
    }
}
