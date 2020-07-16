package com.adison.shop.users;

import com.adison.shop.orders.Order;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Table(name = "users")
@Entity
@Data
@EqualsAndHashCode(exclude = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_orders", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<Order> orders;
}
