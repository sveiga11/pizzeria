package Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table( name = "contains" )
public class Contains {
    @EmbeddedId
    private ContainsID containsID;

    @Transient
    Ingredient ingredient;

    @Transient
    Pizza pizza;

    public Contains(Ingredient ingredient, Pizza pizza) {
        super();
        this.ingredient = ingredient;
        this.pizza = pizza;
        containsID = new ContainsID(ingredient.getIdIngredient(), pizza.getIdPizza());
    }

    public Contains() {
        super();
    }

    public void setIngredient(Ingredient ingredient) { this.ingredient = ingredient; }
    public Ingredient getIngredient() { return ingredient; }

    public void setPizza(Pizza pizza) { this.pizza = pizza; }
    public Pizza getPizza() { return pizza; }

    @Override
    public String toString() {
        return "Contains{" +
                "ingredient=" + ingredient +
                ", pizza=" + pizza +
                '}';
    }
}


