package com.adison.shop.users;

import com.adison.shop.common.PagedResult;
import com.adison.shop.common.web.PagedResultTransferObject;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

//spring container will have a bean called UserMapper that we'll be able to inject into controllers
@Mapper(componentModel = "spring")
public interface UserMapper {

    //because we changed email to emailAddress on a field name, we need to provide tips for Mapstruct
    @Mapping(source = "emailAddress", target = "email")
    User toUser(UserTransferObject userTransferObject);

    @Mapping(source = "email", target = "emailAddress")
    UserTransferObject toUserTransferObject(User user);

    @IterableMapping(elementTargetType = UserTransferObject.class)
    List<UserTransferObject> toUserTransferObjects(List<User> users);

    PagedResultTransferObject<UserTransferObject> toUserTransferObjectsPage(PagedResult<User> usersPage);
}
