package unisul.a3.model;

import java.io.Serializable;

public class Movimentacao implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private long id;
    private long produtoId;
    private String data;
    private int quantidade;
    private String tipo;

    public Movimentacao() {}

    public Movimentacao(long id, long produtoId, String data, int quantidade, String tipo) {
        this.id = id;
        this.produtoId = produtoId;
        this.data = data;
        this.quantidade = quantidade;
        this.tipo = tipo;
    }

    public Movimentacao(long produtoId, String data, int quantidade, String tipo) {
        this.produtoId = produtoId;
        this.data = data;
        this.quantidade = quantidade;
        this.tipo = tipo;
    }

    public Movimentacao(long produtoId, int quantidade, String tipo) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.tipo = tipo;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getProdutoId() { return produtoId; }
    public void setProdutoId(long produtoId) { this.produtoId = produtoId; }
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    @Override
    public String toString() {
        return "Movimentacao{id=" + id + ", produtoId=" + produtoId + ", data='" + data + 
               "', quantidade=" + quantidade + ", tipo='" + tipo + "'}";
    }
}
