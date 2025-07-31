package hello.jpql;

import javax.persistence.*;

@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue
    private long id;
    private int OrderAmount;


    @Embedded
    private Address Address;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product Product;


    public hello.jpql.Product getProduct() {
        return Product;
    }

    public void setProduct(hello.jpql.Product product) {
        Product = product;
    }

    public hello.jpql.Address getAddress() {
        return Address;
    }

    public void setAddress(hello.jpql.Address address) {
        Address = address;
    }

    public int getOrderAmount() {
        return OrderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        OrderAmount = orderAmount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
