package Entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table( name = "ordersdetails" )
public class OrdersDetail {

    @EmbeddedId
    private OrdersDetailsID ordersDetailsID;

    @Column(name = "quantity")
    int quantity;

    @Column(name = "priceEach")
    float priceEach;

    @Transient
    Pizza pizza;

    @Transient
    Order order;

    public OrdersDetail(int quantity, float priceEach, Pizza pizza, Order order) {
        super();
        this.quantity = quantity;
        this.priceEach = priceEach;
        this.pizza = pizza;
        this.order = order;
        ordersDetailsID = new OrdersDetailsID(pizza.getIdPizza(), order.getIdOrder());
    }

    public OrdersDetail(){
        super();
    }

    public void setQuantity(int quantity) { this.quantity = quantity; }
    public int getQuantity() { return quantity; }

    public void setPriceEach(float priceEach) { this.priceEach = priceEach; }
    public float getPriceEach() { return priceEach; }

    public void setIdPizza(Entities.Pizza pizza) { this.pizza = pizza; }
    public Entities.Pizza getIdPizza() { return pizza; }

    public void setIdOrder(Entities.Order order) { this.order = order; }
    public Entities.Order getIdOrder() { return order; }

    @Override
    public String toString() {
        return "OrdersDetail{" +
                "quantity=" + quantity +
                ", priceEach=" + priceEach +
                ", pizza=" + pizza +
                '}';
    }
}
