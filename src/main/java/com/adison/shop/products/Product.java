package com.adison.shop.products;

import com.adison.shop.common.FastMoneyUserType;
import lombok.*;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.TypeDef;
import org.javamoney.moneta.FastMoney;

import javax.persistence.*;

//for named queries that go through some
@NamedQuery(name = "selectProducts", query = "select p from Product p")
@TypeDef(name = "fastMoney", typeClass = FastMoneyUserType.class, defaultForType = FastMoney.class)
@Table(name = "products", indexes = @Index(name = "product_type", columnList = "type"))
@Entity
@EqualsAndHashCode(exclude = "id")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    // to avoid typos with named queries, store query name in a constant
    public static final String SELECT_PRODUCTS = "selectProducts";

    @Id
    @GeneratedValue
    @Column(updatable = false)
    private Long id;
    private String name;
    private String description;
    @Columns(columns = {
            @Column(name = "currency"),
            @Column(name = "amount")
    })
    private FastMoney price;
    @Enumerated(value = EnumType.STRING)
    private ProductType type;
}
