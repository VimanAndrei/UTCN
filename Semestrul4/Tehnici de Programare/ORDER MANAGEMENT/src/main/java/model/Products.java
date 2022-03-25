package model;

public class Products {
    private int id;
    private double price;
    private int quantity;
    private String productName;

    public Products(int productId, double price, int quantity, String productName) {
        this.id = productId;
        this.price = price;
        this.quantity = quantity;
        this.productName = productName;
    }
    public Products(){

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "Products[" +
                "productId=" + id +
                ", price=" + price +
                ", quantity=" + quantity +
                ", productName='" + productName + '\'' +
                "]\n";
    }
}
