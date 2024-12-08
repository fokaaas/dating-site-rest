package org.spring.datingsite.service;

import org.spring.datingsite.domain.User;
import org.spring.datingsite.web.dto.PageWithSort;
import org.spring.datingsite.web.dto.query.UserQueries;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface UserService {

    String signUp(User user);

    String signIn(String email, String password);

    Page<User> getAll(UserQueries queries, PageWithSort pageWithSort);

    User getById(UUID id);

    User update(User user);

    User delete(UUID id);
}
