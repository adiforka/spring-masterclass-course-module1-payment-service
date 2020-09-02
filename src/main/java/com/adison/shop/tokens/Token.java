package com.adison.shop.tokens;

import lombok.*;

import javax.persistence.*;

@Table(name = "tokens", indexes = {@Index(name = "token_value_index", columnList = "value", unique = true)})
@Entity
@Data
@EqualsAndHashCode(exclude = "id")
@RequiredArgsConstructor
@NoArgsConstructor
public class Token {

    @GeneratedValue
    @Id
    private Long id;
    @Column(unique = true)
    @NonNull
    private String value;
    @Column(name = "owner_id", nullable = false)
    @NonNull
    private Long ownerId;
}
