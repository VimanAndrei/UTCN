package model;

public class Orders{
    private int id;
    private int clientId;
    private int productId;
    private int quantity;

    public Orders(int orderId, int clientId, int productId, int quantity) {
        this.id = orderId;
        this.clientId = clientId;
        this.productId = productId;
        this.quantity = quantity;
    }
    public Orders(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order[" +
                "orderId=" + id +
                ", clientId=" + clientId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                "]\n";
    }
}
