package org.happinessmeta.last.auth.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<BasicUser, Long> {
    Optional<BasicUser> findByEmail(String email);
}
