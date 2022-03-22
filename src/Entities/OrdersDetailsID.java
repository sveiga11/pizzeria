package Entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrdersDetailsID implements Serializable {

    @Column(name = "idpizza")
    int idPizza;

    @Column(name = "idorder")
    int idOrder;

    public OrdersDetailsID() {

    }
    public OrdersDetailsID(int idPizza, int idOrder) {
        this.idPizza = idPizza;
        this.idOrder = idOrder;
    }

    public int getIdPizza() {
        return idPizza;
    }

    public int getIdOrder() {
        return idOrder;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdPizza(), getIdOrder());
    }
}
