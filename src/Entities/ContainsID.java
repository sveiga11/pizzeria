package Entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ContainsID implements Serializable {

    @Column(name = "idIngredient")
    int idIngredient;

    @Column(name = "idPizza")
    int idPizza;

    public ContainsID() {

    }

    public ContainsID(int idIngredient, int idPizza) {
        this.idPizza = idPizza;
        this.idIngredient = idIngredient;
    }

    public int getIdPizza() {
        return idPizza;
    }

    public int getIdIngredient() {
        return idIngredient;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdPizza(), getIdIngredient());
    }
}
