package com.adison.shop.users;

import lombok.Data;

@Data
public class UserTransferObject {

    //we choose what fields we want visible to the client. we can also change field names
    private String firstName;
    private String lastName;
    private String emailAddress;
}
