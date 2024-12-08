package org.spring.datingsite.data;

import org.spring.datingsite.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    Page<User> findAll(Specification<User> specification, Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.email = :email and u.id != :id")
    Optional<User> isEmailInUse(@Param("email") String email, @Param("id") UUID id);

    @Query("select u from User u where u.phoneNumber = :phoneNumber and u.id != :id")
    Optional<User> isPhoneNumberInUse(@Param("phoneNumber") String phoneNumber, @Param("id") UUID id);
}
