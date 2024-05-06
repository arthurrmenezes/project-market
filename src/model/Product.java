package model;

import utils.Utils;

public class Product {

    private String name;
    private double price;
    private static int productCounter = 1;
    private int productId;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
        this.productId = productCounter;
        Product.productCounter += 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String toString() {
        return "Nome do produto: " + getName() +
                "\nValor do produto: " + Utils.doubleToString(getPrice()) +
                "\nNúmero do produto: " + getProductId();
    }

}
