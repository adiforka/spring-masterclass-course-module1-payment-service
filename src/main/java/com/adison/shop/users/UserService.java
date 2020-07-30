package com.adison.shop.users;

import com.adison.shop.common.PagedResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;
import java.util.List;

@Log
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User add(User user) {
        return userRepository.save(user);
    }

    public User getById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public PagedResult<User> getByLastName(String lastNameFragment, int pageNumber, int pageSize) {
        var usersPage = userRepository.findByLastNameContaining(lastNameFragment, PageRequest.of(pageNumber, pageSize));
        return new PagedResult<>(usersPage.getContent(), pageNumber, usersPage.getTotalPages());
    }

    public PagedResult<User> getAll(int pageNumber, int pageSize) {
        var usersPage = userRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return new PagedResult<>(usersPage.getContent(), pageNumber, usersPage.getTotalPages());
    }
}
