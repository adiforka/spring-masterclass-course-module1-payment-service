package com.adison.shop.users;

import com.adison.shop.common.PagedResult;

import com.adison.shop.common.TextSource;
import com.adison.shop.mails.MailService;
import com.adison.shop.tokens.Token;
import com.adison.shop.tokens.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Log
@Transactional
@RequiredArgsConstructor
public class UserService {

    private static final String ACTIVATION_EMAIL_TEMPLATE = "activation-email";
    private static final String TOKEN_KEY = "token";
    private static final String USER_ID_KEY = "userId";
    private static final String SUBJECT_KEY = "email.subject";

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final TextSource textSource;
    private final MailService mailService;

    @Value("defaultLanguage}")
    @Setter
    private String defaultLanguage;

    public User add(User user) {
        User savedUser = userRepository.save(user);
        /*MailMessage mailMessage = prepareConfirmationEmail(savedUser, ACTIVATION_EMAIL_TEMPLATE);
        mailService.send(mailMessage);*/
        return savedUser;
    }

    public void activateUser(Long userId, String tokenValue) {
        updateAccount(user -> user.setEnabled(true), userId, tokenValue);
    }

    //very interesting
    public void updateAccount(Consumer<User> updater, Long userId, String tokenValue) {
        Token token = tokenService.getToken(tokenValue);
        if (!userId.equals(token.getOwnerId())) {
            throw new IllegalStateException();
        }
        User user = getById(token.getOwnerId());
        updater.accept(user);
        userRepository.saveAndFlush(user);
        tokenService.deleteToken(token);
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

    /*private MailMessage prepareConfirmationEmail(User user, String templateName) {
        Token token = tokenService.createToken(user.getId());
        Map<String, Object> variables = Map.of(TOKEN_KEY, token.getValue(), USER_ID_KEY, user.getId());
        String subject = textSource.getText(SUBJECT_KEY, defaultLanguage);
        String text = textSource.getTextFromTemplate(templateName, variables, defaultLanguage);
        return new MailMessage(user.getEmail(), subject, text);
    }*/
}
