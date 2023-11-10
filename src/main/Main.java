package main;

import model.Dao;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    Dao dao = new Dao();
    private Connection con;

    private static Scanner le = new Scanner(System.in);
    private static ArrayList<Product> productList;

    public static void main(String[] args) {
        Main mainInstance = new Main();
        mainInstance.start();
    }

    private void start() {
        System.out.println("Bem-vindo(a) ao nosso mercado!");
        productList = new ArrayList<>();
        loadProductsFromDatabase();
        menu();
    }

    private void loadProductsFromDatabase() {
        try {
            con = dao.connectionDb();
            if (con != null) {
                productList.clear();

                String query = "SELECT product_name, product_price, product_id FROM products";
                try (PreparedStatement preparedStatement = con.prepareStatement(query);
                     ResultSet resultSet = preparedStatement.executeQuery()) {

                    while (resultSet.next()) {
                        String productName = resultSet.getString("product_name");
                        double productPrice = resultSet.getDouble("product_price");
                        int productId = resultSet.getInt("product_id");

                        Product product = new Product(productName, productPrice);
                        product.setProductId(productId);

                        productList.add(product);
                    }

                    if (!productList.isEmpty()) {
                        System.out.println("Produtos carregados do banco de dados!");
                    } else {
                        System.out.println("Não há produtos cadastrados no banco de dados!");
                    }
                }
            } else {
                System.out.println("Erro de conexão com o banco de dados!");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao carregar os produtos do banco de dados: " + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão com o banco de dados: " + e.getMessage());
            }
        }
    }

    private void menu() {
        System.out.println("\nOperações disponíveis: ");
        System.out.println("1 - Cadastrar novo produto");
        System.out.println("2 - Listar produtos");
        System.out.println("3 - Editar produto");
        System.out.println("4 - Sair");
        System.out.print("\nDigite a opção desejada: ");

        int option = le.nextInt();

        switch (option) {
            case 1:
                registerProduct();
                break;
            case 2:
                listProduct();
                break;
            case 3:
                editProduct();
                break;
            case 4:
                System.out.println("\nObrigado por utilizar nossos serviços!");
                System.exit(0);
            default:
                System.out.println("\nErro! Número Inválido.\nDigite uma opção válida.");
                menu();
                break;
        }
    }

    private void editProduct() {
        System.out.print("Digite o ID do produto que deseja editar: ");
        int productId = le.nextInt();

        Product productToEdit = findProductById(productId);

        if (productToEdit != null) {
            System.out.println("Produto encontrado:");
            System.out.println(productToEdit);

            System.out.println("O que deseja editar?");
            System.out.println("1 - Nome");
            System.out.println("2 - Valor");
            System.out.println("3 - Excluir produto");
            System.out.println("4 - Voltar");

            int editOption = le.nextInt();

            switch (editOption) {
                case 1:
                    System.out.print("Novo nome: ");
                    le.nextLine();
                    String newName = le.nextLine();
                    productToEdit.setName(newName);
                    updateProductInDatabase(productToEdit);
                    break;
                case 2:
                    System.out.print("Novo valor: ");
                    double newValue = le.nextDouble();
                    productToEdit.setPrice(newValue);
                    updateProductInDatabase(productToEdit);
                    break;
                case 3:
                    deleteProductFromDatabase(productToEdit);
                    productList.remove(productToEdit);
                    System.out.println("Produto excluído com sucesso!");
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } else {
            System.out.println("Produto não encontrado.");
        }

        menu();
    }

    private void deleteProductFromDatabase(Product productToDelete) {
        try {
            con = dao.connectionDb();
            if (con != null) {
                String query = "DELETE FROM market.products WHERE product_id = ?";
                try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                    preparedStatement.setInt(1, productToDelete.getProductId());
                    preparedStatement.executeUpdate();
                }
                System.out.println("Produto excluído do banco de dados!");
            } else {
                System.out.println("Erro de conexão com o banco de dados!");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir o produto do banco de dados: " + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão com o banco de dados: " + e.getMessage());
            }
        }
    }

    private void updateProductInDatabase(Product updatedProduct) {
        try {
            con = dao.connectionDb();
            if (con != null) {
                String updateQuery = "UPDATE products SET product_name=?, product_price=? WHERE product_id=?";
                try (PreparedStatement preparedStatement = con.prepareStatement(updateQuery)) {
                    preparedStatement.setString(1, updatedProduct.getName());
                    preparedStatement.setDouble(2, updatedProduct.getPrice());
                    preparedStatement.setInt(3, updatedProduct.getProductId());
                    preparedStatement.executeUpdate();
                }
                System.out.println("Produto atualizado no banco de dados!");
            } else {
                System.out.println("Erro de conexão com o banco de dados!");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar o produto no banco de dados: " + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão com o banco de dados: " + e.getMessage());
            }
        }
    }

    private Product getProductById(int productId) {
        for (Product p : productList) {
            if (p.getProductId() == productId) {
                return p;
            }
        }
        return null;
    }

    private void registerProduct() {
        System.out.print("Nome do Produto: ");
        le.nextLine();
        String productName = le.nextLine();

        System.out.print("Valor do Produto: ");
        double productValue = le.nextDouble();

        Product product = new Product(productName, productValue);
        productList.add(product);

        saveProductToDatabase(productName, productValue);

        System.out.println(productName + " foi cadastrado com sucesso!");
        dataBaseStatus();
        menu();
    }

    private void saveProductToDatabase(String productName, double productValue) {
        try {
            con = dao.connectionDb();
            if (con != null) {
                String query = "INSERT INTO market.products (product_name, product_price) VALUES (?, ?)";
                try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                    preparedStatement.setString(1, productName);
                    preparedStatement.setDouble(2, productValue);
                    preparedStatement.executeUpdate();
                }
                System.out.println("Produto salvo no banco de dados!");
            } else {
                System.out.println("Erro de conexão com o banco de dados!");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar o produto no banco de dados: " + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão com o banco de dados: " + e.getMessage());
            }
        }
    }

    private void listProduct() {
        loadProductsFromDatabase();

        if (!productList.isEmpty()) {
            System.out.println("\nLista de Produtos cadastrados no sistema: \n");

            for (Product p : productList) {
                System.out.println(p);
            }
        } else {
            System.out.println("\nNão há produtos cadastrados no momento!");
        }
        menu();
    }

    private Product findProductById(int productId) {
        for (Product product : productList) {
            if (product.getProductId() == productId) {
                return product;
            }
        }
        return null;
    }

    private void dataBaseStatus() {
        try {
            con = dao.connectionDb();
            if (con == null) {
                System.out.println("Erro de conexão com o banco de dados!");
            } else {
                System.out.println("Banco de dados conectado com sucesso!");
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
