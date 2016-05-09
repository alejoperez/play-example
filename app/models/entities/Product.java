package models.entities;

import javax.persistence.*;


@NamedQueries({
        @NamedQuery(
                name = Product.FIND_ALL,
                query = "FROM Product"
        ),
        @NamedQuery(
                name = Product.DELETE_BY_ID,
                query = "DELETE FROM Product p WHERE p.id=:"+Product.ID
        )
})
@Entity
public class Product {

    public static final String FIND_ALL = "Product.findAll";
    public static final String DELETE_BY_ID = "Product.deleteById";

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = NAME)
    private String name;

    @Column(name = DESCRIPTION)
    private String description;

    @Column(name = PRICE)
    private Double price;

    public Product() {
    }

    public Product(String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
