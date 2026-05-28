package br.com.sistemaestoque.repository;

import java.io.*;

import br.com.sistemaestoque.adapter.ProdutoAdapter;
import br.com.sistemaestoque.model.Produto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


public class EstoqueRepository {

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Produto.class, new ProdutoAdapter())
            .create();

    public void addEstoqueOnRepository(List<Produto> listaDeProdutos) {

        try (Writer writer = new FileWriter("src/sistemaEstoque.json")) {
            gson.toJson(
                    listaDeProdutos,
                    new TypeToken<List<Produto>>() {}.getType(),
                    writer);
            System.out.println("SERIALIZE CHAMADO");
        }  catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public List<Produto> readEstoqueOnRepository() {
        File arquivo = new File("src/sistemaEstoque.json");

        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(arquivo)) {
            List<Produto> estoqueRepository = gson.fromJson(
                    reader,
                    new TypeToken<List<Produto>>() {}.getType()
            );
            System.out.println("DESERIALIZE CHAMADO");
            return estoqueRepository != null ? estoqueRepository : new ArrayList<>();

        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o estoque: " + e.getMessage());
        }
    }
}