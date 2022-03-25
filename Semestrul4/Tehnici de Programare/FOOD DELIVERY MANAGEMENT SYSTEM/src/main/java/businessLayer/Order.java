package businessLayer;

import dataLayer.Serializator;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Order implements Serializable {
    private int orderId;
    private int clientId;
    private Date orderDate;
    private double price;

    public Order(int orderId, int clientId, Date orderDate,double price ) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.orderDate = orderDate;
        this.price = price;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getClientId() {
        return clientId;
    }

    public double getPrice() {
        return price;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    @Override
    public String toString() {
        return "Order: " +
                "orderId=" + orderId +
                ", clientId=" + clientId +
                ", orderDate=" + orderDate +
                ", price="+price+"\n";

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId && clientId == order.clientId && Objects.equals(orderDate, order.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, clientId, orderDate);
    }
}
