import Entities.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class ManagePizzeria {

    public static SessionFactory factory;

    public static void main(String[] args) throws IOException, ParseException {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        ManagePizzeria MA = new ManagePizzeria();

        // Leer y printar ingrediente
        FileAccessor fa = new FileAccessor();
        fa.readIngredientsFile("ingredients.txt");
        for (int i = 0; i < fa.llistaIngredients.size(); i++) {

            System.out.println(fa.llistaIngredients.get(i).toString());
            MA.addIngredient(fa.llistaIngredients.get(i));

        }
        MA.updateIngredient(7, "Atún");
        MA.deleteIngredient(4);

        // Leer y printar pizzas
        fa.readPizzasFile("pizzas.txt");
        for (int i = 0; i < fa.llistaPizzes.size(); i++) {

            System.out.println(fa.llistaPizzes.get(i).toString());
            MA.addPizza(fa.llistaPizzes.get(i));

        }
        fa.printPizzas();
        MA.updatePizza(7, "Parmesana", 12.99f);
        MA.deletePizza(4);

        // Leer y printar customers
        fa.readCustomersFile("customers.txt");
        fa.readOrdersFile("orders.txt");
        fa.readOrdersDetailsFile("ordersdetails.txt");
        for (int i = 0; i < fa.llistaCustomers.size(); i++) {

            System.out.println(fa.llistaCustomers.get(i).toString());
            MA.addCustomer(fa.llistaCustomers.get(i));

        }
        for (int i = 0; i < fa.llistaCustomers.size(); i++) {
            for (int j = 0; j < fa.llistaCustomers.get(i).getOrders().size(); j++) {
                for (int k = 0; k < fa.llistaCustomers.get(i).getOrder(j).getOrdersdetails().size(); k++) {
                    MA.addOrdersDetails(fa.llistaCustomers.get(i).getOrder(j).getOrderDetail(k));
                }
            }
        }
        fa.printCustomers();
        MA.updateCustomer(1, "Manolo", "C/España", "manoloeldelbombo@sefutbol.es,", "634888555");
        MA.deleteCustomer(3);

        java.sql.Date orderDate = java.sql.Date.valueOf("2022-03-21");
        MA.updateOrder(4, orderDate);
        MA.deleteOrder(9);

        MA.updateOrderDetail(fa.llistaPizzes.get(4),fa.llistaCustomers.get(1).getOrder(1),80);
        MA.deleteOrderDetail(fa.llistaPizzes.get(fa.llistaPizzes.size()-1), fa.llistaCustomers.get(5).getOrder(0));

        fa.readContainsFile("contains.txt");
        System.out.println("Contains llegit des del fitxer");
        for (int i = 0; i < fa.llistaContains.size(); i++) {

            System.out.println(fa.llistaContains.get(i).toString());
            MA.addContains(fa.llistaContains.get(i));

        }
        fa.printContains();
        MA.updateContains(fa.llistaIngredients.get(4), fa.llistaPizzes.get(2), fa.llistaIngredients.get(fa.llistaIngredients.size()-1));
        MA.deleteContains(fa.llistaIngredients.get(fa.llistaIngredients.size()-1), fa.llistaPizzes.get(fa.llistaPizzes.size()-1));
    }
    public void addPizza(Pizza pizza) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer PizzaID = null;
        try {
            tx = session.beginTransaction();

            PizzaID = (Integer) session.save(pizza);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    //Update Pizza
    public void updatePizza(Integer PizzaID, String name, float price) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Pizza pizza = (Pizza) session.get(Pizza.class, PizzaID);
            pizza.setName(name);
            pizza.setPrice(price);
            session.update(pizza);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    //delete pizzas
    public void deletePizza(Integer PizzaID) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Pizza pizza = (Pizza) session.get(Pizza.class, PizzaID);
            session.delete(pizza);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void addIngredient(Ingredient ingredient) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer IngredientID = null;
        try {
            tx = session.beginTransaction();

            IngredientID = (Integer) session.save(ingredient);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    //Update Ingredient
    public void updateIngredient(Integer IngredientID, String name) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Ingredient ingredient = (Ingredient) session.get(Ingredient.class, IngredientID);
            ingredient.setName(name);
            session.update(ingredient);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    //delete Ingredient
    public void deleteIngredient(Integer IngredientID) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Ingredient ingredient = (Ingredient) session.get(Ingredient.class, IngredientID);
            session.delete(ingredient);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void addContains(Contains contains) {
        Session session = factory.openSession();
        Transaction tx = null;
        ContainsID ContainsID = null;
        try {
            tx = session.beginTransaction();

            ContainsID = (Entities.ContainsID) session.save(contains);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    //Update Contains
    public void updateContains(Ingredient ingredient, Pizza pizza, Ingredient ingredientA) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Contains contains = (Contains) session.get(Contains.class, new ContainsID(ingredient.getIdIngredient(), pizza.getIdPizza()));
            contains.setIngredient(ingredientA);
            session.update(contains);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    //delete Contains
    public void deleteContains(Ingredient ingredient, Pizza pizza) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Contains contains = (Contains) session.get(Contains.class, new ContainsID(ingredient.getIdIngredient(), pizza.getIdPizza()));
            session.delete(contains);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void addCustomer(Customer customer) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer CustomerID = null;
        try {
            tx = session.beginTransaction();

            CustomerID = (Integer) session.save(customer);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    //Update Customer
    public void updateCustomer(Integer CustomerID, String name, String address, String email, String phone) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Customer customer = (Customer) session.get(Customer.class, CustomerID);
            customer.setName(name);
            customer.setAddress(address);
            customer.setEmail(email);
            customer.setPhone(phone);
            session.update(customer);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    //delete Customer
    public void deleteCustomer(Integer CustomerID) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Customer customer = (Customer) session.get(Customer.class, CustomerID);
            session.delete(customer);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    //Update Order
    public void updateOrder(Integer OrderID, Date orderDate) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Order order = (Order) session.get(Order.class, OrderID);
            order.setOrderDate(orderDate);
            session.update(order);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    //delete Order
    public void deleteOrder(Integer OrderID) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Order order = (Order) session.get(Order.class, OrderID);
            session.delete(order);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void addOrdersDetails(OrdersDetail ordersDetail) {
        Session session = factory.openSession();
        Transaction tx = null;
        OrdersDetailsID OrdersDetailsID = null;
        try {
            tx = session.beginTransaction();

            OrdersDetailsID = (Entities.OrdersDetailsID) session.save(ordersDetail);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    //Update OrderDetail
    public void updateOrderDetail(Pizza pizza, Order order, Integer cantidad) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            OrdersDetail ordersDetail = (OrdersDetail) session.get(OrdersDetail.class, new OrdersDetailsID(pizza.getIdPizza(), order.getIdOrder()));
            ordersDetail.setQuantity(cantidad);
            session.update(ordersDetail);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    //delete OrderDetail
    public void deleteOrderDetail(Pizza pizza, Order order) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            OrdersDetail ordersDetail = (OrdersDetail) session.get(OrdersDetail.class, new OrdersDetailsID(pizza.getIdPizza(), order.getIdOrder()));
            session.delete(ordersDetail);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
