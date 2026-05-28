package br.com.sistemaestoque.model;

public class ProdutoEletronico extends Produto{

    private int garantiaMeses;

    public ProdutoEletronico (String nome, double preco, int quantidade, int garantiaMeses){
        super(nome, preco, quantidade);
        this.garantiaMeses = garantiaMeses;
    }

    public int getGarantiaMeses() {
        return garantiaMeses;
    }

    public void setGarantiaMeses(int garantiaMeses) {
        this.garantiaMeses = garantiaMeses;
    }
}
