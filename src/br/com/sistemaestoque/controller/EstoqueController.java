package br.com.sistemaestoque.controller;

import br.com.sistemaestoque.model.Produto;
import br.com.sistemaestoque.repository.EstoqueRepository;

import java.util.ArrayList;
import java.util.List;

public class EstoqueController {

    private List<Produto> listaDeProdutos = new ArrayList<>();
    EstoqueRepository repository = new EstoqueRepository();

    public void adicionarProdutoEstoque(Produto novoProduto){
         listaDeProdutos.add(novoProduto);
         repository.addEstoqueOnRepository(listaDeProdutos);
    }

    public void atualizaListaDeProdutos()  {
        List<Produto> lista = repository.readEstoqueOnRepository();

        if(lista != null){
            this.listaDeProdutos = lista;
        }
    }

    public List<Produto> listarProdutoEstoque(){
        return listaDeProdutos;
    }

    public List<Produto> buscarProdutoPeloNome(String nome){
        String nomeMinusculo = nome.toLowerCase();

        try {
            List<Produto> produtoEncontrado = listaDeProdutos.stream()
                    .filter(produto -> produto.getNome() != null && produto.getNome().contains(nomeMinusculo))
                    .toList();

            return produtoEncontrado;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removerProdutoPeloNome(String nome){
        Produto encontrado = null;

        for (Produto produto : listaDeProdutos ){
            if (produto.getNome().equalsIgnoreCase(nome)) {
                encontrado = produto;
                break;
            }
        }

        if (encontrado != null){
            listaDeProdutos.remove(encontrado);
            repository.addEstoqueOnRepository(listaDeProdutos);
        }

    }
}