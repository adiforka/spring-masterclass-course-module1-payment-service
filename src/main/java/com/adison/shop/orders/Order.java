package com.adison.shop.orders;

import com.adison.shop.payments.LocalMoney;
import com.adison.shop.payments.Payment;
import com.adison.shop.products.Product;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.FastMoney;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class Order {

    private Long id;
    @NotEmpty
    @NonNull
    private List<Product> products;
    //normally validation only on top-object level. if you want nested objects (properties) validated, use @Valid
    @Valid
    private Payment payment;
    //good spot to calc total price for the order
    public FastMoney getTotalPrice() {
        return products.stream()
                .map(Product::getPrice)
                //actually there already is a zero method in the FM API but whatever
                .reduce(LocalMoney.zero(), FastMoney::add);
    }

}
