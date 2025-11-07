package com.example.model;

import java.io.Serializable;

public class RelatorioMaioresMovimentacoes implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Movimentacao maiorEntrada;
    private Movimentacao maiorSaida;

    public RelatorioMaioresMovimentacoes() {}

    public RelatorioMaioresMovimentacoes(Movimentacao maiorEntrada, Movimentacao maiorSaida) {
        this.maiorEntrada = maiorEntrada;
        this.maiorSaida = maiorSaida;
    }

    public Movimentacao getMaiorEntrada() { return maiorEntrada; }
    public void setMaiorEntrada(Movimentacao maiorEntrada) { this.maiorEntrada = maiorEntrada; }
    public Movimentacao getMaiorSaida() { return maiorSaida; }
    public void setMaiorSaida(Movimentacao maiorSaida) { this.maiorSaida = maiorSaida; }
}
