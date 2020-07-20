package com.adison.shop.users;

import com.adison.shop.orders.Order;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Table(name = "users", indexes = @Index(name = "email", columnList = "email"))
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
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email", unique = true, nullable = false)
    private String email;

}
