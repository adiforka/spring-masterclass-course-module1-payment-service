package com.adison.shop.payments;

import com.adison.shop.common.FastMoneyUserType;
import lombok.*;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.TypeDef;
import org.javamoney.moneta.FastMoney;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

//set up custom mapped type, with a default for type for target class mapped
@TypeDef(name = "fastMoney", typeClass = FastMoneyUserType.class, defaultForType = FastMoney.class)
//putting an index on the PaymentStatus column make status-related queries faster
@Table(name = "payments", indexes = @Index(name = "payment_status", columnList = "status"))
@Entity
//hibernate needs constructor
@NoArgsConstructor
@AllArgsConstructor
//need to exclude id from equals/hashcode to make hibernate happy in terms of its reqs for object equality
@EqualsAndHashCode(exclude = "id")
@Data
@Builder
public class Payment {

    @Id
    private String id;
    //custom type needs to be mapped with a custom class implementing Hibernate's CompositeUserType interface
    @Columns(columns = {
            @Column(name = "currency", length = 3),
            @Column(name = "value", length = 15)
    })
    private FastMoney money;
    //hibernate picks the new time API types automatically
    private Instant timestamp;
    @Enumerated(value = EnumType.STRING)
    private PaymentStatus status;
}
