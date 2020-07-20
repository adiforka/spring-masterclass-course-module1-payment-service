package com.adison.shop.orders;

import com.adison.shop.payments.LocalMoney;
import com.adison.shop.payments.Payment;
import com.adison.shop.products.Product;
import com.adison.shop.users.User;
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
    @GeneratedValue
    private Long id;
    @ManyToMany(fetch = FetchType.EAGER)
    @NotEmpty
    @NonNull
    private List<Product> products;
    @OneToOne(cascade = CascadeType.PERSIST)
    //normally validation only on top-object level. if you want nested objects (properties) validated, use @Valid
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
