package org.spring.datingsite.service.impl;

import lombok.RequiredArgsConstructor;
import org.spring.datingsite.data.UserRepository;
import org.spring.datingsite.domain.User;
import org.spring.datingsite.domain.specification.UserSpecification;
import org.spring.datingsite.exception.EntityAlreadyExistsException;
import org.spring.datingsite.exception.EntityNotFoundException;
import org.spring.datingsite.exception.InvalidPasswordException;
import org.spring.datingsite.security.JwtService;
import org.spring.datingsite.service.UserService;
import org.spring.datingsite.web.dto.PageWithSort;
import org.spring.datingsite.web.dto.query.UserQueries;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Override
    public String signUp(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EntityAlreadyExistsException("User", "email");
        }
        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new EntityAlreadyExistsException("User", "phone number");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return jwtService.createJwtToken(user);
    }

    @Override
    public String signIn(String email, String password) {
        var user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User", "email"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException();
        }
        return jwtService.createJwtToken(user);
    }


    @Override
    public Page<User> getAll(UserQueries queries, PageWithSort pageWithSort) {
        var specification = UserSpecification.filter(queries);
        String sortField = Optional.ofNullable(pageWithSort)
                .map(PageWithSort::getSortBy)
                .orElse("createdAt");

        Sort sort = Optional.ofNullable(pageWithSort)
                .map(PageWithSort::getSort)
                .filter("desc"::equalsIgnoreCase)
                .map(direction -> Sort.by(sortField).descending())
                .orElse(Sort.by(sortField).ascending());

        int page = Optional.ofNullable(pageWithSort)
                .map(PageWithSort::getPage)
                .orElse(0);

        int size = Optional.ofNullable(pageWithSort)
                .map(PageWithSort::getSize)
                .orElse(10);

        Pageable pageable = PageRequest.of(page, size, sort);
        return userRepository.findAll(specification, pageable);
    }

    @Override
    public User getById(UUID id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User", "id"));
    }

    @Override
    public User update(User user) {
        if (userRepository.isEmailInUse(user.getEmail(), user.getId()).isPresent()) {
            throw new EntityAlreadyExistsException("User", "email");
        }
        if (userRepository.isPhoneNumberInUse(user.getPhoneNumber(), user.getId()).isPresent()) {
            throw new EntityAlreadyExistsException("User", "phone number");
        }
        return userRepository.save(user);
    }

    @Override
    public User delete(UUID id) {
        var user = getById(id);
        userRepository.deleteById(id);
        return user;
    }
}
