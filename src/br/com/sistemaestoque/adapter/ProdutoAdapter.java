package br.com.sistemaestoque.adapter;

import br.com.sistemaestoque.model.Produto;
import br.com.sistemaestoque.model.ProdutoEletronico;
import br.com.sistemaestoque.model.ProdutoPerecivel;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ProdutoAdapter implements JsonSerializer<Produto>, JsonDeserializer<Produto> {

    @Override
    public JsonElement serialize(Produto produto, Type type, JsonSerializationContext context) {

        JsonObject objeto = new JsonObject();

        objeto.addProperty("nome", produto.getNome());
        objeto.addProperty("preco", produto.getPreco());
        objeto.addProperty("quantidade", produto.getQuantidade());

        if (produto instanceof ProdutoPerecivel p) {

            objeto.addProperty("tipo", "perecivel");
            objeto.addProperty("dataValidade", p.getDataValidade());

        } else if (produto instanceof ProdutoEletronico e) {

            objeto.addProperty("tipo", "eletronico");
            objeto.addProperty("garantiaMeses", e.getGarantiaMeses());
        }

        return objeto;
    }

    @Override
    public Produto deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject objeto = json.getAsJsonObject();

        String nome = objeto.get("nome").getAsString();
        double preco = objeto.get("preco").getAsDouble();
        int quantidade = objeto.get("quantidade").getAsInt();

        String tipo = objeto.get("tipo").getAsString();

        if (tipo.equals("perecivel")) {

            String dataValidade = objeto.get("dataValidade").getAsString();

            return new ProdutoPerecivel(
                    nome,
                    preco,
                    quantidade,
                    dataValidade
            );

        } else if (tipo.equals("eletronico")) {

            int garantiaMeses = objeto.get("garantiaMeses").getAsInt();

            return new ProdutoEletronico(
                    nome,
                    preco,
                    quantidade,
                    garantiaMeses
            );
        }

        throw new JsonParseException("Tipo desconhecido");
    }
}