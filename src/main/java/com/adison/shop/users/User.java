package com.adison.shop.users;

import com.adison.shop.common.validator.Name;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Table(name = "users", indexes = @Index(name = "email", columnList = "email"))
@Entity
@Builder
@Data
@EqualsAndHashCode(exclude = "id")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;
    @Pattern(regexp = "[A-Za-z]+")
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Name
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    //used with user activation
    private boolean enabled;

}
