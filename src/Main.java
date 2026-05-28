import br.com.sistemaestoque.controller.EstoqueController;
import br.com.sistemaestoque.model.Produto;
import br.com.sistemaestoque.model.ProdutoEletronico;
import br.com.sistemaestoque.model.ProdutoPerecivel;
import br.com.sistemaestoque.repository.EstoqueRepository;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        EstoqueController controller = new EstoqueController();

        try{
            controller.atualizaListaDeProdutos();
        }catch (RuntimeException e){
            System.out.println("Error: " + e.getMessage());
        }


        while (true){
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Listar produtos");
            System.out.println("3 - Buscar produto");
            System.out.println("4 - Remover produto");
            System.out.println("0 - Sair");

            int opcao =  Integer.parseInt(scan.nextLine());

            if (opcao == 1){
                String nomeProduto;
                double preco;
                int quantidade;
                String validade;
                String dataValidade = "";
                Integer garantiaMeses;

                System.out.println("*** QUAL É O PRODUTO? ***");
                nomeProduto = scan.nextLine();

                // VERIFICA SE O PRODUTO JA EXITE ANTES DE CADASTRAR
                if(controller.buscarProdutoPeloNome(nomeProduto).isEmpty()){
                    System.out.println("*** QUAL É O PREÇO DO PRODUTO? ***");
                    preco = Double.parseDouble(scan.nextLine());

                    System.out.println("*** QUANTOS PRODUTOS TEM NO ESTOQUE? ***");
                    quantidade = Integer.parseInt(scan.nextLine());

                    System.out.println("*** O PRODUTO TEM VALIDADE? (SIM OU NÃO) ***");
                    validade = scan.nextLine();

                    try {
                        if (validade.equalsIgnoreCase("SIM")){
                            System.out.println("*** QUAL É A DATA DE VALIDADE? ***");
                            dataValidade = scan.nextLine();

                            ProdutoPerecivel novoProdutoPerecivel = new ProdutoPerecivel(nomeProduto, preco, quantidade, dataValidade);
                            controller.adicionarProdutoEstoque(novoProdutoPerecivel);

                        } else if (validade.equalsIgnoreCase("NÃO")) {
                            System.out.println("*** Quantos MESES DE GARANTIA? ***");
                            garantiaMeses = Integer.parseInt(scan.nextLine());

                            ProdutoEletronico novoProdutoEletronico = new ProdutoEletronico(nomeProduto, preco, quantidade, garantiaMeses);
                            controller.adicionarProdutoEstoque(novoProdutoEletronico);
                        }
                    } catch (Exception e){
                        System.out.println("Error:" + e.getMessage());
                    }
                } else {
                    System.out.println("PRODUTO JÁ CADASTRADO");
                    System.out.println("-------------------");
                }

            }
            else if (opcao == 2) {
                List<Produto> listaProduto = controller.listarProdutoEstoque();

                for (Produto produto : listaProduto){
                    System.out.println("Nome: " + produto.getNome());
                    System.out.println("Preço: " + produto.getPreco());
                    System.out.println("Quantidade: " + produto.getQuantidade());

                    if (produto instanceof ProdutoPerecivel p){
                        System.out.println("Data de validade: " + p.getDataValidade());
                    }else if (produto instanceof ProdutoEletronico e){
                        System.out.println("Meses de Garantia: " + e.getGarantiaMeses());
                    }

                    System.out.println("-------------------");
                }
            }
            else if (opcao == 3) {
                System.out.println("-------------------");
                System.out.println("DIGITE O NOME DO PRODUTO");

                String nomeProduto = scan.nextLine();

                //BUSCA UM PRODUTO PELO NOME
                List<Produto> produtos = controller.buscarProdutoPeloNome(nomeProduto);


                //EXIBE PRODUTO SE ENCONTRAR
                if (produtos.isEmpty()){
                    System.out.println("-------------------");
                    System.out.println("PRODUTO NÃO ENCONTRADO.");
                }else {
                    for (Produto produto : produtos ){
                        System.out.println("Nome: " + produto.getNome());
                        System.out.println("Preço: " + produto.getPreco());
                        System.out.println("Quantidade: " + produto.getQuantidade());
                    }
                }

            }
            else if (opcao == 4) {
                System.out.println("-------------------");
                System.out.println("DIGITE O NOME DO PRODUTO");

                String nomeProduto = scan.nextLine();

                try {
                    controller.removerProdutoPeloNome(nomeProduto);
                    System.out.println("PRODUTO REMOVIDO COM SUCESSO");
                }catch (Exception e){
                    System.out.println("Error:" + e.getMessage());
                }

            }
            else if (opcao == 0) {
                // ENCERRA O PROGRAMA
                System.out.println("-------------------");
                System.out.println("Sistema encerrado");
                System.out.println("-------------------");

//                controller.atualizaRepository();
                break;
            }
            else {
                // INFORMA OPÇÃO INVALIDA
                System.out.println("-------------------");
                System.out.println("Opção digitada invalida.");
                System.out.println("-------------------");
            }
        }
    }
}