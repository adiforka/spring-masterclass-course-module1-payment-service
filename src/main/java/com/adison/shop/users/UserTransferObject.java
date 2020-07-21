package com.adison.shop.users;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UserTransferObject extends RepresentationModel<UserTransferObject> {

    //we choose what fields we want visible to the client. we can also change field names
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @Email
    private String emailAddress;
}
