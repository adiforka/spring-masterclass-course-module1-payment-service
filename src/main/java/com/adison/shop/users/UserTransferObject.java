package com.adison.shop.users;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UserTransferObject {

    //we choose what fields we want visible to the client. we can also change field names
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @Email
    private String emailAddress;
}
