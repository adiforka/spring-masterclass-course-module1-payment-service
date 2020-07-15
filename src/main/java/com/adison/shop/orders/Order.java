package com.adison.shop.orders;

import com.adison.shop.payments.LocalMoney;
import com.adison.shop.payments.Payment;
import com.adison.shop.products.Product;
import lombok.*;
import org.javamoney.moneta.FastMoney;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.List;

@Table(name = "orders")
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Data
public class Order {

    @Id
    private Long id;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "order_product", joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    @NotEmpty
    @NonNull
    private List<Product> products;
    //normally validation only on top-object level. if you want nested objects (properties) validated, use @Valid
    @OneToOne(cascade = CascadeType.PERSIST)
    @Valid
    private Payment payment;
    private Instant timestamp;


    //good spot to calc total price for the order
    public FastMoney getTotalPrice() {
        return products.stream()
                .map(Product::getPrice)
                //actually there already is a zero method in the FM API but whatever
                .reduce(LocalMoney.zero(), FastMoney::add);
    }

}
