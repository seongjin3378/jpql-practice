package hello.jpql.dto;

import javax.persistence.*;

@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue
    private long id;
    private int OrderAmount;


    @Embedded
    private Address address;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private hello.jpql.dto.Product Product;


    public Product getProduct() {
        return Product;
    }

    public void setProduct(Product product) {
        Product = product;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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
