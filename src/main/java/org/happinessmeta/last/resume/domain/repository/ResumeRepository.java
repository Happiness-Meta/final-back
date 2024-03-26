package org.happinessmeta.last.resume.domain.repository;

import org.happinessmeta.last.resume.domain.entity.Resume;
import org.happinessmeta.last.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    Optional<Resume> findByUser(User user);

    @Modifying
    @Query("DELETE FROM Resume r WHERE r.user = :user")
    void deleteByUser(User user);
}
